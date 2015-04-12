package csci3320.kuilin.sat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;


public class welcomeActivity extends ActionBarActivity {

    //variables pass to the main game
    String name = "";
    int level = 0;

    public void startGame(View view) {
        TextView txtView = (TextView) findViewById(R.id.player_name_editText);
        name = txtView.getText().toString();
        level = getLevel();
        Intent newGame = new Intent(this,gameActivity.class);
        newGame.putExtra("level",level);
        newGame.putExtra("name",name);

        startActivity(newGame);
    }

    private int getLevel(){
        int level =0;

        //get operation radio button that is checked
        RadioGroup operationGroup = (RadioGroup)findViewById(R.id.operation_groupbuttons);
        int operationID = operationGroup.getCheckedRadioButtonId();

        //get operand radio button that is checked
        RadioGroup operandGroup = (RadioGroup)findViewById(R.id.operand_groupbuttons);
        int operandID = operandGroup.getCheckedRadioButtonId();


        return level;
    }

    public void showScores(View view) {
        TextView txtView = (TextView) findViewById(R.id.player_name_editText);
        name = txtView.getText().toString();

        Intent showScores = new Intent(this,scoresActivity.class);
        showScores.putExtra("level",level);
        showScores.putExtra("name",name);

        startActivity(showScores);
    }

    public void getHelp(View view) {
        Intent getHelp = new Intent(this,helpActivity.class);
        startActivity(getHelp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
