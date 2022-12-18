/*
 * To handle user profile.
 */
package Menu;

import Users.User;
import databaseConnector.DataBaseWriter;

public class ProfileMenu extends Menu {
    // CONSTRUCTOR
    public ProfileMenu(){
        this.createProfileMenu(); // set the menu
    }
    
    /**
     * Create a profile menu options with menu items.
     */
    private void createProfileMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem changeNameItem = new MenuItem(2, "Edit Name", true);
        MenuItem changeSurnameItem = new MenuItem(3, "Edit Surname", true);
        MenuItem changePasswordItem = new MenuItem(4, "Change Password", true);
        // add them to array list of menu items        
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(changeNameItem); // index: 1
        this.menuItems.add(changeSurnameItem); // 2
        this.menuItems.add(changePasswordItem); // 3
    }
    
    /**
     * Display user menu.
     * Keep displaying until user select back (1) 
     * @param user - logged in user
     */
    public void useMenu(User user){
        boolean isBack = false;
        do{
            // display user data so we can check changes
            this.displayUserData(user);
            // get options menu with all available options 
            int selectedOption = this.getMenu(); 
            switch(selectedOption){
                case 1:
                    isBack = true;
                    break;
                case 2: 
                    String name = this.myKb.getUserText("Enter new Name:");
                    user.setName(name);
                    this.updateUser(user);
                    break;
                case 3: 
                    String surName = this.myKb.getUserText("Enter new Surname:");
                    user.setSurname(surName);
                    this.updateUser(user);
                    break;    
                case 4: 
                    String password = this.myKb.getUserText("Type new Password:");
                    user.setPassword(password);
                    this.updateUser(user);
                    break;
                default:
                    break;
            
            }
        }while(!isBack);
    }
    
    /**
     * Call a database Writer class that will update 
     * changes made to user
     * @param user -- currently logged in user
     * @return - true if success
     */
    private boolean updateUser(User user){
        DataBaseWriter dbWriter = new DataBaseWriter();
        boolean isRegistred = dbWriter.updateUser(user);
        if(!isRegistred){
            System.out.println("Updating user failed.");
            return false;
        }
        System.out.println("Profile was successfully updated.");
        return true;
    }
    
    /**
    * Display all profile menu options and return the option
    * @return - option (value) user selected as integer
    */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "PROFILE MENU");
        return this.myKb.getUserOptionInRange("", 1, 4);
    }
    
    /**
     * Will print user data on screen
     * @param user - currently logged in user
     */
    private void displayUserData(User user){
        System.out.println("Name: " +user.getName() + "|  Surname: " +user.getSurname());
    }
}
