package fi.jonilassila.snoozebuddy;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView sleepname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        databaseHelper = new DatabaseHelper(this);
        sleepname = findViewById(R.id.txtName);
        populateListView();

        Bundle b = getIntent().getExtras();
        int i = b.getInt(MainActivity.EXTRA, 0);
    }


    private void populateListView() {
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
}

}
