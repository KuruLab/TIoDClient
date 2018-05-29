/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import game.Player;
import java.util.HashMap;
import models.Command;
import models.CommandFactory;
import models.CommandParser;

/**
 *
 * @author Kurumin
 */
public class MessageInterpreter {
    
    private boolean nameSet;
    private boolean gameStarted;

    public MessageInterpreter() {
        nameSet = false;
        gameStarted = false;
    }
    
    public String interpretMessage(Player player, String msg, HashMap<String, Command> cmdMap){
        if(!nameSet && !gameStarted){
            CommandFactory cmdfactory = new CommandFactory();
            Command cmdname = cmdfactory.defaultNameCommand();
            CommandParser parser = new CommandParser(cmdMap);
            if(parser.isTargetCommand(msg, cmdname)){
                String[] msgArray = msg.split(" ", 2);
                if(msgArray.length < 2)
                    return "Type a name after the command!\n"+cmdname.getUsageText();
                
                msgArray[1] = msgArray[1].replace("\n", "");
                player.setName(msgArray[1]);
                nameSet = true;
                return "Nice to meet you, "+player.getName()+"!";
            }
            else{
                return "Sorry! You must set your name first.";
            }
        }
        if(nameSet && !gameStarted){
            return "Ok... we need to create a game server now.";
        }
        return "I don't know what to say.";
    }
    
    
}
