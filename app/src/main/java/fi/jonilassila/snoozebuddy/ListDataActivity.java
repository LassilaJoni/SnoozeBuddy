package fi.jonilassila.snoozebuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;



public class ListDataActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    public final static String EXTRA = "fi.jonilassila.snoozebuddy_EXTRA";
    DatabaseHelper databaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        databaseHelper = new DatabaseHelper(this);
        ListView lv = findViewById(R.id.listview);
        populateListView();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(ListDataActivity.this, DetailsActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);

            }
        });

    }


    private void populateListView() {
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }


}
