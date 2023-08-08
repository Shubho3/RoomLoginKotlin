package com.example.room.mvvm.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository

class LoginViewModel : ViewModel() {
    var liveDataLogin: LiveData<LoginTableModel>? = null
    var liveListAllUsers: LiveData<List<LoginTableModel>>? = null

    init {
      /*  val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes*/
    }
    fun insertData(context: Context, username: String, password: String) {
        LoginRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String): LiveData<LoginTableModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

    fun getAllUsers(context: Context): LiveData<List<LoginTableModel>>? {
        liveListAllUsers = LoginRepository.getAllUsers(context)
        Log.e("+", "getAllUsers: "+liveListAllUsers.toString() )
        return liveListAllUsers
    }
    suspend fun delete(context: Context, data : LoginTableModel) {
        LoginRepository.delete(context, data)

    }

}