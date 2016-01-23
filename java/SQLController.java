package olesya.example.com.salary10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLController {

	private MyDbHelper dbhelper;
	private Context ourcontext;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		ourcontext = c;
	}

	public SQLController open() throws SQLException {
		dbhelper = new MyDbHelper(ourcontext);
		database = dbhelper.getWritableDatabase();
		return this;

	}

	public void close() {
		dbhelper.close();
	}

	public void insertData(String name, String lname) {

		ContentValues cv = new ContentValues();
		cv.put(MyDbHelper.MEMBER_FIRSTNAME, name);
		cv.put(MyDbHelper.MEMBER_LASTNAME, lname);
		database.insert(MyDbHelper.TABLE_MEMBER, null, cv);

	}

    public void insertSalary(int s_e_id, int casing, int days, int year, int month, int salary) {

        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.SALARY_EMPLOYEE_ID, s_e_id);
        cv.put(MyDbHelper.SALARY_CASING, casing);
        cv.put(MyDbHelper.SALARY_DAYS, days);
        cv.put(MyDbHelper.SALARY_YEAR, year);
        cv.put(MyDbHelper.SALARY_MONTH, month);
        cv.put(MyDbHelper.SALARY, salary);

        database.insert(MyDbHelper.TABLE_SALARY, null, cv);

    }

    public void deleteRecord(long id) {

    }

    public Cursor getEmployee(long employee_id){
        String selectQuery = "SELECT * FROM " + MyDbHelper.TABLE_MEMBER + " WHERE " + MyDbHelper.MEMBER_ID + " = " + employee_id;

        Cursor c = database.rawQuery(selectQuery, null);

        return c;
    }

    public void addNewMonth(long s_e_id) {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.SALARY_EMPLOYEE_ID, s_e_id);
        cv.put(MyDbHelper.SALARY_CASING, 1000);
        cv.put(MyDbHelper.SALARY_DAYS, 30);
        cv.put(MyDbHelper.SALARY_YEAR, today.year);
        cv.put(MyDbHelper.SALARY_MONTH, today.month);
        cv.put(MyDbHelper.SALARY, 0);

        database.insert(MyDbHelper.TABLE_SALARY, null, cv);


    }

    public Cursor readEntry() {

		String[] allColumns = new String[] { MyDbHelper.MEMBER_ID, MyDbHelper.MEMBER_FIRSTNAME,
				MyDbHelper.MEMBER_LASTNAME };

		Cursor c = database.query(MyDbHelper.TABLE_MEMBER, allColumns, null, null, null,
				null, null);

		if (c != null) {
			c.moveToFirst();
		}
		return c;

	}

    public Cursor readSalary() {

        String[] allColumns = new String[] { MyDbHelper.SALARY_ID, MyDbHelper.SALARY_EMPLOYEE_ID,
                MyDbHelper.SALARY_CASING, MyDbHelper.SALARY_DAYS, MyDbHelper.SALARY_YEAR, MyDbHelper.SALARY_MONTH };

        Cursor c = database.query(MyDbHelper.TABLE_SALARY, allColumns, null, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public Cursor readEmployeeWithSalary() {

        String selectQuery = "SELECT * FROM " + MyDbHelper.TABLE_SALARY + " WHERE " + MyDbHelper.SALARY_EMPLOYEE_ID + " = 1 ";
/*        String selectQuery = "SELECT " + MyDbHelper.MEMBER_ID + ", " + MyDbHelper.MEMBER_FIRSTNAME + ", " + MyDbHelper.SALARY
                + " FROM " + MyDbHelper.TABLE_MEMBER + " INNER JOIN "
                + MyDbHelper.TABLE_SALARY + " ON " + MyDbHelper.MEMBER_ID + " = " + MyDbHelper.SALARY_EMPLOYEE_ID;
*/

        Cursor c = database.rawQuery(selectQuery, null);
        return c;
    }


    public Cursor readSalaryOnly() {

        String[] allColumns = new String[] { MyDbHelper.SALARY_ID, MyDbHelper.SALARY };

        Cursor c = database.query(MyDbHelper.TABLE_SALARY, allColumns, null, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public Cursor readAll() {
        String selectQuery = "SELECT "  + MyDbHelper.MEMBER_ID + ", "
                + MyDbHelper.MEMBER_FIRSTNAME + ", " + MyDbHelper.SALARY + ", "
                + MyDbHelper.SALARY_MONTH + " FROM " + MyDbHelper.TABLE_MEMBER + " INNER JOIN "
                + MyDbHelper.TABLE_SALARY + " ON " + MyDbHelper.MEMBER_ID + " = "
                + MyDbHelper.SALARY_EMPLOYEE_ID + " WHERE " + MyDbHelper.SALARY_MONTH + " = 3";

        Cursor c = database.rawQuery(selectQuery, null);

         return c;
    }

    public Cursor readAllByMonth(long month){
        String selectQuery = "SELECT * FROM " + MyDbHelper.TABLE_MEMBER + " INNER JOIN "
                + MyDbHelper.TABLE_SALARY + " ON " + MyDbHelper.SALARY_MONTH + " = " + month;
        Cursor c = database.rawQuery(selectQuery, null);
        return c;
    }

    public void calcSalary(long s_e_id, long casing, long days){
        long salary = casing + days;

        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.SALARY, salary);

        database.update(MyDbHelper.TABLE_SALARY, cv, "s_id = " + s_e_id, null);
    }

    public Cursor getPrevMonth(long i) {
        String selectQuery = "SELECT "  + MyDbHelper.MEMBER_ID + ", "
                + MyDbHelper.MEMBER_FIRSTNAME + ", " + MyDbHelper.SALARY + ", "
                + MyDbHelper.SALARY_MONTH + " FROM " + MyDbHelper.TABLE_MEMBER + " INNER JOIN "
                + MyDbHelper.TABLE_SALARY + " ON " + MyDbHelper.MEMBER_ID + " = "
                + MyDbHelper.SALARY_EMPLOYEE_ID + " WHERE " + MyDbHelper.SALARY_MONTH + " = " + i;

        Cursor c = database.rawQuery(selectQuery, null);
        return c;
    }

    public int getMaxId() {
        String squery = "SELECT MAX(" + MyDbHelper.MEMBER_ID + ") FROM " + MyDbHelper.TABLE_MEMBER;
        Cursor n = database.rawQuery(squery, null);

        int id = 0;
        if (n.moveToFirst())
        {
            do
            {
                id = n.getInt(0);
            } while(n.moveToNext());
        }
        return id;
    }
}
