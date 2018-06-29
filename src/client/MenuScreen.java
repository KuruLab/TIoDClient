/*
 * Copyright (C) 2018 Kurumin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package client;

import com.bulenkov.darcula.DarculaLaf;
import engine.DungeonGenerator;
import engine.LevelGenerator;
import game.CorePlayer;
import server.DungeonServer;

import java.io.File;
import java.io.FileFilter;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
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

    private LevelGenerator generator;
    private DungeonServer localServer;

    private ChatScreen chatScreen;

    public MenuScreen() {
        initComponents();
        updateDungeonListComboBox();
    }

    private void updateDungeonListComboBox() {
        //Config.folder = "." + File.separator + "data" + File.separator + "levels" + File.separator + "";
        File dungeonDirectory = new File(DungeonGenerator.folder);
        File[] dungeonArray = dungeonDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.getName().endsWith(".json"))
                    return true;
                else return false;
            }
        });

        System.out.print("Loading dungeon list...");
        dungeonCombo.removeAllItems();
        dungeonCombo.addItem("Select a Dungeon ID");
        if (dungeonArray.length > 0) {
            for (int i = 0; i < dungeonArray.length; i++) {
                //if (dungeonArray[i].isDirectory()) {
                //File mapFile = new File(dungeonArray[i], "map_" + dungeonArray[i].getName() + ".json");
                //System.out.print("Checking if " + dungeonArray[i] + " is valid... ");
                //if (mapFile.exists()) {
                //    System.out.println("ok!");
                File level = dungeonArray[i];
                String dungeonName = level.getName().replace(".json", "");
                dungeonCombo.addItem(dungeonName);
                //} else {
                //    System.err.println("warning: " + dungeonArray[i] + " is not a valid folder.");
                //}
                // }
            }
        }
        System.out.println(" done.");
        dungeonCombo.repaint();
        dungeonCombo.validate();
        dungeonCombo.updateUI();
    }

    private void toogleInterfaceON_OFF(boolean mode) {
        generateButton.setEnabled(mode);
        hostButton.setEnabled(mode);
        joinButton.setEnabled(mode);
        cancelButton.setEnabled(!mode);
    }

    /*the client won't have a player instance anymore
    * it will stay in server
    */
    /*private CorePlayer getNewPlayerInstance() {
        CorePlayer player = new CorePlayer(playerName.getText()); // -> player.setName(playerName.getText());
        return player;
    }*/

    private void join() {
        IPTextFieldVerifier verifier = new IPTextFieldVerifier();
        if (verifier.verify(ipTextField)) {
            System.out.println("IP Verified!");
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        /* issue: if the connection is refused for any reason,
                        *   the program will throw an exception and the it will close.
                        *   the user won't have a friendly feedback
                        */
                        Thread.sleep(1000);
                        chatScreen = new ChatScreen(playerName.getText(), ipTextField.getText());
                        chatScreen.setVisible(true);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(getClass().getName(), ex.getMessage());
                    }
                }
            });

            this.setVisible(false);
        } else {
            ipTextField.setText("127.0.0.1");
        }
    }

    private void host() {
        int index = dungeonCombo.getSelectedIndex();
        if (index > 0) {
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
                Logger.getLogger(getClass().getName(), ex.getMessage());
            }

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                        chatScreen = new ChatScreen(playerName.getText(), "localhost");
                        chatScreen.setVisible(true);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(getClass().getName(), ex.getMessage());
                    }
                }
            });

            this.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "Select a level in the list", "Attention!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generate() {
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
                                Logger.getLogger(getClass().getName(), ex.getMessage());
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
    }

    private void cancellGeneration() {
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
                            Logger.getLogger(getClass().getName(), ex.getMessage());
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

    class IPTextFieldVerifier {

        public boolean verify(JTextField input) {
            try {
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
                    if (numberCount == 4) {
                        return true;
                    } else {
                        return malformedIPAddress();
                    }
                }
                return false;
            } catch (java.lang.NumberFormatException ex) {
                return malformedIPAddress();
            }
        }

        public boolean malformedIPAddress() {
            JOptionPane.showMessageDialog(null, "Malformed IP Address!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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
        dungeonCombo = new javax.swing.JComboBox<>();
        joinButton = new javax.swing.JButton();
        ipTextField = new javax.swing.JTextField();
        generateButton = new javax.swing.JButton();
        progress = new javax.swing.JProgressBar();
        cancelButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Your Name:");

        playerName.setText("Unamed Player");

        hostButton.setText("Host");
        hostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostButtonActionPerformed(evt);
            }
        });

        dungeonCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a Dungeon ID" }));

        joinButton.setText("Join");
        joinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinButtonActionPerformed(evt);
            }
        });

        ipTextField.setText("127.0.0.1");

        generateButton.setText("Generate New");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

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

    private void hostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostButtonActionPerformed
        host();
    }//GEN-LAST:event_hostButtonActionPerformed

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        join();
    }//GEN-LAST:event_joinButtonActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generate();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancellGeneration();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            BasicLookAndFeel darcula = new DarculaLaf();
            UIManager.setLookAndFeel(darcula);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuScreen().setVisible(true);
            }
        });
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
