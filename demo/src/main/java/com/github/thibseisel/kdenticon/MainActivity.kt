package com.github.thibseisel.kdenticon

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.github.thibseisel.javadenticon.Identicon
import com.github.thibseisel.javadenticon.rendering.Renderer
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var iconBitmap: Bitmap
    private lateinit var canvasRenderer: Renderer
    private var subscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val size = 400
        iconBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        canvasRenderer = AndroidBitmapRenderer(iconBitmap)

        val sequence = editText.text
        createIdenticon(sequence)

        subscription = Observable.create<Any> { emitter ->

            val textChangeObserver = object : TextWatcher {
                override fun afterTextChanged(text: Editable) {
                    emitter.onNext(text)
                }

                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    // Nothing to do
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Nothing to do
                }
            }

            editText.addTextChangedListener(textChangeObserver)
            emitter.setCancellable { editText.removeTextChangedListener(textChangeObserver) }

        }.debounce(300L, TimeUnit.MILLISECONDS).subscribe { nextValue ->
            createIdenticon(nextValue)
        }
    }

    private fun createIdenticon(value: Any) {
        val identicon = Identicon.fromValue(value, 400)
        identicon.draw(canvasRenderer, identicon.getIconBounds())

        imageView.setImageBitmap(iconBitmap)
    }

    override fun onDestroy() {
        subscription?.dispose()
        super.onDestroy()
    }
}
