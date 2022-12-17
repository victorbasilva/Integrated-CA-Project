/*
 * Used to register new users
 */
package Menu;

import Users.User;
import Utility.InputUtilities;
import databaseConnector.DataBaseWriter;

public class RegistrationMenu {
    InputUtilities myKb = new InputUtilities();
    
    public RegistrationMenu(){}
    
    public boolean registerUser(){
        System.out.println("Register a New User by typing user details below");
        String name = myKb.getUserText("First Name: ");
        String surname = myKb.getUserText("Surname: ");
        String userName = myKb.getUserText("User Name: ");
        String password = myKb.getUserText("Password: ");
                
        User newUser = new User(userName, name, surname, password);
        
        return this.register(newUser);
    }
    
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
