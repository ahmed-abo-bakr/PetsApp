package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDBHelper;


public class CatalogActivity extends AppCompatActivity {

    private PetDBHelper mDbHelper;

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInf();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        mDbHelper=new PetDBHelper(this);
    }

    //insert dummy data
    private void insertPet(){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME,"ssd");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED,"toy");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, "male");
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        try {
            long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME,null,values);


        }catch (Exception e){
            Log.e("LOOK HEEEEEER  : "," DOESNONT WORK");
        }
    }

    private void displayDatabaseInf() {
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();

        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT
        };

        Cursor cursor = dbRead.query(
                PetContract.PetEntry.TABLE_NAME,
                projection, null,
                null, null, null, null);

        try {
            TextView textView = (TextView) findViewById(R.id.text_view_pet);
            textView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            Log.v("loook", Integer.toString(cursor.getCount()));

            textView.append(PetContract.PetEntry._ID + " - " +
                    PetContract.PetEntry.COLUMN_PET_NAME + " - " +
                    PetContract.PetEntry.COLUMN_PET_BREED + " - " +
                    PetContract.PetEntry.COLUMN_PET_GENDER + " - " +
                    PetContract.PetEntry.COLUMN_PET_WEIGHT + "\n");

            int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                    textView.append(("\n" +currentID + " \t " + " \t " +
                        currentName + " \t " + " \t " +
                        currentBreed + " \t " + " \t "+
                        currentGender + " \t " + " \t "+
                        currentWeight));

            }

        } finally {
            cursor.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                insertPet();
                displayDatabaseInf();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
