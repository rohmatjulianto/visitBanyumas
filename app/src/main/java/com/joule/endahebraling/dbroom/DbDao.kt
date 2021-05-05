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

    @Query("SELECT * FROM DbClass WHERE name IN (:userIds)")
    fun getByName(userIds: String): DbClass

    @Insert
    fun insertAll(vararg data: DbClass)

    @Query("DELETE FROM DbClass WHERE name = :name")
    fun delete(name: String)
}