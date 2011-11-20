package analysis.simulation.visual;

import analysis.simulation.ControlListRecord;
import analysis.simulation.MarkingMatrix;
import analysis.simulation.Simulation;
import analysis.utils.ColorBarRenderer;
import analysis.simulation.SimulationState;
import analysis.tree.visual.MarkovGraphPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import model.Graph;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import utils.RowHeaderRenderer;
import utils.Utils;

/**
 *
 * @author Aloren
 */
public class SimulationFrame extends JFrame {

    public static Color GRAPH_COLOR = new Color(217, 207, 229);
    public static String MARKING_STR = "Маркировка";
    JPanel markovGraphPanel;

    public SimulationFrame(Simulation s, Graph graph, Integer[] startChips) {
        super("Simulation Analysis");
        setLocationRelativeTo(null);
        Set<SimulationState> simStates = s.getSimStates();

        ChartPanel FrequencyChartPanel = new ChartPanel(createChart(createPlot(createDataset(simStates, 0), GRAPH_COLOR, MARKING_STR, "Частота"), "Частоты появления маркировки"));
        ChartPanel ProbabilityChartPanel = new ChartPanel(createChart(createPlot(createDataset(simStates, 1), GRAPH_COLOR, MARKING_STR, "Вероятность"), "Вероятности появления маркировки"));
        ChartPanel StayTimeChartPanel = new ChartPanel(createChart(createPlot(createDataset(simStates, 2), GRAPH_COLOR, MARKING_STR, "Время"), "Время пребывания"));
        ChartPanel ReturnTimeChartPanel = new ChartPanel(createChart(createPlot(createDataset(simStates, 3), GRAPH_COLOR, MARKING_STR, "Время"), "Время возвращения"));

        markovGraphPanel = createMarkovPanel(graph, startChips, s.getMarkings(), s.getMarkingMatrix());
        JTabbedPane tabbedPane = new JTabbedPane();
        //tabbedPane.addTab("Процесс симуляции", createSimulationPanel(s.getOutput()));
        tabbedPane.addTab("Частоты", FrequencyChartPanel);
        tabbedPane.addTab("Вероятности", ProbabilityChartPanel);
        tabbedPane.addTab("Время пребывания", StayTimeChartPanel);
        tabbedPane.addTab("Время возвращения", ReturnTimeChartPanel);
        tabbedPane.addTab("Управляющий список", createControlPanel(s.getControlList()));
        tabbedPane.addTab("Вероятности переходов", createProbabilityTablePanel(s.getMarkings(), s.getMarkingMatrix(), s.getSimStates()));
        tabbedPane.addTab("Граф Маркова вероятностный", markovGraphPanel);
        add(tabbedPane);
    }

    protected static DefaultCategoryDataset createDataset(Set<SimulationState> simStates, final int mode) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        for (int i = 0; i < simStates.size(); i++) {
            switch (mode) {
                case (0):
                    result.addValue(((SimulationState) simStates.toArray()[i]).getFreq(), "i", ((SimulationState) simStates.toArray()[i]).getName());
                    break;
                case (1):
                    result.addValue(((SimulationState) simStates.toArray()[i]).getProbability(), "i", ((SimulationState) simStates.toArray()[i]).getName());
                    break;
                case (2):
                    result.addValue(((SimulationState) simStates.toArray()[i]).getTimeStayForChart(), "i", ((SimulationState) simStates.toArray()[i]).getName());
                    break;
                case (3):
                    result.addValue(((SimulationState) simStates.toArray()[i]).getTimeReturnForChart(), "i", ((SimulationState) simStates.toArray()[i]).getName());

            }
        }
        return result;
    }

    /**
     * Создавние графика.
     * @param Данные с частотатми и интервалами для графика.
     * @param Цвет графика.
     * @return График.
     */
    protected static CategoryPlot createPlot(DefaultCategoryDataset dataset, Color color, String category, String value) {
        ColorBarRenderer renderer = new ColorBarRenderer(color);
        final CategoryAxis categoryAxis = new CategoryAxis(category);
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        final ValueAxis valueAxis = new NumberAxis(value);
        CategoryPlot catplot = new CategoryPlot(dataset, categoryAxis,
                valueAxis,
                renderer);
        renderer.setBaseSeriesVisibleInLegend(false); //legend is not visible
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseNegativeItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        return catplot;
    }

    /**
     *
     * @param catplot
     * @param name
     * @return
     */
    protected static JFreeChart createChart(CategoryPlot catplot, String name) {
        JFreeChart chart = new JFreeChart(name, JFreeChart.DEFAULT_TITLE_FONT,
                catplot, true);
        return chart;
    }

    private static Object[] createRowHeaders(List<String> data) {
        Object[] columnHeaders = new Object[data.size()];
        for (int i = 0; i < columnHeaders.length; i++) {
            columnHeaders[i] = data.get(i).toString();
        }
        return columnHeaders;
    }

    private static Component createSimulationPanel(String data) {
        JTextArea output = new JTextArea();
        output.append(data);
        Utils.setFontSize(output,3);
        JScrollPane scrollPane1 = new JScrollPane(output);
        output.setEditable(false);
        return scrollPane1;
    }

    private static Component createControlPanel(List<ControlListRecord> controlList) {
        TableModel model = new ControlListTableModel(controlList);
        JTable table = new ETable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private static JPanel createProbabilityTablePanel(List<String> markingLabels, MarkingMatrix markingMatrix, Set<SimulationState> ss) {
        double[] stayTimes = new double[markingLabels.size()];
        for (int i = 0; i < stayTimes.length; i++) {
            stayTimes[i] = getByTime(ss, markingLabels.get(i));
        }
        TableModel model2 = new MarkingToMarkingTableModel(markingLabels, markingMatrix, stayTimes);
        JTable table = new ETable(model2);
        JScrollPane scrollPane = new JScrollPane(table);
        Object[] RowHeaders = createRowHeaders(markingLabels);
        JList rowHeadersO = new JList(RowHeaders);
        rowHeadersO.setFixedCellHeight(16);
        rowHeadersO.setCellRenderer(new RowHeaderRenderer(table));
        rowHeadersO.setBackground(scrollPane.getBackground());
        scrollPane.setRowHeaderView(rowHeadersO);
        if (!markingLabels.isEmpty()) {
            FontMetrics fm = table.getFontMetrics(table.getFont());
            int pixelwidth = fm.stringWidth(markingLabels.get(0));
            rowHeadersO.setFixedCellWidth(pixelwidth + 10);
        }
        JPanel panel = new MarkingToMarkingPanel(scrollPane,table,stayTimes,markingMatrix);
        return panel;
    }

    private static double getByTime(Set<SimulationState> ss, String marking) {
        Iterator<SimulationState> it = ss.iterator();
        double time = -1;
        while (it.hasNext()) {
            SimulationState s = it.next();
            if (marking.equals(s.getName())) {
                time = s.getTimeStay();
                break;
            }
        }
        return time;
    }

    private static JPanel createMarkovPanel(Graph graph, Integer[] startChips, List<String> markingLabels, MarkingMatrix markingMatrix) {
        return new MarkovGraphPanel("Real", startChips, graph, markingLabels, markingMatrix,false);
    }
}
