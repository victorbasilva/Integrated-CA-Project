/*
 * User Class
 */
package Users;

public class User {
    private String userName;
    private String name;
    private String surname;
    private String password;
    private boolean isAdministrator = false;
    private boolean isActive = true;
    private String accountType;
    private String accountStatus;
    
    // CONSTRUCTORS 
    public User(){}
    
    public User(String userName, String name, String surname){
        this.userName = userName;
        this.name = name;
        this.surname = surname;
    }
    
    public User(String userName, String name, String surname, String password){
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
    
    public User(String userName, String name, String surname, String accountType, String accountStatus ){
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }
    
    // SETTERS 
    public void setName(String name){
        this.name = name;
    }
    
    public void setSurname(String surname){
        this.surname = surname;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setAccountType(String accountType){
        this.accountType = accountType;
    }
    
    public void setAccountStatus(String accountStatus){
        this.accountStatus = accountStatus;
    }
    
    public void setIsActive(boolean _isActive){
        this.isActive = _isActive;
    }
    
    public void setIsAdministrator(boolean _isAdministrator){
        this.isAdministrator = _isAdministrator;
    }
     
    // GETTERS
    public String getAccountType(){
        return this.accountType;
    }
    
    public String getAccountStatus(){
        return this.accountStatus;
    }
        
    public boolean isActive(){
        return this.isActive;
    }
    
    public boolean isAdministrator(){
        return this.isAdministrator;
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
    
    public String getPassword(){
        return this.password;
    }
    
    public int getAccountTypeByBoolean(){
        if(this.isAdministrator){
            return 0;
        }
        return 1;
    }
    
    public int getAccountStatusByBoolean(){
        if(!this.isActive){
            return 0;
        }
        return 1;
    }
}
