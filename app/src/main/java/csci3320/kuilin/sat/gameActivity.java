package csci3320.kuilin.sat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.concurrent.TimeUnit;





public class gameActivity extends ActionBarActivity {

    public String name;
    public int level;
    public GameModel newGame;
    public int result;
    public int userResult;
    boolean startNextRound;
    Button[] grid = new Button[4]; //game grid
    TextView txtOperand1;
    TextView txtOperand2;
    ImageView imgOperation;
    EditText txtScore;
    EditText timer;
    EditText response;
    Button btnHelp;
    Button btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //add custom keyboard to layout
        createKeyboard();
        //Get info from welcome activity
        Intent i = getIntent();
        name = i.getStringExtra("name");
        level = i.getIntExtra("level", 0);


        //get game grid to work on
        grid[0] = (Button)findViewById(R.id.button_shape1);
        grid[1] = (Button)findViewById(R.id.button_shape2);
        grid[2] = (Button)findViewById(R.id.button_shape3);
        grid[3] = (Button)findViewById(R.id.button_shape4);

        //get labels and other views needed for game
        txtOperand1 = (TextView)findViewById(R.id.first_shape_textView);
        txtOperand2 = (TextView)findViewById(R.id.second_shape_textView);
        imgOperation = (ImageView)findViewById(R.id.imageView_operator);
        txtScore = (EditText)findViewById(R.id.score_editText);
        timer = (EditText) findViewById( R.id.timer_editText );
        response = (EditText) findViewById(R.id.result_editText);
        btnHelp = (Button)findViewById(R.id.help_button);
        btnHome = (Button)findViewById(R.id.home_button);
        //start a new game
        StartNewGame();
    }

    public void StartNewGame()
    {
        //create new game
        newGame = new GameModel(level);

        //clean up variables
        txtScore.setText("");

        //start the countdown timer and start the round
        TimerCountDown();
        startNextRound = true;
        NextRound(newGame);
    }

    public void NextRound(GameModel gm){
        if(startNextRound) {
            //reset boolean for next round
            startNextRound=false;
//            btnHelp.setEnabled(false);
//            btnHome.setEnabled(true);

            //Get shapes and operation
            Shape[] sh = gm.GetShapes();
            Operation op = gm.GetOperator();

            //Randomly select the shapes that will be used for the expression to be evaluated
            //calculate the result
            long seed = System.currentTimeMillis();
            Random gen = new Random(seed);
            int shape1 = gen.nextInt(4);
            int shape2 = gen.nextInt(4);
            while (shape1 == shape2) {
                shape2 = gen.nextInt(4);
            }

            //validate the numbers to prevent negative numbers or math errors
            String operator = op.GetName();
            if(operator.equals("subtract") || operator.equals("divide")) {
                if(sh[shape1].GetNumber()<sh[shape2].GetNumber()){
                    int temp = shape1;
                    shape1 = shape2;
                    shape2 = temp;
                }
                if(operator.equals("divide")){
                    int denominator = sh[shape2].GetNumber();

                    switch(level){
                        case 1:
                        case 4:
                            sh[shape2].SetNumber(calculateDenominator(denominator,GameModel.MAXNUM_EASY));
                            sh[shape1].SetNumber(calculateNumerator(denominator,GameModel.MAXNUM_EASY));
                            break;
                        case 2:
                        case 5:
                            sh[shape2].SetNumber(calculateDenominator(denominator,GameModel.MAXNUM_MED));
                            sh[shape1].SetNumber(calculateNumerator(denominator,GameModel.MAXNUM_MED));
                            break;
                        case 3:
                        case 6:
                            sh[shape2].SetNumber(calculateDenominator(denominator,GameModel.MAXNUM_HARD));
                            sh[shape1].SetNumber(calculateNumerator(denominator,GameModel.MAXNUM_HARD));
                            break;
                    }
                }
            }
            //calculate the correct result
            result = gm.Calculate(sh[shape1],sh[shape2],op);

            //Populate grid with shapes, display current expression to user
            for (int i = 0; i < 4; ++i) {
                grid[i].setBackgroundResource(sh[i].GetImage());
                grid[i].setText(String.valueOf(sh[i].GetNumber()));
            }

            txtOperand1.setText(sh[shape1].GetName());
            imgOperation.setBackgroundResource(op.GetImage());
            txtOperand2.setText(sh[shape2].GetName());


        }
    }
    public int calculateDenominator(int denominator,int maxNum){
        int den = denominator;
        if((den > (maxNum/2)) || (den == 0)){
            den = (den+1)/2;
        }
        return den;
    }

    public int calculateNumerator(int denominator,int maxNum){
        long seed = System.currentTimeMillis();
        Random generator = new Random(seed);

        int numerator;
        do {
            numerator = denominator * generator.nextInt(maxNum);
        }while (numerator > maxNum);

        return numerator;
    }



    private void animShake(View v) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        v.startAnimation(shake);
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
                endGame();
                timer.setText("Time up!");
            }
        }.start();
    }

    public void addPoints(){
        newGame.AddPoints();
        txtScore.setText(Integer.toString(newGame.GetTotalPoints()));
    }

    public void endGame(){

//        btnHelp.setEnabled(true);
//        btnHome.setEnabled(true);

        //CREATE END GAME ALERT
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("GAME OVER");

        // set dialog message
        alertDialogBuilder
                .setMessage("Final Score: " + newGame.GetTotalPoints() + "\n\nPLAY AGAIN?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //start new game
                        StartNewGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //end game and go back to main menu
                        gameActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void createKeyboard(){
        // Create the Keyboard
        Keyboard mKeyboard= new Keyboard(this,R.xml.satkb);

        // Lookup the KeyboardView
        KeyboardView mKeyboardView= (KeyboardView)findViewById(R.id.keyboardview);

        //Disable key popup preview
        mKeyboardView.setPreviewEnabled(false);
        //setPreviewEnabled(boolean previewEnabled)
        // Attach the keyboard to the view
        mKeyboardView.setKeyboard( mKeyboard );
        // Do not show the preview balloons

        // Install the key handler
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

    }

    //create a keyboard action listener
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener;
    {
        mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {



            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                //Get the EditText for response
                Editable editable = response.getText();

                if(primaryCode==10) {
                    if(editable.length()>0) {
                        userResult = Integer.parseInt(response.getText().toString());
                        if(result == userResult) {
                            response.setText("");
                            addPoints();
                            startNextRound = true;
                            NextRound(newGame);
                        }
                        else{
                            animShake(response);
                            response.setText("");
                        }
                    }
                }
                else if(primaryCode==67){
                    if(editable.length()>0) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                else{
                    if(editable.length()<4) {
                        //Enter user's input to response edittext
                        int start = response.getSelectionStart();
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                }
            }



            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };

    }

    public void loadHomePage(View view) {
        Intent home = new Intent(this,welcomeActivity.class);
        startActivity(home);
    }

    public void loadHelpPage(View view) {
        Intent help = new Intent(this,helpActivity.class);
        startActivity(help);
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
}
