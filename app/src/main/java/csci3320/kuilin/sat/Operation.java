package csci3320.kuilin.sat;

/**
 * Created by Kirk Karavouzis on 4/14/2015.
 */
public class Operation {

    private String name;
    private int operationImage;
    private String op;

    Operation(String _name, int imageResource, String operation){
        name = _name;
        operationImage = imageResource;
        op = operation;
    }

    //property getters and setters
    public void SetName(String newName){
        name = newName;
    }

    public String GetName(){
        return name;
    }

    public void SetImage(int newImageResource){
        operationImage = newImageResource;
    }

    public int GetImage(){
        return operationImage;
    }

    public void SetOperator(String newOp){
        op = newOp;
    }

    public String GetOperator(){
        return op;
    }



}
