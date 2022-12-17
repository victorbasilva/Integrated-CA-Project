/*
 * Class to writte to database
 */
package databaseConnector;

import Users.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseWriter {
    private Statement stmt;
    private Connection conn;
    private String dbName = "";
   
    // CONSTRUCTOR
    public DataBaseWriter(){}
    
    /**
     * Connect to database using DataBaseConnection class
     * @return -- true if successfully connected
     */
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
         } catch(Exception e){
            System.out.println("Error setting up connection: " +e.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * 
     * @param user
     * @return 
     */
    public boolean registerUser(User user){
        // if we didn't connect to database
        if(!this.connectToDatabase()){
            return false;
        }
        
        // if connected
        try{
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            this.stmt.execute(
                    String.format("INSERT INTO user (user, password, name, surname, accountTypeId, accountStatusId) "
                            + "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %d, %d) ;",
                            user.getUserName(),  user.getPassword(), user.getName(), user.getSurname(), user.getAccountTypeByBoolean(), user.getAccountStatusByBoolean())
            );
            return true;
        }catch(SQLException e){
            System.out.println("ERROR! "+e.getMessage());
            return false;
        }
    }
    
    /**
     * Connect to database and save user attempt
     * @param user
     * @param linearOperations
     * @param solution
     * @return 
     */
    public boolean saveAttempt(User user, String linearOperations, String solution){
        System.out.println("linearOperations "+linearOperations);
        System.out.println("solution "+solution);
        // if we didn't connect to database
        if(!this.connectToDatabase()){
            return false;
        }
        // when connected tr to write to db
        try{
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            // save tried operation
            this.stmt.execute(
                    String.format("INSERT INTO linear_operation (linearOperation, solution) "
                            + "VALUES (\"%s\", \"%s\") ;",
                            linearOperations,  solution)
            );
            
            ResultSet rs = stmt.executeQuery("SELECT * from linear_operation");
            int id =-1;
            while(rs.next()){
                String equations = rs.getString("linearOperation");
                String answer = rs.getString("solution");
                if((equations == null ? linearOperations == null : equations.equals(linearOperations)) && (answer == null ? solution == null : answer.equals(solution))){
                    id = rs.getInt("linearOperationId");
                }
            }
            rs.close();
            
            // save as user attempt
            if(id >= 0 ){
                this.stmt.execute(
                    String.format("INSERT INTO user_linear_operation (user, linearOperationId) "
                            + "VALUES (\"%s\", \"%s\") ;",
                            user.getUserName(), id )
                );
            }
            
            return true;
        }catch(SQLException e){
            System.out.println("ERROR! "+e.getMessage());
            return false;
        }
    }
    
    /**
     * Update user details
     * SQL update format is
     * UPDATE table_name
     * SET column1 = value1, column2 = value2, ...
     * WHERE condition;
     * @param user -- user we are updating
     * @return -- true if successful update 
     */
    public boolean updateUser(User user){
      // if we didn't connect to database
        if(!this.connectToDatabase()){
            return false;
        }
        // if user is active set id to 1 else to 0 
        int accountStatusId = user.isActive() ? 1 : 0;
        
        // if connected
        try{     
            String useDatabaseByName = "USE " + this.dbName;
            this.stmt.execute(useDatabaseByName);
            // update operation
            this.stmt.execute(
                    String.format("UPDATE user "
                            + " SET name = \"%s\" , surname = \"%s\" , password = \"%s\", accountStatusId = %d "
                            + " WHERE user = \"%s\" ",
                            user.getName(), user.getSurname(), user.getPassword(), accountStatusId, user.getUserName())
            );
            
            return true;
        }catch(SQLException e){
            System.out.println("ERROR! "+e.getMessage());
            return false;
        }
    }
    
}
