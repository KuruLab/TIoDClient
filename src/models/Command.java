package models;

import java.util.HashMap;

/**
 *
 * @author Kurumin
 */
public class Command {
    
    private String primary, primaryDescription;
    private HashMap<String, String> arguments;
    private HashMap<String, String> description;
    
    //private int size;
    public Command() {
        arguments = new HashMap<>();
        arguments.put("-h", "");
        
        description = new HashMap<>();
        description.put("-h", "display this usage text");
    }

    public Command(String primary, String primaryDescription) {
        this();
        this.primary = primary;
        this.primaryDescription = primaryDescription;
    }
    
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getPrimaryDescription() {
        return primaryDescription;
    }

    public void setPrimaryDescription(String primaryDescription) {
        this.primaryDescription = primaryDescription;
    }

    public HashMap<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, String> arguments) {
        this.arguments = arguments;
    }

    public HashMap<String, String> getDescription() {
        return description;
    }

    public void setDescription(HashMap<String, String> description) {
        this.description = description;
    }

    public String getUsageText(){
        String result = primary+" : "+primaryDescription+"\n";
        for(String key : arguments.keySet()){
            result+= key+" : "+arguments.get(key)+" : "+description.get(key)+"\n";
        }
        return result;
    }
}
