package com.myToDoList.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myToDoList.constants.Constants;
import com.myToDoList.model.Task;

public class DbHandler extends SQLiteOpenHelper {


    private static DbHandler sInstance;
    private final String TAG = DbHandler.class.getName();
    private SharedPreferences sharedPreferences;
    private String contactNumber;
    private Context context;


    private DbHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DbHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DbHandler(context.getApplicationContext());
        }
        return sInstance;
    }



    private void saveTask(Task task) {

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        ContentValues taskContentValues = new ContentValues();

        taskContentValues.put(Constants.COLUMN_TASK_TITTLE, task.getTaskTittle());
        taskContentValues.put(Constants.COLUMN_TASK_CONTENT, task.getTaskContent());
        taskContentValues.put(Constants.COLUMN_TASK_TYPE, task.getTaskType());


        }




    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TASKS_TABLE = "CREATE TABLE " + Constants.TABLE_TASK +
                "(" +
                Constants.COLUMN_TASK_PK + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Constants.COLUMN_TASK_TITTLE + " VARCHAR, " +
                Constants.COLUMN_TASK_CONTENT + " VARCHAR, " +
                Constants.COLUMN_TASK_TYPE + " VARCHAR" +
                ")";


        db.execSQL(CREATE_TASKS_TABLE);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }
}
