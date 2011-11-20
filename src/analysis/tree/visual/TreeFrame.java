package analysis.tree.visual;

import analysis.tree.HistoryPanel;
import analysis.tree.Marking;
import analysis.tree.MarkovLink;
import analysis.tree.TYPE;
import analysis.tree.Tree;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformerDecorator;
import edu.uci.ics.jung.visualization.util.Animator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ChainedTransformer;
import constants.GraphMarkovColors;
import utils.ExtendedFileChooser;
import utils.Utils;

/**
 *
 * @author Aloren
 */
public class TreeFrame extends JFrame {

    public static final int SHAPE_SIZE = 40;
    public static final Dimension THIS_DIM = Toolkit.getDefaultToolkit().getScreenSize();
    VisualizationViewer<Marking, MarkovLink> vv;
    Forest<Marking, MarkovLink> forestGraph;
    ArrayList<Marking> allMarkings;
    VisualizationServer.Paintable rings;
    TreeLayout<Marking, MarkovLink> treeLayout;
    BalloonLayout<Marking, MarkovLink> radialLayout;
    Layout<Marking, MarkovLink> curLayout;
    model.Graph graph;
    Marking treeWithInst;
    Marking treeReal = null;
    Marking visibleM = null;
    int distx = 150;
    int disty = 100;

    public TreeFrame(final Marking tree, final model.Graph graph) {
        this.treeWithInst = tree;
        this.visibleM = treeWithInst;
        this.graph = graph;
        fillGraph(tree);
        treeLayout = new TreeLayout<Marking, MarkovLink>((Forest<Marking, MarkovLink>) forestGraph, distx, disty);
        curLayout = treeLayout;
        vv = new VisualizationViewer<Marking, MarkovLink>(curLayout, THIS_DIM);
        decorateVV();
        GraphZoomScrollPane scroll = new GraphZoomScrollPane(vv);
        JPanel jp = getGraphPanel();
        jp.add(scroll, BorderLayout.CENTER);
        add(jp);

    }

    protected void decorateVV() {
        radialLayout = new BalloonLayout<Marking, MarkovLink>(forestGraph);
        radialLayout.setSize(THIS_DIM);
        rings = new Rings(radialLayout);
        Transformer<Marking, String> markingToStringTransformer = new Transformer<Marking, String>() {

            public String transform(Marking m) {
                //return "S".concat(Integer.toString(allMarkings.indexOf(m)));
                return m.toString();
            }
        };

        Transformer<MarkovLink, String> linkToStringTransformer = new Transformer<MarkovLink, String>() {

            public String transform(MarkovLink link) {
                return link.getChild().getTransString();
            }
        };


        vv.setVertexToolTipTransformer(
                new ChainedTransformer<Marking, String>(new Transformer[]{
                    new MarkingTips<Marking>(),
                    new Transformer<String, String>() {

                        public String transform(String input) {
                            return "<html><left>Vertex " + input;
                        }
                    }}));

        vv.setBackground(Color.white);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexFillPaintTransformer(new MarkingToColorTransformer(vv));

        vv.getRenderContext().setVertexLabelTransformer(markingToStringTransformer);
        vv.getRenderContext().setEdgeLabelTransformer(linkToStringTransformer);
        vv.getRenderContext().setVertexShapeTransformer(new VertexShapeAspect<Marking, MarkovLink>(forestGraph));

    }

