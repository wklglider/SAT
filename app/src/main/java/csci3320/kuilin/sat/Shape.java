package csci3320.kuilin.sat;

/**
 * Created by Kirk Karavouzis on 4/13/2015.
 */
public class Shape {

    private int number;
    private String name;
    private int shapeImage;

    Shape(){
        number = 0;
        shapeImage = 0;
        name = "generic";
    }

    Shape(String shapeName, int imageResource){
        name = shapeName;
        shapeImage = imageResource;
        number = 0;
    }

    Shape(String shapeName, int imageResource, int num){
        name = shapeName;
        shapeImage = imageResource;
        number = num;
    }

    //property getters and setters
    public void SetNumber(int newNum){
        number = newNum;
    }

    public int GetNumber(){
        return number;
    }

    public void SetName(String newName){
        name = newName;
    }

    public String GetName(){
        return name;
    }

    public void SetImage(int newImageResource){
        shapeImage = newImageResource;
    }

    public int GetImage(){
        return shapeImage;
    }
}
