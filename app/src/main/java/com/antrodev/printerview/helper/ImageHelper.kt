package com.antrodev.printerview.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageHelper {
    companion object Converters {
        fun convertBitmapToBase64(bitmap: Bitmap): String {
            val byteStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
            return Base64.encodeToString(byteStream.toByteArray(), Base64.DEFAULT);
        }

        fun convertBase64ToBitmap(base64: String): Bitmap {
            val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }
    }

}