
package Utility;

import java.util.Scanner;


public class InputUtilities {
    private Scanner myKB = new Scanner(System.in); // create myKb from scanner
    
    /**
     * Get text from the user keyboard.
     * Keep asking to enter equation until user type it correctly
     * Rules accept numbers, letters x,y,z and + - and =
     * @param message - message to display to user
     * @param is3x3 - true if we are solving 3x3 equation
     * @return - the users input as a String
    */
    public String getUserEquationInput(String message, boolean is3x3){
        String userInput = ""; //set userInput to empty string
        boolean isValid = false;
        // prompt user until input is valid
        do{
            //display message to user
            System.out.println(message);
            // get user input
            userInput = this.myKB.nextLine();
            // remove all spaces
            userInput = userInput.replace(" ", "").trim();
            // Create an array of characters from equation string
            char[] charArray = userInput.toCharArray();
            // equation needs to have at least 3 members
            if(charArray.length <3){
                System.out.println("Please enter an equation containing at least 4 elements!");
            }
            // to find wrong input
            boolean isWrongInput = false;
            // to count equals
            int equals = 0;
            //to have previous character for comparation
            char previousChar = ' ';
            // check each character entered
            for(int i = 0; i < charArray.length; i++){
                // if character is digit we don't need checks
                if(Character.isDigit(charArray[i])){
                    previousChar = charArray[i]; // save current char to compare with next element of array
                    continue; // use continue to skip all other checks
                }
                // accept x or y only if previous character was not x or y 
                if((charArray[i] == 'x' && previousChar != 'x') || (charArray[i] == 'y' && previousChar != 'y')){
                    previousChar = charArray[i]; // save current char to compare with next element of array
                    continue;
                }
                
                //accept z for 3x3 matric
                if((charArray[i] == 'z' && previousChar != 'z') && is3x3){
                    previousChar = charArray[i]; // save current char to compare with next element of array
                    continue;
                }
                
                // if is not x,y,z or digit check signs
                // accept signs +,- and = only if previous was not same sign and it is not end of equation
                if(((charArray[i] == '+' && previousChar != '+') || (charArray[i] == '-' &&  previousChar != '-') || (charArray[i] == '=' &&  previousChar != '='))&& i < (charArray.length-1) ){
                    // when we find = count it. Only 1 equal is allowed in equation and it is mandatory
                    if(charArray[i] == '='){
                        equals++; // count equals
                    }
                    previousChar = charArray[i]; // save current char to compare with next element of array
                    continue;
                }
                // in any other case equation is not valid equation so break loop and set input wrong
                isWrongInput = true;
            }
            
            // if there is no equals or there is more then one equal or there was wrong input
            if((equals > 1 || equals == 0) || isWrongInput){
                isValid = false;
                System.out.println("Enter only digits 0-9, letters: x,y,z and +,- or = ");
            }else{
                isValid = true; // end for loop
            }
        }while (!isValid);
        //return user input
        return userInput;
    }
    
    /**
     * Get user option from menu as integer
     * Keep asking until user type it correctly
     * @param message
     * @param maxAllowed 
     * @return - the users input as a String
    */
    public int getUserOption(String message, int maxAllowed){
        int userInput = 0; //set userInput to 0 initially
        boolean isValid = false;
        try {
            // prompt user until input is valid
            do{
                System.out.println(message);
                String input = this.myKB.nextLine();
                //check if is number
                if(input.matches("[0-9]+")){
                    //set user input to number
                    userInput = Integer.parseInt(input);
                    //check if is available option
                    if(userInput == 0 || userInput > maxAllowed){
                        System.out.println("There is no that option available. Please try again.");
                        userInput = 0;
                    }
                } else {
                    System.out.println("Try again. Please choose number.");
                    userInput = 0;
                }
            } while (userInput == 0);
        } catch(Exception e){
            System.out.println("Error "+e);
        }
        // return user input
        return userInput;
    }
    
    /**
     * Get user option from menu as integer
     * Keep asking until user type it correctly
     * @param message
     * @param minAlowed 
     * @param maxAllowed 
     * @return - the users input as a String
    */
    public int getUserOptionInRange(String message, int minAlowed, int maxAllowed){
        int userInput = 0; //set userInput to 0 initially
        boolean isValid = false;
        try {
            // prompt user until input is valid
            do{
                System.out.println(message);
                String input = this.myKB.nextLine();
                //check if is number
                if(input.matches("[0-9]+")){
                    //set user input to number
                    userInput = Integer.parseInt(input);
                    //check if is available option
                    if(userInput == 0 || userInput > maxAllowed || userInput < minAlowed){
                        System.out.println("There is no that option available. Please try again.");
                        userInput = 0;
                    }
                } else {
                    System.out.println("Try again. Please choose number.");
                    userInput = 0;
                }
            } while (userInput == 0);
        } catch(Exception e){
            System.out.println("Error "+e);
        }
        // return user input
        return userInput;
    }
    
    /**
     * 
     * @param message
     * @return 
     */
    public int getUserInteger(String message){
        int userInput = 0; //set userInput to 0 initially
        boolean isValid = false;
        do{
            System.out.println(message);
            try{
                userInput = this.myKB.nextInt();
                isValid = true;
            }catch(Exception e){
                isValid = false;
            }   
        }while(!isValid);
        return userInput;
    }
    
    /**
     * 
     * @param message
     * @return 
     */
    public String getUserText(String message){
        String userInput = ""; //set userInput to 0 initially
        boolean isValid = false;
        do{
            System.out.println(message);
            try{
                userInput = this.myKB.nextLine();
                isValid = true;
            }catch(Exception e){
                isValid = false;
            }   
        }while(!isValid);
        return userInput;
    }
}
