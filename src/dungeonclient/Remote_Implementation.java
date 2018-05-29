package dungeonclient;

import game.Player;
import java.rmi.*;
import java.rmi.server.*;

public class Remote_Implementation extends UnicastRemoteObject implements Notification {
    
    private static final String baseURL = "./";
    
    // GUI modificada pelo servidor
    private javax.swing.JTextArea textArea;
    private String name;
    private Player player;

    // *******************************************************************************************
    // Construtor da classe Remote_Implementation que recebe uma área de texto para mostrar as mensagens
    // *******************************************************************************************
    public Remote_Implementation() throws RemoteException {
            
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela deste Cliente a mensagem -> (nome)juntou-se \n
    // **************************************************************************************** 
    @Override
    @SuppressWarnings("empty-statement")
    public void juntarMensagem(String name, int slot) throws RemoteException {
        try {
            // Metodo Antigo
            //textArea.append(name + " juntou-se \n");
            setName(name);
            if(slot!=-1)
                textArea.append(name + " entrou na posição " + slot + "...\n");
            else
                textArea.append(name + " entrou como espectador.\n");
            //System.out.println("JuntarMensagem= "+ getName());
        } catch (Exception e) {
            System.out.println("DisplayMessage -> Falha no envio da Mesagem");
        };
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela  deste Cliente a mensagem -> (nome)diz (menssagem) \n
    // ****************************************************************************************     
    @Override
    @SuppressWarnings("empty-statement")
    public void enviarMensagem(String name, String message) throws RemoteException {
        try {
            textArea.append(name + " diz : " + message + "\n");
        } catch (Exception e) {
            System.out.println("Remote_Cliente_Impl -> Falha no envio da Mesagem");
        };
    }

    // **************************************************************************************** 
    // Método para MOSTRAR na janela deste Cliente a mensagem -> (nome) deixou o chat \n
    // ****************************************************************************************        
    @Override
    @SuppressWarnings("empty-statement")
    public void sairMensagem(String name) throws RemoteException {
        try {
            textArea.append(name + " deixou o chat.\n");
            System.out.println("Remote_Cliente_Impl -> O Cliente " + name + " deixou o chat.\n");
        } catch (Exception e) {
            System.out.println("Remote_Cliente_Impl -> Falha no envio da Mesagem");
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
