/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeonclient;

import com.bulenkov.darcula.DarculaLaf;
import dungeonserver.DungeonServer;
import engine.DungeonGenerator;
import evoGraph.Config;
import game.Player;
import gui.Main;
import java.io.File;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;

/**
 *
 * @author Kurumin
 */
public class MenuScreen extends javax.swing.JFrame {

    private DungeonGenerator generator;
    private DungeonServer localServer;
    
    private ChatScreen chatScreen;
    
    public MenuScreen() {
        initComponents();
        updateDungeonListComboBox();
    }
    
    private void updateDungeonListComboBox(){
        Config.folder = "." + File.separator + "data" + File.separator + "dungeons" + File.separator + "";
        File dungeonDirectory = new File(Config.folder);
        File[] dungeonArray = dungeonDirectory.listFiles();
        
        System.out.println("Loading dungeon list...");
        dungeonCombo.removeAllItems();
        dungeonCombo.addItem("Select a Dungeon ID");
        if (dungeonArray.length > 0) {
            for (int i = 0; i < dungeonArray.length; i++) {
                if(dungeonArray[i].isDirectory()){
                    File mapFile = new File(dungeonArray[i], "map_"+dungeonArray[i].getName()+".json");
                    System.out.print("Checking if "+dungeonArray[i]+" is valid... ");
                    if(mapFile.exists()){
                        System.out.println("ok!");
                        File dungeon = dungeonArray[i];
                        dungeonCombo.addItem(dungeon.getName());
                    }
                    else{
                        System.err.println("warning: "+dungeonArray[i]+" is not a valid folder.");
                    }
                }
            }
        }
        dungeonCombo.repaint();
        dungeonCombo.validate();
        dungeonCombo.updateUI();
    }
    
    private void toogleInterfaceON_OFF(boolean mode){
        generateButton.setEnabled(mode);
        hostButton.setEnabled(mode);
        joinButton.setEnabled(mode);
        cancelButton.setEnabled(!mode);
    }

    private Player getNewPlayerInstance(){
        Player player = new Player();
        player.setName(playerName.getText());
        return player;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        playerName = new javax.swing.JTextField();
        hostButton = new javax.swing.JButton();
        joinButton = new javax.swing.JButton();
        dungeonCombo = new javax.swing.JComboBox<>();
        generateButton = new javax.swing.JButton();
        ipTextField = new javax.swing.JTextField();
        progress = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Your Name:");

        playerName.setText("Unamed Player");

        hostButton.setText("Host");
        hostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostButtonActionPerformed(evt);
            }
        });

        joinButton.setText("Join");
        joinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinButtonActionPerformed(evt);
            }
        });

        dungeonCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Dungeon ID" }));

        generateButton.setText("Generate New");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        ipTextField.setText("127.0.0.1");

        progress.setForeground(new java.awt.Color(255, 102, 2));
        progress.setToolTipText("");

        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(joinButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hostButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ipTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dungeonCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                            .addComponent(playerName)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(playerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostButton)
                    .addComponent(dungeonCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(joinButton)
                    .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        IPTextFieldVerifier verifier = new IPTextFieldVerifier();
        if (verifier.verify(ipTextField)) {
            System.out.println("IP Verified!");
        } else {
            ipTextField.setText("127.0.0.1");
        }
    }//GEN-LAST:event_joinButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        String dungeonName = JOptionPane.showInputDialog(null, "Enter the name of the new dungeon or leave blank for default ID", "Dungeon Generator", JOptionPane.QUESTION_MESSAGE);
        toogleInterfaceON_OFF(false);
        generator = new DungeonGenerator(dungeonName, progress, statusLabel);
        SwingWorker worker1 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Thread thread = new Thread(generator);
                thread.start();

                return null;
            }
        };
        worker1.run();
       
        SwingWorker worker2 = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!generator.isFinished()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MenuScreen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        System.out.println("Job done!");
                        toogleInterfaceON_OFF(true);
                        updateDungeonListComboBox();
                    }
                });
                thread.start();
                return null;
            }
        };
        worker2.run();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void hostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostButtonActionPerformed
        int index = dungeonCombo.getSelectedIndex();
        if(index > 0){
            try {
                String id = (String) dungeonCombo.getSelectedItem();
                localServer = new DungeonServer(id);
                
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        Thread thread = new Thread(localServer);
                        thread.start();
                        
                        return null;
                    }
                };
                worker.run();
            } catch (RemoteException ex) {
                Logger.getLogger(MenuScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    chatScreen = new ChatScreen(getNewPlayerInstance());
                    chatScreen.setVisible(true);
                }
            });
            
            this.setVisible(false);
            
        } else{
          JOptionPane.showMessageDialog(null, "Select a dungeon in the list", "Attention!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_hostButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        generator.setFinished(true);
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MenuScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        statusLabel.setText("Cancelled.");
                        progress.setValue(0);
                    }
                });
                thread.start();

                return null;
            }
        };
        worker.run();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            BasicLookAndFeel darcula = new DarculaLaf();
            UIManager.setLookAndFeel(darcula);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuScreen().setVisible(true);
            }
        });
    } 

    class IPTextFieldVerifier {

        public boolean verify(JTextField input) {
            try{
                String ipText = input.getText();
                if (ipText != null || ipText.isEmpty()) {
                    int numberCount = 0;
                    StringTokenizer st = new StringTokenizer(ipText, ".");
                    while (st.hasMoreTokens()) {
                        int value = Integer.parseInt((String) st.nextToken());
                        if (value < 0 || value > 255) {
                            return malformedIPAddress();
                        }
                        numberCount++;
                    }
                    if(numberCount == 4)
                        return true;
                    else{
                        return malformedIPAddress();
                    }
                }
                return false;
            }
            catch(java.lang.NumberFormatException ex){
                return malformedIPAddress();
            }
        }
        
        public boolean malformedIPAddress(){
            JOptionPane.showMessageDialog(null, "Malformed IP Address!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> dungeonCombo;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton hostButton;
    private javax.swing.JTextField ipTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton joinButton;
    private javax.swing.JTextField playerName;
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
