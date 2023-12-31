package com.example.room.mvvm.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.room.mvvm.model.LoginTableModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?): LiveData<LoginTableModel>

    @Query("Select * from Login order by username ASC")
    fun getAllUser(): LiveData<List<LoginTableModel>>

    @Delete
    suspend fun delete(note: LoginTableModel)
}