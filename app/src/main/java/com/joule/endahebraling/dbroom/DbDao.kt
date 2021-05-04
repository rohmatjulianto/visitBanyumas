package com.joule.endahebraling.dbroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.joule.endahebraling.model.DbClass

@Dao
interface DbDao {
    @Query("SELECT * FROM DbClass")
    fun getAll(): List<DbClass>

    @Query("SELECT * FROM DbClass WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<DbClass>

    @Insert
    fun insertAll(vararg data: DbClass)

    @Delete
    fun delete(data : DbClass)
}