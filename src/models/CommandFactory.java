/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
