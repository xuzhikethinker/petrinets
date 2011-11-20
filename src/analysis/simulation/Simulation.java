package analysis.simulation;

import analysis.tree.Marking;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import model.Graph;
import model.Transition;

/**
 * Controller of simulation process.
 * @author Aloren
 */
public class Simulation {

    private Graph g;
    private double Tmod = 0;
    private List<ControlListRecord> controlList = new ArrayList<ControlListRecord>();
    private Map<Double, Transition> TransitionTimeStack = new TreeMap<Double, Transition>(new TimedTransitionComparator());
    private Set<SimulationState> SimulationStates = new TreeSet<SimulationState>();
    private List<String> markingLabels = new ArrayList<String>();
    private MarkingMatrix markingMatrix = new MarkingMatrix();
    private String prevMarking;
    private boolean changePrev = true;
    private int steps = 0;

    public Simulation(Graph g) {
        this.g = g;
    }

    /**
     * Performs next iteration of the simulation.
     * @return
     * @throws NullPointerException if no flows to transitions are set.
     */
    public boolean next() throws NullPointerException {
        Marking curM = new Marking(g.getChips(), g.getDr(), g.getDi(), g.getTransitions());
        ArrayList<Transition> allowedTrans = g.getAllowedTransitions();
        Marking.removeTimedTransitions(allowedTrans);
        if (!allowedTrans.isEmpty()) {
            Transition wonTransition = null;
            double minTime;
            Double Tlast = 0.0;
            boolean instant = false;
            if (!allowedTrans.get(0).isReal()) {
                wonTransition = allowedTrans.get(new Random().nextInt(allowedTrans.size()));
                minTime = getTmod();
                instant = true;
            } else {
                // это реальные переходы
                steps++;
                ArrayList<Transition> wonConflictAndOther = resolveConflict(allowedTrans);
                if (!wonConflictAndOther.isEmpty()) {
                    Object[] ar = getMinTime(wonConflictAndOther, wonTransition);
                    minTime = (Double) ar[0];
                    wonTransition = (Transition) ar[1];
                } else {
                    Object[] ar = getMinTimeFromStack(wonTransition);
                    minTime = (Double) ar[0];
                    wonTransition = (Transition) ar[1];

                }
            }
            if (!instant) {
                if (!markingLabels.contains(curM.toString())) {
                    markingLabels.add(curM.toString());
                }
                if (changePrev) {
                    prevMarking = curM.toString();
                }
                Tlast = new Double(getTmod());
                Tmod = minTime;
                updateControlList(curM, Tlast);
                boolean found = updateMarkings(Tlast, curM);
                if (!found) {
                    Double prob = (getTmod() - Tlast) / getTmod();
                    SimulationState s = new SimulationState(curM.toString(), Tlast, getTmod() - Tlast, prob, 1);
                    SimulationStates.add(s);
                }
            }
            Marking nextM = curM.buildNextMarking(curM.getChips(), Marking.buildVecOfAllow(wonTransition, g.getTransitions().size()), 0, null);
            g.setMarking(nextM.getChips());


            if (!instant) {
                controlList.add(new ControlListRecord(wonTransition.getNameWithType(), Tlast, ControlListRecord.TYPE.LAUNCH, "Планирование срабатывания в " + getTmod(), prevMarking, "-"));
                controlList.add(new ControlListRecord(wonTransition.getNameWithType(), getTmod(), ControlListRecord.TYPE.FIRE, "Срабатывание перехода", prevMarking, Arrays.toString(nextM.getChips())));
                Collections.sort(controlList);

                if (!nextM.isInstant()) {
                    if (!markingLabels.contains(nextM.toString())) {
                        markingLabels.add(nextM.toString());
                    }
                    markingMatrix.inc(markingLabels.indexOf(curM.toString()), markingLabels.indexOf(nextM.toString()));
                    changePrev = true;
                } else {
                    changePrev = false;
                }
            } else {
                if (!nextM.isInstant()) {
                    if (!markingLabels.contains(nextM.toString())) {
                        markingLabels.add(nextM.toString());
                    }
                    if (prevMarking == null) {
                        prevMarking = nextM.toString();
                        changePrev = false;
                    } else {
                        markingMatrix.inc(markingLabels.indexOf(prevMarking), markingLabels.indexOf(nextM.toString()));
                        changePrev = true;
                    }
                } else {
                    changePrev = false;
                }
            }
            return true;
        } else {
            //terminal
            return false;
        }
    }

