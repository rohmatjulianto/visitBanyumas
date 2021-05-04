package com.joule.endahebraling.dbroom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joule.endahebraling.model.DbClass


@Database(entities = arrayOf(DbClass::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDao(): DbDao
}
