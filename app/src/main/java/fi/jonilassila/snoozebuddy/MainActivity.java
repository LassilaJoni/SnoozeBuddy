package fi.jonilassila.snoozebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button sleepStartButton;
    Button sleepEndButton;
    int hour, minute, hour2, minute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sleepStartButton = findViewById(R.id.sleepStartTime);

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
                sleepEndButton.setText(String.format(Locale.getDefault(),"%02d:%02d", hour2, minute2));
            }
        };

        int style2 = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog endTimePickerDialog = new TimePickerDialog(this, style2, onTimeSetListener2, hour2, minute2, true);
        endTimePickerDialog.setTitle("Select time");
        endTimePickerDialog.show();
    }
}