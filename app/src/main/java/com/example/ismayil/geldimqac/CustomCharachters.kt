package com.example.ismayil.geldimqac

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_chose_personaj.*
import kotlinx.android.synthetic.main.activity_custom_charachters.*


class CustomCharachters : AppCompatActivity() {

        var chName: String = "null"
        var newName:String = "null"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_charachters)

        val chNameArray = ArrayList<String>()
        val chImageArray = ArrayList<Bitmap>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chNameArray)


        listView.adapter = arrayAdapter
        listView.cacheColorHint

        try {
            val database = this.openOrCreateDatabase("Personaj", android.content.Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS personaj (name VARCHAR, image BLOB)")


            val cursor = database.rawQuery("SELECT * FROM personaj", null)
            val nameIx = cursor.getColumnIndex("name")
            val imageIx = cursor.getColumnIndex("image")
            cursor.moveToFirst()

            while (cursor != null) {

                chNameArray.add(cursor.getString(nameIx))
                val byteArray = cursor.getBlob(imageIx)
                val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                chImageArray.add(image)
                cursor.moveToNext()
                arrayAdapter.notifyDataSetChanged()
            }
            cursor?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DataYoxla::class.java)
            this.selectLevel1
            intent.putExtra("name", chNameArray[i])
            intent.putExtra("id", "code")
            val chosen = Globals.Chosen
            chosen.chosenImage = chImageArray[i]

            startActivity(intent)
        }

        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            val intent = Intent(applicationContext, CustomCharachters::class.java)
            val nameFromLv = listView.getItemAtPosition(position).toString()
            val popUp = PopupMenu(applicationContext, view)

            chName = nameFromLv
            newName = "haci"

            popUp.setOnMenuItemClickListener { it ->
                when (it.itemId) {
                    R.id.delete -> {
                        deleteFromList()
                        true
                    }
                    R.id.edit -> {
                        editFromList()
                        true
                    }
                    else -> false
                }
            }

            popUp.inflate(R.menu.menu_list)
            popUp.show()
            return@OnItemLongClickListener true
        }


    }


    fun deleteFromList() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Sime Emeliyyati!")
        alertDialog.setMessage("Silmeye Eminsiniz?")
        alertDialog.setPositiveButton("Beli"){ dialogInterface: DialogInterface, i: Int ->
            try {
                val database = this.openOrCreateDatabase("Personaj", android.content.Context.MODE_PRIVATE, null)
                val sqlString = "DELETE FROM personaj WHERE name=?"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, chName)
                statement.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(this,"$chName silindi!",Toast.LENGTH_LONG).show()
            startActivity(intent)
            finish()
        }
        alertDialog.setNegativeButton("Xeyir"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(this,"Ged Emin Olanda Gelersen...",Toast.LENGTH_LONG).show()
        }
        alertDialog.show()


    }

    fun editFromList(){
        val alertDialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val alertView = inflater.inflate(R.layout.custom_dialog,null)
        val edt = alertView.findViewById<View>(R.id.edit_alert) as EditText
        alertDialog.setView(alertView)
        alertDialog.setTitle("Ad deyisme!")
        alertDialog.setMessage("Yeni adi qeyd et:")
        alertDialog.setPositiveButton("Deyis"){ dialogInterface: DialogInterface, i: Int ->
            newName = edt.text.toString()
            try {
                val database = this.openOrCreateDatabase("Personaj", android.content.Context.MODE_PRIVATE, null)
                val sqlString = "UPDATE personaj SET name=? WHERE name=?"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1, newName)
                statement.bindString(2,chName)
                statement.execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(this,"$chName,$newName ile deyishdirildi!",Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }
        alertDialog.setNegativeButton("Legv et"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(this,"Deyishiklik olmadi!",Toast.LENGTH_SHORT).show()
        }
        val bashlat = alertDialog.create()
        bashlat.show()


    }

    fun set(view: View) {
        startActivity(Intent(applicationContext, Settings::class.java))
        finish()
    }



}