    protected JPanel getGraphPanel() {
        final DefaultModalGraphMouse<Marking, MarkovLink> graphMouse = new DefaultModalGraphMouse<Marking, MarkovLink>();
        vv.setGraphMouse(graphMouse);
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        JComboBox modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener(((DefaultModalGraphMouse<Marking, MarkovLink>) vv.getGraphMouse()).getModeListener());
        JButton saveToFile = new JButton("Save tree to file");
        saveToFile.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new ExtendedFileChooser(visibleM, ".csv");
                fc.setDialogTitle("Save as");
                if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Writing to file");
                }

            }
        });
        final JToggleButton baloonBut = new JToggleButton("Balloon");
        baloonBut.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    changeLayout(true);
                } else {
                    changeLayout(false);
                }
            }
        });
        JCheckBox onlyReal = new JCheckBox("Show only real");
        onlyReal.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                boolean onlyRealSelected = abstractButton.getModel().isSelected();
                if (onlyRealSelected) {
                    if (treeReal == null) {
                            Integer[] startChips = graph.getChips();
                            treeReal = Tree.buildTree("Real", startChips, graph);
                    }
                    visibleM = treeReal;
                } else {
                    visibleM = treeWithInst;
                }
                fillGraph(visibleM);
                changeLayout(baloonBut.isSelected());

            }
        });
        JButton saveImage = Utils.getSaveImageButton(vv);
        JPanel control_panel = new JPanel(new GridLayout(2, 1));
        JPanel controls = new JPanel();
        HistoryPanel historyP = new HistoryPanel();
        controls.add(saveToFile);
        controls.add(baloonBut);
        controls.add(modeBox);
        controls.add(onlyReal);
        controls.add(saveImage);
        control_panel.add(controls);
        control_panel.add(historyP);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(control_panel, BorderLayout.NORTH);
        return jp;
    }

    private void changeLayout(boolean baloonButSelected) {
        LayoutTransition<Marking, MarkovLink> lt;
        if (baloonButSelected) {
            vv.removePreRenderPaintable(rings);
            radialLayout = new BalloonLayout<Marking, MarkovLink>(forestGraph);
            radialLayout.setSize(THIS_DIM);
            rings = new Rings(radialLayout);
            lt = new LayoutTransition<Marking, MarkovLink>(vv, vv.getGraphLayout(), radialLayout);
            vv.addPreRenderPaintable(rings);
        } else {
            vv.removePreRenderPaintable(rings);
            treeLayout = new TreeLayout<Marking, MarkovLink>((Forest<Marking, MarkovLink>) forestGraph, distx, disty);
            treeLayout.setInitializer(vv.getGraphLayout());
            lt = new LayoutTransition<Marking, MarkovLink>(vv, vv.getGraphLayout(), treeLayout);
        }
        Animator animator = new Animator(lt);
        animator.start();
        vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
        vv.repaint();
    }

    class Rings implements VisualizationServer.Paintable {

        BalloonLayout<Marking, MarkovLink> layout;

        public Rings(BalloonLayout<Marking, MarkovLink> layout) {
            this.layout = layout;
        }

        public void paint(Graphics g) {
            g.setColor(Color.gray);

            Graphics2D g2d = (Graphics2D) g;

            Ellipse2D ellipse = new Ellipse2D.Double();
            for (Marking v : layout.getGraph().getVertices()) {
                Double radius = layout.getRadii().get(v);
                if (radius == null) {
                    continue;
                }
                Point2D p = layout.transform(v);
                ellipse.setFrame(-radius, -radius, 2 * radius, 2 * radius);
                AffineTransform at = AffineTransform.getTranslateInstance(p.getX(), p.getY());
                Shape shape = at.createTransformedShape(ellipse);

                MutableTransformer viewTransformer =
                        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);

                if (viewTransformer instanceof MutableTransformerDecorator) {
                    shape = vv.getRenderContext().getMultiLayerTransformer().transform(shape);
                } else {
                    shape = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT, shape);
                }

                g2d.draw(shape);
            }
        }

        public boolean useTransform() {
            return true;
        }
    }

    private void fillGraph(Marking tree) {
        forestGraph = new DelegateForest<Marking, MarkovLink>();
        allMarkings = createAllStates(tree);
        forestGraph.addVertex(tree);
        for (int i = 0; i < allMarkings.size(); i++) {
            for (int j = 0; j < allMarkings.get(i).getTransForTree().size(); j++) {
                forestGraph.addEdge(allMarkings.get(i).getTransForTree().get(j),
                        allMarkings.get(i).getTransForTree().get(j).getParent(),
                        allMarkings.get(i).getTransForTree().get(j).getChild(), EdgeType.DIRECTED);
            }
        }
    }

    private ArrayList<Marking> createAllStates(Marking tree) {
        allMarkings = new ArrayList<Marking>();
        allMarkings.add(tree);
        for (int i = 0; i < tree.getChildren().size(); i++) {
            allMarkings.add(tree.getChildren().get(i));
            createChildStates(allMarkings, tree.getChildren().get(i));
        }
        return allMarkings;
    }

    private void createChildStates(ArrayList<Marking> allMarkings, Marking struct) {
        for (int i = 0; i < struct.getChildren().size(); i++) {
            allMarkings.add(struct.getChildren().get(i));
            createChildStates(allMarkings, struct.getChildren().get(i));

        }
    }

    public class MarkingTips<E>
            implements Transformer<Marking, String> {

        public String transform(Marking m) {
            String tooltip = m.toStringForTreeOrMarkov("T");
            return tooltip;
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

    public final static class MarkingToColorTransformer implements Transformer<Marking, Paint> {

        VisualizationViewer vv;

        public MarkingToColorTransformer(VisualizationViewer vv) {
            this.vv = vv;
        }

        public Paint transform(Marking m) {
            if (!vv.getPickedVertexState().isPicked(m)) {
                if (m.getType().equals(TYPE.Instant)) {
                    return GraphMarkovColors.INST_STATE_COLOR;
                } else if (m.getType().equals(TYPE.Real)) {
                    return GraphMarkovColors.REAL_STATE_COLOR;
                } else if (m.getType().equals(TYPE.Terminal)) {
                    return GraphMarkovColors.TERMINAL_STATE_COLOR;
                } else if (m.getType().equals(TYPE.Root)) {
                    return GraphMarkovColors.ROOT_COLOR;
                } else if (m.getType().equals(TYPE.InstantDup)) {
                    return GraphMarkovColors.INST_STATE_DUP_COLOR;
                } else if (m.getType().equals(TYPE.RealDup)) {
                    return GraphMarkovColors.REAL_STATE_DUP_COLOR;
                } else if (m.getType().equals(TYPE.InstantRoot)) {
                    return GraphMarkovColors.INST_ROOT_COLOR;
                } else if (m.getType().equals(TYPE.Covering)) {
                    return GraphMarkovColors.COV_STATE_COLOR;
                } else {
                    //something has gone wrong
                    return GraphMarkovColors.WRONG_STATE_COLOR;
                }
            } else {
                if (m.getType().equals(TYPE.Instant)) {
                    return GraphMarkovColors.INST_STATE_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.Real)) {
                    return GraphMarkovColors.REAL_STATE_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.Terminal)) {
                    return GraphMarkovColors.TERMINAL_STATE_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.Root)) {
                    return GraphMarkovColors.ROOT_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.InstantDup)) {
                    return GraphMarkovColors.IST_STATE_DUP_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.RealDup)) {
                    return GraphMarkovColors.REAL_STATE_DUP_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.InstantRoot)) {
                    return GraphMarkovColors.INST_ROOT_SELECTED_COLOR;
                } else if (m.getType().equals(TYPE.Covering)) {
                    return GraphMarkovColors.COV_STATE_SELECTED_COLOR;
                } else {
                    //something has gone wrong
                    return GraphMarkovColors.WRONG_STATE_COLOR;
                }
            }
        }
    }
}
