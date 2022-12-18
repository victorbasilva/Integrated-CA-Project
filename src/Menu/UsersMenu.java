/*
 * Display all users
 * Delete user
 */
package Menu;

import Users.User;
import databaseConnector.DataBaseReader;
import databaseConnector.DataBaseWriter;
import java.util.ArrayList;

public class UsersMenu extends Menu{
    ArrayList<User> users = new ArrayList<>();
    ArrayList<User> deletedUsers = new ArrayList<>();
    
    public UsersMenu(){
        this.createUsersMenu();
    }
    
    private void createUsersMenu(){
        // all possible menu items 
        MenuItem backItem = new MenuItem(1, "Back", true);
        MenuItem deleteUserItem = new MenuItem(2, "Delete User", true);
        MenuItem restoreDeleteUserItem = new MenuItem(3, "Restore Deleted User", true);                
        this.menuItems.add(backItem); // index: 0
        this.menuItems.add(deleteUserItem); // index: 1
        this.menuItems.add(restoreDeleteUserItem); // index: 2
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
            // get all users so we can display them
            boolean isUsers = this.getUsers(false);
            // if there are no users remove option to delete user
            this.menuItems.get(1).setIsDisplayed(isUsers);
            // display all users (or message: no users,  if there are none)
            this.displayUsers(this.users, true);
            // get user menu with all available options 
            int selectedOption = this.getMenu(); 
            // show menu options
            switch(selectedOption){
                case 1:
                    isBack = true;
                    break;
                case 2: 
                    // only administrator can delete user
                    if(user.isAdministrator()){
                        // handle delete
                        String userName = this.myKb.getUserText("To delete user type username of user: ");
                        // find user
                        User userForUpdate = this.getUserByUserName(userName, this.users);
                        if(userForUpdate == null){
                            System.out.println("No user with user name "+userName +" found. Please try again.");
                            // go back in menu
                            break;
                        }
                        // set deleted user as inactive 
                        userForUpdate.setIsActive(false);
                        this.updateUser(userForUpdate);
                    }else{
                        System.out.println("You don't have permision to delete user");
                    }
                    break;
                case 3:
                    // only administrator can restore user
                    if(user.isAdministrator()){
                        boolean isDeletedUsers = this.getUsers(true);
                        if(!isDeletedUsers){
                            System.out.println("There are no deleted users available for restore");
                            break;
                        }
                        // display all users (or message: no users,  if there are none)
                        this.displayUsers(this.deletedUsers, false);
                        // display deleted users
                        // handle restore
                        // first get user to restore
                        String userName = this.myKb.getUserText("To restore user type username of deleted user: ");
                        // find user from deleted users
                        User userForUpdate = this.getUserByUserName(userName, this.deletedUsers);
                        if(userForUpdate == null){
                            System.out.println("No user with user name "+userName +" found. Please try again.");
                            // go back in menu
                            break;
                        }
                        // set restored user as active 
                        userForUpdate.setIsActive(true);
                        // if user is found update it
                        this.updateUser(userForUpdate);
                        
                    }else{
                        System.out.println("You don't have permision to restore deleted user");
                    }
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
        this.displayMenuItems(this.menuItems, "USERS MANAGEMENT MENU");
        return this.myKb.getUserOptionInRange("", 1, 3);
    }
    
    /**
     * Get users from database.
     * If isDeleted is used return only deleted users
     * @param isOnlyDeleted
     * @return 
     */
    public boolean getUsers(boolean isOnlyDeleted){
        DataBaseReader dbReader = new DataBaseReader();
        if(isOnlyDeleted){
            this.deletedUsers = dbReader.getUsers(isOnlyDeleted);
            if(this.deletedUsers == null){
                return false;
            }//deletedUsers
        } else{
            this.users = dbReader.getUsers(isOnlyDeleted);
            if(this.users == null){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Display all users from parameter _users 
     * @param _users - array list of users we want to display
     * @param isActive -- if true display only active users if false display only inactive users
     */
    private void displayUsers(ArrayList<User> _users, boolean isActive){
        //get some space
        System.out.println();
        System.out.println(isActive? "USERS:" : "DELETED USERS: ");
        if((_users != null) && (_users.size() > 0)){
            for(int i=0; i< _users.size(); i++){
                User user = _users.get(i);
                // only display active or inactive users based on isActive
                if(user.isActive() == isActive){
                    // string to format display of each user
                    String userString = "username: " + user.getUserName() + ", name: " + user.getName() + ", surname: "+user.getSurname();
                    // if user is administrator show it
                    userString += user.isAdministrator() ? " ADMINISTRATOR" : "";
                    // print users 
                    System.out.println(userString);
                }
                
            }
        }else{
            System.out.println("No users");
        }
        //add free space
        System.out.println();
        
    }
    
    /**
     * Returns user by user name
     * @param userName -- user name we are searching for
     * @param _users -- an array of all users
     * @return user as User, null if we couldn't find user
     */
    private User getUserByUserName(String userName, ArrayList<User> _users){
        // if there are users
        if(_users.size()>0){
            // go trough all users
            for(int i=0; i < _users.size(); i++){
                User tempUser = _users.get(i);
                // find a user
                if(tempUser.getUserName().equals(userName)){
                    // if user is found return it
                    return tempUser;
                }
            }
        }
        // if no user found return null
        return null;
    }
}
