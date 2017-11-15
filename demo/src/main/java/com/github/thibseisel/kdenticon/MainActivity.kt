package com.github.thibseisel.kdenticon

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.github.thibseisel.kdenticon.rendering.Renderer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var iconBitmap: Bitmap
    private lateinit var canvasRenderer: Renderer
    private var subscriptionPool = CompositeDisposable()
    private var iconStyle = IdenticonStyle().apply {
        backgroundColor = Color.TRANSPARENT
    }

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

    override fun onDestroy() {
        subscriptionPool.dispose()
        super.onDestroy()
    }

    private companion object {
        private const val ICON_SIZE = 300
    }
}
