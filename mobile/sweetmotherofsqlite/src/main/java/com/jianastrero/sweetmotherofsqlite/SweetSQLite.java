package com.jianastrero.sweetmotherofsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.jianastrero.sweetmotherofsqlite.exception.SweetSQLiteSuperNotCalledException;
import com.jianastrero.sweetmotherofsqlite.exception.UnknownSubclassInstanceException;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Jian Astrero on 1/28/2017.
 */
public class SweetSQLite {

    public long id;
    private HashMap<String, String> map;
    private boolean isSuperCalled=false;
    private Object object;
    private boolean sweeter=true;

    public SweetSQLite() {
        id=-1;
        isSuperCalled=true;
        map=new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSweeter() {
        return sweeter;
    }

    public void setSweeter(boolean sweeter) {
        this.sweeter = sweeter;
    }

    public void addData(String key, Object value) {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        map.put(key, ""+value);
    }

    private void addAllData() {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();
        if (sweeter) {
            Field[] fields=object.getClass().getDeclaredFields();

            for (Field f:fields) {
                try {
                    addData(f.getName(), f.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSubclassInstance(SweetSQLite subclassInstance) {
        this.object = subclassInstance;

        Field[] fields=object.getClass().getDeclaredFields();
        String sql="CREATE TABLE "+object.getClass().getSimpleName().toUpperCase()+" (";
        sql+="ID INTEGER PRIMARY KEY,";

        for (int i=0; i<fields.length; i++) {
            Field f=fields[i];
            if (!f.getName().toUpperCase().equals("$CHANGE") && !f.getName().toUpperCase().equals("SERIALVERSIONUID"))
                sql+=""+f.getName().toUpperCase()+" "+f.getType().getSimpleName().toUpperCase();
            if (i<fields.length-1) sql+=",";
        }
        while (sql.substring(sql.length()-1).equals(",")) {
            sql=sql.substring(0, sql.length()-2);
        }
        sql+=")";

        try {
            SweetSQLiteConfig.dbWritable.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();

        if (sweeter) addAllData();

        ContentValues values=new ContentValues();

        for (String key:map.keySet()) {
            if (!key.equalsIgnoreCase("$CHANGE") && !key.equalsIgnoreCase("SERIALVERSIONUID"))
                values.put(key, map.get(key));
        }

        if (id==-1)
            SweetSQLiteConfig.dbWritable.insert(object.getClass().getSimpleName().toUpperCase(), null, values);
        else {
            SweetSQLiteConfig.dbWritable.update(object.getClass().getSimpleName().toUpperCase(), values, "ID='"+id+"'", new String[0]);
        }
    }

    public void delete() {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();

        String whereClause="";

        Field[] fields=object.getClass().getFields();

        for (int i=0; i<fields.length; i++) {
            if (fields[i].getName().toUpperCase().equalsIgnoreCase("$CHANGE") || fields[i].getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                continue;
            try {
                whereClause+=fields[i].getName().toUpperCase()+" = '"+fields[i].get(object)+"' ";
                whereClause+=" AND ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        while (whereClause.endsWith(" AND ")) {
            whereClause=whereClause.substring(0, whereClause.length()-6);
        }

        SweetSQLiteConfig.dbWritable.delete(object.getClass().getSimpleName(), whereClause, new String[0]);
    }

    public void delete(String whereClause) {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();

        SweetSQLiteConfig.dbWritable.delete(object.getClass().getSimpleName(), whereClause, new String[0]);
    }

    public boolean load(long id) {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();

        if (sweeter) addAllData();

        Field[] fields=object.getClass().getFields();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = new String[fields.length+1];

        projection[0]="ID";
        for (int i=0; i<fields.length; i++) {
            if (fields[i].getName().toUpperCase().equalsIgnoreCase("$CHANGE") || fields[i].getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                continue;
            projection[i]=fields[i].getName().toUpperCase();
        }

        // Filter results WHERE "title" = 'My Title'
        String selection = "ID" + " = ?";
        String[] selectionArgs = { ""+id };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "ID ASC";

        Cursor cursor = SweetSQLiteConfig.dbReadable.query(
                object.getClass().getSimpleName().toUpperCase(),        // The table to query
                projection,                                             // The columns to return
                selection,                                              // The columns for the WHERE clause
                selectionArgs,                                          // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                sortOrder                                               // The sort order
        );

        if (!cursor.moveToFirst()) return false;
        for (Field f:fields) {
            if (f.getName().toUpperCase().equalsIgnoreCase("$CHANGE") || f.getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                continue;
            try {
                if (f.getType()==int.class || f.getType()==Integer.class)
                    f.set(object, cursor.getInt(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==boolean.class || f.getType()==Boolean.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase()))==0?false:true);
                else if (f.getType()==byte.class || f.getType()==Byte.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==char.class || f.getType()==Character.class)
                    f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())).charAt(0));
                else if (f.getType()==double.class || f.getType()==Double.class)
                    f.set(object, cursor.getDouble(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==float.class || f.getType()==Float.class)
                    f.set(object, cursor.getFloat(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==long.class || f.getType()==Long.class)
                    f.set(object, cursor.getLong(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==short.class || f.getType()==Short.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else {
                    Log.e("smosqlite", "column name: "+f.getName().toUpperCase());
                    Log.e("smosqlite", "column index: "+cursor.getColumnIndexOrThrow(f.getName().toUpperCase()));
                    f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean load(String whereClause) {
        if (!isSuperCalled) throw new SweetSQLiteSuperNotCalledException();
        if (object==null) throw new UnknownSubclassInstanceException();

        if (sweeter) addAllData();

        Field[] fields=object.getClass().getFields();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = new String[fields.length+1];

        projection[0]="ID";
        for (int i=0; i<fields.length; i++) {
            if (fields[i].getName().toUpperCase().equalsIgnoreCase("$CHANGE") || fields[i].getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                continue;
            projection[i]=fields[i].getName().toUpperCase();
        }

        // Filter results WHERE "title" = 'My Title'
        String selection = whereClause;

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "ID ASC";

        Cursor cursor = SweetSQLiteConfig.dbReadable.query(
                object.getClass().getSimpleName().toUpperCase(),        // The table to query
                projection,                                             // The columns to return
                selection,                                              // The columns for the WHERE clause
                null,                                          // The values for the WHERE clause
                null,                                                   // don't group the rows
                null,                                                   // don't filter by row groups
                sortOrder                                               // The sort order
        );

        if (!cursor.moveToFirst()) return false;
        for (Field f:fields) {
            if (f.getName().toUpperCase().equalsIgnoreCase("$CHANGE") || f.getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                continue;
            try {
                if (f.getType()==int.class || f.getType()==Integer.class)
                    f.set(object, cursor.getInt(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==boolean.class || f.getType()==Boolean.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase()))==0?false:true);
                else if (f.getType()==byte.class || f.getType()==Byte.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==char.class || f.getType()==Character.class)
                    f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())).charAt(0));
                else if (f.getType()==double.class || f.getType()==Double.class)
                    f.set(object, cursor.getDouble(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==float.class || f.getType()==Float.class)
                    f.set(object, cursor.getFloat(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==long.class || f.getType()==Long.class)
                    f.set(object, cursor.getLong(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else if (f.getType()==short.class || f.getType()==Short.class)
                    f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                else {
                    f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static void raw(String raw) {
        try {
            SweetSQLiteConfig.dbWritable.execSQL(raw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
