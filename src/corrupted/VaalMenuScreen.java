/*//GEN-FIRST:event_joinButtonActionPerformed
 * To change this license header, choose License Headers in Project Properties.//GEN-LAST:event_joinButtonActionPerformed
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corrupted;

/* CORRUPTED */

import client.ChatScreen;
import com.bulenkov.darcula.DarculaLaf;
import server.DungeonServer;
import engine.LevelGenerator;
import evoGraph.Config;
import game.CorePlayer;
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
public class VaalMenuScreen extends javax.swing.JFrame {

    private LevelGenerator generator;
    private DungeonServer localServer;
    
    private ChatScreen chatScreen;
    
    public VaalMenuScreen() {
        initComponents();
        updateLevelListComboBox();
    }
    
    private void updateLevelListComboBox(){
        Config.folder = "." + File.separator + "data" + File.separator + "levels" + File.separator + "";
        File levelDirectory = new File(Config.folder);
        File[] levelArray = levelDirectory.listFiles();
        
        System.out.println("Loading level list...");
        levelCombo.removeAllItems();
        levelCombo.addItem("Select a Level ID");
        if (levelArray.length > 0) {
            for (int i = 0; i < levelArray.length; i++) {
                if(levelArray[i].isDirectory()){
                    File mapFile = new File(levelArray[i], "map_"+levelArray[i].getName()+".json");
                    System.out.print("Checking if "+levelArray[i]+" is valid... ");
                    if(mapFile.exists()){
                        System.out.println("ok!");
                        File level = levelArray[i];
                        levelCombo.addItem(level.getName());
                    }
                    else{
                        System.err.println("warning: "+levelArray[i]+" is not a valid folder.");
                    }
                }
            }
        }
        levelCombo.repaint();
        levelCombo.validate();
        levelCombo.updateUI();
    }
    
    private void toogleInterfaceON_OFF(boolean mode){
        generateButton.setEnabled(mode);
        hostButton.setEnabled(mode);
        joinButton.setEnabled(mode);
        cancelButton.setEnabled(!mode);
    }

    private CorePlayer getNewPlayerInstance(){
        CorePlayer player = new CorePlayer(playerName.getText());
        //player.setName();
        return player;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        playerName = new javax.swing.JTextField();
        hostButton = new javax.swing.JButton();
        joinButton = new javax.swing.JButton();
        levelCombo = new javax.swing.JComboBox<>();
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

        levelCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Level ID" }));

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
                            .addComponent(levelCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
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
                    .addComponent(levelCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    }// </editor-fold>                        

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        IPTextFieldVerifier verifier = new IPTextFieldVerifier();
        if (verifier.verify(ipTextField)) {
            System.out.println("IP Verified!");
        } else {
            ipTextField.setText("127.0.0.1");
        }
    }                                          

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        String levelName = JOptionPane.showInputDialog(null, "Enter the name of the new level or leave blank for default ID", "Level Generator", JOptionPane.QUESTION_MESSAGE);
        toogleInterfaceON_OFF(false);
        generator = new LevelGenerator(levelName, progress, statusLabel);
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
                                Logger.getLogger(VaalMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        System.out.println("Job done!");
                        toogleInterfaceON_OFF(true);
                        updateLevelListComboBox();
                    }
                });
                thread.start();
                return null;
            }
        };
        worker2.run();
    }                                              

    private void hostButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        int index = levelCombo.getSelectedIndex();
        if(index > 0){
            try {
                String id = (String) levelCombo.getSelectedItem();
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
                Logger.getLogger(VaalMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    //chatScreen = new ChatScreen(getNewPlayerInstance());
                    chatScreen.setVisible(true);
                }
            });
            
            this.setVisible(false);
            
        } else{
          JOptionPane.showMessageDialog(null, "Select a level in the list", "Attention!", JOptionPane.ERROR_MESSAGE);
        }
    }                                          

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
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
                            Logger.getLogger(VaalMenuScreen.class.getName()).log(Level.SEVERE, null, ex);
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
    }                                            

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
                new VaalMenuScreen().setVisible(true);
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

    // Variables declaration - do not modify                     
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> levelCombo;
    private javax.swing.JButton generateButton;
    private javax.swing.JButton hostButton;
    private javax.swing.JTextField ipTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton joinButton;
    private javax.swing.JTextField playerName;
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration                   
}
