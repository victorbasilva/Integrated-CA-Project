/*
 * To Solve 3x3 matric 
 */
package Equations;

import java.util.ArrayList;

public class Matrix3x3 extends Matrix{
    // variables specific to 3x3 Matrix
    private double[][] _matrix;
    private double[][] matrixOfMinors;
    private double[][] matrixOfSigns;
    private double[][] coFactorsMatrix;  
    
    private double x,y,z;
    
    public Matrix3x3() {
    }
    
    // SOLVE 3x3 Matrix Calculations
    public boolean solve3x3Matric(){
        try{
            // Solve 3x3 matrix system using Cramer method     
            this.getSolutionByCramerMethod();
            return true;
        }catch(Exception e){
            System.out.println("Error solving 3x3 matric: " +e.getMessage());
            return false;
        }
    }
        
    /**
     * Will find solution for 3x3 matrix by Cramer method
     * Cramer method first calculate determinant of original matrix
     * Then calculate x determinant by substituting first column with 1x1 matrix  of constants
     * Then calculate y determinant by substituting second column with 1x1 matrix of constants
     * Then calculate z determinant by substituting third column with 1x1 matrix of constants
     * To get result finally:
     * x = dx/x
     * y = dy/d
     * z = dz/z
     */
    public void getSolutionByCramerMethod(){
        // get equatiosn so we can get constnt matrix
        ArrayList<Equation> _equations = this.getEquations();
        double firstConstant = _equations.get(0).getConstant();
        double secondConstant = _equations.get(1).getConstant();
        double thirdConstant = _equations.get(2).getConstant();
        // add values from righet size of equation to constant array (matrix 1x1
        double[] constants = new double[]{firstConstant, secondConstant, thirdConstant};
        // get the matrix from equations
        double[][]originalMatrix = this.getMatrix();
        
        //this.readMatrix(originalMatrix); // uncomment for debuging to check if matrix is proper
        // get determinant of matrix
        double d = getCramerDeterminant(originalMatrix);
        this.setDeterminant(d);
        
        // Find X - by replacing first column on matric
        double[][] solutionMatrixX = new double[3][3];
        this.copyMatrixValues(solutionMatrixX, originalMatrix);
        solutionMatrixX[0][0] = constants[0];
        solutionMatrixX[1][0] = constants[1];
        solutionMatrixX[2][0] = constants[2];
        // get determinant of X
        double dx = getCramerDeterminant(solutionMatrixX); 
        // uncomment for debuging to check if values are proper
        // System.out.println("Solution matrix X");
        // this.readMatrix(solutionMatrixX);
        // System.out.println("Dx: "+dx);
        
        // Find y - by replacing second column on original matrix with constant matrix
        double[][] solutionMatrixY = new double[3][3];
        this.copyMatrixValues(solutionMatrixY, originalMatrix);
        solutionMatrixY[0][1] = constants[0];
        solutionMatrixY[1][1] = constants[1];
        solutionMatrixY[2][1] = constants[2];
        // get determinant of Y
        double dy = getCramerDeterminant(solutionMatrixY);
        // uncomment for debuging to check if values are proper
        // System.out.println("Solution matrix Y");
        // this.readMatrix(solutionMatrixY);
        // System.out.println("Dy: "+dy);
        
        // Find Z - by replacing third column on matrix with constant matrix
        double[][] solutionMatrixZ = new double[3][3];
        this.copyMatrixValues(solutionMatrixZ, originalMatrix);
        solutionMatrixZ[0][2] = constants[0];
        solutionMatrixZ[1][2] = constants[1];
        solutionMatrixZ[2][2] = constants[2];
        this.readMatrix(solutionMatrixZ);
        // get determinant z
        double dz = getCramerDeterminant(solutionMatrixZ);
        // uncomment for debuging to check if values are proper
        // System.out.println("Solution matrix Z");
        // this.readMatrix(solutionMatrixZ);
        // System.out.println("Dz: "+dz);
        
        // Calculate X, Y and Z
        this.x = dx/d;
        this.y = dy/d;
        this.z = dz/d;
        
        
        if(Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isInfinite(this.x) || Double.isInfinite(this.y)
                || Double.isInfinite(this.z) || Double.isInfinite(this.z)){
            // If is not solvable set solution to no solution
            this.setSolution("There is no solution.");
        }else{
            // else set solution to result
            this.setSolution("X = " +this.x + " and Y = " +this.y+ " and Z = " +this.z);
        }
        System.out.println(this.getSolution()); // display solution
        System.out.println(); // empty row
    }
    
    /**
     * Copy values from original to new matrix
     * @param newMatrix
     * @param originalMatrix 
     */
    private void copyMatrixValues(double[][]newMatrix, double[][]originalMatrix){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                newMatrix[i][j] = originalMatrix[i][j];
            }
        }
    }
    
    /**
     * Get a determinant for each matrix
     * @param _matrix -- matrix determinant is calculated for
     * @return - determinant value as double
     */
    private double getCramerDeterminant(double[][]_matrix){
        double temp1 = _matrix[0][0]*((_matrix[1][1]*_matrix[2][2])-(_matrix[2][1]*_matrix[1][2]));
        double temp2 = _matrix[0][1]*((_matrix[1][0]*_matrix[2][2])-(_matrix[2][0]*_matrix[1][2]));
        double temp3 = _matrix[0][2]*((_matrix[1][0]*_matrix[2][1])-(_matrix[1][1]*_matrix[2][0]));
        double temp = temp1-temp2+temp3;
        return temp;
    }
        
}

