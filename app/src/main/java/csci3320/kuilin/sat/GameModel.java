package csci3320.kuilin.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import csci3320.kuilin.sat.Shape;

/**
 * Created by Kirk Karavouzis on 4/13/2015.
 */
public class GameModel {


    //---------------Initialize------------------------
    private int roundPoints;
    private int totalPoints;
    private int level;
    private ArrayList<Shape> shapes;


    //----------------------Constructors--------------
    GameModel(){
        roundPoints = 1;
        totalPoints = 0;
        level = 1;
    }

    GameModel(int _level){
        roundPoints = _level;
        totalPoints = 0;
        level = _level;
    }

    //property getters and setters
    public void SetRoundPoints(int newNum){
        roundPoints = newNum;
    }

    public int GetRoundPoints(){
        return roundPoints;
    }

    public void SetTotalPoints(int newNum){
        totalPoints = newNum;
    }

    public int GetTotalPoints(){
        return totalPoints;
    }

    public void SetLevel(int newNum){
        level = newNum;
    }

    public int GetLevel(){
        return level;
    }


    //Generate the shapes
    //Array of shape symbols
    // private Shape shapes[] = {square,triangle,circle,rectangle,oval,pentagon,octagon,star};
     {
        Shape square = new Shape("Square",R.drawable.square);
        Shape triangle = new Shape("Triangle",R.drawable.square);;
        Shape circle = new Shape("Circle",R.drawable.square);;
        Shape rectangle = new Shape("Rectangle",R.drawable.square);;
        Shape oval = new Shape("Oval",R.drawable.square);;
        Shape pentagon = new Shape("Pentagon",R.drawable.square);;
        Shape octagon = new Shape("Octagon",R.drawable.square);;
        Shape star = new Shape("Star",R.drawable.square);;

        shapes = new ArrayList<Shape>();
        shapes.add(square);
        shapes.add(triangle);
        shapes.add(circle);
        shapes.add(rectangle);
        shapes.add(oval);
        shapes.add(pentagon);
        shapes.add(octagon);
        shapes.add(star);
    }
    /*private Shape square = new Shape("Square",R.drawable.square);
    private Shape triangle;
    private Shape circle;
    private Shape rectangle;
    private Shape oval;
    private Shape pentagon;
    private Shape octagon;
    private Shape star;*/

    //Array of math symbols
    private String operations[] = {"+","-","/","*"};

    
    //------------Round Start Helper Methods-----------------

    //Random number generator
    private int NumberGen(){
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);

        switch(level){
            case 1:
            case 4:
                return generator.nextInt(9);
            case 2:
            case 5:
                return generator.nextInt(20);
            case 3:
            case 6:
                return generator.nextInt(50);

        }
        return -1;
    }

    //Shape selector
    public Shape[] GetShapes(){

        Shape[] s = new Shape[4];
       //shuffle the shapes list
        Collections.shuffle(shapes);

        //select shapes and assign random numbers
        for(int i=0; i < 4; ++i){
            s[i] = shapes.get(i);
            s[i].SetNumber(NumberGen());
        }

        return s;
    }

    //Math symbol selector
    public String GetOperator(){
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);

        switch(level){
            case 1:
            case 2:
            case 3:
                return operations[generator.nextInt(1)];
            case 4:
            case 5:
            case 6:
                return operations[generator.nextInt(3)];

        }
        return "";
    }


}
