package analysis.tree.visual;

import analysis.simulation.MarkingMatrix;
import analysis.simulation.visual.MarkingToMarkingTableModel;
import analysis.tree.HistoryPanel;
import analysis.tree.MarkovLink;
import analysis.tree.TYPE;
import analysis.tree.Marking;
import analysis.tree.Tree;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
import edu.uci.ics.jung.visualization.picking.PickedInfo;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.CenterEdgeArrowRenderingSupport;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.apache.commons.collections15.functors.ChainedTransformer;
import constants.GraphMarkovColors;
import utils.Utils;

/**
 *
 * @author Aloren
 */
public class MarkovGraphPanel extends JPanel {

    public static final Dimension THIS_DIM = Toolkit.getDefaultToolkit().getScreenSize();
    public static int SHAPE_SIZE = 40;
    protected Graph graph = new DirectedSparseMultigraph<Marking, MarkovLink>();
    final VisualizationViewer<Marking, MarkovLink> vv;
    List<String> markingLabels;
    MarkingMatrix markingMatrix;

    /**
     * 
     * @param type строим только реальные или и мгновенные вершины тоже
     * @param startChips начальная маркировка для построения дерева, на к-м основано построение графа Маркова
     * @param DrawGraph граф
     * @param markingLabels возможно null, если работаем не с симуляцией
     * @param markingMatrix возможно null, если работаем не с симуляцией
     * @param paintInside рисовать ли переходы в самого себя
     */
    public MarkovGraphPanel(String type, Integer[] startChips, model.Graph DrawGraph, List<String> markingLabels, MarkingMatrix markingMatrix, boolean paintInside) {
        super();
        setSize(MarkovGraphPanel.THIS_DIM);
        this.markingLabels = markingLabels;
        this.markingMatrix = markingMatrix;
        fillGraph(Tree.buildTree(type, startChips, DrawGraph), paintInside);
        vv = new VisualizationViewer<Marking, MarkovLink>(new FRLayout(graph), THIS_DIM);
        vv.setDoubleBuffered(true);
        decorateVV();
        getGraphPanel();
        GraphZoomScrollPane scroll = new GraphZoomScrollPane(vv);
        add(scroll, BorderLayout.CENTER);
    }

