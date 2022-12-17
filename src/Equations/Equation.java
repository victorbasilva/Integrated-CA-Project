/*
* Class that represents an entire equation
* Exuation can have up to 3 variables and one constant
* Example 2x+y-z =4 or less for example: 5y-z=0 etc.
*/
package Equations;

import java.util.ArrayList;

public class Equation {
    // variables 
    private EquationMember xMember;
    private EquationMember yMember;
    private EquationMember zMember;
    // non variable member
    private double constant;
    
    // helper array list to group elements left and right of =
    private ArrayList<EquationMember> leftMembers;
    private ArrayList<EquationMember> rightMembers; 
    
    // Left and right members after sorting
    // on left side keep all variables and add all x, y and z pairs together 
    private ArrayList<EquationMember> sortedLeftMembers = new ArrayList<>();
    // on right side keep only values without variables
    private ArrayList<EquationMember> sortedRightMembers = new ArrayList<>();
    
    // helper array lists to group same variables together
    // For example if equation is 2x+3y-x=0  it will hold all x's together
    private ArrayList<EquationMember> xMembers = new ArrayList<>();
    private ArrayList<EquationMember> yMembers = new ArrayList<>();
    private ArrayList<EquationMember> zMembers = new ArrayList<>();
    private ArrayList<EquationMember> noVariableMembers = new ArrayList<>();
    
    // CONSTRUCTOR
    /**
     * Creates and object of equation class.
     * Initialize left and right members
     */
    public Equation(){
        this.leftMembers = new ArrayList<>();
        this.rightMembers = new ArrayList<>();
    }
    
    // GETTERS
    /**
     * Get all members of equation left of =
     * @return array list of EquationMember 
     */
    public ArrayList<EquationMember> getLeftMembers(){
        return this.leftMembers;
    }
    
    /**
     * Get all members of equation right of =
     * @return array list of EquationMember 
     */
    public ArrayList<EquationMember> getRightMembers(){
        return this.rightMembers;
    }
    /**
     * Get a x Member of equation
     * @return - xMember of equation as EquationMember
     */
    public EquationMember getXMember(){
        return this.xMember;
    }
    /**
     * Get a y Member of equation
     * @return - yMember of equation as EquationMember
     */
    public EquationMember getYMember(){
        return this.yMember;
    }
    /**
     * Get a z Member of equation
     * @return - zMember of equation as EquationMember
     */
    public EquationMember getZMember(){
        return this.zMember;
    }
    /**
     * Get a constant of equation
     * @return - constant of equation as EquationMember
     */
    public double getConstant(){
        return this.constant;
    }
    
    // SETTERS
    /**
     * Add a Equation Member to left members.
     * Left members are all values left from = in equation
     * @param member - a new value. It can be just a variable (ex. x or value -7x or constant 6)
     */
    public void addLeftMember(EquationMember member){
        this.leftMembers.add(member);
    }
    
    /**
     * Add a Equation Member to right members.
     * Right members are all values right from = in equation
     * @param member - a new value. It can be just a variable (ex. x or value -7x or constant 6)
     */
    public void addRightMember(EquationMember member){
        this.rightMembers.add(member);
    }
    
    // METHODS
    /**
     * Sort equation in a way that can be used inside a matrix.
     * Will Filter left and right members of equation and 
     * then add all values together that can be added together.
     * For example equation: 3y-2x+7-5z =8 
     * will be sorted into: -2x+3y-5z=1
     */
    public void sortEquation(){
        this.filterLeftMembers();
        this.filterRightMembers();
        this.addAllSameVariablesTogether();
    }
    
    /**
     * Find x, y, z variables on the left side of equation and values attached 
     * to them and add them
     * to xMembers, yMembers and zMembers ArrayList.
     * Do same for any constant (value without variable)
     */
    public void filterLeftMembers(){
        try{
            for(int i=0; i < this.leftMembers.size(); i++){
                EquationMember member = this.leftMembers.get(i);
                if(member.getVariable().contains("x")){
                    this.xMembers.add(member);
                } else if(member.getVariable().contains("y")){
                    this.yMembers.add(member);
                } else if(member.getVariable().contains("z")){
                    this.zMembers.add(member);
                }else{
                    // multiply to -1 so we move member from left to right side of =
                    member.setCoefficient(member.getCoefficient() * (-1));
                    // add to members without variables
                    this.noVariableMembers.add(member);
                }
            }
        }catch(Exception e){
            System.out.println("Error filtering equation left members: " + e);
        }
        
    }
    
