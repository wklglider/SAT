package csci3320.kuilin.sat;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import csci3320.kuilin.sat.Shape;
import csci3320.kuilin.sat.Operation;
import csci3320.kuilin.sat.GameModel;


public class gameActivity extends ActionBarActivity {

    public String name;
    public int level;
    public GameModel round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        createKeyboard();
        //Get info from welcome activity
        Intent i = getIntent();
        name = i.getStringExtra("name");
        level = i.getIntExtra("level", 0);

        //Create new game and start the round
        round = new GameModel(level);

        TimerCountDown();
        StartRound(round);

        //Toast.makeText(this, "Name: " + name, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Level: " + level, Toast.LENGTH_SHORT).show();
    }

    public void StartRound(GameModel gm)
    {
        //get game grid to work on
        Button[] grid = new Button[4];
        grid[0] = (Button)findViewById(R.id.button_shape1);
        grid[1] = (Button)findViewById(R.id.button_shape2);
        grid[2] = (Button)findViewById(R.id.button_shape3);
        grid[3] = (Button)findViewById(R.id.button_shape4);

        //get labels and other views needed for game
        TextView txtOperand1 = (TextView)findViewById(R.id.first_shape_textView);
        TextView txtOperand2 = (TextView)findViewById(R.id.second_shape_textView);
        TextView txtOperation = (TextView)findViewById(R.id.operator_textView);
        EditText txtScore = (EditText)findViewById(R.id.score_editText);


        //Get shapes and operation
        Shape[] sh = gm.GetShapes();
        Operation op = gm.GetOperator();

        //Randomly select the shapes that will be used for the expression to be evaluated
        //calculate the result
        long seed = System.currentTimeMillis();
        Random gen = new Random(seed);
        int shape1 = gen.nextInt(4);
        int shape2 = gen.nextInt(4);
        while(shape1 == shape2){
            shape2 = gen.nextInt(4);
        }

       

        //Populate grid with shapes, display current expression to user
        for(int i=0; i < 4;++i) {
            grid[i].setBackgroundResource(sh[i].GetImage());
            grid[i].setText(String.valueOf(sh[i].GetNumber()));
        }

        txtOperand1.setText(sh[shape1].GetName());
        txtOperation.setText(op.GetOperator());
        txtOperand2.setText(sh[shape2].GetName());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void TimerCountDown (){
        final EditText timer = (EditText) findViewById( R.id.timer_editText );
        new CountDownTimer(60000, 50) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timer.setText(""+String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toSeconds( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) -
                                TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("Time up!");
            }
        }.start();
    }

    public void createKeyboard(){
        // Create the Keyboard
        Keyboard mKeyboard= new Keyboard(this,R.xml.satkb);

        // Lookup the KeyboardView
        KeyboardView mKeyboardView= (KeyboardView)findViewById(R.id.keyboardview);
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard( mKeyboard );
        // Do not show the preview balloons
        mKeyboardView.setPreviewEnabled(false);


    }

    public void loadHomePage(View view) {
        Intent home = new Intent(this,welcomeActivity.class);
        startActivity(home);
    }

    public void loadHelpPage(View view) {
        Intent help = new Intent(this,helpActivity.class);
        startActivity(help);
    }
}
