package main;

import actionlisteners.GridSpacingListener;
import actions.OpenAction;
import actions.ArcAction;
import actions.BuildMarkovChain;
import actions.DeleteAction;
import actions.ExitAction;
import actions.MTransitionAction;
import actions.NewAction;
import actions.PlaceAction;
import actions.PlayAction;
import actions.SaveAction;
import actions.SaveAsAction;
import actions.VTransitionAction;
import analysis.simulation.visual.ETable;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import constants.CONSTANTS;
import main.visual.DrawPanel;
/**
 *
 * @author Aloren
 */
public class Main extends javax.swing.JFrame {
    /**
     * TableUpdator between JFrame components and drawing panel.
     */
    public static TableUpdator m = new TableUpdator();
    
    /** Creates new form Main */
    public Main(String title) {
        super(title);
        initComponents();
        drawPanel.setPreferredSize(new Dimension(1000, 1000));        
        jToolBar.setFloatable(false);        
        setFocusable(true);
    }

    public DrawPanel getDrawPanel(){
        return (DrawPanel)drawPanel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        drawPanel = new DrawPanel();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtI = new ETable();
        DiLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        DqLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtO = new ETable();
        ToolbarTabbedPane = new javax.swing.JTabbedPane();
        jToolBar = new javax.swing.JToolBar();
        PlaceButton = new javax.swing.JToggleButton(new PlaceAction());
        VTransitionButton = new javax.swing.JToggleButton(new VTransitionAction());
        MTransitionButton = new javax.swing.JToggleButton(new MTransitionAction());
        ArcButton = new javax.swing.JToggleButton(new ArcAction());
        jSeparator1 = new javax.swing.JToolBar.Separator();
        DeleteButton = new javax.swing.JButton(new DeleteAction());
        jSeparator2 = new javax.swing.JToolBar.Separator();
        UndoButton = new javax.swing.JButton();
        RedoButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        PlayButton = new javax.swing.JButton(new PlayAction());
        jSeparator3 = new javax.swing.JToolBar.Separator();
        TreeButton = new javax.swing.JButton(new actions.BuildTreeAction());
        MChainButton = new javax.swing.JButton(new BuildMarkovChain());
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        snapToGridBox = new javax.swing.JCheckBox();
        showGridBox = new javax.swing.JCheckBox();
        GridSlider = new javax.swing.JSlider(5,30,5);
        jLabel1 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewMenuItem = new javax.swing.JMenuItem(new NewAction());
        OpenMenuItem = new javax.swing.JMenuItem(new OpenAction());
        jMenuItem1 = new javax.swing.JMenuItem(new SaveAction());
        SaveAsMenuItem = new javax.swing.JMenuItem(new SaveAsAction());
        ExitMenuItem = new javax.swing.JMenuItem(new ExitAction());
        ModellingMenu = new javax.swing.JMenu();
        BuildJTreeMenuItem = new javax.swing.JMenuItem();
        BuildMarkovMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        transMenuItem = new javax.swing.JMenuItem(new actions.TransitionsItemAction());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame"); // NOI18N

        jSplitPane1.setDividerLocation(800);
        jSplitPane1.setDividerSize(7);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSplitPane1.setDoubleBuffered(true);
        jSplitPane1.setOneTouchExpandable(true);

        jScrollPane3.setDoubleBuffered(true);

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 994, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(drawPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jtI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, new String [] {}));
    jScrollPane2.setViewportView(jtI);

