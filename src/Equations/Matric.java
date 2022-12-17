/*
 * Matric class
 */
package Equations;

import java.util.ArrayList;

public class Matric {
    // VARIABLES
    // set order of matric (number of columns and
    private int rows = 0;
    private int columns = 0;
    private int[] order = new int[2]; // 0 is number of rows, 1 is number of columns
    private ArrayList<Double> matricMembers = new ArrayList<>();  
    // list of equations that matric was created from
    private ArrayList<Equation> equations = new ArrayList<>();
    private double[][] matric;
    private double determinant;
    private String solution;
    
    // CONSTRUCTORS
    /**
     * Creates object of Matric
     */
    public Matric(){}
        
    // Matric ROWS
    /**
     * Set number of rows in matric
     * @param rows -- number of rows in matric as integer
     */
    public void setMatricRows(int rows){
        this.rows = rows;
    }
    
    /**
     * Get number of rows in matric
     * @return number of rows in matric as integer
     */
    public int getMatricRows(){
        return this.rows;
    }
    
    // Matric COLUMNS
    /**
     * Set number of columns in matric
     * @param columns -- number of columns in matric as integer
     */
    public void setMatricColumns(int columns){
        this.columns = columns;
    }
    
    /**
     * Get number of columns in matric
     * @return number of columns in matric as integer
     */
    public int getMatricColumns(){
        return this.columns;
    }
    
    
    // Matric ORDER
    /**
     * Set the order of matric. 
     * Order of matric is number of rows and number of columns
     * @param rows -- number of rows in matric as integer
     * @param columns -- number of columns in matric as integer
     */
    public void setMatricOrder(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.order[0] = rows;
        this.order[1] = columns;
    }
    
    /**
     * Get the order of matric. 
     * Order is number of rows and columns in matric. 
     * Returns integer array where on position 0 is number of rows and on
     * position 1 is number of columns
     * @return order of matric as integer array 
     */
    public int[] getMatricOrder(){
        return this.order;
    }

    public void setMatricMembers(int rows, int columns, ArrayList<Equation> equations ){
        // save equations for later
        this.equations = equations;
        // create a matric based on order
        this.matric = new double[rows][columns];
        
        for(int i=0; i < rows; i++){
            for(int j=0;j< columns;j++){
                try{
                    //creates matric from members
                    this.matric[i][j] = this.getEquationMember(i, j, equations);
                }
                catch(Exception e){
                    System.out.println("An error occured. Please retry");
                    return;
                }
            }
        }
    }
    
    private double getEquationMember(int rowIndex, int columnIndex, ArrayList<Equation> equations){
        Equation rowEquation = equations.get(rowIndex);
        EquationMember returnValue;
        switch(columnIndex){
            case 0: returnValue = rowEquation.getXMember(); break;
            case 1: returnValue = rowEquation.getYMember(); break;
            case 2: returnValue = rowEquation.getZMember(); break;
            default: returnValue = rowEquation.getXMember(); break;
        }
        return returnValue.getCoefficient();
    }
    
    /**
     * Read members of matric for debugging
     */
    public void readMatric(double[][] matric){
        for(int i=0; i < matric.length; i++){
            for(int j=0;j< matric[i].length;j++){
                System.out.println(i+j + " :" +matric[i][j]);
            }
        }
    }
    
    public void setSolution(String solution){
        this.solution = solution;
    }
   
    public String getSolution(){
        return this.solution;
    }
    
    public void setDeterminant(double determinant){
        this.determinant = determinant;
    }
    
    public double getDeterminant(){
        return this.determinant;
    }
   
    public ArrayList<Equation> getEquations(){
        return this.equations;
    }
    
    public double[][] getMatric(){
        return this.matric;
    }
}
