/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*Use examples:
*       // -y-2x=7
*       // -3x+5x -2y+10y -6 = 0

x+y+z=2
2x+3y+5z=11
x-5y+6z=29
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
            // do until isExit is set to true be in login menu
            do{
                // get login menu with 3 options login register exit
                int loginOption = loginMenu.getMenu(); 
                
                switch (loginOption) {
                    case 1:
                        boolean isLoginSuccessful = loginMenu.login();
                        if(!isLoginSuccessful){
                            //if login is not successfull return to login menu
                            break;
                        }
                        User user = loginMenu.getUser();
                        boolean isAdmin = user.isAdministrator();
                        // set options menu for admin or regular user
                        optionsMenu.setMenu(isAdmin);
                        boolean isExitOptions = false;
                        
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
                                        boolean isSolved = equationMenu.SolveMatric();
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
