package fi.jonilassila.snoozebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA = "fi.jonilassila.snoozebuddy_EXTRA";

    Button sleepStartButton;
    Button sleepEndButton;
    Button calculateButton;
    int hour, minute, hour2, minute2;
    String startTime, endTime;
    EditText sleepname;
    EditText sleepDescription;
    DatabaseHelper databaseHelper;
    long minutes, hours;

    /**
     * provide methods for converting date between a specific instant in time
     * fill the window with the UI provided from layout file
     * onCreate where we initialize our activity.
     * Database Helper is a simple utility interface for looking-up a datasource, retrieving a connection and performing SQL statement retrieval based on key replacements.
     *
     * @author Joni Lassila, Edvard Nivala
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sleepStartButton = findViewById(R.id.sleepStartTime);
        sleepEndButton = findViewById(R.id.sleepEndTime);
        calculateButton = findViewById(R.id.calcButton);
        sleepname = findViewById(R.id.editTxtSleepName);
        sleepDescription = findViewById(R.id.editTxtDescription);

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);

        Calendar time2 = Calendar.getInstance();
        time2.set(Calendar.HOUR_OF_DAY, hour2);
        time2.set(Calendar.MINUTE, minute2);

        databaseHelper = new DatabaseHelper(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                    case R.id.moon:
                        startActivity(new Intent(getApplicationContext(), ListDataActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * TimePicker static class that handles operations of the time selection fragment
     *
     * @param view
     * @author Edvard Nivala
     */
    // Done with the help of YouTube content creator Code With Cal
    public void popTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                sleepStartButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                startTime = selectedHour + ":" + selectedMinute;
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

    /**
     * TimePicker static class that handles operations of the time selection fragment
     * it is a widget for selecting the time of day, in either 24-hour or AM/PM mode
     *
     * @author Edvard Nivala
     */
    // Done with the help of YouTube content creator Code With Cal
    public void popTimePicker2(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker2, int selectedHour2, int selectedMinute2) {
                hour2 = selectedHour2;
                minute2 = selectedMinute2;
                sleepEndButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour2, minute2));
                endTime = selectedHour2 + ":" + selectedMinute2;
            }
        };

        // used to determine the style for the timepicker
        int style2 = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog endTimePickerDialog = new TimePickerDialog(this, style2, onTimeSetListener2, hour2, minute2, true);
        endTimePickerDialog.setTitle("Select time");
        endTimePickerDialog.show();

        /** onClick on the view for each ID specified
         * SimpleDateFormat concrete class for formatting and parsing dates in a locale-sensitive manner
         */
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                try {
                    Date a = sdf.parse(startTime);
                    Date b = sdf.parse(endTime);
                    long difference = (b.getTime() - a.getTime()) / 60000;
                    if (difference < 0) {
                        difference = difference + 1440;
                    }
                    Log.d("Time", String.valueOf(difference));
                    minutes = difference % 60;
                    hours = difference / 60;
                    Log.d("TimeHours", hours + ":" + minutes);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // submit name and description to

                // Gets sleep name and sleep description
                String newEntry = sleepname.getText().toString();
                String newEntry2 = sleepDescription.getText().toString();

                //Check if text field is empty
                long sleepMinutes = minutes;
                long sleepHours = hours;
                Log.d("Sleepminute1", String.valueOf(sleepHours));

                //If sleep name is empty doesn't allow placing data, description is optional
                if(newEntry.isEmpty()) {
                    toastMessage("Give your sleep a name");
                } else {
                    AddData(newEntry, newEntry2, sleepMinutes, sleepHours, startTime, endTime);
                    sleepname.setText("");
                    sleepDescription.setText("");
                }

            }
        });
    }

    /**
     * Adds data to the database
     *
     * @param newEntry     Sleep name
     * @param newEntry2    Sleep description
     * @param sleepMinutes Sleep minutes
     * @param sleepHours   Sleep hours
     * @param startTime    Sleep start time
     * @param endTime      Sleep end time
     *                     If data is succesfully placed to the database insertdata returns true otherwise false
     * @author Joni Lassila
     */
    public void AddData(String newEntry, String newEntry2, long sleepMinutes, long sleepHours, String startTime, String endTime) {
        boolean insertData = databaseHelper.addData(newEntry, newEntry2, sleepMinutes, sleepHours, startTime, endTime);
        Log.d("Sleepminute2", Long.toString(hours));
        //If insertdata is true
        if (insertData) {
            toastMessage(newEntry + " added to the list");
            toastMessage(sleepHours + " hours " + sleepMinutes + " minutes");
        } else {
            toastMessage("Error");
        }
    }

    /**
     * Takes to the listview
     * @author Ahmad Ksbiati Chahrour
     */
    public void viewData(View view) {
        Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
        startActivity(intent);
    }
    /**
     * Popup message
     * @author Ahmad Ksbiati Chahrour
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}