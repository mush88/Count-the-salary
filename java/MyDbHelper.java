package olesya.example.com.salary10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

	// TABLE INFORMATTION ABOUT EMPLOYEE
	public static final String TABLE_MEMBER = "member";

	public static final String MEMBER_ID = "_id";
	public static final String MEMBER_FIRSTNAME = "firstname";
	public static final String MEMBER_LASTNAME = "lastname";

    // TABLE INFORMATTION ABOUT SALARY
    public static final String TABLE_SALARY = "salary";

    public static final String SALARY_ID = "s_id";
    public static final String SALARY_EMPLOYEE_ID = "s_e_id";
    public static final String SALARY_CASING = "s_casing";
    public static final String SALARY_DAYS = "s_days";
    public static final String SALARY_YEAR = "s_year";
    public static final String SALARY_MONTH = "s_month";

    public static final String SALARY = "salary";

	// DATABASE INFORMATION
	static final String DB_NAME = "MEMBER.DB";
	static final int DB_VERSION = 1;

	// TABLE CREATION STATEMENT

	private static final String CREATE_TABLE = "create table " + TABLE_MEMBER
			+ "(" + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEMBER_FIRSTNAME + " TEXT NOT NULL ," + MEMBER_LASTNAME
			+ " TEXT NOT NULL);";

    private static final String CREATE_TABLE_SALARY = "create table " + TABLE_SALARY
            + "(" + SALARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SALARY_EMPLOYEE_ID + " INTEGER NOT NULL," + SALARY_CASING
            + " INTEGER NOT NULL," + SALARY_DAYS + " INTEGER NOT NULL," + SALARY_YEAR
            + " INTEGER NOT NULL," + SALARY_MONTH + " INTEGER NOT NULL," + SALARY + " INTEGER);";


    public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_SALARY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALARY);
		onCreate(db);

	}

}