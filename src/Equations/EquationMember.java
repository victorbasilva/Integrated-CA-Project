/*
 * Class that represent each member of equation
 */
package Equations;

public class EquationMember {
    private double coefficient; // value 
    private String variable; // variable

    // CONSTRUCTORS
    public EquationMember(){}
    /**
     * Create an object of EquationMember
     * @param _coefficient - value as double
     * @param _variable - variable name as String (x,y,z)
     */
    public EquationMember(double _coefficient, String _variable){
        this.coefficient = _coefficient;
        this.variable = _variable;
    }
    
    //GETTERS
    /**
     * Get a value (coefficient)
     * @return a coefficient as double
     */
    public double getCoefficient(){
        return this.coefficient;
    }
    /**
     * Get a variable
     * @return a variable as String
     */
    public String getVariable(){
        return this.variable;
    }
    
    /**
     * Get coefficient and variable as a String
     * @return - coefficient and variable as a String 
     */
    public String getStringAnnotation(){
        return coefficient + "" + variable;
    }
    
    // SETTERS
    /**
     * Set the coefficient
     * @param _coefficient - value as double 
     */
    public void setCoefficient(double _coefficient){
        this.coefficient = _coefficient;
    }
    
    /**
     * Set variable name(x,y,z)
     * @param _variable -- variable name as string
     */
    public void setVariable(String _variable){
        this.variable = _variable;
    }
    
}
