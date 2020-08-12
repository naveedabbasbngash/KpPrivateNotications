package kp.schools.notications.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import kp.schools.notications.Model.NotificationModel;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notificationDB";
    private static final String TABLE_CONTACTS = "notification";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "file_url";
    private static final String KEY_DES = "description";
    private static final String KEY_IMG = "imageurl";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " integer primary key autoincrement,"+ KEY_TITLE + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_DES + " TEXT," + KEY_IMG +" TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addContact(NotificationModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, contact.getTitle()); // title Name
        values.put(KEY_DATE, contact.getDate()); // Contact Name
        values.put(KEY_DES, contact.getDescription()); // Contact Phone
        values.put(KEY_IMG, contact.getImageurl()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    NotificationModel
    getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_TITLE,
                        KEY_DATE, KEY_DES}, KEY_TITLE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NotificationModel contact = new NotificationModel(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<NotificationModel> getAllContacts() {
        List<NotificationModel> contactList = new ArrayList<NotificationModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS +" ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationModel contact = new NotificationModel();
                contact.setTitle(cursor.getString(1));
                contact.setDate(cursor.getString(2));
                contact.setDescription(cursor.getString(3));
                contact.setImageurl(cursor.getString(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact

    // Deleting single contact

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}


 ;



        // Reading all contacts


