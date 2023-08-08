package com.example.room.mvvm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.room.LoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class LoginRepository {

    companion object {

        var loginDatabase: LoginDatabase? = null

        var loginTableModel: LiveData<LoginTableModel>? = null
        var loginusers: LiveData<List<LoginTableModel>>? = null

        fun initializeDB(context: Context): LoginDatabase {
            return LoginDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, username: String, password: String) {

            loginDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(username, password)
                loginDatabase!!.loginDao().InsertData(loginDetails)
            }

        }

        fun getLoginDetails(context: Context, username: String): LiveData<LoginTableModel>? {

            loginDatabase = initializeDB(context)

            loginTableModel = loginDatabase!!.loginDao().getLoginDetails(username)

            return loginTableModel
        }

        fun getAllUsers(context: Context): LiveData<List<LoginTableModel>>? {
            loginDatabase = initializeDB(context)
            loginusers = loginDatabase!!.loginDao().getAllUser()
            return loginusers
        }
        suspend fun delete(context: Context,  data :LoginTableModel){
            loginDatabase = initializeDB(context)

            loginDatabase!!.loginDao().delete(data)
        }
    }
}