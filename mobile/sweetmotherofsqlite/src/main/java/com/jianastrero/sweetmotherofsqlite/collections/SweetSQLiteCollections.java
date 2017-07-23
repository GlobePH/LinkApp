package com.jianastrero.sweetmotherofsqlite.collections;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jianastrero.sweetmotherofsqlite.SweetSQLite;
import com.jianastrero.sweetmotherofsqlite.SweetSQLiteConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by rane on 6/3/2017.
 */

public class SweetSQLiteCollections {
    public static @NonNull <T extends SweetSQLite> ArrayList<T> all(Class<T> tClass) {
        ArrayList<T> arrayList=new ArrayList<>();

        try {

            Field[] fields=tClass.getFields();

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
            String selection = "";
            String[] selectionArgs = { "" };

            // How you want the results sorted in the resulting Cursor
            String sortOrder = "ID ASC";

            Cursor cursor = SweetSQLiteConfig.dbReadable.query(
                    tClass.getSimpleName().toUpperCase(),        // The table to query
                    null,                                             // The columns to return
                    null,                                              // The columns for the WHERE clause
                    null,                                          // The values for the WHERE clause
                    null,                                                   // don't group the rows
                    null,                                                   // don't filter by row groups
                    sortOrder                                               // The sort order
            );

            if (!cursor.moveToFirst()) return null;

            do {
                T object= null;
                try {
                    object = tClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Field f : fields) {
                    if (f.getName().toUpperCase().equalsIgnoreCase("$CHANGE") || f.getName().toUpperCase().equalsIgnoreCase("SERIALVERSIONUID"))
                        continue;
                    try {
                        if (f.getType() == int.class || f.getType() == Integer.class)
                            f.set(object, cursor.getInt(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else if (f.getType() == boolean.class || f.getType() == Boolean.class)
                            f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())) == 0 ? false : true);
                        else if (f.getType() == byte.class || f.getType() == Byte.class)
                            f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else if (f.getType() == char.class || f.getType() == Character.class)
                            f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())).charAt(0));
                        else if (f.getType() == double.class || f.getType() == Double.class)
                            f.set(object, cursor.getDouble(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else if (f.getType() == float.class || f.getType() == Float.class)
                            f.set(object, cursor.getFloat(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else if (f.getType() == long.class || f.getType() == Long.class)
                            f.set(object, cursor.getLong(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else if (f.getType() == short.class || f.getType() == Short.class)
                            f.set(object, cursor.getShort(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        else {
                            Log.e("smosqlite", "column name: " + f.getName().toUpperCase());
                            Log.e("smosqlite", "column index: " + cursor.getColumnIndexOrThrow(f.getName().toUpperCase()));
                            f.set(object, cursor.getString(cursor.getColumnIndexOrThrow(f.getName().toUpperCase())));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                arrayList.add(object);
            } while (cursor.moveToNext());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
