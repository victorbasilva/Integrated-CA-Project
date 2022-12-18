/*
 * Matrix class
 */
package Equations;

import java.util.ArrayList;

public class Matrix {
    // VARIABLES
    // set order of matrix (number of columns and
    private int rows = 0;
    private int columns = 0;
    private int[] order = new int[2]; // 0 is number of rows, 1 is number of columns
    private ArrayList<Double> matrixMembers = new ArrayList<>();  
    // list of equations that matrix was created from
    private ArrayList<Equation> equations = new ArrayList<>();
    private double[][] matrix;
    private double determinant;
    private String solution;
    
    // CONSTRUCTORS
    /**
     * Creates object of Matrix
     */
    public Matrix(){}
        
    // Matrix ROWS
    /**
     * Set number of rows in matrix
     * @param rows -- number of rows in matrix as integer
     */
    public void setMatrixRows(int rows){
        this.rows = rows;
    }
    
    /**
     * Get number of rows in matrix
     * @return number of rows in matrix as integer
     */
    public int getMatrixRows(){
        return this.rows;
    }
    
    // Matrix COLUMNS
    /**
     * Set number of columns in matrix
     * @param columns -- number of columns in matrix as integer
     */
    public void setMatrixColumns(int columns){
        this.columns = columns;
    }
    
    /**
     * Get number of columns in matrix
     * @return number of columns in matrix as integer
     */
    public int getMatrixColumns(){
        return this.columns;
    }
    
    
    // Matrix ORDER
    /**
     * Set the order of matrix. 
     * Order of matrix is number of rows and number of columns
     * @param rows -- number of rows in matrix as integer
     * @param columns -- number of columns in matrix as integer
     */
    public void setMatrixOrder(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.order[0] = rows;
        this.order[1] = columns;
    }
    
    /**
     * Get the order of matrix. 
     * Order is number of rows and columns in matrix. 
     * Returns integer array where on position 0 is number of rows and on
     * position 1 is number of columns
     * @return order of matrix as integer array 
     */
    public int[] getMatrixOrder(){
        return this.order;
    }

    public void setMatrixMembers(int rows, int columns, ArrayList<Equation> equations ){
        // save equations for later
        this.equations = equations;
        // create a matrix based on order
        this.matrix = new double[rows][columns];
        // go trough each row and column to set a each member
        for(int i=0; i < rows; i++){
            for(int j=0;j< columns;j++){
                try{
                    //creates matrix from members
                    this.matrix[i][j] = this.getEquationMember(i, j, equations);
                }
                catch(Exception e){
                    System.out.println("An error occured. Please retry");
                    return;
                }
            }
        }
    }
    
    /**
     * Get equation based on row index and then find a member based on column index 
     * @param rowIndex - index of row in equation
     * @param columnIndex - index of column in equation
     * @param equations - array list of equations
     * @return - return value for a equation member
     */
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
     * Method that will print out all matrix members
     * Used in debugging to check if values are proper
     * @param matrix - matrix we want to print as a double[][]
     */
    public void readMatrix(double[][] matrix){
        for(int i=0; i < matrix.length; i++){
            for(int j=0;j< matrix[i].length;j++){
                System.out.println("[" +i + "]" + "[" +j + "]" + " :" +matrix[i][j]);
            }
        }
    } 
    
    /**
     * Set the value of solution
     * @param solution - string that we set solution to
     */
    public void setSolution(String solution){
        this.solution = solution;
    }
   
    /**
     * Get solution
     * @return solution as String
     */
    public String getSolution(){
        return this.solution;
    }
    
    /**
     * Set value for determinant
     * @param determinant as double
     */
    public void setDeterminant(double determinant){
        this.determinant = determinant;
    }
    
    /**
     * Get determinant 
     * @return determinant as double
     */
    public double getDeterminant(){
        return this.determinant;
    }
   
    /**
     * get array list of equations
     * @return equations as ArrayList<Equation>
     */
    public ArrayList<Equation> getEquations(){
        return this.equations;
    }
    
    /**
     * get matrix variable
     * @return matrix as double[][]
     */
    public double[][] getMatrix(){
        return this.matrix;
    }
}
