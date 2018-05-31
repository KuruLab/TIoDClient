package models;

import java.util.HashMap;

/**
 *
 * @author Kurumin
 */
public class CommandParser {
    
    private HashMap<String, Command> cmdMap;

    public CommandParser(HashMap<String, Command> cmdMap) {
        this.cmdMap = cmdMap;
    }

    public HashMap<String, Command> getCmdMap() {
        return cmdMap;
    }

    public void setCmdMap(HashMap<String, Command> cmdMap) {
        this.cmdMap = cmdMap;
    }
    
    public boolean isTargetCommand(String text, Command target){
        String primary = target.getPrimary();
        if(text.contains(primary)){
            return true;
        }
        else{
            return false;
        }
    }
    
}
