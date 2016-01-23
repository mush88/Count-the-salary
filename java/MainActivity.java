package olesya.example.com.salary10;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TableLayout table_layout;
	EditText firstname_et, lastname_et;
	Button previous_btn;

	SQLController sqlcon;
    Cursor c, cur;

	ProgressDialog PD;

    Boolean b = false;

    long num_of_month;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sqlcon = new SQLController(this);

		firstname_et = (EditText) findViewById(R.id.fistname_et_id);
		lastname_et = (EditText) findViewById(R.id.lastname_et_id);
		previous_btn = (Button) findViewById(R.id.button_down);
		table_layout = (TableLayout) findViewById(R.id.tableLayout1);


//		BuildTable();

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        sqlcon.open();
        cur = sqlcon.getPrevMonth(today.month);
        num_of_month = today.month;

        BuildTable();
        sqlcon.close();


        previous_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                sqlcon.open();

                num_of_month = num_of_month-1;
                cur = sqlcon.getPrevMonth(num_of_month);
                //cur.moveToPrevious();

                table_layout.removeAllViews();
                BuildTable();
                sqlcon.close();
			//	new MyAsync().execute()

			}
		});


	}

    public Cursor getCur() {
        return cur;
    }

	private void BuildTable() {

		sqlcon.open();
		//Cursor c = sqlcon.readEntry();
        //Cursor c = sqlcon.readSalary();
        //c = sqlcon.readEmployeeWithSalary();
        //c = sqlcon.readSalaryOnly();
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        //c = sqlcon.readAll();
        //c = getCur();

        int rows = cur.getCount();
		int cols = cur.getColumnCount();

		cur.moveToFirst();

		// outer for loop
		for (int i = 0; i < rows; i++) {

			TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			// inner for loop
			for (int j = 0; j < cols; j++) {

				TextView tv = new TextView(this);
				tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));

				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(18);
				tv.setPadding(0, 5, 0, 5);

				tv.setText(cur.getString(j));

				row.addView(tv);

			}

			cur.moveToNext();

			table_layout.addView(row);

		}
		sqlcon.close();
	}

	private class MyAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			table_layout.removeAllViews();

			PD = new ProgressDialog(MainActivity.this);
			PD.setTitle("Please Wait..");
			PD.setMessage("Loading...");
			PD.setCancelable(false);
			PD.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			//String firstname = firstname_et.getText().toString();
            int firstname = Integer.valueOf(firstname_et.getText().toString());
			int lastname = Integer.valueOf(lastname_et.getText().toString());

			// inserting data
			sqlcon.open();
		//	sqlcon.insertData(firstname, lastname);

            //sqlcon.insertSalary(firstname, lastname);
			// BuildTable();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			BuildTable();
			PD.dismiss();
		}
	}

    //---------------------CONTEXT MENU--------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                //Toast.makeText(this, "Action add invoked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddData.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.catalogue:
                Intent intent1 = new Intent(this, ShowCatalogue.class);
                startActivityForResult(intent1, 1);
                return true;
            case R.id.get_salary:
                //Toast.makeText(this, "Action calc salary invoked", Toast.LENGTH_SHORT).show();
            {
                calcSalary();
                table_layout.removeAllViews();
                BuildTable();
                return true;
            }
            case R.id.add_month:
                //Toast.makeText(this, "Insert new month invoked", Toast.LENGTH_SHORT).show();
                addMonth();
                table_layout.removeAllViews();
                BuildTable();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        table_layout.removeAllViews();
        BuildTable();
        Toast.makeText(this, "Inserting is done", Toast.LENGTH_SHORT).show();

    }

    public void calcSalary() {
        sqlcon.open();
        //long month = 3;
        Cursor cur2 = sqlcon.readAllByMonth(num_of_month);
        cur2.moveToFirst();

        do {
//            long id1 = c.getLong(c.getColumnIndex(MyDbHelper.MEMBER_ID));
            long salary_id = cur2.getLong(cur2.getColumnIndex(MyDbHelper.SALARY_ID));
//        String name1 = c.getString(c.getColumnIndex(MyDbHelper.MEMBER_FIRSTNAME))
//        String surname1 = c.getString(c.getColumnIndex(MyDbHelper.MEMBER_LASTNAME));
            long casing1 = cur2.getLong(cur2.getColumnIndex(MyDbHelper.SALARY_CASING));
            long days1 = cur2.getLong(cur2.getColumnIndex(MyDbHelper.SALARY_DAYS));


            sqlcon.calcSalary(salary_id, casing1, days1);
        }while(cur2.moveToNext());

        sqlcon.close();
    }

    public void addMonth(){
        sqlcon.open();
        Cursor cur1 = sqlcon.readEntry();
        //cur.moveToFirst();

        do{
            long id1 = cur1.getLong(cur1.getColumnIndex(MyDbHelper.MEMBER_ID));

            sqlcon.addNewMonth(id1);

        }while(cur1.moveToNext());

        sqlcon.close();
    }

}
