/*
 * String Helper class
 * It will transform String to equation members and finaly equation 
*/
package Utility;

import Equations.Equation;
import Equations.EquationMember;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringHelper {
   
    
    /*
    * Transforms a strings representation of an equation into an Equation class
    */
    public Equation getMembers(String equationString)
    {
        // Remove all empty spaces in equation string and remove empty spaces before and after with trim
        String trimmedEquation = equationString.replace(" ", "").trim();
        // Create an array of characters from equation string
        char[] charArray = trimmedEquation.toCharArray();
        // Create boolean isLeft
        // set boolean isLeft to true until we get to = then set to false so we know we are on right side of equeation 
        boolean isLeft = true;
        // to set last member of equation
        String lastMember = "";
        // Create a new object of equation class (that has ArrayList for left and right members of equation)
        Equation equation = new Equation();
        
        // go trough each character
        for(int i = 0; i < charArray.length; i++){   
            // if character is -,+ or = 
            if(charArray[i] == '-' || charArray[i] == '+' || charArray[i] == '='){
                // and we dont have last member
                if(lastMember.length() > 0){
                    
                    // create a new object of equation member
                    EquationMember eqMember = new EquationMember();
                    
                    // Escape non number string
                    lastMember = this.getNumberVariablePair(lastMember);
                    // set coefficient (double)
                    String coefficient = this.match("([-+]?[\\d\\.]+)[A-Za-z]?", lastMember, 1);
                    eqMember.setCoefficient(Double.parseDouble(coefficient));
                    
                    // set variable of equation (x,y,z)
                    String variable = this.match("[\\d\\.]+([A-Za-z]?)", lastMember, 1);
                    eqMember.setVariable(variable);
                    
                    // if is left member
                    if(isLeft){
                        //add left memeber
                        equation.addLeftMember(eqMember);
                    }else{
                        // else add right member
                        equation.addRightMember(eqMember);
                    }
                        
                }
                // add last member unless is =
                lastMember = charArray[i] == '=' ? "" : String.valueOf(charArray[i]);
            } else {
                // add character at current position in charArray to lastMember
                lastMember += charArray[i]; 
            }
            // when we reach = switch to right side by setting isLeft to false
            if(charArray[i] == '='){
                isLeft = false;
            }
        }
        // add last member (what is left after for loop)
        EquationMember lastEquationMember = new EquationMember();
        lastEquationMember.setCoefficient(Double.parseDouble(this.match("([-+]?[\\d\\.]+)[A-Za-z]?", lastMember, 1)));
        lastEquationMember.setVariable(this.match("[\\d\\.]+([A-Za-z]?)", lastMember, 1));
        // add last one as right member always
        equation.addRightMember(lastEquationMember);
        // return newly created equation
        return equation;
    }

    /**
     * Fix when 1 is assumed in equation, so it will be added to matrix.
     * For our regEx we need number and variable so add assumed 1 or -1 when we are missing it next 
     * to variable.
     * * @param lastMember -- last member or members took from character array 
     * @return - fixed last member as string (needs to be value and variable)
     */
    private String getNumberVariablePair(String lastMember){
        if("x".equals(lastMember) || "y".equals(lastMember) || "z".equals(lastMember)){
            // if there is only variable( x, y or z) add 1 in front so it can be taken as value 
            return "1"+lastMember;
        } else if("+x".equals(lastMember) || "+y".equals(lastMember) || "+z".equals(lastMember)){
            // if there is only variable( x, y or z) and + add 1 in front so it can be taken as value and rmeove plus 
            return "1"+lastMember.replace("+","");
        } else if("-x".equals(lastMember) || "-y".equals(lastMember) || "-z".equals(lastMember)){
            // if there is only variable( x, y or z) and - 
            // first remove minus 
            lastMember = lastMember.replace("-","");
            // and then add -1 on front
            return "-1"+lastMember;
        }
        return lastMember;
    }
    
    /*
     * Performs a regex match
     */
    public String match(String regexp, String text, int group)
    {
        Pattern p1 = Pattern.compile(regexp);
        Matcher m1 = p1.matcher(text);
         
        if(m1.find()){
            return m1.group(group);
        }else{
            return null;
        }
    }
}
