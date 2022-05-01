package fi.jonilassila.snoozebuddy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


/**
 * The type Details activity.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String TABLE_NAME = "people_table";


    /**
     * The Database helper.
     */
    DatabaseHelper databaseHelper;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtDuration = findViewById(R.id.txtDuration);
        TextView txtStart = findViewById(R.id.txtStart);
        TextView txtEnd = findViewById(R.id.txtEnd);

        Bundle b = getIntent().getExtras();
        i = b.getInt(MainActivity.EXTRA, 0);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String strSQL = "UPDATE " + TABLE_NAME + " SET ID = " + i + " WHERE ID = "+ i;

        db.execSQL(strSQL);

        String selectQuery = "SELECT sleepName, sleepDescription, sleepDurationMinutes, sleepDurationHours, sleepStartTime, sleepEndTime  FROM " + TABLE_NAME + " WHERE ID= " + (i + 1);

        Cursor c = db.rawQuery(selectQuery, null);

        String sleepName = "";
        String sleepDescription = "";
        String sleepMinutes = "";
        String sleepHours = "";
        String sleepStart = "";
        String sleepEnd = "";

        if(c!=null) {
            c.moveToFirst();
            Log.i("cursor",c.getString(0));
            Log.i("cursor",c.getString(1));
            sleepName = c.getString(0);
            sleepDescription = c.getString(1);
            sleepMinutes = c.getString(2);
            sleepHours = c.getString(3);
            sleepStart = c.getString(4);
            sleepEnd = c.getString(5);

        }

        txtName.setText(sleepName);
        txtDescription.setText(sleepDescription);
        txtDuration.setText(sleepHours + " hours and " + sleepMinutes + " minutes");
        txtStart.setText("You went to sleep at " + sleepStart);
        txtEnd.setText("You woke up at " + sleepEnd);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.moon:
                        startActivity(new Intent(getApplicationContext(),ListDataActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * Message popup
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Deletes selected sleep from database
     * Automatically resets the ID when removing a sleep
     *
     * @param view the view
     */
    public void deleteData(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ID= " + (i + 1));

        //Reset the id when deleting a sleep
        String strSQL1 = "UPDATE people_table SET id = (id +1) WHERE id < 0";
        String strSQL = "UPDATE people_table SET id = (id -1) WHERE id > 1";
        db.execSQL(strSQL);
        db.execSQL(strSQL1);
        //After deleting sleep goes back to the listview
        Intent intent = new Intent(DetailsActivity.this, ListDataActivity.class);
        startActivity(intent);
        toastMessage("Sleep deleted");

    }


}

