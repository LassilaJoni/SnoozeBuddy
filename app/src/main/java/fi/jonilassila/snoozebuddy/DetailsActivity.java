package fi.jonilassila.snoozebuddy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class DetailsActivity extends AppCompatActivity {

  /*  private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "sleepName";
    private static final String COL3 = "sleepDescription";
    private static final String COL4 = "sleepDurationMinutes";
    private static final String COL5 = "sleepDurationHours";
    String sleepnames;

    DatabaseHelper databaseHelper;
    TextView sleepname;
    int i;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

       /* databaseHelper = new DatabaseHelper(this);
        sleepname = findViewById(R.id.txtName);

        Bundle b = getIntent().getExtras();
        i = b.getInt(MainActivity.EXTRA, 0);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        //SELECT id FROM order WHERE orderNumber = '$orderNumber'
        //Cursor data = db.rawQuery("SELECT id FROM " + TABLE_NAME +" WHERE id = 3",null);
        //"SELECT column1,column2,column3 FROM table ", null
        //String column1 = data.getString(2);
        sleepname.setText(sleepnames);*/

        //populateDetailPage();
    }
/*
    private void populateDetailPage() {
        Cursor nameData = databaseHelper.getName();

            sleepnames = nameData.getString(1);

    }*/







}

