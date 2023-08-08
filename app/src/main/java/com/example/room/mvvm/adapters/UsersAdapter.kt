package com.example.room.mvvm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room.mvvm.R
import com.example.room.mvvm.model.LoginTableModel

class UsersAdapter(
    val context: Context,
    private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val noteClickInterface: NoteClickInterface
) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    //on below line we are creating a variable for our all notes list.
    private val allNotes = ArrayList<LoginTableModel>()

    //on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //on below line we are creating an initializing all our variables which we have added in layout file.
        val lblUseraname = itemView.findViewById<TextView>(R.id.lblUseraname)
        val lblPassword = itemView.findViewById<TextView>(R.id.lblPassword)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //on below line we are setting data to item of recycler view.
        holder.lblUseraname.text = allNotes[position].Username
        holder.lblPassword.text = allNotes[position].Password
        //on below line we are adding click listner to our delete image view icon.
        holder.deleteIV.setOnClickListener {
            //on below line we are calling a note click interface and we are passing a position to it.
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

        //on below line we are adding click listner to our recycler view item.
        holder.itemView.setOnClickListener {
            //on below line we are calling a note click interface and we are passing a position to it.
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        //on below line we are returning our list size.
        return allNotes.size
    }

    //below method is use to update our list of notes.
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<LoginTableModel>) {
        //on below line we are clearing our notes array list/
        allNotes.clear()
        //on below line we are adding a new list to our all notes list.
        allNotes.addAll(newList)
        //on below line we are calling notify data change method to notify our adapter.
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface {
    //creating a method for click action on delete image view.
    fun onDeleteIconClick(note: LoginTableModel)
}

interface NoteClickInterface {
    //creating a method for click action on recycler view item for updating it.
    fun onNoteClick(note: LoginTableModel)
}