package com.example.registerloginexample;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 4, exportSchema = false)
public abstract class UserDB extends RoomDatabase{
    public abstract UserDao userDao();
}
