package com.example.todolist

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tasksAdapter: ArrayAdapter<String>
        val tasksList: ArrayList<String> = ArrayList()
        var selectedPriority: String = "Medium"

        val prioritySpinner: Spinner = findViewById(R.id.spinner_priority)
        val taskDescriptionEditText: EditText = findViewById(R.id.edittext_task_description)
        val addTaskButton: Button = findViewById(R.id.button_add_task)
        val tasksListView: ListView = findViewById(R.id.listview_tasks)

        var options = arrayOf("High","Medium", "Low")

        prioritySpinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options )

        prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedPriority = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedPriority = "Medium" // Default value
            }
        }

        // Set up ListView
        tasksAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasksList)
        tasksListView.adapter = tasksAdapter

        // Set up Button
        addTaskButton.setOnClickListener {
            val taskDescription = taskDescriptionEditText.text.toString()
            if (taskDescription.isNotEmpty()) {
                val task = "$selectedPriority: $taskDescription"
                tasksList.add(task)
                sortTasks(tasksList)
                tasksAdapter.notifyDataSetChanged()
                taskDescriptionEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a task description", Toast.LENGTH_SHORT).show()
            }
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun sortTasks(tasksList: ArrayList<String> ) {
        tasksList.sortWith { task1: String, task2: String ->
            getPriorityValue(task1).compareTo(getPriorityValue(task2))
        }
    }

    private fun getPriorityValue(task: String): Int {
        return when {
            task.startsWith("High") -> 1
            task.startsWith("Medium") -> 2
            else -> 3
        }
    }

}


