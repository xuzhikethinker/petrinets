package actions;

import analysis.simulation.AllInstantTransitionException;
import analysis.simulation.NoCyclesException;
import analysis.simulation.Simulation;
import analysis.simulation.visual.SimulationFrame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import main.Main;
import main.visual.DrawPanel;
import utils.DIMENSIONS;

/**
 *
 * @author Aloren
 */
public class PlayAction extends AbstractAction{

    public void actionPerformed(ActionEvent e) {
                try {
            if (((DrawPanel) Main.drawPanel).getGraph().allInstant()) {
                throw new AllInstantTransitionException();
            }
            String text = JOptionPane.showInputDialog("Input number of cycles", "0");
            if (text != null) {
                int n = Integer.valueOf(text);
                if (n == 0 || n < 0) {
                    throw new NoCyclesException();
                }
                SimulationModePerformed(n);
            }
        } catch (AllInstantTransitionException ex1) {
            JOptionPane.showMessageDialog(null, "All transitions are instant. Simulation mode is not possible.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (NoCyclesException ex2) {
            JOptionPane.showMessageDialog(null, "No cycles are set.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex3) {
            JOptionPane.showMessageDialog(null, "Bad format.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            ex3.printStackTrace();
        }
    }
    
        private void SimulationModePerformed(int n) {
        try {
            Integer[] startChips = ((DrawPanel) Main.drawPanel).getGraph().getChips();
            Simulation s = new Simulation(((DrawPanel) Main.drawPanel).getGraph()); //must be clone
            while (s.getSteps() < n) {
                if (!s.next()) {
                    break;
                }
            }
            ((DrawPanel) Main.drawPanel).repaint();
            SimulationFrame simAnalys = new SimulationFrame(s, ((DrawPanel) Main.drawPanel).getGraph(), startChips);
            DIMENSIONS.fullScreen(simAnalys);
            simAnalys.setVisible(true);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "No flows are set.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException ex1) {
            JOptionPane.showMessageDialog(null, "Petri nets is empty. Input more data.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(ex1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error. Contact program developer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
    
}
