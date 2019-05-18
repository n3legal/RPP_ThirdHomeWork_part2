package com.example.labwork3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public final static int DB_VERSION = 2;

    public DBHelper(Context context){
        super(context, "StudentsDB", null, DB_VERSION);
    }

    /*public DBHelper(Context context, int DB_VERSION){
        super(context, "StudentsDB", null, DB_VERSION);
        this.DB_VERSION = DB_VERSION;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("LAB", "onCreate()");
        if (DB_VERSION == 1) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Students" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "FIO TEXT," +
                    "Time REAL  NOT NULL)"
            );
        }
        else
            db.execSQL("CREATE TABLE IF NOT EXISTS Students" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "Surname TEXT," +
                    "Name TEXT," +
                    "Patronymic TEXT," +
                    "Time REAL NOT NULL)"
            );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion > oldVersion){
            Log.i("NEW_VERS", Integer.toString(newVersion));
            //oldVersion++;
            db.execSQL("CREATE TABLE IF NOT EXISTS Students2" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "Surname TEXT," +
                    "Name TEXT," +
                    "Patronymic TEXT," +
                    "Time REAL NOT NULL)"
            );
            Cursor c = db.rawQuery("SELECT id, FIO, Time FROM Students", null);
            ContentValues cv = new ContentValues();
            if (c.moveToFirst()){
                do {
                    cv.clear();
                    cv.put("id", c.getInt(0));
                    String FIO = c.getString(1);
                    cv.put("Surname", FIO.substring(0, FIO.indexOf(' ')));
                    cv.put("Name", FIO.substring(FIO.indexOf(' ')+1, FIO.substring(FIO.indexOf(' ')+1).indexOf(' ') + FIO.indexOf(' ')+1));
                    cv.put("Patronymic", FIO.substring(FIO.substring(FIO.indexOf(' ')+1).indexOf(' ') + FIO.indexOf(' ')+2));
                    cv.put("Time", c.getLong(2));
                    db.insert("Students2", null, cv);
                }while (c.moveToNext());
            }
            /*db.rawQuery("INSERT INTO Students2(id, Surname, Name, Patronymic, Time) SELECT id, " +
                    "SUBSTR(FIO, 0, CHARINDEX(' ', FIO)), " +
                    "SUBSTR(FIO, CHARINDEX(' ', FIO)+1, CHARINDEX(' ', SUBSTR(FIO, CHARINDEX(' ', FIO)+1))), " +
                    "SUBSTR(FIO, CHARINDEX(' ', SUBSTR(FIO, CHARINDEX(' ', FIO)+1))+1), " +
                    "Time FROM Students", null);*/
            db.execSQL("DROP TABLE IF EXISTS Students");
            db.execSQL("ALTER TABLE Students2 RENAME TO Students");
            c.close();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 2 && newVersion == 1){
            Log.i("NEW_VERS", Integer.toString(newVersion));
            oldVersion++;
            db.execSQL("CREATE TABLE IF NOT EXISTS Students2" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "FIO TEXT," +
                    "Time REAL NOT NULL)"
            );
            Cursor c = db.rawQuery("SELECT id, Surname, Name, Patronymic, Time FROM Students", null);
            ContentValues cv = new ContentValues();
            if (c.moveToFirst()){
                do {
                    cv.clear();
                    cv.put("id", c.getInt(0));
                    String FIO = c.getString(1) + " " + c.getString(2)  + " " +  c.getString(3);
                    cv.put("FIO", FIO);
                    cv.put("Time", c.getLong(4));
                    db.insert("Students2", null, cv);
                }while (c.moveToNext());
            }
            /*db.rawQuery("INSERT INTO Students2(id, Surname, Name, Patronymic, Time) SELECT id, " +
                    "SUBSTR(FIO, 0, CHARINDEX(' ', FIO)), " +
                    "SUBSTR(FIO, CHARINDEX(' ', FIO)+1, CHARINDEX(' ', SUBSTR(FIO, CHARINDEX(' ', FIO)+1))), " +
                    "SUBSTR(FIO, CHARINDEX(' ', SUBSTR(FIO, CHARINDEX(' ', FIO)+1))+1), " +
                    "Time FROM Students", null);*/
            db.execSQL("DROP TABLE IF EXISTS Students");
            db.execSQL("ALTER TABLE Students2 RENAME TO Students");
            c.close();
        }
    }

    /*public void setVersionOfDB(int DB_VERSION){
        SQLiteDatabase db = getWritableDatabase();
        this.DB_VERSION = DB_VERSION;
        db.rawQuery("UPDATE Version SET version = '"+ DB_VERSION + "'", null).close();
    }

    public int getVersionOfDB(int DB_VERSION){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT version FROM Version", null);
        }catch (SQLException ex){
            createVersionTable(DB_VERSION, false);
            c = db.rawQuery("SELECT version FROM Version", null);
        }
        if (c.moveToFirst()){
            Log.i("YES", "");
            DB_VERSION = c.getInt(0);
            this.DB_VERSION = DB_VERSION;
        }else {
            this.DB_VERSION = DB_VERSION;
            createVersionTable(DB_VERSION, true);
        }
        c.close();
        return DB_VERSION;
    }*/

    /*public void createVersionTable(int version, boolean needChange){
        DB_VERSION = version;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS Version(version INTEGER NOT NULL PRIMARY KEY)");
        Cursor c= null;
        if (needChange) {
            c = db.rawQuery("SELECT COUNT(version) FROM Version", null);
            if (c.moveToFirst())
                if (c.getInt(0) > 0)
                    db.rawQuery("UPDATE Version SET version = '" + DB_VERSION + "'", null).close();
                else
                    db.rawQuery("INSERT INTO Version VALUES(" + DB_VERSION + ")", null).close();
        }
        if (c != null)
            c.close();
    }*/

    public void changeStudent(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String surname = "Иванов", name = "Иван", patronymic = "Иванович";
        db.execSQL("UPDATE Students SET Surname = '" + surname + "', Name = '" + name + "', Patronymic = '" + patronymic + "'" + " WHERE id = " + id);
        db.close();
    }

    public void addStudent(){
        String arraySurname[] = {
                "Чекановкин", "Шачнов", "Карчевский", "Шардаков", "Кунегин", "Светличный", "Нефедов",
                "Щербаков", "Бембеев", "Бадаев", "Есиков", "Резников", "Алаторцев", "Шапошников", "Мурадян",
                "Ангелюк", "Ахов", "Шуляк", "Кузнецов", "Крючков", "Ким", "Жмышенко", "Попцов"
        };
        String arrayName[] = {
                "Никита", "Анатолий", "Игорь", "Алексадр", "Руслан", "Леонид", "Филипп",
                "Матвей", "Фёдор", "Григорий", "Андрей", "Сергей", "Даниил", "Дмитрий",
                "Эдуард", "Ярослав", "Василий", "Михаил", "Евгений", "Виктор", "Замба"
        };
        String arrayPatronymic [] = {
                "Максимович", "Анатольевич", "Игоревич", "Александрович", "Русланович", "Леонидович", "Филиппович",
                "Матвеевич", "Фёдорович", "Григорьевич", "Андреевич", "Сергеев", "Даниилович", "Дмитриевич", "Эдуардович",
                "Алексеевич", "Ярославоввич", "Васильевич", "Михаилович", "Евгеньевич", "Викторович"
        };
        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        ContentValues cv = new ContentValues();
        int randSurname = (int)(Math.random() * arraySurname.length), randName = (int)(Math.random() * arrayName.length), randPatronymic = (int)(Math.random() * arrayPatronymic.length);
        cv.put("id", id + 1);
        cv.put("Surname", arraySurname[randSurname]);
        cv.put("Name", arrayName[randName]);
        cv.put("Patronymic", arrayPatronymic[randPatronymic]);
        cv.put("Time", date.getTime());
        db.insert("Students", null, cv);
        c.close();
        db.close();
    }

    public void addFiveStudents(){
        SQLiteDatabase db = getWritableDatabase();

        for (int i = 0; i < 5; i++)
            addStudent();
        db.close();
    }

    public ArrayList<StudentNew> readAll(){
        ArrayList<StudentNew> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id, Surname, Name, Patronymic FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                list.add(new StudentNew(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void changeStudentOld(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String t = "Иванов Иван Иванович";
        db.execSQL("UPDATE Students SET FIO = '" + t + "' WHERE id = " + id);
    }

    public void addStudentOld(){
        String arraySurname[] = {
                "Николаев", "Шатилов", "Пачкин", "Медянник", "Барчо", "Лаврентьев", "Русаков",
                "Имасс", "Фурсов", "Трофимов", "Ананьев", "Аракелян", "Комов", "Аджигитов", "Маилян",
                "Санников", "Никифоров", "Хитров", "Кузнецов", "Торхов", "Крючков", "Ким", "Винтер"
        };
        String arrayName[] = {
                "Никита", "Анатолий", "Игорь", "Алексадр", "Руслан", "Леонид", "Филипп",
                "Матвей", "Фёдор", "Григорий", "Андрей", "Сергей", "Даниил", "Дмитрий", "Эдуард",
                "Радмир", "Алекс", "Эльвек", "Ярослав", "Василий", "Михаил", "Евгений", "Виктор"
        };
        String arrayPatronymic [] = {
                "Никитович", "Анатольевич", "Игоревич", "Александрович", "Русланович", "Леонидович", "Филиппович",
                "Матвеевич", "Фёдорович", "Григорьевич", "Андреевич", "Сергеев", "Даниилович", "Дмитриевич", "Эдуардович",
                "Радмирович", "Алексеевич", "Эльвекович", "Ярославоввич", "Васильевич", "Михаилович", "Евгеньевич", "Викторович"
        };
        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        ContentValues cv = new ContentValues();
        int randSurname = (int)(Math.random() * arraySurname.length), randName = (int)(Math.random() * arrayName.length), randPatronymic = (int)(Math.random() * arrayPatronymic.length);
        String FIO = arraySurname[randSurname] + " " + arrayName[randName] + " " + arrayPatronymic[randPatronymic];
        cv.put("id", id + 1);
        cv.put("FIO", FIO);
        cv.put("Time", date.getTime());
        db.insert("Students", null, cv);
        c.close();
        db.close();
    }

    public void addFiveStudentsOld(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Students");

        for (int i = 0; i < 5; i++)
            addStudentOld();
        db.close();
    }

    public ArrayList<StudentOld> readAllOld(){
        ArrayList<StudentOld> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                list.add(new StudentOld(c.getInt(0), c.getString(1)));
                Log.i("КОНТАКТЫ = ", list.toString());
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    /*public int getNewVersion() {
        return DB_VERSION;
    }

    public void setNewVersion(int DB_VERSION) {
        this.DB_VERSION = DB_VERSION;
    }*/
}
