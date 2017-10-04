package com.example.android.moneymonitor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.moneymonitor.data.PetContract;

/**
 * Created by W10 on 9/23/2017.
 */

public class PetCursorAdapter extends CursorAdapter{

    private  Context mContext;

    public PetCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.catalog_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.catalog_pet_name);
        TextView summaryView = (TextView) view.findViewById(R.id.catalog_initial_char);
        TextView individualIdView = (TextView) view.findViewById(R.id.catalog_individual_id);
        TextView circle = (TextView) view.findViewById(R.id.catalog_initial_char);

        String currentPetName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        String currentPetSummary = currentPetName.substring(0,1);
        String currentIndividualId = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_INDIVIDUAL_ID)));

        nameView.setText(currentPetName);
        summaryView.setText(currentPetSummary);
        individualIdView.setText(currentIndividualId);
        circle.setBackgroundColor(getMagnitudeColor(currentPetName.charAt(0)));

//        Log.d("CursorAdapter", "IndividualId=" + currentIndividualId);

    }

    private int getMagnitudeColor(char firstChar){
        int magnitudeColorResId ;
        int magnitudeColor; // We need to convert magnitudeColorResId to magnitudeColor integer.
        switch (firstChar){
            case 'A' :
            case 'B' :
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 'K' :
            case 'C' :
            case 'S' :magnitudeColorResId = R.color.magnitude2;
                break;
            case 'D' :
            case 'L' :
            case 'T' :magnitudeColorResId = R.color.magnitude3;
                break;
            case 'E' :
            case 'M' :
            case 'U' :magnitudeColorResId = R.color.magnitude4;
                break;
            case 'F' :
            case 'N' :
            case 'V' :magnitudeColorResId = R.color.magnitude5;
                break;
            case 'G' :
            case 'O' :
            case 'W' :magnitudeColorResId = R.color.magnitude6;
                break;
            case 'H' :
            case 'P' :
            case 'X' :magnitudeColorResId = R.color.magnitude7;
                break;
            case 'I' :
            case 'Q' :
            case 'Y' :magnitudeColorResId = R.color.magnitude8;
                break;
            case 'J' :
            case 'R' :magnitudeColorResId = R.color.magnitude9;
                break;
            default :
                magnitudeColorResId = R.color.magnitude10plus;
                break;
        }
        magnitudeColor = //ResourcesCompat.getColor(getResourses(), magnitudeColorResId, null);
                ContextCompat.getColor(mContext, magnitudeColorResId);
        return magnitudeColor;
    }
}
