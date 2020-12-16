package com.hurtarte.listmaker2

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
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

class MainActivity : AppCompatActivity(),ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    companion object {
        const val INTENT_LIST_KEY="list"
        const val LIST_DETAIL_REQUEST_CODE=123
    }

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
        listsRecyclerView.adapter= ListSelectionRecyclerViewAdapter(lists,this)



        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            showCreateListDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //1
        if(requestCode== LIST_DETAIL_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            //2
            data?.let {
                //3
                listDataManager.saveList(data.getParcelableExtra<TaskList>(INTENT_LIST_KEY) as TaskList)
                updateList()
            }
        }
    }

    private fun updateList(){
        val lists = listDataManager.readList()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists,this)
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
            showListDetail(list)

        //4

        }
        builder.create().show()
    }

    private fun showListDetail(list:TaskList){

        //1
        val listDetailIntent = Intent(this,ListDetailActivity::class.java)
        //2
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)

        //3
        startActivityForResult(listDetailIntent,LIST_DETAIL_REQUEST_CODE)

    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

}