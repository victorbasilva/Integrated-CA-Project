/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

public class OptionsMenu extends Menu{
    
    public OptionsMenu(){
        this.createOptionsMenu();
    }
    
    private void createOptionsMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem profileItem = new MenuItem(2, "Profile Management", true);
        MenuItem reviewOperationsItem = new MenuItem(3, "Review used operations", true);
        MenuItem equationItem = new MenuItem(4, "Solve Equation", true);
        MenuItem userProfilesItem = new MenuItem(4, "List and remove users", true);
        
        
        
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(profileItem); // index: 1
        this.menuItems.add(reviewOperationsItem); // 2
        this.menuItems.add(equationItem); // 3
        this.menuItems.add(userProfilesItem); // 4
        
    }
    
    public void setMenu(boolean isAdmin){
        // shove admin specific options
        if(isAdmin){
            this.menuItems.get(4).setIsDisplayed(true); // add user management options for admin 
            this.menuItems.get(3).setIsDisplayed(false); // don't allow equation solving for admin
        }else{
            // shove non admin specific options
            this.menuItems.get(3).setIsDisplayed(true); // allow equation solving for non admin
            this.menuItems.get(4).setIsDisplayed(false); // hide user management options for non admin user
        }
    }
    
    /**
    * Display all main menu options and return the option
    * @return 
    */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "MAIN Menu");
        return this.myKb.getUserOptionInRange("", 1, 4);
    }
}
