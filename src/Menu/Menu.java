/*
 * Class to handle menu and meu questions
 */
package Menu;

import Utility.InputUtilities;
import java.util.ArrayList;

public class Menu {
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    InputUtilities myKb = new InputUtilities();
    /**
     * CONSTRUCTOR
    */
    public Menu(){}

    /**
     * Displays menu item for each menu item
     * @param items -- array list of menu items as ArrayList of MenuItems
     * @param menuName --
     */
    public void displayMenuItems(ArrayList<MenuItem> items, String menuName){
        System.out.println("****** "+ menuName + " ******");
        for(int i=0; i<items.size(); i++){
            items.get(i).displayMenuItem();
        }
    }
    
    /**
     * Helper method to display message on menu
     * @param message - message to display
     */
    public void showMessage(String message){
        System.out.println(message);
    }
   
    /**
     * Shows welcome message
     * @param user - currently logged user
     */
    public void showWelcomeMessage(String user){
        System.out.println("Welcome to Linear Equations Calculator " +user);
    }
    
}
