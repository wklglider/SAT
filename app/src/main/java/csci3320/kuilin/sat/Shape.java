package csci3320.kuilin.sat;

/**
 * Created by Kirk Karavouzis on 4/13/2015.
 */
public class Shape {

    public int number;
    public String name;
    public int shapeImage;

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
}
