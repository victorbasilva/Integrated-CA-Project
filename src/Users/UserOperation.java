/*
 * Class that holds all operations done by user
 */
package Users;

public class UserOperation {
    private String operation;
    private String solution;
    private String userName;
    private String name;
    private String surname;
    
    // CONSTRUCTORS
    public UserOperation(String operation, String solution, String userName, String name, String surname){
        this.operation = operation;
        this.solution  = solution;
        this.userName  = userName;
        this.name  = name;
        this.surname  = surname;
    }
    
    public UserOperation(String operation, String solution){
        this.operation = operation;
        this.solution  = solution;
    }
    
    // GETTERS
    public String getOperation(){
        return this.operation;
    }
    public String getSolution(){
        return this.solution;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    
    // SETTERS
    public void setOperation(String operation){
        this.operation = operation;
    }
    public void setSolution(String solution){
        this.solution = solution;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSurname(String surname){
        this.surname= surname;
    }
}
