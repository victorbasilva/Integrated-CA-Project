/*Use examples:

1) 2𝑥−3𝑦−2=0
   3𝑥+8𝑦-3=0


2) x+𝑦+𝑧=2
   2𝑥+3𝑦+5𝑧=11
   𝑥−5𝑦+6𝑧=29

 */

package linearequationscalculator;

import Menu.EquationMenu;
import Menu.LoginMenu;
import Menu.OptionsMenu;
import Menu.ProfileMenu;
import Menu.RegistrationMenu;
import Menu.UserOperationsMenu;
import Menu.UsersMenu;
import Users.User;
import databaseConnector.DatabaseCon;


/**
 *
 * @authors Gabriel Eugenio 2021240 and Victor Silva 2021259
 */
public class LinearEquationsCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Connect to database and create tables and add values
            DatabaseCon myDatabase = new DatabaseCon();
            boolean isDatabaseSetUp = myDatabase.setupDatabase();
            if(!isDatabaseSetUp){
                // if we can't connect to database break application
                return;
            }
            
            // create login menu
            LoginMenu loginMenu = new LoginMenu();
            // create registration menu
            RegistrationMenu registrationMenu = new RegistrationMenu();
            // create Equations menu
            EquationMenu equationMenu = new EquationMenu();
            // create Options Menu -- to hold main menu options
            OptionsMenu optionsMenu = new OptionsMenu();
            // create Profile Menu to handle user profile
            ProfileMenu profileMenu = new ProfileMenu();
            // create menu for users Management
            UsersMenu usersManagementMenu = new UsersMenu();
            // menu for user operations
            UserOperationsMenu userOperationsMenu = new UserOperationsMenu();
            
            // to exit application if is true
            boolean isExit = false;
            // set initially to true so we can set user like it was logout before
            boolean isLoggout = true;
            boolean isExitOptions = false;
            // do until isExit is set to true be in login menu
            do{
                // get login menu with 3 options login register exit
                int loginOption = loginMenu.getMenu(); 
                
                switch (loginOption) {
                    case 5:
                    case 1:
                        if(isLoggout){
                            boolean isLoginSuccessful = loginMenu.login();
                            if(!isLoginSuccessful){
                                //if login is not successfull return to login menu
                                break;
                            }
                        }
                        User user = loginMenu.getUser();
                        loginMenu.showWelcomeMessage(user.getName());
                        boolean isAdmin = user.isAdministrator();
                        
                        // set options menu for admin or regular user
                        optionsMenu.setMenu(isAdmin);
                        // reset to false
                        isExitOptions = false;
                        //set to fals so coming back to menu don't trigger login again
                        isLoggout = false;
                        
                        // until isExitOptions is set to true stay inside options menu
                        do{
                            // get options menu with all available options 
                            int selectedOption = optionsMenu.getMenu(); 
                            switch(selectedOption){
                                case 1: 
                                    isExitOptions = true; // go back to previous menu
                                    break;
                                case 2: 
                                    profileMenu.useMenu(user);
                                    break;
                                case 3: 
                                    userOperationsMenu.useMenu(user);
                                    break;
                                case 4: 
                                    // forth option when logged in as admin is different then when logged in as regular user
                                    if(isAdmin){
                                        // show User management to admin
                                        usersManagementMenu.useMenu(user);
                                        break;
                                    }else{
                                        // show solve equations to non admin user
                                        int eqautionMenuOption = equationMenu.getMenu();
                                        // if option 3 - back to main menu is selected break
                                        if(eqautionMenuOption == 3){
                                            break;
                                        }
                                        // else solve the matric
                                        // first ask user to type in equations
                                        equationMenu.addEquations();
                                        // then add them to matric and solve it
                                        boolean isSolved = equationMenu.SolveMatrix();
                                        if(!isSolved){
                                            break;
                                        }
                                        // if equation is solved save attempt
                                        equationMenu.saveAttempt(user);
                                        break;
                                    }
                                    
                                default: 
                                    break;
                            }
                            
                        }while(!isExitOptions);
                        break;
                    case 2:
                        registrationMenu.registerUser();
                        break;
                    case 3:
                        isExit = true;
                        break;
                    case 4:
                        isLoggout = true;
                        loginMenu.logout();
                        break;
                    default:
                        break;
                }
            } while(!isExit);
        
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        } 
    }
    
}
