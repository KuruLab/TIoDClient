package client;

import java.rmi.*;
import java.rmi.server.*;
import javax.swing.JTextArea;

public class Remote_Implementation extends UnicastRemoteObject implements Notification {
    
    private static final String baseURL = "./";
    
    // GUI modificada pelo servidor
    private JTextArea textArea;
    private String name;

    // *******************************************************************************************
    // Construtor da classe Remote_Implementation que recebe uma área de texto para mostrar as mensagens
    // *******************************************************************************************
    public Remote_Implementation(String name, JTextArea text) throws RemoteException {
        this.name = name;   
        this.textArea = text;
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela deste Cliente a mensagem -> (nome)juntou-se \n
    // **************************************************************************************** 
    @Override
    @SuppressWarnings("empty-statement")
    public void joinMessage(String name) throws RemoteException {
        try {
            setName(name);
            textArea.append(name + " joined the game...\n");
            
            System.out.println("JoinMessage -> "+ getName());
        } catch (Exception e) {
            System.err.println("DisplayMessage -> Error sending the message.");
        };
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela  deste Cliente a mensagem -> (nome)diz (menssagem) \n
    // ****************************************************************************************     
    @Override
    @SuppressWarnings("empty-statement")
    public void sendMessage(String name, String message) throws RemoteException {
        try {
            textArea.append("["+ name + "]: " + message + "\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error sending the message.");
        };
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela deste Cliente a mensagem -> (nome) deixou o chat \n
    // ****************************************************************************************        
    @Override
    @SuppressWarnings("empty-statement")
    public void leaveMessage(String name) throws RemoteException {
        try {
            textArea.append(name + " left the chat.\n");
            System.out.println("Remote_Cliente_Impl -> The client " + name + " left the chat.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error sending the message.");
        };
    }

    //Note que estes método Não são chamado remotamente 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    //Note que este método não é, mas pode ser 
    @Override
    public String getPlayerName() throws RemoteException {
        return name;
    }

    @Override
    public void wrongCommandMessage() throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: I can't understand your request!\n"
                          + "[Dungeon Master]: Please, check your message and try again! Or type /help for help.\n");
            System.out.println("Remote_Cliente_Impl -> Wrong command message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the wrong command message.");
        };
    }

    /*Note:
    * The methods help, location, movement and loot are essentially the same.
    * The difference happens only on the server side.
    * However, it is good to keep it.
    */
    
    @Override
    public void helpMessage(String message) throws RemoteException {
       try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Help message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the help message.");
        };
    }

    @Override
    public void locationInformationMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Look information message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the look information message.");
        };
    }

    @Override
    public void movementMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Movement message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the movement message.");
        };
    }

    @Override
    public void lootMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Loot message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the loot message.");
        };
    }

    @Override
    public void inventoryMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Inventory message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the inv. message.");
        };
    }
    
    
    @Override
    public void attackMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Attack message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the attack message.");
        };
    }

    @Override
    public void historicMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: "+message+"\n");
            System.out.println("Remote_Cliente_Impl -> Historic message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the historic message.");
        };
    }
}
