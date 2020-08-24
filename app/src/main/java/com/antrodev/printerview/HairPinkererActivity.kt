package com.antrodev.printerview


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antrodev.printerview.adapters.PreviousImagesAdapter
import com.antrodev.printerview.helper.ImageHelper
import com.antrodev.printerview.model.HairRequest
import com.antrodev.printerview.network.ApiManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*


class HairPinkererActivity : AppCompatActivity() {

    private val GET_IMG_FROM_GALLERY = 1337

    private lateinit var progressBar: ProgressBar
    private lateinit var noImageText: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: PreviousImagesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var previousImages: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hair_pinkerer_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        progressBar = findViewById(R.id.progress_bar)
        noImageText = findViewById(R.id.no_image_text)

        recyclerView = findViewById(R.id.recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = PreviousImagesAdapter(previousImages)

        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)

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
    private fun pinkifyImage(input: String) {
        previousImages.add(input)
        updateHistory()

        ApiManager.getPRServiceInstance()
            ?.performHairRequest(HairRequest(input))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                progressBar.visibility = View.GONE
                ImageHelper.convertBase64ToBitmap(it.image)
                findViewById<ImageView>(R.id.image_pinkified).setImageBitmap(
                    ImageHelper.convertBase64ToBitmap(it.image)
                )
            }
    }

    private fun updateHistory() {
        viewAdapter.previousImages = previousImages
        viewAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            noImageText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            try {
                val bitmap =
                    BitmapFactory.decodeStream(contentResolver.openInputStream(data?.data!!))

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream)
                val imageInByte: ByteArray = stream.toByteArray()

                pinkifyImage(Base64.encodeToString(imageInByte, Base64.DEFAULT))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, getString(R.string.error_message_unknown), Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.no_image_picked), Toast.LENGTH_LONG).show()
        }
    }

}