package csci3320.kuilin.sat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kirk Karavouzis on 4/23/2015.
 */
public class UserScoreAdapter extends ArrayAdapter<UserScore>{

    // declaring class members
    private Context context;
    private int layoutResourceId;
    private ArrayList<UserScore> arrayListData = null;

    public UserScoreAdapter(Context context, int layoutResourceId, ArrayList<UserScore> arrayListData) {
        super(context, layoutResourceId, arrayListData);
        this.arrayListData = arrayListData;
        this.layoutResourceId=layoutResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variable
        View row = convertView;

        if(row==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);

        }


        UserScore i = arrayListData.get(position);

        if(i != null) {
            TextView txtPlayerName = (TextView) row.findViewById(R.id.txtPlayerName);
            TextView txtLevel = (TextView) row.findViewById(R.id.txtLevel);
            TextView txtScore = (TextView) row.findViewById(R.id.txtScore);


            if(txtPlayerName !=null)
                txtPlayerName.setText(i.name);
            if(txtLevel !=null)
                txtLevel.setText(String.valueOf(i.level));
            if(txtScore !=null)
                txtScore.setText(String.valueOf(i.score));

        }
        return row;
    }

}
