package com.example.android.moneymonitor;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.moneymonitor.data.PetContract.PetEntry;

/**
 * Created by W10 on 9/30/2017.
 */

public class TransactionsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int TRANSACT_LOADER = 0;

    private final String CURRENCY_SYMBOL = "₹ ";

    private TransactCursorAdapter mCursorAdapter;

    private int mCurrentIndividualId;
    private String mCurrentPetName;
    private String mCurrentTransactionAmount;
    private int mCurrentTransactionType;
    private int mCurrentIdType;
    private String mCurrentTransactionComment;
    private TextView totalAmountView;
    private String mCurrentDate;


    private static final String LOG_TAG = "TransactionsActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        setTitle("Summary");

        Intent intent = getIntent();
        Uri currentPetUri = intent.getData();
        mCurrentIndividualId = intent.getIntExtra(PetEntry.COLUMN_PET_INDIVIDUAL_ID, 0/*default value*/);
        mCurrentPetName = intent.getStringExtra(PetEntry.COLUMN_PET_NAME);
        Log.e(LOG_TAG, "mCurrentIndividualId: " + mCurrentIndividualId);
        Log.e(LOG_TAG, "mCurrentPetName: " + mCurrentPetName);

        TextView nameTextView = (TextView) findViewById(R.id.transaction_pet_name);
        nameTextView.setText(mCurrentPetName);

        TextView initialChar = (TextView) findViewById(R.id.initial_char_header_transact);
        initialChar.setText((mCurrentPetName.substring(0,1)));

        totalAmountView = (TextView) findViewById(R.id.transaction_summary);

        final ListView transactionListView = (ListView) findViewById(R.id.transaction_list_view);

        mCursorAdapter = new TransactCursorAdapter(this, null);
        transactionListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(TRANSACT_LOADER, null, this);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_transaction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionsActivity.this, EditorActivity.class);
                intent.setData(PetEntry.CONTENT_URI);
                intent.putExtra(PetEntry.COLUMN_PET_INDIVIDUAL_ID, mCurrentIndividualId);
                intent.putExtra(PetEntry.COLUMN_PET_NAME, mCurrentPetName);
                startActivity(intent);
            }
        });

        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mCurrentTransactionAmount = ((TextView) view.findViewById(R.id.transaction_amount)).getText().toString();
                mCurrentTransactionComment = ((TextView) view.findViewById(R.id.transaction_comment)).getText().toString();
                mCurrentDate = ((TextView)view.findViewById(R.id.transaction_date)).getText().toString();
                mCurrentTransactionType = Integer.parseInt(((TextView) view.findViewById(R.id.transaction_type_hidden)).getText().toString());
                mCurrentIdType = Integer.parseInt(((TextView) view.findViewById(R.id.id_type_hidden)).getText().toString());

                Intent intent = new Intent(TransactionsActivity.this, EditorActivity.class);
                intent.setData(ContentUris.withAppendedId(PetEntry.CONTENT_URI, id));
                intent.putExtra(PetEntry.COLUMN_PET_INDIVIDUAL_ID, mCurrentIndividualId);
                intent.putExtra(PetEntry.COLUMN_PET_NAME, mCurrentPetName);
                intent.putExtra(PetEntry.COLUMN_PET_DATE, mCurrentDate);
                intent.putExtra(PetEntry.COLUMN_PET_WEIGHT, mCurrentTransactionAmount);
                intent.putExtra(PetEntry.COLUMN_PET_BREED, mCurrentTransactionComment);
                intent.putExtra(PetEntry.COLUMN_PET_GENDER, mCurrentTransactionType);
                intent.putExtra(PetEntry.COLUMN_PET_ID_TYPE, mCurrentIdType);
                startActivity(intent);
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {PetEntry._ID,
                PetEntry.COLUMN_PET_WEIGHT,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_GENDER,
                PetEntry.COLUMN_PET_DATE,
                PetEntry.COLUMN_PET_ID_TYPE};

        String selection = PetEntry.COLUMN_PET_INDIVIDUAL_ID + "=?";
        String[] selectionArgs = new String[] {String.valueOf(mCurrentIndividualId)};

        return new CursorLoader(this, PetEntry.CONTENT_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

        if (data.getCount() > 0) {
            int amountIOwe = 0;
            int amountIPaid = 0;
            int totalAmountBenifit;
            String displayAmount;

            int amount;
            int gender;

            int amountColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);
            int genderColumnIndex = data.getColumnIndex(PetEntry.COLUMN_PET_GENDER);

            data.moveToFirst();
            do {
                amount = data.getInt(amountColumnIndex);
                gender = data.getInt(genderColumnIndex);
                if (gender == PetEntry.I_OWE_YOU || gender == PetEntry.YOU_PAID_ME) {
                    amountIOwe += amount;
                } else {
                    amountIPaid += amount;
                }

                totalAmountBenifit = amountIPaid - amountIOwe;
                if (totalAmountBenifit > 0) {
                    displayAmount = CURRENCY_SYMBOL.concat(String.valueOf(totalAmountBenifit));
                    totalAmountView.setText(displayAmount);
                    totalAmountView.setTextColor(getResources().getColor(R.color.amountColorProfit));
                } else if (totalAmountBenifit < 0) {
                    totalAmountBenifit = (-1) * totalAmountBenifit;
                    displayAmount = CURRENCY_SYMBOL.concat(String.valueOf(totalAmountBenifit));
                    totalAmountView.setText(displayAmount);
                    totalAmountView.setTextColor(getResources().getColor(R.color.amountColorLoss));
                } else {
                    displayAmount = CURRENCY_SYMBOL.concat(String.valueOf(totalAmountBenifit));
                    totalAmountView.setText(displayAmount);
                    totalAmountView.setTextColor(getResources().getColor(R.color.amountColorBalanced));
                }

            } while (data.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_delete_account:
                String selection = PetEntry.COLUMN_PET_INDIVIDUAL_ID + "=?";
                String[] selectionArg = new String[] {String.valueOf(mCurrentIndividualId)};
                getContentResolver().delete(PetEntry.CONTENT_URI, selection, selectionArg);

                finish();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_share_transaction:
               //TODO: Add email intent
        }
        return super.onOptionsItemSelected(item);
    }
}
