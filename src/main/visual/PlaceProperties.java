/*
 * PlaceProperties.java
 *
 * Created on 31.10.2010, 23:03:19
 */
package main.visual;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.Place;
import main.Main;

/**
 *
 * @author Aloren
 */
public class PlaceProperties extends javax.swing.JDialog {

    private Place p;
    private AbstractAction okButtonAction = new AbstractAction()  {

        public void actionPerformed(ActionEvent actionEvent) {
            boolean okey = false;
            while (okey == false) {
                try {
                    int chips = Integer.valueOf(inputT.getText());
                    //  undoManager.undoableEditHappened(new UndoableEditEvent(
                    //           this, new ChipsEdit((Place) p, p.getChips(), chips)));
                    p.setChips(chips);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Bad format.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                okey = true;
            }
            Main m = (Main) getParent();
            m.getDrawPanel().repaint();
        }
    };

    /** Creates new form PlaceProperties */
    public PlaceProperties(Place p, DrawPanel panel) {
        super(javax.swing.JOptionPane.getFrameForComponent(panel));
        this.p = p;
        setTitle("Place properties");
        setResizable(false);
        initComponents();
        OkButton.setAction(okButtonAction);
        OkButton.setText("Ok");
        inputT.setText(Integer.toString(p.getChips()));
        inputT.setSelectionStart(0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        OkButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        chipsLabel = new javax.swing.JLabel();
        inputT = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        OkButton.getInputMap(JOptionPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "ok");
        OkButton.getActionMap().put("ok", okButtonAction);
        OkButton.setText("Ok");

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        chipsLabel.setText("Chips");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(chipsLabel)
                .addGap(18, 18, 18)
                .addComponent(inputT, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(OkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(CancelButton))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(chipsLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(inputT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(OkButton))
                .addGap(11, 11, 11)
                .addComponent(CancelButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton OkButton;
    private javax.swing.JLabel chipsLabel;
    private javax.swing.JTextField inputT;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
