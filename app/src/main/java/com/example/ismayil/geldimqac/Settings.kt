package com.example.ismayil.geldimqac

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream

class Settings : AppCompatActivity() {

    var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


    }



    @TargetApi(Build.VERSION_CODES.M)
    fun selectImage(view: View){
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),2)
        }else{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode==2){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            val image = data.data

            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver,image)
                selectImageView.setImageBitmap(selectedImage)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun saveCh(view: View){

        val chName = editText.text.toString()

        val byteOutputStream = ByteArrayOutputStream()
        selectedImage?.compress(Bitmap.CompressFormat.JPEG,50,byteOutputStream)
        val byteArray = byteOutputStream.toByteArray()

        try {
            val database = this.openOrCreateDatabase("Personaj",android.content.Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS personaj (name VARCHAR, image BLOB)")

            //Onemli kisim buradi sql icine data vermek
            val sqlString = "INSERT INTO personaj (name, image) VALUES (?, ?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,chName)
            statement.bindBlob(2,byteArray)
            statement.execute()
        }catch (e: Exception){
            e.printStackTrace()
        }
        val intent = Intent(applicationContext,CustomCharachters::class.java)
        startActivity(intent)
        finish()
    }


}
