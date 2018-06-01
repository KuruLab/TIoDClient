package client;

import game.Player;
import java.rmi.*;
import java.rmi.server.*;
import javax.swing.JTextArea;

public class Remote_Implementation extends UnicastRemoteObject implements Notification {
    
    private static final String baseURL = "./";
    
    // GUI modificada pelo servidor
    private JTextArea textArea;
    private String name;
    private Player player;

    // *******************************************************************************************
    // Construtor da classe Remote_Implementation que recebe uma área de texto para mostrar as mensagens
    // *******************************************************************************************
    public Remote_Implementation(Player player, JTextArea text) throws RemoteException {
           this.player = player;
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
            System.out.println("DisplayMessage -> Error sending the message.");
        };
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela  deste Cliente a mensagem -> (nome)diz (menssagem) \n
    // ****************************************************************************************     
    @Override
    @SuppressWarnings("empty-statement")
    public void sendMessage(String name, String message) throws RemoteException {
        try {
            textArea.append(name + " says : " + message + "\n");
        } catch (Exception e) {
            System.out.println("Remote_Cliente_Impl -> Error sending the message.");
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
            System.out.println("Remote_Cliente_Impl -> Error sending the message.");
        };
    }

    //Note que estes método Não são chamado remotamente 
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
