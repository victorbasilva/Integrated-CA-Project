/*
* For initial connection to database
* Will create tables if not existing
* Will set up initial data
*/
package databaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * 
 */
public class DatabaseCon {
    private Statement stmt;
    private Connection conn;
    private String dbName = "";
       
    // CONSTRUCTOR
    public DatabaseCon(){}
    
    /**
     * Set up database.
     * First creates an object DataBaseConnection class and use it to connect to database.
     * Then create tables and populate them with initial values
     * @return 
     */
    public boolean setupDatabase(){
        try{
            //create an object of DataBaseConnection class
            DataBaseConnection dBConnection = new DataBaseConnection();
            // first connect to database
            boolean isConnectedToDatabase = dBConnection.connectToDatabase();
            // stop program if we can't connect to database
            if(!isConnectedToDatabase){
                System.out.println("Can't connect to database");
                return false;
            }else{
                // if connected set statment, connection and db name
                this.conn = dBConnection.getConnection();
                this.stmt = dBConnection.getStatement();
                this.dbName = dBConnection.getDbName();
            }
            
            // then create database and tables if not created already
            boolean isCreated = this.createDatabase();
            if(!isCreated){
                System.out.println("Can't create database");
                return false;
            }
            
            // then INSERT starting values
            // INSERT values for account types if values already not exist
            boolean isAccountTypesCreated = this.createAccountTypes();
            if(!isAccountTypesCreated){
                System.out.println("Error inserting values to account types");
                return false;
            }
            // INSERT values for account statuses if values already not exist
            boolean isAccountStatusCreated = this.createAccountStatus();
            if(!isAccountStatusCreated){
                System.out.println("Error inserting values to account status");
                return false;
            }
            // INSERT administrator account to user table if not exists
            boolean isAdministratorAccountCreated = this.createAdministratorAccount();
            if(!isAdministratorAccountCreated){
                System.out.println("Error adding administrator account");
                return false;
            }
            
            // if everything is ok return true
            return true;
        } catch(Exception e){
            System.out.println("Error setting up database: " +e.getMessage());
            return false;
        }
    }
    
    /**
     * Will create tables if they don't exist
     * @return true if database is created
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public boolean createDatabase() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
       try{
            // Create database with dbName if not existing
            this.stmt.execute("CREATE SCHEMA IF NOT EXISTS " + this.dbName +";");
            // use created database
            this.stmt.execute("USE "+this.dbName+";");
            
            // Create table for Account Types
            this.stmt.execute(
                    "CREATE TABLE IF NOT EXISTS account_type ("
                            + "accountType VARCHAR(30),"
                            + "accountTypeId INT(10) NOT NULL PRIMARY KEY"
                            + ");"
            );
            
            // Create table for Account Status
            this.stmt.execute(
                    "CREATE TABLE IF NOT EXISTS account_status ("
                            + "accountStatus VARCHAR(30),"
                            + "accountStatusId INT(10) NOT NULL PRIMARY KEY"
                            + ");"
            );
            
            // Create table for users
            this.stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user ("
                            + "user VARCHAR(30) NOT NULL PRIMARY KEY,"
                            + "password VARCHAR(30),"
                            + "name VARCHAR(30),"
                            + "surname VARCHAR(30),"
                            + "accountTypeId INT(10),"
                            + "accountStatusId INT(10),"
                            + "FOREIGN KEY(accountTypeId) REFERENCES account_type(accountTypeId),"
                            + "FOREIGN KEY(accountStatusId) REFERENCES account_status(accountStatusId)"
                            + ");"
            );
            
            // Create table for storing Linear operations
            this.stmt.execute(
                    "CREATE TABLE IF NOT EXISTS linear_operation ("
                            + "linearOperation VARCHAR(200),"
                            + "solution VARCHAR(200),"
                            + "linearOperationId INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT"
                            + ");"
            );
            
            // Create table linking User to Linear operations
            this.stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_linear_operation ("
                            + "user VARCHAR(30) NOT NULL,"
                            + "linearOperationId INT(10) NOT NULL,"
                            + "FOREIGN KEY(user) REFERENCES user(user),"
                            + "PRIMARY KEY(user, linearOperationId),"
                            + "FOREIGN KEY(linearOperationId) REFERENCES linear_operation(linearOperationId)"
                            + ");"
            );
            
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Error creating database tables: "+e.getMessage());
            return false;
        }
    }
    
    /**
     * Insert set up values for Account types
     * @return - true if is success
     */
    public boolean createAccountTypes(){
        try{
            this.stmt.execute(
                    "INSERT IGNORE INTO account_type (accountTypeId, accountType)\n"
                    + "VALUES (0, 'admin'); \n"    
                         
            );
            this.stmt.execute(
                    "INSERT IGNORE INTO account_type (accountTypeId, accountType)\n" 
                        + "VALUES (1, 'user');"
            );
            return true;
        }catch (SQLException e){
            //e.printStackTrace();
            System.out.println("Error: "+e.getMessage());
            return false; 
        }      
    }
    
    /**
     * Insert values for Account status
     * @return  - true if is success 
     */
    public boolean createAccountStatus(){
        try{
            this.stmt.execute(
                   "INSERT IGNORE INTO account_status \n" 
                        + "VALUES ('inActive', 0);"
            );
            this.stmt.execute(
                   "INSERT IGNORE INTO account_status \n" 
                        + "VALUES ('active', 1);"
            );
            return true;
        }catch (SQLException e){
            System.out.println("Error: "+e.getMessage());
            return false; 
        }      
    }
    
    /**
     * Insert values for Administrator account into user table
     * @return  - true if is success
     */
    public boolean createAdministratorAccount(){
        try{
            this.stmt.execute(
                    "INSERT IGNORE INTO user (user, password, name, surname, accountTypeId, accountStatusId )\n" 
                        + "VALUES ('CCT', 'Dublin', 'Admin', 'Account', 0, 1);"
            );
            return true;
        }catch (SQLException e){
            System.out.println("Error: "+e.getMessage());
            return false; 
        }      
    }
}
