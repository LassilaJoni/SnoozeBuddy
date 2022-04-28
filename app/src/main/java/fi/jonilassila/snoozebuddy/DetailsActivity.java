package fi.jonilassila.snoozebuddy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DetailsActivity extends AppCompatActivity {

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "sleepName";
    private static final String COL3 = "sleepDescription";
    private static final String COL4 = "sleepDurationMinutes";
    private static final String COL5 = "sleepDurationHours";
    String sleepnames;

    DatabaseHelper databaseHelper;
    TextView sleepname;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtDuration = findViewById(R.id.txtDuration);

        Bundle b = getIntent().getExtras();
        i = b.getInt(MainActivity.EXTRA, 0);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT sleepName, sleepDescription, sleepDurationMinutes, sleepDurationHours  FROM " + TABLE_NAME + " WHERE id= " + (i +1);

        Cursor c = db.rawQuery(selectQuery, null);
        String sleepName = "";
        String sleepDescription = "";
        String sleepMinutes = "";
        String sleepHours = "";
        if(c!=null) {
            c.moveToFirst();
            Log.i("cursor",c.getString(0));
            Log.i("cursor",c.getString(1));
            sleepName = c.getString(0);
            sleepDescription = c.getString(1);
            sleepMinutes = c.getString(2);
            sleepHours = c.getString(3);


        }
        txtName.setText(sleepName);
        txtDescription.setText(sleepDescription);
        txtDuration.setText(sleepHours + " hours and " + sleepMinutes + " minutes");



}

    }

