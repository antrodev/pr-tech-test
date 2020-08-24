package com.antrodev.pr_interview

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileNotFoundException
import java.io.InputStream



class HairPinkererActivity : AppCompatActivity() {

    private val GET_IMG_FROM_GALLERY = 1337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hair_pinkerer_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            startPhotoGallery()
        }
    }

    private fun startPhotoGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, GET_IMG_FROM_GALLERY)
    }

    /**
     * Where the magic happen.
     *
     * @param bitmap Bitmap of the image to pinkify
     *
     * Set directly the image to the ImageView
     */
    private fun pinkifyImage(bitmap: Bitmap) {
        /*ApiManager.getPRServiceInstance(baseContext)
                ?.performHairRequest(HairRequest(...)))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    //do something
                }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                pinkifyImage(BitmapFactory.decodeStream(imageStream))

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, getString(R.string.error_message_unknown), Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.no_image_picked), Toast.LENGTH_LONG).show()
        }
    }


}