/*
 *Class to connect to database
 */
package databaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    // Connection string
    private final String dbName = "calculator";
    private final String DB_LOCATION = "jdbc:mysql://localhost/";
    private final String DB_URL = DB_LOCATION + dbName;
    private final String USER = "root";
    private final String PASS = "root";
    
    private Connection conn;
    private Statement stmt;
    
    public DataBaseConnection(){}
    
    /**
     * Used to connect to database
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public boolean connectToDatabase() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // create new instance of class 
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try{
            // get database connection using location, user and password
            this.conn = DriverManager.getConnection(this.DB_LOCATION, this.USER, this.PASS);
            // create statement
            this.stmt = conn.createStatement();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Can't connect to database: "+e);
            return false;
        }
    }
    
    /**
     * Get a connection with database
     * @return connection as Connection
     */
    public Connection getConnection(){
        return this.conn;
    }
    
    /**
     * get statement created from connection 
     * @return statement as Statement
     */
    public Statement getStatement(){
        return this.stmt;
    }
    
    //GETTERS
    /**
     * Get database name
     * @return database name as String
     */
    public String getDbName(){
        return this.dbName;
    }
    
    public Connection getConn(){
        return this.conn;
    }
    public Statement getStmt(){
        return this.stmt;
    }
    
}
