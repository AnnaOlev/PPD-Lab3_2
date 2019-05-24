package com.example.lab3_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Student";
    private static final String TABLE_STUDENT = "student";
    private static final String TABLE_STUDENT2 = "student2";
    private static final String KEY_ID = "id";
    private static final String KEY_FIO = "fio";
    private static final String KEY_I = "name";
    private static final String KEY_F = "surname";
    private static final String KEY_O = "patronymic";
    private static final String KEY_TIME = "time_of_writing";

    private static final String DB_CREATE = "create table " + TABLE_STUDENT + "(" + KEY_ID  + " integer primary key autoincrement NOT NULL," + KEY_FIO
            + " text," + KEY_TIME + " text" + ")";
    private static final String DB_CREATE_NEW = "create table " + TABLE_STUDENT2 + "(" + KEY_ID  + " integer primary key autoincrement NOT NULL," + KEY_F
            + " text," + KEY_I + " text," + KEY_O + " text,"+ KEY_TIME + " text" + ")";

    private final String format = "dd.MM.yy HH:mm";
    private ArrayList<String> list = new ArrayList<>();

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        list.add("Абдуалимов Амирхон Рустамович"); list.add("Акжигитов Радмир Русланович"); list.add("Артемов Александр Андреевич"); list.add("Аскандер Стивен Самех Саад"); list.add("Болдырев Григорий Михайлович"); list.add("Гарянин Никита Андреевич"); list.add("Гриценко Александр Сергеевич"); list.add("Звенигородская Арина Александровна"); list.add("Зекирьяев Руслан Тимурович"); list.add("Исхак Керолос Камал");
        list.add("Коватьев Иван Михайлович"); list.add("Костина Оксана Владимировна"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Миночкин Константин Даниилович"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Миночкин Константин Даниилович");list.add("Нгуен Куок Ань"); list.add("Олевская Анна Леонидовна"); list.add("Рабочих Андрей Юрьевич"); list.add("Сторожук Даниил Сергеевич"); list.add("Терентьев Павел Владимирович");
        list.add("Турсунов Парвиз Бахоралиевич"); list.add("Флоря Дмитрий"); list.add("Чимидов Эльвек Эренцович"); list.add("Шатров Савелий Иванович");
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        for (int i =0; i < 5; i++) {
            int rand = 0 + (int) (Math.random() * list.size());
            StringBuffer name = new StringBuffer(), surname = new StringBuffer(), patronymic = new StringBuffer();
            split(list.get(rand), name, surname, patronymic);
            ContentValues initialValues = createContentValues(surname.toString(), name.toString(), patronymic.toString(), date);
            db.insert(TABLE_STUDENT, null, initialValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "i was here");
        db.beginTransaction();
        try {
            db.execSQL(DB_CREATE_NEW);
            Cursor table = db.query(TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME }, null, null, null,
                    null, null);
            if (table != null) {
                table.moveToFirst();
                while (!table.isLast()){
                    StringBuffer surname = new StringBuffer(), name = new StringBuffer(), patro = new StringBuffer();
                    split (table.getString(1), surname, name, patro);
                    ContentValues initialValues = createContentValues(surname.toString(), name.toString(), patro.toString(), table.getString(2));
                    db.insert(TABLE_STUDENT2, null, initialValues);
                    table.moveToNext();
                }
                StringBuffer surname = new StringBuffer(), name = new StringBuffer(), patro = new StringBuffer();
                split (table.getString(1), surname, name, patro);
                ContentValues initialValues = createContentValues(surname.toString(), name.toString(), patro.toString(), table.getString(2));
                db.insert(TABLE_STUDENT2, null, initialValues);
            }
            db.execSQL("drop table if exists " + TABLE_STUDENT);
            db.execSQL("ALTER TABLE " + TABLE_STUDENT2 + " RENAME TO " + TABLE_STUDENT);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    void addValues(SQLiteDatabase db){
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        for (int i =0; i < 5; i++) {
            int rand = 0 + (int) (Math.random() * list.size());
            StringBuffer name = new StringBuffer(), surname = new StringBuffer(), patronymic = new StringBuffer();
            split(list.get(rand), name, surname, patronymic);
            ContentValues initialValues = createContentValues(surname.toString(), name.toString(), patronymic.toString(), date);
            db.insert(TABLE_STUDENT, null, initialValues);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_STUDENT);
        onCreate(db);
    }


    private ContentValues createContentValues(String surname, String name, String patro, String TIME) {
        ContentValues values = new ContentValues();
        values.put(KEY_F, surname);
        values.put(KEY_I, name);
        values.put(KEY_O, patro);
        values.put(KEY_TIME, TIME);
        return values;
    }

    void update() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(true, TABLE_STUDENT, new String[] { KEY_ID, KEY_F, KEY_I, KEY_O, KEY_TIME}, null,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToLast();
        }
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        db.execSQL("UPDATE " + TABLE_STUDENT + " SET " + KEY_F + " = 'Иванов'," + KEY_I + " = 'Иван'," + KEY_O + " = 'Иванович', " +
                KEY_TIME + " = '" + date + "' WHERE " + KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM " + TABLE_STUDENT + ")");
    }

    void add(String FIO) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        StringBuffer name = new StringBuffer(), surname = new StringBuffer(), patronymic = new StringBuffer();
        split(FIO, surname, name, patronymic);
        ContentValues values = createContentValues(surname.toString(), name.toString(), patronymic.toString(), date);
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public void add(String F, String I, String O) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        ContentValues values = createContentValues(F, I,O, date);
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    Cursor getStudentTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, new String[] { KEY_ID, KEY_F, KEY_I, KEY_O, KEY_TIME}, null, null, null,
                null, null);
    }

    private void split(String string, StringBuffer surname, StringBuffer name, StringBuffer patro){
        int i = string.indexOf(' ');
        if (i != -1) {
            surname.append(string, 0, i);
            string = string.substring(i + 1);
            i = string.indexOf(' ');
            if (i != -1) {
                name.append(string, 0, i);
                if (i+1 <= string.length()) {
                    string = string.substring(i + 1);
                    patro.append(string);
                }
            }
            else name.append(string);
        }
        else surname.append(string);
    }

}
