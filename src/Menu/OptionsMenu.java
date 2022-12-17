/*
 * Class that extends Menu that will be used to present options for logged user 
 */
package Menu;

public class OptionsMenu extends Menu{
    
    // CONSTRUCTOR
    public OptionsMenu(){
        this.createOptionsMenu(); // create options menu
    }
    
    /**
     * Method that will create all menu items needed for Options Menu
     * and add them to menu items
     */
    private void createOptionsMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem profileItem = new MenuItem(2, "Profile Management", true);
        MenuItem reviewOperationsItem = new MenuItem(3, "Review used operations", true);
        MenuItem equationItem = new MenuItem(4, "Solve Equation", true);
        MenuItem userProfilesItem = new MenuItem(4, "List and remove users", true);
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(profileItem); // index: 1
        this.menuItems.add(reviewOperationsItem); //index: 2
        this.menuItems.add(equationItem); //index: 3
        this.menuItems.add(userProfilesItem); //index: 4
        
    }
    
    /**
     * Set menu differently if logged user is administrator or regular user.
     * Administrator have different options available
     * @param isAdmin -- true if logged user is administrator
     */
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
    * @return - user selected option as integer
    */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "MAIN Menu");
        return this.myKb.getUserOptionInRange("", 1, 4);
    }
}
