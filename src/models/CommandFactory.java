package models;

/**
 *
 * @author Kurumin
 */
public class CommandFactory {
    
    public Command defaultNameCommand(){
        Command name = new Command();
        
        name.setPrimary("/name");
        name.setPrimaryDescription("set up a new name for your character");
        return name;
    }
    
}
