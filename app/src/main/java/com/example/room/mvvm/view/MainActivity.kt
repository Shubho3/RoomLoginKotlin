package com.example.room.mvvm.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.mvvm.R
import com.example.room.mvvm.adapters.NoteClickDeleteInterface
import com.example.room.mvvm.adapters.NoteClickInterface
import com.example.room.mvvm.adapters.UsersAdapter
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.btnAddLogin
import kotlinx.android.synthetic.main.activity_main.btnReadLogin
import kotlinx.android.synthetic.main.activity_main.lblInsertResponse
import kotlinx.android.synthetic.main.activity_main.lblPassword
import kotlinx.android.synthetic.main.activity_main.lblReadResponse
import kotlinx.android.synthetic.main.activity_main.lblUseraname
import kotlinx.android.synthetic.main.activity_main.txtPassword
import kotlinx.android.synthetic.main.activity_main.txtUsername
import kotlinx.android.synthetic.main.activity_main.users
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() ,NoteClickInterface ,NoteClickDeleteInterface {

    lateinit var loginViewModel: LoginViewModel

    lateinit var context: Context

    lateinit var strUsername: String
    lateinit var strPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val noteRVAdapter = UsersAdapter(this, this, this)
        users.adapter = noteRVAdapter
        loginViewModel.liveListAllUsers?.observe(this, Observer { list ->
            list?.let {
                noteRVAdapter.updateList(it)
                Log.e("TAG", "onCreate: "+it )
            }
        })
        btnAddLogin.setOnClickListener {

            strUsername = txtUsername.text.toString().trim()
            strPassword = txtPassword.text.toString().trim()

            if (strPassword.isEmpty()) {
                txtUsername.error = "Please enter the username"
            } else if (strPassword.isEmpty()) {
                txtPassword.error = "Please enter the username"
            } else {
                loginViewModel.insertData(context, strUsername, strPassword)
                lblInsertResponse.text = "Inserted Successfully"
                loginViewModel.getAllUsers(this)

            }
        }

        btnReadLogin.setOnClickListener {
            strUsername = txtUsername.text.toString().trim()
            loginViewModel.getLoginDetails(context, strUsername)!!.observe(this, Observer {
                if (it == null) {
                    lblReadResponse.text = "Data Not Found"
                    lblUseraname.text = "- - -"
                    lblPassword.text = "- - -"
                } else {
                    lblUseraname.text = it.Username
                    lblPassword.text = it.Password
                    lblReadResponse.text = "Data Found Successfully"
                }
                loginViewModel.getAllUsers(this)

            })


        }
    }

    override fun onResume() {
        loginViewModel.getAllUsers(this)
        super.onResume()
    }
    override fun onDeleteIconClick(note: LoginTableModel) {
        MainScope().launch {
            loginViewModel.delete(context,note)
            loginViewModel.getAllUsers(context)
        }

    }

    override fun onNoteClick(note: LoginTableModel) {
    }
}