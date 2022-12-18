/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseConnector;

import Users.User;
import Users.UserOperation;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBaseReader {
    private Statement stmt;
    private Connection conn;
    private String dbName = "";
    
    public DataBaseReader(){
        this.connectToDatabase();
    }
    
    public boolean connectToDatabase(){
         try{
            DataBaseConnection newDbConnection = new DataBaseConnection();
            // first connect to database
            boolean isConnectedToDatabase = newDbConnection.connectToDatabase();
            // stop program if we can't connect to database
            if(!isConnectedToDatabase){
                System.out.println("Can't connect to database");
                return false;
            }else{
                this.conn = newDbConnection.getConnection();
                this.stmt = newDbConnection.getStatement();
                this.dbName = newDbConnection.getDbName();
            }
         } catch(ClassNotFoundException | IllegalAccessException | InstantiationException e){
            System.out.println("Error setting up database: " +e.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * Login to system. Takes user name and password.
     * Find first username in database to check if user exist
     * Then match his password with provided password 
     * * @param _userName - user name user types in when login
     * @param _password - password user types in when login
     * @return user if logged in else null 
     */
    public User loginToSystem(String _userName, String _password){
        try{
            // use this database
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            // get all users
            ResultSet rs = stmt.executeQuery("SELECT * from user");
            // while there is record (row in table)
            while(rs.next()){
                // get user name
                String userName = rs.getString("user");
                // if we find user in table on this record
                if(userName == null ? _userName == null : userName.equals(_userName)){
                    // read his password saved in database
                    String password = rs.getString("password");
                    // if password matches password provided by user
                    if(password == null ? _password == null : password.equals(_password)){
                        // successfull login 
                        System.out.println("You have successfully login");
                        // get all values to create new object of user class
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        int accountTypeId = rs.getInt("accountTypeId");
                        int accountStatusId = rs.getInt("accountStatusId");       
                        boolean isActive = accountStatusId == 1;
                        boolean isAdmin = accountTypeId == 0;
                        // create new user with values from database
                        User loggedUser = new User(userName,name,surname);
                        loggedUser.setIsActive(isActive);
                        loggedUser.setIsAdministrator(isAdmin);
                        return loggedUser; // return user
                    }else{
                        System.out.println("Invalid password.");
                        return null;
                    }
                }
            }
            rs.close();
            return null;
        }catch (SQLException e) {
           // e.printStackTrace();
            System.out.println("Failed to login. Please try again. Error: " +e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all users from database and return them in an ArrayList of users
     * If parameter isOnlyDeleted is used return only deleted users
     * @param isOnlyDeleted
     * @return 
     */
    public ArrayList<User> getUsers(boolean isOnlyDeleted){
        try{
            ArrayList<User> returnUsers = new ArrayList<>();
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            
            ResultSet rs = isOnlyDeleted ? stmt.executeQuery("SELECT * from user WHERE accountStatusId = 0"): stmt.executeQuery("SELECT * from user");
            while(rs.next()){
                String userName = rs.getString("user");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int accountTypeId = rs.getInt("accountTypeId");
                int accountStatusId = rs.getInt("accountStatusId");       
                boolean isActive = accountStatusId == 1;
                boolean isAdmin = accountTypeId == 0;
                User newUser = new User(userName, name, surname, password);
                newUser.setIsActive(isActive);
                newUser.setIsAdministrator(isAdmin);
                returnUsers.add(newUser);
            }
            rs.close();
            return returnUsers;
        }catch (SQLException e) {
           // e.printStackTrace();
            System.out.println("Failed to get users. Please try again. Error: " +e.getMessage());
            return null;
        }
    }
    
    /**
     * Return all operations that all users did and their solutions
     * @return 
     */
    public ArrayList<UserOperation> getAllUsersOperations(){
        try{
            ArrayList<UserOperation> userOperations = new ArrayList<>();
            
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            // go trough all results
            try ( // Select username, name, surname operation and solution for all active users
                    ResultSet rs = stmt.executeQuery("SELECT u.user, u.name, u.surname, o.linearOperation, o.solution "
                            + " FROM user_linear_operation uo "
                            + " LEFT JOIN user u "
                            + " ON u.user = uo.user "
                            + " LEFT JOIN linear_operation o "
                            + " ON o.linearOperationId = uo.linearOperationId "
                            + " WHERE u.accountStatusId = 1")) {
                // go trough all results
                while(rs.next()){
                    String user = rs.getString("user");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String operation = rs.getString("linearOperation");
                    String solution = rs.getString("solution");
                    
                    UserOperation newOperation = new UserOperation(operation, solution, user, name, surname);
                    userOperations.add(newOperation);
                }
            }
            return userOperations;
        }catch (SQLException e) {
           // e.printStackTrace();
            System.out.println("Failed to get users. Please try again. Error: " +e.getMessage());
            return null;
        }
    }
    
    /**
     * Get all operations that currently logged in user did
     * @param user
     * @return 
     */
    public ArrayList<UserOperation> getUserOperations(User user){
        try{
            ArrayList<UserOperation> userOperations = new ArrayList<>();
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            // Select username, name, surname operation and solution for all active users
            ResultSet rs = stmt.executeQuery("SELECT o.linearOperation, o.solution "
                    + " FROM user_linear_operation uo "
                    + " LEFT JOIN user u "
                    + " ON u.user = uo.user "
                    + " LEFT JOIN linear_operation o "
                    + " ON o.linearOperationId = uo.linearOperationId "
                    + " WHERE u.user = '" +user.getUserName() +"' ");
            // go trough all results
            while(rs.next()){
                String operation = rs.getString("linearOperation");
                String solution = rs.getString("solution");
                
                UserOperation newOperation = new UserOperation(operation, solution);
                userOperations.add(newOperation);
            }
            rs.close();
            return userOperations;
        }catch (SQLException e) {
           // e.printStackTrace();
            System.out.println("Failed to get users. Please try again. Error: " +e.getMessage());
            return null;
        }
    }
    
    
}
