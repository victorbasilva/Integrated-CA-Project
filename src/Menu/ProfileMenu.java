/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Users.User;
import databaseConnector.DataBaseWriter;

public class ProfileMenu extends Menu {
    public ProfileMenu(){
        this.createProfileMenu();
    }
    
    private void createProfileMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem changeNameItem = new MenuItem(2, "Edit Name", true);
        MenuItem changeSurnameItem = new MenuItem(3, "Edit Surname", true);
        MenuItem changePasswordItem = new MenuItem(4, "Change Password", true);
                
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(changeNameItem); // index: 1
        this.menuItems.add(changeSurnameItem); // 2
        this.menuItems.add(changePasswordItem); // 3
    }
    
    public void useMenu(User user){
        boolean isBack = false;
        do{
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
    * @return 
    */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "PROFILE MENU");
        return this.myKb.getUserOptionInRange("", 1, 4);
    }
}
