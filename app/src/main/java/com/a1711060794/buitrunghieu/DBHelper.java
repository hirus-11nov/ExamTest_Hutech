package com.a1711060794.buitrunghieu;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bike.db";
    private static final int SCHEMA_VERSION = 1;
    // Bổ sung constructor chứa một tham số kiểu Context
    public DBHelper(Context context)
    {
        // gọi constructor của SQLiteOpenHelper truyền tên database và chema
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE bike (_id INTEGER PRIMARY KEY AUTOINCREMENT, cName TEXT, cID TEXT, rType TEXT, rBike TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
    // phuong thuc insert mot dong thong tin nha hang
    public void addData( String cName, String cID, String rType, String rBike)
    {
        // tao doi tuong du lieu ContentValue
        ContentValues cv = new ContentValues();
        // dua cac du lieu vao theo tung cap ten field va value
        cv.put("cName", cName);
        cv.put("cID", cID);
        cv.put("rType", rType);
        cv.put("rBike", rBike);
        // yeu cau SQLiteDatabase insert du lieu vao database
        getWritableDatabase().insert("bike", "cName", cv);
    }

    public void updateData (String cName, String cID, String rType, String rBike)
    {
        // tao doi tuong du lieu ContentValue
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // dua cac du lieu vao theo tung cap ten field va value
        cv.put("cName", cName);
        cv.put("cID",cID);
        cv.put("rType", rType);
        cv.put("rBike", rBike);
        // yeu cau SQLiteDatabase insert du lieu vao database
        db.update("bike",cv,"cID=?",new String[]{cID});
    }

    public void deleteData(String cID){
       getWritableDatabase().delete("bike","cID=?" ,new String[]{cID});
    }

    // phuong thuc truy van toan bo du lieu
    public Cursor getAll()
    {
        Cursor cur;
        cur = getReadableDatabase().rawQuery("SELECT _id, cName, cID, rType, rBike FROM bike ORDER BY _id", null);
        return (cur);
    }

    public String getcName(Cursor c)
    {
        // truy cap cot thu 2 la cot name
        return (c.getString(1));
    }
    public String getcID(Cursor c)
    {
        // truy cap cot thu 3 la cot cID
        return (c.getString(2));
    }
    public String getrType(Cursor c)
    {
        // truy cap cot thu 4 la type
        return (c.getString(3));
    }
    public String getrBike (Cursor c)
    {
        // truy cap cot thu 5 la bike
        return (c.getString(4));
    }

}// end DBHelper