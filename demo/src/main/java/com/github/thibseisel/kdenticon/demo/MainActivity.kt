package com.github.thibseisel.kdenticon.demo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.thibseisel.kdenticon.Identicon
import com.github.thibseisel.kdenticon.IdenticonStyle
import com.github.thibseisel.kdenticon.android.AndroidBitmapRenderer
import com.github.thibseisel.kdenticon.rendering.Renderer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var iconBitmap: Bitmap
    private lateinit var canvasRenderer: Renderer
    private var subscriptionPool = CompositeDisposable()
    private var iconStyle = IdenticonStyle(backgroundColor = Color.TRANSPARENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iconBitmap = Bitmap.createBitmap(ICON_SIZE, ICON_SIZE, Bitmap.Config.ARGB_8888)
        canvasRenderer = AndroidBitmapRenderer(iconBitmap)

        val subscription = Observable.create<CharSequence> { emitter ->

            val textChangeObserver = object : TextWatcher {
                override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    // Nothing to do
                }

                override fun onTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                    // Nothing to do
                }

                override fun afterTextChanged(text: Editable) {
                    emitter.onNext(text)
                }
            }

            editText.addTextChangedListener(textChangeObserver)
            emitter.setCancellable { editText.removeTextChangedListener(textChangeObserver) }

        }.debounce(300L, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    iconBitmap.eraseColor(Color.TRANSPARENT)
                    val identicon = Identicon.fromValue(it, ICON_SIZE)
                    identicon.style = iconStyle
                    identicon.draw(canvasRenderer, identicon.getIconBounds())
                    imageView.setImageBitmap(this.iconBitmap)
                }

        subscriptionPool.add(subscription)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            prepareToSaveBitmap()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun prepareToSaveBitmap() {
        // Ask for permission to write the icon bitmap to external storage
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 99)
    }

    private fun saveBitmapToSdCard() {
        val filename = "kdenticon-${Date().time}}.png"
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val iconFile = File(filePath, filename)

        iconFile.outputStream().use { output ->
            iconBitmap.compress(Bitmap.CompressFormat.PNG, 90, output)
        }

        Toast.makeText(this, R.string.confirm_icon_saved, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 99 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveBitmapToSdCard()
        }
    }

    override fun onDestroy() {
        subscriptionPool.dispose()
        super.onDestroy()
    }

    private companion object {
        private const val ICON_SIZE = 300
    }
}
