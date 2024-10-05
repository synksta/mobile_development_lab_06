package com.example.mobile_development_lab_06.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mobile_development_lab_06.Crime

@Database(
    entities = [ Crime::class ],
    version=1
)

@TypeConverters(
    CrimeTypeConverters::class
)

abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao

}