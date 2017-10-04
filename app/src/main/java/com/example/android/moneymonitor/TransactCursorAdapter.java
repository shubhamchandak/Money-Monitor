package com.example.android.moneymonitor;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.moneymonitor.data.PetContract.PetEntry;

/**
 * Created by W10 on 9/30/2017.
 */

public class TransactCursorAdapter extends CursorAdapter {

    public TransactCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.transaction_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView amountView = (TextView) view.findViewById(R.id.transaction_amount);
        TextView dateView = (TextView) view.findViewById(R.id.transaction_date);
        TextView commentView = (TextView) view.findViewById(R.id.transaction_comment);
        TextView oweView = (TextView) view.findViewById(R.id.transaction_type);
        TextView transactTypeHidden = (TextView) view.findViewById(R.id.transaction_type_hidden);
        TextView idType = (TextView) view.findViewById(R.id.id_type_hidden);

        String currentAmount = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_WEIGHT));
        String currentDate = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_DATE));
        String currentComment = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_BREED));
        int currentType = cursor.getInt(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_GENDER));
        int idTypeint = cursor.getInt(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_ID_TYPE));

        if (currentType == PetEntry.I_OWE_YOU){
            oweView.setText(R.string.i_owe_you);
        } else if (currentType == PetEntry.I_PAID_YOU){
            oweView.setText(R.string.i_paid_you);
        }else if (currentType == PetEntry.YOU_OWE_ME){
            oweView.setText(R.string.you_owe_me);
        }else {
            oweView.setText(R.string.you_paid_me);
        }

        String amountwithCurrency = "₹ " + currentAmount;
        amountView.setText(amountwithCurrency);
        dateView.setText(currentDate);
        transactTypeHidden.setText(String.valueOf(currentType));
        idType.setText(String.valueOf(idTypeint));

        if (!TextUtils.isEmpty(currentComment)){
            commentView.setText(currentComment);
        }
    }
}
