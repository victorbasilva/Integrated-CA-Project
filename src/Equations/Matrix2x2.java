/*
 * To solve 2x2 Matrix 
*/
package Equations;

import java.util.ArrayList;

public class Matrix2x2 extends Matrix{
    private double[][] matrixInverse;
    private double x,y;
    // SOLVE 2x2 Matrix Calculations
    /**
    * Method to solve 2x2 matrix
     * @return true if no error
    */
    public boolean solve2x2Matrix(){
        try{
            this.calcualte2x2Determinant();
            this.calculate2x2Inverse();
            return this.solve2x2();
        }catch(Exception e){
            System.out.println("Error solving 2x2 matric: " +e.getMessage());
            return false;
        }
    }
    
    /**
     * 2x2 matrix are 
     * 00 01
     * 10 11
     * Determinant is (00 * 11) - (01 * 10)
     */
    private void calcualte2x2Determinant(){
        double[][] _matrix = this.getMatrix();
        this.setDeterminant((_matrix[0][0] * _matrix[1][1]) - (_matrix[1][0] * _matrix[0][1]));
    }
    
    /**
     * Inverse for 2x2 matrix is:
     * 00 01
     * 10 11 
     * First re-arrange matrix to be
     * 11       -1*(01)
     * -1*(10)  00
     * Formula
     * 1/determinant * rearranged matrix 
     */
    private void calculate2x2Inverse(){
        double[][] _matric = this.getMatrix();
        double[][] rearangedMatrix = new double[2][2];
        rearangedMatrix[0][0] = _matric[1][1];
        rearangedMatrix[0][1] = _matric[0][1] * (-1);
        rearangedMatrix[1][0] = _matric[1][0] * (-1);
        rearangedMatrix[1][1] = _matric[0][0];
        this.readMatrix(rearangedMatrix);
        this.matrixInverse = new double[2][2];
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                this.matrixInverse[i][j] = (1/this.getDeterminant()) * rearangedMatrix[i][j];
            }
        }
        this.readMatrix(this.matrixInverse);
   }
    
   /**
    * To solve 2x2 matrix
    * 00 01
    * 10 11
    * 
    * with
    * constant 1
    * constant 2
    * 
    * Apply formula
    * 00 * constant1 + 01 * constant 2
    * 10 * constant1 + 11 * constant 2
    */
   private boolean solve2x2(){
        ArrayList<Equation> _equations = this.getEquations();
        double firstConstant = _equations.get(0).getConstant();
        double secondConstant = _equations.get(1).getConstant();
        double[] constants = new double[]{firstConstant, secondConstant};
        
        for(int i=0; i<2; i++){
             for(int j=0; j<2; j++){
                 if(i == 0){
                     this.x += this.matrixInverse[i][j] * constants[j];
                 }else{
                     this.y += this.matrixInverse[i][j] * constants[j];
                 }

             }
        }
        if(Double.isNaN(x) || Double.isNaN(y) || Double.isInfinite(x) || Double.isInfinite(y)){
            this.setSolution("No Solution"); // save no solution
        }else{
            this.setSolution("X = " +this.x + " and Y = " +this.y);
        }
        System.out.println(this.getSolution());
        System.out.println(""); //empty line
        return true;
   }
}
