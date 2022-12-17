/*
 * Menu for all equation and equation options
 */
package Menu;

import Equations.Equation;
import Equations.Matric2x2;
import Equations.Matric3x3;
import Users.User;
import Utility.StringHelper;
import databaseConnector.DataBaseWriter;
import java.util.ArrayList;

public class EquationMenu extends Menu{
    // use StringHelper class to get equation from user input
    StringHelper equationStringHelper = new StringHelper();
    // create array list to store linear equations
    ArrayList<Equation> linearEquations = new ArrayList<>();
    // for 2x2 matric
    Matric2x2 matric2x2;
    // for 3x3 matric
    Matric3x3 matric3x3;
    // number of equations as int
    int numberOfEquations;
    // determinant
    double determinant;
    
    // CONSTRUCTOR
    public EquationMenu(){
        // set all items for menu on init
        this.setMenuItems();
    }
    
    /**
     * Method that sets all menu items
     */
    private void setMenuItems(){
       // all possible menu items 
        MenuItem firstEquationSystem = new MenuItem(1, "Select 2x2 Linear Equations System", true);
        MenuItem secondEquationSystem = new MenuItem(2, "Select 3x3 Linear Equations System", true);
        MenuItem backItem = new MenuItem(3, "Back To Main Menu", true);
        this.menuItems.add(firstEquationSystem);
        this.menuItems.add(secondEquationSystem);
        this.menuItems.add(backItem);
    }
    
    /**
     * Display all menu options and return the option
     * @return user option as integer (either 1 or 2)
     */
    public int getMenu(){
        this.displayMenuItems(this.menuItems, "EQUATIONS Menu");
        int userOption = this.myKb.getUserOptionInRange("", 1, 3); // prevent user option other then 1 , 2, 3
        // set number of equations based on user option
        this.numberOfEquations =  userOption == 1 ? 2 : 3;
        return userOption;
    }
    
    public void addEquations(){
        // if there are existing linear equations clear them
        if(this.linearEquations.size() > 0){
            this.linearEquations.clear();
        }
        // ask user to add new linear equations for selected numberOfEquations
        for(int i=1; i <= this.numberOfEquations; i++){
            // Ask user to write equation
            String userInput = this.myKb.getUserEquationInput("Write " + i + ". equation: ", this.numberOfEquations == 3);
            // use string helper class to get linear equation from string
            Equation linearEquation = this.equationStringHelper.getMembers(userInput);
            
            // Sort linear equation to get format x + y + z = constant 
            linearEquation.sortEquation();
            // add each linear equation to list of linear equations
            this.linearEquations.add(linearEquation);
         }
    }
    
    /**
     * Public method to solve matrix
     * If there are 2 equations it will solve 2x2 matrix system.
     * If there are 2 equations solve 3x3 matrix system
     * @return true if successful
     */
    public boolean SolveMatric(){
        // if there are 2 equations
        if(this.numberOfEquations == 2){
            //if is 2x2 solve 2x2 matric
            this.matric2x2 = new Matric2x2();
             // set size of matric based on user selection
            this.matric2x2.setMatricColumns(this.numberOfEquations);
            this.matric2x2.setMatricRows(this.numberOfEquations);
            this.matric2x2.setMatricOrder(this.numberOfEquations, this.numberOfEquations);
            //set matric members
            this.matric2x2.setMatricMembers(this.matric2x2.getMatricRows(), this.matric2x2.getMatricColumns(), this.linearEquations);
            // return true if solved
            return this.matric2x2.solve2x2Matric();
        }
        
        // else solve 3x3 matric
        this.matric3x3 = new Matric3x3();
        // set size of matric based on user selection
        this.matric3x3.setMatricColumns(this.numberOfEquations);
        this.matric3x3.setMatricRows(this.numberOfEquations);
        this.matric3x3.setMatricOrder(this.numberOfEquations, this.numberOfEquations);
        //set matric members
        this.matric3x3.setMatricMembers(this.matric3x3.getMatricRows(), this.matric3x3.getMatricColumns(), this.linearEquations);
        // set matric of signs for 3x3 matric
        this.matric3x3.setMatricOfSigns();
        // return true if solved
        return this.matric3x3.solve3x3Matric();
    }
    
    /**
     * Save Attempt of solving Linear equations to database
     * @param user - current user
     * @return - true if save successful
     */
    public boolean saveAttempt(User user){
        String linearOperation = "";
        // get all equations used and concat them to string
        for(int i = 0; i< this.linearEquations.size(); i++){
            Equation currentEquation = this.linearEquations.get(i);
            linearOperation += currentEquation.getEquation(currentEquation.getLeftMembers(), currentEquation.getRightMembers());
            // if is not a last operation
            if(i< this.linearEquations.size()-1){
                //add comma sparator
                linearOperation += ", ";
            }
        }
        // add solution to solution string
        String solution = (this.numberOfEquations == 2) ? this.matric2x2.getSolution() : this.matric3x3.getSolution();
        // get a new object of database writer
        DataBaseWriter dbWriter = new DataBaseWriter();
        // save attempt
        boolean isAttemptSaved = dbWriter.saveAttempt(user, linearOperation, solution);
        // if there is an error saving
        if(!isAttemptSaved){
            // display message and return false
            System.out.println("Saving attempt failed");
            return false;
        }
        // if success return true
        return true;
    }

}
