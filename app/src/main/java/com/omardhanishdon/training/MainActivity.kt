package com.omardhanishdon.training

import android.app.Service
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    //spojo clas
    inner  class Information(var name : String , var number : String)

    //creating arrayList for saving contacts
    val contactList = mutableListOf<Information>()

    //flag for edit purpose
    var isForEdit = false
    var editPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list : RecyclerView = findViewById(R.id.list)
        val addContact = findViewById<Button>(R.id.addContact)
        val framelayout = findViewById<FrameLayout>(R.id.frameLayout)
        val name = findViewById<EditText>(R.id.name)
        val number = findViewById<EditText>(R.id.number)
        val saveContact = findViewById<Button>(R.id.saveContact)

        val adapater = ListAdapter(this , contactList)
        val manager = LinearLayoutManager (this, LinearLayoutManager.VERTICAL,false)
        list.adapter = adapater
        list.layoutManager = manager

        // anonymous inner class
        adapater.setCallBackListener(object  : ListAdapter.CallBack {

            override fun onEdit(pos: Int) {
                isForEdit = true
                editPosition = pos

                list.visibility = View.INVISIBLE
                addContact.visibility = View.INVISIBLE
                framelayout.visibility = View.VISIBLE

                val info = contactList.get(pos)
                name.setText(info.name)
                number.setText(info.number)
            }

        })



        addContact.setOnClickListener {
            list.visibility = View.INVISIBLE
            addContact.visibility = View.INVISIBLE
            framelayout.visibility = View.VISIBLE
        }

        saveContact.setOnClickListener {

            val imm: InputMethodManager =
                this.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(name.getWindowToken(), 0);

            //getting user entered information
            val nameStr = name.text.toString()
            val numberStr = number.text.toString()

            if(numberStr.length != 10) {
                number.error = "enter valid number"
                Toast.makeText(this , "enter valid number" , Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

           if(isForEdit) {
               contactList.get(editPosition).name = nameStr
               contactList.get(editPosition).number = numberStr

               adapater.notifyItemChanged(editPosition)
               name.setText("")
               number.setText("")

               Toast.makeText(this , "edited successfully" , Toast.LENGTH_SHORT).show()
           }else{
               //changing string's to Information class type
               val info = Information(nameStr , numberStr)

               //now adding Information to arraylist
               contactList.add(info)

               //after adding notify recyclerview that i added
               adapater.notifyItemInserted(contactList.size)

               name.setText("")
               number.setText("")

               Toast.makeText(this , "added successfully" , Toast.LENGTH_SHORT).show()
           }

            framelayout.visibility = View.INVISIBLE
            list.visibility = View.VISIBLE
            addContact.visibility = View.VISIBLE
        }

    }


}