    protected void decorateVV() {
        PickedState<Marking> picked_state = vv.getPickedVertexState();
        PickedState<MarkovLink> picked_edge = vv.getPickedEdgeState();

        Transformer<MarkovLink, String> edgeToStringTransformer = new Transformer<MarkovLink, String>() {

            public String transform(MarkovLink link) {
                if (markingLabels == null || markingMatrix == null) {
                    return link.toString();
                } else {
                    return link.toString() + " " + getProbabilityForLink(link);
                }
            }
        };
        Transformer<MarkovLink, Paint> markovLinkToColorTransformer = new Transformer<MarkovLink, Paint>() {

            public Paint transform(MarkovLink i) {
                return GraphMarkovColors.EDGE_COLOR;
            }
        };
        Transformer<Marking, String> markingToStringTransformer = new Transformer<Marking, String>() {

            public String transform(Marking m) {
                return m.toString();
                //return "S".concat(Integer.toString(allMarkings.indexOf(m)));
            }
        };


        vv.getRenderContext().setVertexStrokeTransformer(new VertexStrokeHighlight<Marking, MarkovLink>(graph, picked_state));
        vv.getRenderContext().setEdgeDrawPaintTransformer(markovLinkToColorTransformer);
        vv.getRenderContext().setEdgeStrokeTransformer(new EdgeWeightStrokeFunction<MarkovLink>(graph, picked_edge));

        vv.getRenderContext().setArrowDrawPaintTransformer(markovLinkToColorTransformer);
        vv.getRenderContext().setArrowFillPaintTransformer(markovLinkToColorTransformer);

        vv.getRenderContext().setEdgeLabelTransformer(edgeToStringTransformer);
        vv.getRenderContext().setVertexLabelTransformer(markingToStringTransformer);

        vv.getRenderer().getEdgeRenderer().setEdgeArrowRenderingSupport(new CenterEdgeArrowRenderingSupport());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexFillPaintTransformer(new TreeFrame.MarkingToColorTransformer(vv));
        vv.setVertexToolTipTransformer(
                new ChainedTransformer<Marking, String>(new Transformer[]{
                    new MarkingTips<Marking>(),
                    new Transformer<String, String>() {

                        public String transform(String input) {
                            return "<html><left>Vertex " + input;
                        }
                    }}));

        vv.getRenderContext().setVertexShapeTransformer(new VertexShapeAspect<Marking, MarkovLink>(graph));
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Marking, MarkovLink>());
        vv.setBackground(Color.white);
    }

    protected void fillGraph(Marking tree, final boolean paintInside) {
        ArrayList<Marking> allMarkings = createAllStates(tree);
        for (int i = 0; i < allMarkings.size(); i++) {
            graph.addVertex(allMarkings.get(i));
        }
        for (int i = 0; i < allMarkings.size(); i++) {
            for (int j = 0; j < allMarkings.get(i).getTransForMarkov().size(); j++) {
                if (paintInside) {
                    graph.addEdge(allMarkings.get(i).getTransForMarkov().get(j),
                            allMarkings.get(i).getTransForMarkov().get(j).getParent(),
                            allMarkings.get(i).getTransForMarkov().get(j).getChild(), EdgeType.DIRECTED);
                } else {
                    if (!allMarkings.get(i).getTransForMarkov().get(j).getParent().equals(allMarkings.get(i).getTransForMarkov().get(j).getChild())) {
                        graph.addEdge(allMarkings.get(i).getTransForMarkov().get(j),
                                allMarkings.get(i).getTransForMarkov().get(j).getParent(),
                                allMarkings.get(i).getTransForMarkov().get(j).getChild(), EdgeType.DIRECTED);
                    }
                }
            }
        }
    }

    protected ArrayList<Marking> createAllStates(Marking tree) {
        ArrayList<Marking> allMarkings = new ArrayList<Marking>();
        allMarkings.add(tree);
        for (int i = 0; i < tree.getChildren().size(); i++) {
            if (!tree.getChildren().get(i).getType().equals(TYPE.InstantDup)
                    && !tree.getChildren().get(i).getType().equals(TYPE.RealDup)) {
                allMarkings.add(tree.getChildren().get(i));
                createChildStates(allMarkings, tree.getChildren().get(i));
            }
        }
        return allMarkings;
    }

    protected void createChildStates(ArrayList<Marking> allMarkings, Marking struct) {
        for (int i = 0; i < struct.getChildren().size(); i++) {
            if (!struct.getChildren().get(i).getType().equals(TYPE.InstantDup)
                    && !struct.getChildren().get(i).getType().equals(TYPE.RealDup)) {
                allMarkings.add(struct.getChildren().get(i));
                createChildStates(allMarkings, struct.getChildren().get(i));
            }
        }
    }

    private String getProbabilityForLink(MarkovLink link) {
        int i = markingLabels.indexOf(link.getParent().toString());
        int j = markingLabels.indexOf(link.getChild().toString());
        if (i != -1 && j != -1) {
            //  return Integer.toString(markingMatrix.get(i, j));
            return Double.toString(Utils.round(MarkingToMarkingTableModel.probabilities[i][j], 3));
        } else {
            return "???";
        }
    }

    protected void getGraphPanel() {
        final DefaultModalGraphMouse<Marking, MarkovLink> graphMouse = new DefaultModalGraphMouse<Marking, MarkovLink>();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(graphMouse);

        JComboBox modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener(((DefaultModalGraphMouse<Marking, MarkovLink>) vv.getGraphMouse()).getModeListener());

        setLayout(new BorderLayout());
        add(vv, BorderLayout.CENTER);
        Class[] combos = getCombos();
        final JComboBox jcb = new JComboBox(combos);
        // use a renderer to shorten the layout name presentation
        jcb.setRenderer(new DefaultListCellRenderer() {

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String valueString = value.toString();
                valueString = valueString.substring(valueString.lastIndexOf('.') + 1);
                return super.getListCellRendererComponent(list, valueString, index, isSelected,
                        cellHasFocus);
            }
        });
        jcb.addActionListener(new LayoutChooser(jcb, vv));
        jcb.setSelectedItem(FRLayout.class);
        JButton saveImage = Utils.getSaveImageButton(vv);
        JPanel control_panel = new JPanel(new GridLayout(2, 1));
        JPanel historyP = new HistoryPanel();
        JPanel topControls = new JPanel();
        topControls.add(jcb);
        topControls.add(modeBox);
        topControls.add(saveImage);
        control_panel.add(topControls);
        control_panel.add(historyP);
        add(control_panel, BorderLayout.NORTH);
    }

    public class MarkingTips<E> implements Transformer<Marking, String> {

        public String transform(Marking i) {
            return i.toStringForTreeOrMarkov("M");
        }
    }

    private final static class VertexStrokeHighlight<V, E> implements Transformer<V, Stroke> {

        protected boolean highlight = true;
        protected Stroke heavy = new BasicStroke(5);
        protected Stroke medium = new BasicStroke(3);
        protected Stroke light = new BasicStroke(0);
        protected PickedInfo<V> pi;
        protected Graph<V, E> graph;

        public VertexStrokeHighlight(Graph<V, E> graph, PickedInfo<V> pi) {
            this.graph = graph;
            this.pi = pi;
        }

        public void setHighlight(boolean highlight) {
            this.highlight = highlight;
        }

        public Stroke transform(V v) {
            if (highlight) {
                if (pi.isPicked(v)) {
                    return heavy;
                } else {
                    for (V w : graph.getNeighbors(v)) {
//                    for (Iterator iter = graph.getNeighbors(v)v.getNeighbors().iterator(); iter.hasNext(); )
//                    {
//                        Vertex w = (Vertex)iter.next();
                        if (pi.isPicked(w)) {
                            return medium;
                        }
                    }
                    return light;
                }
            } else {
                return light;
            }
        }
    }

    private final class VertexShapeAspect<Marking, E>
            extends AbstractVertexShapeTransformer<Marking>
            implements Transformer<Marking, Shape> {

        protected Graph<Marking, E> graph;

        public VertexShapeAspect(Graph<Marking, E> graphIn) {
            this.graph = graphIn;
            //   setSizeTransformer(new Transformer<Tree, Integer>() {

            //        public Integer transform(Tree v) {
            //           return SwingUtilities.computeStringWidth(getGraphics().getFontMetrics(), v.toString());
            //  return SHAPE_SIZE;
            //        }
            //    });
        }

        public Shape transform(Marking m) {
            int width = SwingUtilities.computeStringWidth(getGraphics().getFontMetrics(), m.toString());
            return new Rectangle2D.Double(-(width / 2), -(30 / 2), width, 30);
            //return factory.getRectangle(m);

        }
    }

    private final static class EdgeWeightStrokeFunction<E>
            implements Transformer<MarkovLink, Stroke> {

        protected static final Stroke basic = new BasicStroke(1);
        protected static final Stroke heavy = new BasicStroke(2);
        protected static final Stroke dotted = RenderContext.DOTTED;
        protected boolean highlight = true;
        protected Stroke medium = new BasicStroke(2);
        protected Stroke light = new BasicStroke(1);
        protected PickedInfo<MarkovLink> pi;
        protected Graph<Marking, MarkovLink> graph;

        public EdgeWeightStrokeFunction(Graph<Marking, MarkovLink> graph, PickedInfo<MarkovLink> pi) {
            this.graph = graph;
            this.pi = pi;
        }

        public Stroke transform(MarkovLink m) {
            if (pi.isPicked(m)) {
                return medium;
            } else {
                return light;
            }
        }
    }

    private final class LayoutChooser implements ActionListener {

        private final JComboBox jcb;
        private final VisualizationViewer<Marking, MarkovLink> vv;

        private LayoutChooser(JComboBox jcb, VisualizationViewer<Marking, MarkovLink> vv) {
            super();
            this.jcb = jcb;
            this.vv = vv;
        }

        public void actionPerformed(ActionEvent arg0) {

            Class<? extends Layout<Marking, MarkovLink>> layoutC =
                    (Class<? extends Layout<Marking, MarkovLink>>) jcb.getSelectedItem();
//            Class lay = layoutC;
            try {
                Constructor<? extends Layout<Marking, MarkovLink>> constructor = layoutC.getConstructor(new Class[]{Graph.class});
                Object o = constructor.newInstance(graph);
                Layout<Marking, MarkovLink> l = (Layout<Marking, MarkovLink>) o;
                l.setInitializer(vv.getGraphLayout());
                l.setSize(vv.getSize());

                LayoutTransition<Marking, MarkovLink> lt =
                        new LayoutTransition<Marking, MarkovLink>(vv, vv.getGraphLayout(), l);
                Animator animator = new Animator(lt);
                animator.start();

                vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
                vv.repaint();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends Layout>[] getCombos() {
        List<Class<? extends Layout>> layouts = new ArrayList<Class<? extends Layout>>();
        layouts.add(KKLayout.class);
        layouts.add(FRLayout.class);
        layouts.add(CircleLayout.class);
        layouts.add(SpringLayout.class);
        layouts.add(SpringLayout2.class);
        layouts.add(ISOMLayout.class);
        return layouts.toArray(new Class[0]);
    }
}
