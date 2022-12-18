/*
 * Used to register new users
 */
package Menu;

import Users.User;
import Utility.InputUtilities;
import databaseConnector.DataBaseWriter;

public class RegistrationMenu extends Menu{
    
    // CONSTRUCTOR
    public RegistrationMenu(){}
    
    /**
     * Show register user options. 
     * User needs to set all options to be registered
     * @return true if success
     */    
    public boolean registerUser(){
        System.out.println("Register a New User by typing user details below");
        String name = myKb.getUserText("First Name: ");
        String surname = myKb.getUserText("Surname: ");
        String userName = myKb.getUserText("User Name: ");
        String password = myKb.getUserText("Password: ");
        // create a new useer from set values        
        User newUser = new User(userName, name, surname, password);
        // regisetr user (save it to database) and return true if success
        return this.register(newUser);
    }
    
    /**
     * 
     * @param newUser - user with all required properties set for registration
     * @return true if success
     */
    private boolean register(User newUser){
        DataBaseWriter dbWriter = new DataBaseWriter();
        boolean isRegistred = dbWriter.registerUser(newUser);
        if(!isRegistred){
            System.out.println("Registering user failed. Be sure there is no user with same user name in database and please try again");
            return false;
        }
        return true;
    }
}
