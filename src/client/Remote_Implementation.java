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

    @Override
    public void newLocationMessage(String location) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: You are now at "+location.toString()+".\n");
            System.out.println("Remote_Cliente_Impl -> The client " + name + " is "+ location.toString() +".\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error within the new location message.");
        };
    }

    @Override
    public void wrongCommandMessage(String message) throws RemoteException {
        try {
            textArea.append("[Dungeon Master]: I can't understand your request!\n"
                          + "[Dungeon Master]: Please, check your message and try again!\n"
                          + message);
            System.out.println("Remote_Cliente_Impl -> Wrong command message.\n");
        } catch (Exception e) {
            System.err.println("Remote_Cliente_Impl -> Error with the wrong command message.");
        };

    }
}
