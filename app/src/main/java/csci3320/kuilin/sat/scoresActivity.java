package csci3320.kuilin.sat;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class scoresActivity extends ActionBarActivity {

    public ArrayList<UserScore> highScores;
    UserScoreAdapter adapter;

    //final StableArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        DatabaseOperations dbOp = new DatabaseOperations(this);
        highScores = dbOp.getInformation();


        adapter = new UserScoreAdapter(this,R.layout.listview_item_row,highScores);

        ListView scoresListView = (ListView) findViewById(R.id.listView);

        TextView scoresHeaderView = new TextView(this);

        //Set header
        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row,null);
        scoresListView.addHeaderView(header);

        //Set footer

        //Set adapter
        scoresListView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scores, menu);
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

    public void clearScores(MenuItem mi){

        final DatabaseOperations dbOp = new DatabaseOperations(this);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Clear all high scores");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to clear all high scores?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //start new game
                        dbOp.dropTable();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing.
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
