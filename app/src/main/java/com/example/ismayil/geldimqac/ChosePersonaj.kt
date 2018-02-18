package com.example.ismayil.geldimqac

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chose_personaj.*


class ChosePersonaj : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chose_personaj)
        var intent = Intent(this,DataYoxla::class.java)

        naza.setOnClickListener {
            selectLevel()
            Toast.makeText(this,"Ishini bilirsen!",Toast.LENGTH_LONG).show()
            intent.putExtra("id","naza")
            startActivity(intent)
        }
        firu.setOnClickListener {
            selectLevel()
            Toast.makeText(this,"I have no idea",Toast.LENGTH_LONG).show()
            intent.putExtra("id","firudin")
            startActivity(intent)
        }
        elman.setOnClickListener {
            selectLevel()
            Toast.makeText(this,"Geldi yene RASIM!!!",Toast.LENGTH_SHORT).show()
            intent.putExtra("id","elman")
            startActivity(intent)
        }
        adil.setOnClickListener {
            var alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Diqqet!!!")
                alertDialog.setMessage("Adil Ile Davam Etmeye Eminsen?")
                alertDialog.setNegativeButton("Beli"){ dialogInterface: DialogInterface, i: Int ->finish() }
            alertDialog.show()
            Toast.makeText(applicationContext,"FATAL ERROR",Toast.LENGTH_LONG)
        }
        other.setOnClickListener {
            startActivity(Intent(this,CustomCharachters::class.java))
        }
    }

    open fun selectLevel(){
        when {
            r1.isChecked -> {
                level = 700
            }
            r2.isChecked -> {
                level = 500
            }
            r3.isChecked -> {
                level = 300
            }
        }
    }

}
