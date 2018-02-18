package com.example.ismayil.geldimqac

import android.graphics.Bitmap

/**
 * Created by ismayil on 2/6/2018.
 */
class Globals {
    companion object Chosen{
        var chosenImage: Bitmap?=null
        fun returnImage():Bitmap{
            return chosenImage!!
        }

    }
}