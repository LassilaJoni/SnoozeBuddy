package fi.jonilassila.snoozebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.icu.text.UFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button sleepStartButton;
    Button sleepEndButton;
    Button calculateButton;
    int hour, minute, hour2, minute2;
    String startTime, endTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sleepStartButton = findViewById(R.id.sleepStartTime);
        sleepEndButton = findViewById(R.id.sleepEndTime);
        calculateButton = findViewById(R.id.calcButton);

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);

        Calendar time2 = Calendar.getInstance();
        time2.set(Calendar.HOUR_OF_DAY, hour2);
        time2.set(Calendar.MINUTE, minute2);

    }

    /** Time picker to set sleeping start time
     *
     * @param view
     * Done with the help of YouTube content creator Code With Cal
     */
    public void popTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                sleepStartButton.setText(String.format(Locale.getDefault(),"%02d:%02d", hour, minute));

                startTime = selectedHour + ":" + selectedMinute;


            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }

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

        int style2 = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog endTimePickerDialog = new TimePickerDialog(this, style2, onTimeSetListener2, hour2, minute2, true);
        endTimePickerDialog.setTitle("Select time");
        endTimePickerDialog.show();

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
                    long minutes = difference%60;
                    long hours = difference/60;
                    Log.d("TimeHours", hours + ":" + minutes);
                    Log.d("TimeMinutes", String.valueOf(minutes));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //submit name and description to


            }
        });
    }
}