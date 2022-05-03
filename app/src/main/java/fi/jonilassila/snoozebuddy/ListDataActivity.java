package fi.jonilassila.snoozebuddy;

import static fi.jonilassila.snoozebuddy.MainActivity.EXTRA;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListDataActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    /**
     * onCreate called to do initial creation of the fragment
     */

    DatabaseHelper databaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listView = (ListView) findViewById(R.id.listview);
        databaseHelper = new DatabaseHelper(this);

        populateListView();

        /**
         * onItemClick method to determine which ListView item was pressed
         * starts a new Details activity which holds the stored database data to that specific item
         * @author Joni Lassila
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(ListDataActivity.this, DetailsActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.moon);

        /**
         * method onNavigationItemSelected is used to determine which icon in the bottom navigation bar is selected
         * @param item which icon or "item" is selected in the bottom navigation bar
         * @return return boolean value based on which icon or "item" is selected
         * @author Edvard Nivala
         */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    // if selected icon is "home", starts MainActivity.java
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.moon:
                        return true;
                }
                return false;
            }
        });
    }

    //Populates the list with added sleeps
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
