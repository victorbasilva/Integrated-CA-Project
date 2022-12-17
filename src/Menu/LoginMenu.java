/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Users.User;
import databaseConnector.DataBaseReader;

public class LoginMenu extends Menu{
    String userName = "";
    String password = "";
    String userNameText = "User Name: ";
    String passwordText = "Pasword: ";
    User loggedUser;
    
    /**
     * Constructor - creates login menu
     */
    public LoginMenu(){
        this.createLoginMenu();
    }
    
    /**
     * Create a login menu options with menu items.
     * Login menu is different when logged in and when no one is logged on
     * so use boolean to set is menu item displayed option
     */
    private void createLoginMenu(){
        // all possible menu items 
        MenuItem loginItem = new MenuItem(1, "Login", true);
        MenuItem registerItem = new MenuItem(2, "Register", true);
        MenuItem exitItem = new MenuItem(3, "Exit", true);
        MenuItem logoutItem = new MenuItem(4, "Logout", false);
        MenuItem enterMainMenuItem = new MenuItem(5, "Enter Main Menu", false);
        this.menuItems.add(loginItem); // index: 0 
        this.menuItems.add(registerItem); // index: 1
        this.menuItems.add(exitItem); // index: 2
        this.menuItems.add(logoutItem); // index: 3
        this.menuItems.add(enterMainMenuItem); // index: 4
    }
    
    /**
     * Menu is different when user is logged in
     * Show option to log out and hide register and 
     */
    private void setLoggedInMenu(){
        this.menuItems.get(0).setIsDisplayed(false); // hide login option
        this.menuItems.get(1).setIsDisplayed(false); // hide register option
        this.menuItems.get(3).setIsDisplayed(true); // show logout option
        this.menuItems.get(4).setIsDisplayed(true); // show main menu option
    }
    
    /**
     * Menu is different when user is logged in
     * Show option to log out and hide register and 
     */
    private void setNotloggedInMenu(){
        this.menuItems.get(0).setIsDisplayed(true); // show login option
        this.menuItems.get(1).setIsDisplayed(true); // show register option
        this.menuItems.get(3).setIsDisplayed(false); // hide logout option
        this.menuItems.get(4).setIsDisplayed(false); // hide main menu option
    }
    
    /**
     * Display all login menu options and return the option
     * @return 
     */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "LOGIN Menu");
        return this.getOption();
    }
    
    /**
     * Get user options and set a min and max value for available options
     * based if the user is logged on or not.
     * @return selected option by user as int
     */
    private int getOption(){
        int minValue = 3;
        int maxValue = 5;
        // different options are available when user is not logged in
        if(this.loggedUser == null){
            minValue = 1;
            maxValue = 3;
        }
        return this.myKb.getUserOptionInRange("", minValue, maxValue);
    }
    
    /** LOGIN **/
    /**
     * Method that will ask for username and password and then try to login user
     * @return true if successful login
     */
    public boolean login(){
        this.userName = myKb.getUserText(this.userNameText);
        this.password = myKb.getUserText(this.passwordText);
        return this.loginUser();
    }
    
    /**
     * Method that connects to database and try to login user by checking 
     * user credentials in database
     * @return true if success
     */
    private boolean loginUser(){
        DataBaseReader dbReader = new DataBaseReader();
        this.loggedUser = dbReader.loginToSystem(this.userName, this.password);
        if(this.loggedUser == null){
            System.out.println("Login failed. Please try again");
            System.out.println();
            return false;
        }
        // if login is successful replace login to be logout and remove register options
        this.setLoggedInMenu();
        return true;
    }
    
    /** LOGOUT **/
    /**
     * Will logout user. 
     * Reset currently logged user and change menu options after login 
     * @return true when finish
     */
    public boolean logout(){
        this.loggedUser = null;
        System.out.println("You have successfuly logout");
        System.out.println(); //print empty line
        // if logout is successful replace logout with login and add register options
        this.setNotloggedInMenu();
        return true;
    }
    
    /**
     * Getter to get currently logged user
     * @return - loggedUser as User
     */
    public User getUser(){
        return this.loggedUser;
    }
}
