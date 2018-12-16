package com.example.g.airlinetickets.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.g.airlinetickets.User;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public User getUserItem(){
        String uuidString = getString(getColumnIndex(UserSchema.UserTable.Cols.UUID));
        String uname = getString(getColumnIndex(UserSchema.UserTable.Cols.UNAME));
        String pass = getString(getColumnIndex(UserSchema.UserTable.Cols.PWORD));
        User user = new User(UUID.fromString(uuidString), uname, pass);
        return user;
    }
}
