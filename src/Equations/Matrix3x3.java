/*
 * To Solve 3x3 matric 
 */
package Equations;

import java.util.ArrayList;

public class Matrix3x3 extends Matrix{
    // variables specific to 3x3 Matrix
    private double[][] matrixInverse;
    private double[][] matrixOfMinors;
    private double[][] matrixOfSigns;
    private double[][] coFactorsMatrix;  
    
    private double x,y,z;
    
    public Matrix3x3() {
    }
    
    /**
     * Matrix of signs is used to transform 
     */
    public void setMatricOfSigns(){
        this.matrixOfSigns = new double[3][3];
        this.matrixOfSigns[0][0] = 1.0;
        this.matrixOfSigns[0][1]= -1.0;
        this.matrixOfSigns[0][2]= 1;
        this.matrixOfSigns[1][0]= -1;
        this.matrixOfSigns[1][1]= 1;
        this.matrixOfSigns[1][2]= -1;
        this.matrixOfSigns[2][0]= 1;
        this.matrixOfSigns[2][1]= -1;
        this.matrixOfSigns[2][2]= 1;
    }
    
    // SOLVE 3x3 Matrix Calculations
    public boolean solve3x3Matric(){
        try{
            this.readMatrix(this.getMatrix());
            // get matrics of minors
            this.calculateMatrixOfMinors();
            // set cofactors matrix
            this.setCoFactorsMatrix();
            // get determinant of matrics
            this.set3x3Determinant();
            // get invers of matric and that is transpose of co factors matric
            this.getInverseOf3x3();
            // calcualte 1/det(A) * inverse matric
            this.solve3x3();
            return true;
        }catch(Exception e){
            System.out.println("Error solving 3x3 matric: " +e.getMessage());
            return false;
        }
    }
    
    /**
     * Method to calculate
     */
    private void calculateMatrixOfMinors(){
        double[][] _matrix = this.getMatrix();
        this.matrixOfMinors = new double[3][3];
        //double[][] minor = new double[3][3];
        double[][] newMatrix = new double[2][2];  
        // FIRST ROW
        // GET minor for 0,0
        newMatrix[0][0] = _matrix[1][1];
        newMatrix[0][1] = _matrix[1][2];
        newMatrix[1][0] = _matrix[2][1];
        newMatrix[1][1] = _matrix[2][2];
        this.matrixOfMinors[0][0] = this.calculateDet(newMatrix);
        
        // GET minor for 0,1
        newMatrix[0][0] = _matrix[1][0];
        newMatrix[0][1] = _matrix[1][2];
        newMatrix[1][0] = _matrix[2][0];
        newMatrix[1][1] = _matrix[2][2];
        this.matrixOfMinors[0][1] = this.calculateDet(newMatrix);
        
        // GET minor for 0,2
        newMatrix[0][0] = _matrix[1][0];
        newMatrix[0][1] = _matrix[1][1];
        newMatrix[1][0] = _matrix[2][0];
        newMatrix[1][1] = _matrix[2][1];
        this.matrixOfMinors[0][2] = this.calculateDet(newMatrix);
        
        // SECOND ROW
        // GET minor for 1,0
        newMatrix[0][0] = _matrix[0][1];
        newMatrix[0][1] = _matrix[0][2];
        newMatrix[1][0] = _matrix[2][1];
        newMatrix[1][1] = _matrix[2][2];
        this.matrixOfMinors[1][0] = this.calculateDet(newMatrix);
        // GET minor for 1,1
        newMatrix[0][0] = _matrix[0][0];
        newMatrix[0][1] = _matrix[0][2];
        newMatrix[1][0] = _matrix[2][0];
        newMatrix[1][1] = _matrix[2][2];
        this.matrixOfMinors[1][1] = this.calculateDet(newMatrix);
        // GET minor for 1,2
        newMatrix[0][0] = _matrix[0][0];
        newMatrix[0][1] = _matrix[0][1];
        newMatrix[1][0] = _matrix[2][0];
        newMatrix[1][1] = _matrix[2][1];
        this.matrixOfMinors[1][2] = this.calculateDet(newMatrix);
        
        // THIRD ROW
        // GET minor for 2,0
        newMatrix[0][0] = _matrix[0][1];
        newMatrix[0][1] = _matrix[0][2];
        newMatrix[1][0] = _matrix[1][1];
        newMatrix[1][1] = _matrix[1][2];
        this.matrixOfMinors[2][0] = this.calculateDet(newMatrix);
        // GET minor for 2,1
        newMatrix[0][0] = _matrix[0][0];
        newMatrix[0][1] = _matrix[0][2];
        newMatrix[1][0] = _matrix[1][0];
        newMatrix[1][1] = _matrix[1][2];
        this.matrixOfMinors[2][1] = this.calculateDet(newMatrix);
        // GET minor for 2,2
        newMatrix[0][0] = _matrix[0][0];
        newMatrix[0][1] = _matrix[0][1];
        newMatrix[1][0] = _matrix[1][0];
        newMatrix[1][1] = _matrix[1][1];
        this.matrixOfMinors[2][2] = this.calculateDet(newMatrix);
    }
    
    
    private void setCoFactorsMatrix(){
        this.coFactorsMatrix = new double[3][3];
        this.setMatricOfSigns();
        for(int i=0; i<3; i++){
            
            for(int j=0; j<3; j++){
               this.coFactorsMatrix[i][j] = this.matrixOfMinors[i][j] * this.matrixOfSigns[i][j];
            }
        }
        this.readMatrix(this.coFactorsMatrix);
    }
    