    DiLabel.setText("Input matrix Di");

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(DiLabel)
            .addContainerGap(254, Short.MAX_VALUE))
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(DiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
    );

    jSplitPane2.setLeftComponent(jPanel3);

    DqLabel.setText("Output matrix Dq");

    jtO.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {}, new String [] {}
    ));
    jScrollPane1.setViewportView(jtO);

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(DqLabel)
            .addContainerGap(242, Short.MAX_VALUE))
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addComponent(DqLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
    );

    jSplitPane2.setRightComponent(jPanel4);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
    );

    jSplitPane1.setRightComponent(jPanel2);

    jToolBar.setRollover(true);

    PlaceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/position.png"))); // NOI18N
    PlaceButton.setToolTipText("Position");
    PlaceButton.setFocusable(false);
    PlaceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    PlaceButton.setIconTextGap(0);
    PlaceButton.setMaximumSize(new java.awt.Dimension(39, 39));
    PlaceButton.setMinimumSize(new java.awt.Dimension(39, 39));
    PlaceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(PlaceButton);

    VTransitionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/timed.png"))); // NOI18N
    VTransitionButton.setToolTipText("Time transition");
    VTransitionButton.setFocusable(false);
    VTransitionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    VTransitionButton.setMaximumSize(new java.awt.Dimension(39, 39));
    VTransitionButton.setMinimumSize(new java.awt.Dimension(39, 39));
    VTransitionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(VTransitionButton);

    MTransitionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inst.png"))); // NOI18N
    MTransitionButton.setToolTipText("Instant transition");
    MTransitionButton.setFocusable(false);
    MTransitionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    MTransitionButton.setMaximumSize(new java.awt.Dimension(39, 39));
    MTransitionButton.setMinimumSize(new java.awt.Dimension(39, 39));
    MTransitionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(MTransitionButton);

    ArcButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line.png"))); // NOI18N
    ArcButton.setToolTipText("Line");
    ArcButton.setFocusable(false);
    ArcButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    ArcButton.setMaximumSize(new java.awt.Dimension(39, 39));
    ArcButton.setMinimumSize(new java.awt.Dimension(39, 39));
    ArcButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(ArcButton);
    jToolBar.add(jSeparator1);

    DeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
    DeleteButton.setToolTipText("Delete");
    DeleteButton.setMaximumSize(new java.awt.Dimension(39, 39));
    DeleteButton.setMinimumSize(new java.awt.Dimension(39, 39));
    jToolBar.add(DeleteButton);
    jToolBar.add(jSeparator2);

    UndoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/undo.png"))); // NOI18N
    UndoButton.setToolTipText("Undo");
    UndoButton.setEnabled(false);
    UndoButton.setFocusable(false);
    UndoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    UndoButton.setMaximumSize(new java.awt.Dimension(39, 39));
    UndoButton.setMinimumSize(new java.awt.Dimension(39, 39));
    UndoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(UndoButton);

    RedoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/redo.png"))); // NOI18N
    RedoButton.setToolTipText("Redo");
    RedoButton.setEnabled(false);
    RedoButton.setFocusable(false);
    RedoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    RedoButton.setMaximumSize(new java.awt.Dimension(39, 39));
    RedoButton.setMinimumSize(new java.awt.Dimension(39, 39));
    RedoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(RedoButton);
    jToolBar.add(jSeparator4);

    PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/play.png"))); // NOI18N
    PlayButton.setToolTipText("Simulation mode");
    PlayButton.setActionCommand("Play");
    PlayButton.setFocusable(false);
    PlayButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    PlayButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(PlayButton);
    jToolBar.add(jSeparator3);

    TreeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/tree.png"))); // NOI18N
    TreeButton.setToolTipText("Build reachability tree");
    TreeButton.setActionCommand("Build tree");
    TreeButton.setFocusable(false);
    TreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    TreeButton.setIconTextGap(0);
    TreeButton.setMaximumSize(new java.awt.Dimension(39, 39));
    TreeButton.setMinimumSize(new java.awt.Dimension(39, 39));
    TreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(TreeButton);

    MChainButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/markov.png"))); // NOI18N
    MChainButton.setToolTipText("Build Markov chain");
    MChainButton.setActionCommand("Build Markov chain");
    MChainButton.setFocusable(false);
    MChainButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    MChainButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar.add(MChainButton);
    jToolBar.add(jSeparator5);

    ToolbarTabbedPane.addTab("Home", jToolBar);

    snapToGridBox.setSelected(true);
    snapToGridBox.setText("Snap to grid");
    snapToGridBox.addActionListener(new actionlisteners.SnapToGridActionListener());

    showGridBox.setSelected(true);
    showGridBox.setText("Grid");
    showGridBox.addActionListener(new actionlisteners.GridActionListener());

    GridSlider.setSnapToTicks(true);
    GridSlider.setPaintTicks(true);
    GridSlider.setMajorTickSpacing(5);
    GridSlider.setPaintLabels(true);
    GridSlider.addChangeListener(new GridSpacingListener());

    jLabel1.setText("Grid size");

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(snapToGridBox)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(showGridBox, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(GridSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(snapToGridBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showGridBox))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addComponent(GridSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
    );

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(968, Short.MAX_VALUE))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );

    ToolbarTabbedPane.addTab("View", jPanel5);

    FileMenu.setText("File");

    NewMenuItem.setText("New");
    FileMenu.add(NewMenuItem);

    OpenMenuItem.setText("Open");
    FileMenu.add(OpenMenuItem);

    jMenuItem1.setText("Save(X)");
    FileMenu.add(jMenuItem1);

    SaveAsMenuItem.setText("Save as");
    FileMenu.add(SaveAsMenuItem);

    ExitMenuItem.setText("Exit");
    FileMenu.add(ExitMenuItem);

    jMenuBar2.add(FileMenu);

    ModellingMenu.setText("Modeling");

    BuildJTreeMenuItem.setText("Build tree");
    ModellingMenu.add(BuildJTreeMenuItem);

    BuildMarkovMenuItem.setText("Build Markov chain");
    ModellingMenu.add(BuildMarkovMenuItem);

    jMenuBar2.add(ModellingMenu);

    jMenu1.setText("Graph");

    transMenuItem.setText("Transitions");
    jMenu1.add(transMenuItem);

    jMenuBar2.add(jMenu1);

    setJMenuBar(jMenuBar2);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(ToolbarTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(ToolbarTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Main demo = new Main(CONSTANTS.MAIN_TITLE);            
            demo.setLocationRelativeTo(null);
            //DIMENSIONS.fullScreen(demo);
            demo.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JToggleButton ArcButton;
    private javax.swing.JMenuItem BuildJTreeMenuItem;
    private javax.swing.JMenuItem BuildMarkovMenuItem;
    public static javax.swing.JButton DeleteButton;
    private javax.swing.JLabel DiLabel;
    private javax.swing.JLabel DqLabel;
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JSlider GridSlider;
    public static javax.swing.JButton MChainButton;
    public static javax.swing.JToggleButton MTransitionButton;
    private javax.swing.JMenu ModellingMenu;
    private javax.swing.JMenuItem NewMenuItem;
    private javax.swing.JMenuItem OpenMenuItem;
    public static javax.swing.JToggleButton PlaceButton;
    public static javax.swing.JButton PlayButton;
    private static javax.swing.JButton RedoButton;
    private javax.swing.JMenuItem SaveAsMenuItem;
    private javax.swing.JTabbedPane ToolbarTabbedPane;
    public static javax.swing.JButton TreeButton;
    private static javax.swing.JButton UndoButton;
    public static javax.swing.JToggleButton VTransitionButton;
    public static javax.swing.JPanel drawPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar;
    public static javax.swing.JTable jtI;
    public static javax.swing.JTable jtO;
    private javax.swing.JCheckBox showGridBox;
    private javax.swing.JCheckBox snapToGridBox;
    private javax.swing.JMenuItem transMenuItem;
    // End of variables declaration//GEN-END:variables
}