    private void updateControlList(Marking curM, double Tlast) {
        Iterator it1 = TransitionTimeStack.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pairs = (Map.Entry) it1.next();
            controlList.add(new ControlListRecord(((Transition) pairs.getValue()).getNameWithType(), Tlast, ControlListRecord.TYPE.LAUNCH, "Планирование срабатывания в " + pairs.getKey(), Arrays.toString(curM.getChips()), "-"));
            Collections.sort(controlList);
        }
    }

    public ArrayList<Transition> resolveConflict(ArrayList<Transition> allowedTrans) {
        ArrayList<ArrayList<Transition>> ConflictGroups = new ArrayList<ArrayList<Transition>>();
        //находим конфликтующие переходы
        for (int i = 0; i < g.getPlaces().size(); i++) {
            ArrayList<Transition> group = Graph.getConflicting(g.getPlaces().get(i), allowedTrans);
            if (!group.isEmpty()) {
                //    output.append("Conflicting group" + newline);
                //   for (int j = 0; j < group.size(); j++) {
                //       output.append(group.get(j).getName()).append(" ");
                //    }
                //    output.append(newline);
                //удаляем конфликтующую группу из разрешенных
                allowedTrans.removeAll(group);
                ConflictGroups.add(group);
            }
        }

        //block group transition of which is already in stack
        //  output.append("Blocking already in stack" + newline);
        ArrayList<ArrayList<Transition>> toDel = new ArrayList<ArrayList<Transition>>();
        for (int i = 0; i < ConflictGroups.size(); i++) {
            if (blockAlreadyInStack(ConflictGroups.get(i))) {
                toDel.add(ConflictGroups.get(i));
            }
        }
        ConflictGroups.removeAll(toDel);

        //choose transition from each conflicting group depending on its probability
        Random a = new Random();
        ArrayList<Transition> FiringTransitions = new ArrayList<Transition>();
        for (int i = 0; i < ConflictGroups.size(); i++) {
            //создаём случайное число
            double random = a.nextDouble();
            // output.append("random = " + random + newline);
            //сортируем переходы в этой конфликтующей группе относительно их вероятностей
            Collections.sort(ConflictGroups.get(i), new ProbabilityComparator());
            double next = 0.0;
            for (int j = 0; j < ConflictGroups.get(i).size(); j++) {
                double thisProb = ConflictGroups.get(i).get(j).getProbability();
                next += thisProb;
                // output.append(next + newline);
                if (next > random) {
                    //нашли срабатывающий переход
                    FiringTransitions.add(ConflictGroups.get(i).get(j));
                    //переходим на обработку следующей группы
                    break;
                } else if (next < random && j == ConflictGroups.get(i).size() - 1) {
                    //если же и для последнего перехода в конфл. группе 
                    //случ. число не меньше вероятности перехода, тогда 
                    //находим случайный переход из конфл. группы
                    //этом может случиться, когда пользователь неправильно 
                    //задаст вероятности или вообще их не задаст
                    int index = a.nextInt(ConflictGroups.get(i).size());
                    FiringTransitions.add(ConflictGroups.get(i).get(index));
                }
            }
        }
        allowedTrans.addAll(FiringTransitions);
        //   output.append("After resolving conflict situations found:" + newline);
        //    for (int i = 0; i < allowedTrans.size(); i++) {
        //        output.append(allowedTrans.get(i).getName()).append(" ");
        //    }
        //    output.append(newline);
        return allowedTrans;
    }

    private boolean blockAlreadyInStack(ArrayList<Transition> allowedTrans) {
        boolean found = false;
        for (int i = 0; i < allowedTrans.size() && !found; i++) {
            Transition transition = allowedTrans.get(i);
            Iterator it1 = TransitionTimeStack.entrySet().iterator();
            while (it1.hasNext() && !found) {
                Map.Entry pairs = (Map.Entry) it1.next();
                if (pairs.getValue().equals(transition)) {
                    found = true;
                }
            }
        }
        return found;
    }

    private Object[] getMinTimeFromStack(Transition wonTransition) {
        //  output.append("All allowed transitions were blocked" + newline);
        Iterator it = TransitionTimeStack.entrySet().iterator();
        Map.Entry pairs = (Map.Entry) it.next();
        double minTime = (Double) pairs.getKey();
        wonTransition = (Transition) pairs.getValue();
        TransitionTimeStack.remove(minTime);
        return new Object[]{minTime, wonTransition};
    }

    private Object[] getMinTime(ArrayList<Transition> wonConflictAndOther, Transition wonTransition) {
        //  output.append("Generating times" + newline);
        double[] times = new double[wonConflictAndOther.size()];
        for (int i = 0; i < wonConflictAndOther.size(); i++) {
            times[i] = wonConflictAndOther.get(i).getGenerator().next() + getTmod();
            //   times[i] = new Random().nextDouble() + Tmod;
            //     output.append(times[i]).append(" ");
        }
        //     output.append(newline);
        //     output.append("Searching for win in this marking" + newline);
        double minTime = times[0];
        int firingIndex = 0;
        for (int i = 1; i < times.length; i++) {
            if (times[i] < minTime) {
                minTime = times[i];
                firingIndex = i;
            }
        }
        //     output.append("firingIndex=").append(firingIndex).append(" = ").append(wonConflictAndOther.get(firingIndex).getName()).append(newline);
        wonTransition = wonConflictAndOther.get(firingIndex);
        boolean found = false;
        Iterator it = TransitionTimeStack.entrySet().iterator();
        while (it.hasNext() && !found) {
            Map.Entry pairs = (Map.Entry) it.next();
            if ((Double) pairs.getKey() < (minTime)) {
                //             output.append("found less time ");
                minTime = (Double) pairs.getKey();
                wonTransition = (Transition) pairs.getValue();
                TransitionTimeStack.remove(minTime);
                //             output.append(minTime).append(" ").append(wonTransition.getName()).append(newline);
                found = true;
            }
        }
        for (int i = 0; i < wonConflictAndOther.size(); i++) {
            if (found) {
                TransitionTimeStack.put(times[i], wonConflictAndOther.get(i));
            } else {
                if (!wonTransition.equals(wonConflictAndOther.get(i))) {
                    TransitionTimeStack.put(times[i], wonConflictAndOther.get(i));
                }
            }
        }
        return new Object[]{minTime, wonTransition};
    }

    private boolean updateMarkings(double Tlast, Marking curM) {
        boolean found = false;
        for (int i = 0; i < SimulationStates.size(); i++) {
            SimulationState s = ((SimulationState) SimulationStates.toArray()[i]);

            if (s.getMarking().equals(curM.toString())) {
                Double newTime = s.getTimeStay() + getTmod() - Tlast;
                s.setFreq(s.getFreq() + 1);
                s.setProbability(newTime / getTmod());
                s.setTimeStay(newTime);
                s.setTimeReturn((s.getTimeReturn() + (getTmod() - s.getPrevTime())));
                s.setPrevTime(getTmod());
                found = true;
            } else {
                s.setProbability(s.getTimeStay() / getTmod());
            }
        }
        return found;
    }

    /**
     * Returns all markings involved in simulation process.
     * @return the simStates
     */
    public Set<SimulationState> getSimStates() {
        return SimulationStates;
    }

    /**
     * Returns control list of simulation.
     * @return the controlList
     */
    public List<ControlListRecord> getControlList() {
        return controlList;
    }

    /**
     * @return the markings
     */
    public List<String> getMarkings() {
        return markingLabels;
    }

    /**
     * @return the markingMatrix
     */
    public MarkingMatrix getMarkingMatrix() {
        return markingMatrix;
    }

    /**
     * @return the steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * @return the Tmod
     */
    public double getTmod() {
        return Tmod;
    }
}