    /**
     * Calculates determinant following rule:
     * Pick one column/row from your original matrix, and multiply each element by
     * their own co-factors.
     */
    private void set3x3Determinant(){
        double[][] _matrix = this.getMatrix();
       //this.setDeterminant((_matric[0][0]*this.coFactorsMatric[0][0]) + (_matric[0][1]*this.coFactorsMatric[0][1]) + (_matric[0][2]*this.coFactorsMatric[0][2])); 
       /*double temp = (_matric[0][0] * _matric[1][1] * _matric[2][2]) - (_matric[0][0] * _matric[1][2] * _matric[2][1] ) - (_matric[0][1] * _matric[1][0] * _matric[2][2]) +
       (_matric[0][1] * _matric[1][2] * _matric[2][0]) + (_matric[0][2] * _matric[1][0] * _matric[2][1]) - (_matric[0][2] * _matric[1][1] * _matric[2][2]); 
        System.out.println("TEMP:");*/
        double temp1 = (_matrix[0][0]*(_matrix[1][1]*_matrix[2][2])-(_matrix[2][1]*_matrix[1][2]));
        double temp2 = (_matrix[0][1]*(_matrix[1][0]*_matrix[2][2])-(_matrix[2][0]*_matrix[1][2]));
        double temp3 = (_matrix[0][2]*(_matrix[1][0]*_matrix[2][1])-(_matrix[1][1]*_matrix[2][0]));
        double temp = temp1-temp2-temp3;
        this.setDeterminant(temp);
    
    }
    
    /**
     * Get an Inverse of 3x3 matrix.
     * The adjoint of A, denoted adj (A), is the transpose of the matrix of cofactors: adj(A) = CT
    */
    private void getInverseOf3x3(){
        this.matrixInverse = new double[3][3];
                
        // replace co factor matrix positions
        double[][] newMatrix = new double[3][3];
         for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                newMatrix[i][j] = this.coFactorsMatrix[j][i];
            }
        }
        
        this.readMatrix(newMatrix);
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                // to get inverse just replace i and j on coFactor matric and then multiply with 1/det(A)
                this.matrixInverse[i][j] = (1/this.getDeterminant()) * newMatrix[i][j];
            } 
        }
        this.readMatrix(this.matrixInverse);
    }
    
    public void solve3x3(){
        ArrayList<Equation> _equations = this.getEquations();
        double firstConstant = _equations.get(0).getConstant();
        double secondConstant = _equations.get(1).getConstant();
        double thirdConstant = _equations.get(2).getConstant();
        double[] constants = new double[]{firstConstant, secondConstant, thirdConstant};
        for(int i=0; i<3; i++){
             for(int j=0; j<3; j++){
                 switch (i) {
                     case 0:
                         this.x += this.matrixInverse[i][j] * constants[j];
                         break;
                     case 1:
                         this.y += this.matrixInverse[i][j] * constants[j];
                         break;
                     default:
                         this.z += this.matrixInverse[i][j] * constants[j];
                         break;
                 }

             }
        }
        
        if(Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isInfinite(this.x) || Double.isInfinite(this.y)
                || Double.isInfinite(this.z) || Double.isInfinite(this.z)){
            this.setSolution("There is no solution.");
        }else{
            this.setSolution("X = " +this.x + " and Y = " +this.y+ "and Z = " +this.z);
        }
        System.out.println(this.getSolution());
        System.out.println(); // empty row
    }
    
    
    
    private double calculateDet(double[][] _matric){
        double det = (_matric[0][0] * _matric[1][1]) - (_matric[1][0] * _matric[0][1]);
        return det;
    }
}

// x+y+z=2  2x+3y+5z=11   x-5y+6z=29
