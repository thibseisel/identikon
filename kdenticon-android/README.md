[ ![JCenter](https://api.bintray.com/packages/nihilus/android/kdenticon-android/images/download.svg) ](https://bintray.com/nihilus/android/kdenticon-android/_latestVersion)

# Kdenticon-Android

Kdenticon-Android makes it easier to use the Kdenticon library on Android.
It provides an `Renderer` implementation that draws icons onto an instance of the `Bitmap` class,
allowing both to display it in an `ImageView` or save it to the SD card.

## Setup

Just like Kdenticon, Kdenticon-Android is available though JCenter.
Add the following to your `build.gradle` script :

```gradle
dependencies {
    implementation 'com.github.thibseisel:kdenticon-android:1.0.0-alpha2'
}
```

Note that you don't need to include the Kdenticon library in you dependencies
as it is provided with Kdenticon-Android.

## How to use

Kdenticon-Android provides its own Renderer implementation tailored for Android, 
`AndroidBitmapRenderer`. It also provides an utility function that makes it easier to use :

```java
import com.github.thibseisel.kdenticon.Identicon;
import com.github.thibseisel.kdenticon.android.KdenticonAndroid;
import android.graphics.Bitmap;

public class MainActivity extends AppCompatActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Create an icon of the desired size and hash string
        Identicon icon = Identicon.fromValue("Hello World!", 300);
        
        // Create a bitmap of the same size onto the icon should be drawn
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        
        // Utility function that uses AndroidBitmapRenderer behind the scene to draw onto bitmap
        KdenticonAndroid.drawIconToBitmap(icon, bitmap);
        
        // The icon has been drawn onto the Bitmap
        // Do what you want with it !
        imageView.setImageBitmap(bitmap);
    }
}
```

If you use Kotlin, you can do the same thing with an extension function of `Identicon` :

```kotlin
fun onCreate(savedInstanceState: Bundle?) {
    // ...
    icon.drawToBitmap(bitmap)
    // ...
}
```

For a real-world's usage of Kdenticon on Android, see the [demo app](../demo).