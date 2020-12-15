package com.hurtarte.listmaker2

import android.os.Bundle
import android.text.InputType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hurtarte.listmaker2.model.TaskList

class MainActivity : AppCompatActivity() {

    lateinit var listsRecyclerView:RecyclerView

    val listDataManager: ListDataManager = ListDataManager( this )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       setSupportActionBar(findViewById(R.id.toolbar))

        //1
        val lists = listDataManager.readList()
        listsRecyclerView=findViewById(R.id.lists_recyclerview)
        //2
        listsRecyclerView.layoutManager=LinearLayoutManager(this)
        //3
        listsRecyclerView.adapter= ListSelectionRecyclerViewAdapter(lists)



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog(){
        //1
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle= getString(R.string.create_list)

        //2
        val builder = AlertDialog.Builder(this)
        val listTitleEditText= EditText(this)
        listTitleEditText.inputType=InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        //3
        builder.setPositiveButton(positiveButtonTitle){
            dialog,_ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            val recyclerAdapter= listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter

            recyclerAdapter.addList(list)

            dialog.dismiss()

        //4

        }
        builder.create().show()
    }

}