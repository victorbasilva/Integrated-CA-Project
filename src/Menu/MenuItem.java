package Menu;

public class MenuItem {
    private String text;
    private int number;
    private boolean isDisplayed;
    /**
     * Creates menu item. Number is option to select and text is option
     * @param number - option to select
     * @param text - option
     */
    public MenuItem(int number, String text, boolean isDisplayed){
        this.text = text;
        this.number = number;
        this.isDisplayed = isDisplayed;
    }
    
    public void displayMenuItem(){
        if(this.isDisplayed){
            System.out.println(this.number + ". " +this.text);
        }
    }
    
    public String getItem(){
        return this.number + ". " +this.text;
    }
    
    // SETTERS AND GETTERS
    public String getText(){
        return this.text;
    }
    
    public void setText(String newText){
        this.text = newText;
    }
    
    public void setIsDisplayed(boolean isDisplayed){
        this.isDisplayed = isDisplayed;
    }
    
    public boolean getIsDisplayed(){
        return this.isDisplayed;
    }
    
    public void setNumber(int number){
        this.number = number;
    }
    
    public int getNumber(){
        return this.number;
    }
}
