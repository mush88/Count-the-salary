package olesya.example.com.salary10;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by olesya on 03.05.15.
 */
public class AddData extends Activity {
    SQLController sqlcon;
    EditText name, surname,days, number,casing, s_e_id, year, month;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);

        name = (EditText) findViewById(R.id.textView4);
        surname = (EditText) findViewById(R.id.textView5);
        casing = (EditText) findViewById(R.id.textView8);
        s_e_id = (EditText) findViewById(R.id.textView9);
        days = (EditText) findViewById(R.id.textView10);
        year = (EditText) findViewById(R.id.textView6);
        month = (EditText) findViewById(R.id.textView7);

        but = (Button) findViewById(R.id.button);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        year.setText(today.year + "");
        month.setText(today.month + "");
    }

    public void OnClick1(View v) {
        switch (v.getId()) {
            case R.id.button: {
                String name1 = name.getText().toString();
                String surname1 = surname.getText().toString();

                int casing1 = Integer.valueOf(casing.getText().toString());
                int days1 = Integer.valueOf(days.getText().toString());
                int year1 = Integer.valueOf(year.getText().toString());
                int month1 = Integer.valueOf(month.getText().toString());
                int s_e_id1;

                sqlcon = new SQLController(this);
                sqlcon.open();

                s_e_id1 = sqlcon.getMaxId();

                sqlcon.insertData(name1, surname1);
                sqlcon.insertSalary(s_e_id1, casing1, days1, year1, month1, 0);
                sqlcon.close();
            }
            break;
            default:
                break;
        }
        //Intent intent = new Intent();
        //intent.putExtra("name", name.getText().toString());
        setResult(RESULT_OK, null);
        finish();
    }
}
