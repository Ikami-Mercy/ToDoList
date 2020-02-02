package com.myToDoList.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myToDoList.constants.Constants;
import com.myToDoList.model.Task;

import java.util.ArrayList;

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

        if (sInstance == null) {
            sInstance = new DbHandler(context.getApplicationContext());
        }
        return sInstance;
    }


//Write your task

    private void saveTask(Task task) {

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        ContentValues taskContentValues = new ContentValues();

        taskContentValues.put(Constants.COLUMN_TASK_TITTLE, task.getTaskTittle());
        taskContentValues.put(Constants.COLUMN_TASK_ID, task.getTaskTittle());
        taskContentValues.put(Constants.COLUMN_TASK_CONTENT, task.getTaskContent());
        taskContentValues.put(Constants.COLUMN_TASK_TYPE, task.getTaskType());

        if (!isRecordExists(task.getTaskID(), db, Constants.TABLE_TASK, Constants.COLUMN_TASK_ID)) {
            db.insert(Constants.TABLE_TASK, null, taskContentValues);

        } else {
            updateContact(task);
        }

    }

    // Get all your tasks

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_TASK;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    Task task = new Task();
                    task.setTaskTittle(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TASK_TITTLE)));
                    task.setTaskContent(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TASK_CONTENT)));
                    task.setTaskType(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_TASK_TYPE)));

                    tasks.add(task);
                    //Log.i(TAG, "Contact:: " + contact.toString());
                }
                while (cursor.moveToNext());
            }
        }

        return tasks;
    }


    // Get a task filtered by Task type

    public Task getTaskByType(int type) {
        SQLiteDatabase db = getWritableDatabase();
        Task task = new Task();
        Cursor cursor = db.query(Constants.TABLE_TASK,
                new String[]{Constants.COLUMN_TASK_TITTLE, Constants.COLUMN_TASK_CONTENT, Constants.COLUMN_TASK_TYPE},
                Constants.COLUMN_TASK_TYPE + "=?",
                new String[]{String.valueOf(type)}, null, null, null);


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            task.setTaskTittle(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TASK_TITTLE)));
            task.setTaskContent(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TASK_CONTENT)));
            task.setTaskType(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_TASK_TYPE)));
        }

        return task;
    }

    // Update an existing task

    public void updateContact(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues taskContentValues = new ContentValues();

        taskContentValues.put(Constants.COLUMN_TASK_TITTLE, task.getTaskTittle());
        taskContentValues.put(Constants.COLUMN_TASK_CONTENT, task.getTaskContent());
        taskContentValues.put(Constants.COLUMN_TASK_TYPE, task.getTaskType());

        String where = Constants.COLUMN_TASK_ID + "= ?";
        db.update(Constants.TABLE_TASK, taskContentValues, where, new String[]{task.getTaskID()});

        Log.e(TAG, "Record updated");
    }

    public boolean isRecordExists(String searchItem, SQLiteDatabase db, String tableName, String column) {

        String[] columns = {column};
        String selection = column + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = db.query(tableName, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
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
                Constants.COLUMN_TASK_ID + " VARCHAR, " +
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
