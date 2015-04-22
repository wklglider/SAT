package csci3320.kuilin.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import csci3320.kuilin.sat.Shape;
import csci3320.kuilin.sat.Operation;

/**
 * Created by Kirk Karavouzis on 4/13/2015.
 */
public class GameModel {

    //Define static variables
    final static int MAXNUM_EASY = 9;
    final static int MAXNUM_MED = 20;
    final static int MAXNUM_HARD = 50;

    //---------------Initialize------------------------
    private int roundPoints;
    private int totalPoints;
    private int level;
    private ArrayList<Shape> shapes;
    private ArrayList<Operation> operations;


    //----------------------Constructors--------------
    GameModel(){
        roundPoints = 1;
        totalPoints = 0;
        level = 1;

        initializeShapes();
        initializeMathSymbols();
    }

    GameModel(int _level){
        roundPoints = _level;
        totalPoints = 0;
        level = _level;

        initializeShapes();
        initializeMathSymbols();
    }

    //property getters and setters
    public void SetRoundPoints(int newNum){
        roundPoints = newNum;
    }

    public int GetRoundPoints(){
        return roundPoints;
    }

    public void AddPoints(){
        totalPoints += roundPoints;
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
     private void initializeShapes(){
        Shape square = new Shape("Square",R.drawable.square);
        Shape triangle = new Shape("Triangle",R.drawable.triangle);
        Shape circle = new Shape("Circle",R.drawable.circle);
        Shape rectangle = new Shape("Rectangle",R.drawable.rectangle);
        Shape oval = new Shape("Oval",R.drawable.oval);
        Shape diamond = new Shape("Diamond",R.drawable.diamond);
        Shape heart = new Shape("Heart",R.drawable.heart);
        Shape star = new Shape("Star",R.drawable.star);
        Shape hexagon = new Shape("Hexagon",R.drawable.hexagon);


        shapes = new ArrayList<Shape>();
        shapes.add(square);
        shapes.add(triangle);
        shapes.add(circle);
        shapes.add(rectangle);
        shapes.add(oval);
        shapes.add(diamond);
        shapes.add(heart);
        shapes.add(star);
        shapes.add(hexagon);
    }

    //Array of math symbols
    private void initializeMathSymbols(){
        Operation multiply = new Operation("multiply", R.drawable.multiply, "*");
        Operation divide = new Operation("divide", R.drawable.divide, "/");
        Operation add = new Operation("add", R.drawable.add, "+");
        Operation subtract = new Operation("subtract", R.drawable.subtract, "-");

        operations = new ArrayList<Operation>();

        operations.add(add);
        operations.add(subtract);
        operations.add(multiply);
        operations.add(divide);

    }
    //------------Round Start Helper Methods-----------------

    //Random number generator
    private int[] NumberGen(){
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);
        int[] numbers = new int[4];

        switch(level){
            case 1:
            case 4:
                for(int i=0;i < 4; ++i){
                numbers[i]=generator.nextInt(MAXNUM_EASY);
                }
                break;
            case 2:
            case 5:
                for(int i=0;i < 4; ++i){
                    numbers[i]=generator.nextInt(MAXNUM_MED);
                }
                break;
            case 3:
            case 6:
                for(int i=0;i < 4; ++i){
                    numbers[i]=generator.nextInt(MAXNUM_HARD);
                }
                break;

        }
        return numbers;
    }

    //Shape selector
    public Shape[] GetShapes(){

        Shape[] s = new Shape[4];
       //shuffle the shapes list
        Collections.shuffle(shapes);

        //Get some numbers
        int n[];
        n = NumberGen();
        //select shapes and assign random numbers
        for(int i=0; i < 4; ++i){
            s[i] = shapes.get(i);
            s[i].SetNumber(n[i]);
        }

        return s;
    }

    //Math symbol selector
    public Operation GetOperator(){
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);

        switch(level){
            case 1:
            case 2:
            case 3:
                return operations.get(generator.nextInt(2));
            case 4:
            case 5:
            case 6:
                return operations.get(generator.nextInt(4));

        }
        return null;
    }

    public int Calculate(Shape first, Shape second, Operation op){
        int num1=first.GetNumber();
        int num2=second.GetNumber();
        String operation = op.GetName();

        if(operation == "add"){
            return num1 + num2;
        }else if(operation == "subtract"){
            return num1 - num2;
        }else if(operation == "multiply"){
            return num1 * num2;
        }else if(operation == "divide"){
            while(num2==0)
            {
                num2=NumberGen()[0];
            }
            return num1 / num2;
        }

        return 0;
    }

}
