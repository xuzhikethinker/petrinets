package actions;

import generators.Generator;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import main.Main;
import main.visual.DrawPanel;
import model.Graph;
import model.Transition;

/**
 *
 * @author Aloren
 */
public class TransitionsItemAction extends AbstractAction {

    private final static String newline = "\n";

    public void actionPerformed(ActionEvent e) {
        JDialog transDialog = new JDialog();
        transDialog.setTitle("Transitions");
        transDialog.setSize(new Dimension(400, 400));
        transDialog.setLocationRelativeTo(null);
        transDialog.setVisible(true);
        JTextPane textpane = new JTextPane();
        textpane.setContentType("text/html");
        textpane.setEditable(false);
        textpane.setText(createRecords());
        JScrollPane content = new JScrollPane(textpane);
        transDialog.setContentPane(content);
    }

    private String createRecords() {
        StringBuilder sb = new StringBuilder("<h2>All transitions in the current Petri nets</h2>" + newline);
        DrawPanel dp = ((DrawPanel) (Main.drawPanel));
        Graph g = dp.getGraph();
        ArrayList<Transition> transitions = g.getTransitions();
        for (int i = 0; i < transitions.size(); i++) {
            Transition t = transitions.get(i);
            sb.append("<pre><b>Transition name: ").append(t.getName()).append("</b>").append(newline);
            sb.append("Type: <i>").append(t.getType().toString()).append("</i>" + newline);
            sb.append("Allowed: <i>").append(t.isAllowed()).append("</i>");
            if (t.isReal()) {
                Generator gen = t.getGenerator();
                sb.append(newline+"Probability: <i>").append(t.getProbability()).append("</i>" + newline);
                sb.append("Generator: <i>").append(gen).append("</i>" + newline);
                sb.append("Variation: <i>").append(gen.getVariation()).append("</i>" + newline);
                sb.append("Lambda: <i>").append(gen.getLambda()).append("</i></pre>");
            } else {
                sb.append("</pre>");
                if (i != transitions.size() - 1) {
                    sb.append(newline);
                }
            }
        }
        return sb.toString();
    }
}
