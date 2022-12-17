/*
 * Handle display of all user operations saved to db
 */
package Menu;

import Users.User;
import Users.UserOperation;
import databaseConnector.DataBaseReader;
import java.util.ArrayList;

public class UserOperationsMenu extends Menu{
    ArrayList<UserOperation> userOperations = new ArrayList<>();
       
    public UserOperationsMenu(){
        this.createUserOperationsMenu();
    }
    
    private void createUserOperationsMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem listOperationsItem = new MenuItem(2, "List all operations", true);
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(listOperationsItem); // index: 1
    }
    
    /**
     * Display user management menu. 
     * In do while loop so it is displayed until Back option is selected
     * Menu displays all current users and add ability to delete user
     * @param user
    */
    public void useMenu(User user){
        boolean isBack = false;
        do{
            // get operations menu with all available options 
            int selectedOption = this.getMenu(); 
            // show menu options
            switch(selectedOption){
                case 1:
                    isBack = true;
                    break;
                case 2: 
                    boolean isAdmin = user.isAdministrator();
                    // if is administrator list operations by all users else by current user
                    boolean isGotOperations = isAdmin ? this.getAllUsersOperations() : this.getCurrentUserOperations(user);
                    if(!isGotOperations){
                        System.out.println("No operations found. Please try again.");
                        // go back in menu
                        break;
                    }
                    //display results
                    this.listOperations(this.userOperations, isAdmin);
                    break;
                default:
                    break;
            
            }
        }while(!isBack);
    }
    
    /**
    * Display all profile menu options and return the option
    * @return 
    */
    private int getMenu(){
        this.displayMenuItems(this.menuItems, "REVIEW OPERATIONS MENU");
        return this.myKb.getUserOptionInRange("", 1, 2);
    }
    
    /**
     * Get all users operations and solutions from database.
     * @return true if successful fetch from database 
     */
    public boolean getAllUsersOperations(){
        DataBaseReader dbReader = new DataBaseReader();
        this.userOperations = dbReader.getAllUsersOperations();
        if(this.userOperations == null){
            return false;
        }       
        return true;
    }
    
    /**
     * Get current user operations and solutions from database.
     * @param user -- current user
     * @return true if successful fetch from database 
     */
    public boolean getCurrentUserOperations(User user){
        DataBaseReader dbReader = new DataBaseReader();
        this.userOperations = dbReader.getUserOperations(user);
        if(this.userOperations == null){
            return false;
        }       
        return true;
    }
    
    /**
     * List all operations and users 
     * @param _userOperations - array list of all users operations we want to display
    */
    private void listOperations(ArrayList<UserOperation> _userOperations, boolean isAdmin){
        //get some space
        System.out.println();
        System.out.println(isAdmin ? "ALL EQUATIONS BY USERS:" : "ALL EQUATIONS YOU DID:");
        // if there are operations found
        if((_userOperations != null) && (_userOperations.size() > 0)){
            for(int i=0; i< _userOperations.size(); i++){
                // get each operation
                UserOperation userOperation = _userOperations.get(i);
                // string to format display of each user opeartion
                String str = "equations: " + userOperation.getOperation() + ", solution: " + userOperation.getSolution();
                // for admin we show list of all operations by all users so display user information
                if(isAdmin){
                    str += ", username: " + userOperation.getUserName() + ", name: " + userOperation.getName() + ", surname: "+userOperation.getSurname();
                }
                // print each row 
                System.out.println(str);
            }
        }else{
            System.out.println("No user operations");
        }
        //add free space
        System.out.println();
    }

    
}
