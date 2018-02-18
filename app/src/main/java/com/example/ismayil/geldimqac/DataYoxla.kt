package com.example.ismayil.geldimqac

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_data_yoxla.*
import java.util.*


var level:Int = 500


class DataYoxla : AppCompatActivity() {

    //Deyishken ve Sabitler

    var imageArray = ArrayList<ImageView>()
    var handler:Handler = Handler()
    var runnable:Runnable = Runnable {  }
    var nameCh:String? = null
    var isFinished = false
    var score:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_yoxla)

        imageArray = arrayListOf(iV1,iV2,iV3,iV4,iV5,iV6,iV7,iV8,iV9)

        val intent = intent
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        ivScoreFinal.visibility = View.INVISIBLE
        tvScoreFinal.visibility = View.INVISIBLE
        tvYoxla.text = name

        timeLeft()


//Shekilleri secen hisse
        if (id=="naza"){
            nameCh = "Mia"
            for (image in imageArray){
                image.setImageResource(R.drawable.mia)
            }
        } else if (id=="elman"){
            nameCh = "Elman"
            for (image in imageArray){
                image.setImageResource(R.drawable.elman)
            }
        }else if (id=="firudin"){
            nameCh = "Firudin"
            for (image in imageArray){
                image.setImageResource(R.drawable.firu)
            }
        }else{
            nameCh = name
            val chosen = Globals.Chosen
            val bitmap = chosen.returnImage()
            for (image in imageArray){
                image.setImageBitmap(bitmap)
            }
        }

    }



    fun timeLeft(){

        hideImages()
        object : CountDownTimer(10000,1000){
            override fun onFinish() {
                ivScoreFinal.visibility = View.VISIBLE
                tvScoreFinal.visibility = View.VISIBLE
                yoxlaGrid.visibility = View.INVISIBLE
                when (level) {
                    700 -> tvScoreFinal.text = "${score*2}"
                    500 -> tvScoreFinal.text = "${score*4}"
                    300 -> tvScoreFinal.text = "${score*8}"
                }
                Toast.makeText(applicationContext,"Vaxtin Bitti!!",Toast.LENGTH_SHORT).show()
                handler.removeCallbacks(runnable)
                isFinished = true
                tvleft.text = "Vaxtin Bitti!"
                tvYoxla.text = "Tebrikler!!!"
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                tvleft.text = "${nameCh} tutmaga son: ${p0/1000} saniye qalid."
            }
        }.start()

    }


    fun hideImages(){
        runnable = object : Runnable{
            override fun run() {
             for (image in imageArray){
                 image.visibility = View.INVISIBLE
             }
                val random = Random()
                val index = random.nextInt(8-0)
                imageArray[index].visibility = View.VISIBLE
                handler.postDelayed(runnable,level.toLong())
            }
        }
        handler.post(this.runnable)
    }

    fun clicked(view: View){
        if (!isFinished){
            score++
            when (level) {
                700 -> tvYoxla.text = "Xal: ${score*2}"
                500 -> tvYoxla.text = "Xal: ${score*4}"
                300 -> tvYoxla.text = "Xal: ${score*8}"
            }

        }
    }
}
