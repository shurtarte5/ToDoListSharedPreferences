package com.hurtarte.listmaker2

import android.content.Context
import androidx.preference.PreferenceManager
import com.hurtarte.listmaker2.model.TaskList

class ListDataManager(private val context:Context) {

    fun saveList(list:TaskList){
        //1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

        //2
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())

        //3
        sharedPreferences.apply()

    }

    fun readList():ArrayList<TaskList>{
        //1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        //2
        val sharedPreferencesContents = sharedPreferences.all
        //3
        val taskLists = ArrayList<TaskList>()
        //4
        for(taskList in sharedPreferencesContents){
            val itemHashSet= ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemHashSet)
            //5
            taskLists.add(list)

        }
        return taskLists
    }
}