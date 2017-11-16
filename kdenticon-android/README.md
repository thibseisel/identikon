# Kdenticon-Android

Kdenticon-Android makes it easier to use the Kdenticon library on Android.
It provides an `Renderer` implementation that draws icons onto an instance of the `Bitmap` class,
allowing both to display it in an `ImageView` or save it to the SD card.

## Setup

Just like Kdenticon, Kdenticon-Android is available though JCenter.
Add the following to your `build.gradle` script :

```gradle
dependencies {
    implementation 'com.github.thibseisel:kdenticon-android:1.0.0-alpha1'
}
```

Note that you don't need to include the Kdenticon library in you dependencies
as it is provided with Kdenticon-Android.

## How to use

Follow the steps described in [Kdenticon's README](../).
Then, instead of using a built-in `Renderer` implementation, create an instance of `AndroidBitmapRenderer`.

```java
import com.github.thibseisel.kdenticon.Identicon;
import com.github.thibseisel.kdenticon.android.AndroidBitmapRenderer;
import android.graphics.Bitmap;

public class MainActivity extends AppCompatActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Identicon icon = com.github.thibseisel.kdenticon.Identicon.fromValue("Hello World!", 300);
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        
        Renderer renderer = new AndroidBitmapRenderer(bitmap);
        icon.draw(renderer, icon.getIconBounds());
        
        // The icon has been drawn onto the Bitmap
        // Do what you want with it !
        imageView.setImageBitmap(bitmap);
    }
}
```

For a real-world's usage of Kdenticon on Android, see the [demo app](../demo).