    /**
     * Find x, y, z variables on right side of equation and values attached to them and add them
     * to xMembers, yMembers and zMembers ArrayList.
     * Do same for any constant (value without variable)
     * For right side members for variables first multiple value to -1 so we can move them to left side
     * of = sign.
     * Constants will stay right so don't multiply with -1
     */
    public void filterRightMembers(){
        try{
            for(int i=0; i < this.rightMembers.size(); i++){
                EquationMember member = this.rightMembers.get(i);
                if(member.getVariable().contains("x")){
                    // multiply to -1 when we move member from right to left side of =
                    member.setCoefficient(member.getCoefficient() * (-1));
                    this.xMembers.add(member);
                } else if(member.getVariable().contains("y")){
                    // multiply to -1 when we move member from right to left side of =
                    member.setCoefficient(member.getCoefficient() * (-1));
                    this.yMembers.add(member);
                } else if(member.getVariable().contains("z")){
                    // multiply to -1 when we move member from right to left side of =
                    member.setCoefficient(member.getCoefficient() * (-1));
                    this.zMembers.add(member);
                }else{
                    this.noVariableMembers.add(member);
                }
            }
        }catch(Exception e){
            System.out.println("Error filtering right members: " + e);
        }
    }
    
    /**
     * Add all x,y,z and constant values together to get final values for matrix.
     * Sort them so they can be used inside matrix
     */
    public void addAllSameVariablesTogether(){
        try{
            // set x
            double xCoeficient = this.xMembers.size() > 0 ? calculateCoefficient(this.xMembers) : 0;
            String xVariable = this.xMembers.size() > 0 ? this.xMembers.get(0).getVariable() : "x";
            this.xMember = new EquationMember(xCoeficient, xVariable);
            // set y
            double yCoeficient = this.yMembers.size() > 0 ? calculateCoefficient(this.yMembers) : 0;
            String yVariable = this.yMembers.size() > 0 ? this.yMembers.get(0).getVariable() : "y";
            this.yMember = new EquationMember(yCoeficient, yVariable);
            // set z
            double zCoeficient = this.zMembers.size() > 0 ? calculateCoefficient(this.zMembers) : 0;
            String zVariable = this.zMembers.size() > 0 ? this.zMembers.get(0).getVariable() : "z";
            this.zMember = new EquationMember(zCoeficient, zVariable);
            // add x, y and z to left side
            this.sortedLeftMembers.add(this.xMember);
            this.sortedLeftMembers.add(this.yMember);
            this.sortedLeftMembers.add(this.zMember);
            // set constant
            double constantCoeficient = this.noVariableMembers.size() > 0 ? calculateCoefficient(noVariableMembers) : 0;
            EquationMember noVariableMember = new EquationMember(constantCoeficient, "");
            // keep values without variables (constants) on right side of equation
            this.sortedRightMembers.add(noVariableMember);
            this.constant = noVariableMember.getCoefficient();
        }catch(Exception e){
            System.out.println("Exception sorting equation to work with matric: " + e);
        }
    }
    
    /**
     * Add all values that are grouped together to get 
     * final value to be used in matrix 
     * (for example all x's in equation, or all constants etc.)
     * @param members - each equation member that is grouped together
     * @return - a double value of added elements
     */
    private double calculateCoefficient(ArrayList<EquationMember> members){
        double newCoefficient = 0;
        try{
            for(int i=0; i < members.size(); i++){
                newCoefficient += members.get(i).getCoefficient();
            }
            return newCoefficient;
        }catch(Exception e){
            System.out.println("Exception calculateCoefficient: " + e);
            return newCoefficient;
        }
    }
    
    /**
     * List all members of equation.
     * Used to save a equation for data persistency
     * @param leftMembers - all left members
     * @param rightMembers - all right members of equation
     * @return all members as a space separated String
     */
    public String getEquation(ArrayList<EquationMember> leftMembers, ArrayList<EquationMember> rightMembers){
        String returnString = "";
        for(int i = 0; i<leftMembers.size(); i++){
            returnString += leftMembers.get(i).getStringAnnotation() + " ";
        }
        returnString += "= ";
        for(int i = 0; i< rightMembers.size(); i++){
            returnString += rightMembers.get(i).getStringAnnotation() + " ";
        }
        return returnString;
    }
}
