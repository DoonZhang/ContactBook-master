package cn.jike1501_36.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by DoonZhang on 2018/6/19.
 */
public class DBAdapter {

    private static final String DB_NAME = "contactBook.db";
    private static final String DB_TABLE = "contact";
    private static final int DB_version = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHONE2 = "phone2";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_SEX = "sex";
    private static final String KEY_COMPANY = "company";


    private SQLiteDatabase db;
    private final Context context;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + "(" + KEY_ID + " integer primary key autoincrement," +
                KEY_NAME + " varchar(20)," + KEY_PHONE + " varchar(20)," +
                KEY_PHONE2 + " varchar(20)," + KEY_EMAIL + " varchar(50)," +
                KEY_PHOTO + " varchar(100)," + KEY_SEX + " varchar(10)," + KEY_COMPANY + " varchar(100))";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }

    }

    public DBAdapter(Context _context) {
        context = _context;
    }

    /**
     * 打开数据库
     * @throws SQLiteException
     */
    public void open() throws SQLiteException {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_version);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * 插入联系人
     *
     * @param contact
     * @return
     */
    public long insert(Contact contact) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, contact.getName());
        newValues.put(KEY_PHONE, contact.getPhone());
        newValues.put(KEY_PHONE2, contact.getPhone2());
        newValues.put(KEY_EMAIL, contact.getEmail());
        newValues.put(KEY_PHOTO, contact.getPhoto());
        newValues.put(KEY_SEX, contact.getSex());
        newValues.put(KEY_COMPANY, contact.getCompany());
        return db.insert(DB_TABLE, null, newValues);
    }

    /**
     * 默认联系人
     *
     * @param contact
     * @return
     */
    public long insert_default(Contact contact) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, "张三");
        newValues.put(KEY_NAME, "李四");
        newValues.put(KEY_NAME, "王五");
        newValues.put(KEY_NAME, "赵六");
        newValues.put(KEY_PHONE, "110");
        newValues.put(KEY_PHONE, "111");
        newValues.put(KEY_PHONE, "112");
        newValues.put(KEY_PHONE, "113");
        db.close();
        return db.insert(DB_TABLE, null, newValues);

    }


    /**
     * 删除联系人
     * @param id
     * @return
     */
    public long delete(int id) {
        return db.delete(DB_TABLE, KEY_ID + " like ? ", new String[]{id + ""});
    }

    /**
     * 更新联系人
     * @param id
     * @param contact
     * @return
     */
    public long update(int id, Contact contact) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, contact.getName());
        updateValues.put(KEY_PHONE, contact.getPhone());
        updateValues.put(KEY_PHONE2, contact.getPhone2());
        updateValues.put(KEY_EMAIL, contact.getEmail());
        updateValues.put(KEY_PHOTO, contact.getPhoto());
        updateValues.put(KEY_SEX, contact.getSex());
        updateValues.put(KEY_COMPANY, contact.getCompany());
        return db.update(DB_TABLE, updateValues, KEY_ID + " like ? ", new String[]{id + ""});
    }

    /**
     * 根据ID查询联系人
     * @param id
     * @return
     */
    public Contact[] getContact(int id) {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_PHONE2, KEY_EMAIL,
                        KEY_PHOTO, KEY_SEX, KEY_COMPANY},
                KEY_ID + " like ? ", new String[]{id + ""}, null, null, null, null);
        return ConvertToContact(cursor);
    }
    /**
     * 查询所有联系人
     * @return
     */
    public Contact[] getAll() {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PHONE, KEY_PHONE2, KEY_EMAIL,
                        KEY_PHOTO, KEY_SEX, KEY_COMPANY},
                null, null, null, null, KEY_NAME + " asc");
        return ConvertToContact(cursor);
    }

    /**
     * 将游标cursor中的联系人取出，放到联系人数组中
     * @param cursor
     * @return 联系人数组
     */
    private Contact[] ConvertToContact(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) return null;
        Contact[] peoples = new Contact[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            peoples[i] = new Contact();
            peoples[i].setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            peoples[i].setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            peoples[i].setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            peoples[i].setPhone2(cursor.getString(cursor.getColumnIndex(KEY_PHONE2)));
            peoples[i].setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            peoples[i].setPhoto(cursor.getString(cursor.getColumnIndex(KEY_PHOTO)));
            peoples[i].setSex(cursor.getString(cursor.getColumnIndex(KEY_SEX)));
            peoples[i].setCompany(cursor.getString(cursor.getColumnIndex(KEY_COMPANY)));
            cursor.moveToNext();
        }
        return peoples;
    }
}
