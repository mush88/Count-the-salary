package olesya.example.com.salary10;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by olesya on 04.05.15.
 */
public class ShowCatalogue extends Activity {


    EditText id, name, surname,days, number,casing, salary, year, month;
    Cursor c;

    MyDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_of_catalogue);

        id = (EditText) findViewById(R.id.editText);
        name = (EditText) findViewById(R.id.editText2);
        surname = (EditText) findViewById(R.id.editText3);
        casing = (EditText) findViewById(R.id.editText4);
        salary = (EditText) findViewById(R.id.editText5);
        days = (EditText) findViewById(R.id.editText6);
        year = (EditText) findViewById(R.id.editText7);
        month = (EditText) findViewById(R.id.editText8);

        dbHelper = new MyDbHelper(this);
        db = dbHelper.getWritableDatabase();

        //String query
        // = "SELECT * FROM " + MyDbHelper.TABLE_MEMBER + " WHERE " + MyDbHelper.MEMBER_ID + " = " + 1;
        String query = "SELECT  * FROM " + MyDbHelper.TABLE_MEMBER + " INNER JOIN "
                + MyDbHelper.TABLE_SALARY + " ON " + MyDbHelper.MEMBER_ID + " = " + MyDbHelper.SALARY_ID;
                //+ " AND " + MyDbHelper.MEMBER_ID + " = 1";

        c = db.rawQuery(query, null);
        c.moveToFirst();
        show(c);


        //onClick1();
        //setResult(RESULT_OK, null);
        //finish();
    }

    public Cursor onClick2(View v) {
            //if(!c.moveToNext())
              //  return c;

            c.moveToNext();
            show(c);

        return c;
    }

    public void show(Cursor c) {

        long id1 = c.getLong(c.getColumnIndex(MyDbHelper.MEMBER_ID));
        String name1 = c.getString(c.getColumnIndex(MyDbHelper.MEMBER_FIRSTNAME));
        String surname1 = c.getString(c.getColumnIndex(MyDbHelper.MEMBER_LASTNAME));
        long casing1 = c.getLong(c.getColumnIndex(MyDbHelper.SALARY_CASING));
        long days1 = c.getLong(c.getColumnIndex(MyDbHelper.SALARY_DAYS));
        long year1 = c.getLong(c.getColumnIndex(MyDbHelper.SALARY_YEAR));
        long month1 = c.getLong(c.getColumnIndex(MyDbHelper.SALARY_MONTH));

        id.setText("id = " + id1);
        name.setText(name1);
        surname.setText(surname1);
        casing.setText("casing = " + casing1);
        days.setText("days = " + days1);
        year.setText("year = " + year1);
        month.setText("month = " + month1);

    }

    public void update_record(View v) {
       long id1 = c.getLong(c.getColumnIndex(MyDbHelper.MEMBER_ID));
       //Toast.makeText(this, "Action update invoked" + id1, Toast.LENGTH_SHORT).show();

       String name1 = name.getText().toString();
       String surname1 = surname.getText().toString();


       ContentValues cv = new ContentValues();
       cv.put(MyDbHelper.MEMBER_FIRSTNAME, name1);
       cv.put(MyDbHelper.MEMBER_LASTNAME, surname1);
       //cv.put(MyDbHelper.SALARY_CASING, casing1);

       db.update(MyDbHelper.TABLE_MEMBER, cv, "_id" + "=" + id1, null);

    }

}
