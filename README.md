# identikon

identikon is a Kotlin multiplatform library for generating highly recognizable identicons. It is a
Kotlin port of [Jdenticon](https://github.com/dmester/jdenticon).

![](screenshots/sample_1.png)
![](screenshots/sample_2.png)
![](screenshots/sample_3.png)
![](screenshots/sample_4.png)

![](screenshots/sample_5.png)
![](screenshots/sample_6.png)
![](screenshots/sample_7.png)
![](screenshots/sample_8.png)

## Purpose

An Identicon is a visual representation of a hash value, usually of an IP address, that serves to
identify a user of a computer system as a form of avatar while protecting the users' privacy.

You may also use these icons in any other context, for example as a placeholder image.

## Project status

identikon has just released it's first version. More features may be added at a later time. The
following features are currently available:

- save icons as SVG,
- draw icons on an Android `Bitmap`

This library is currently supporting the following platforms:

- JVM 11+
- Android API 21+

## Setup

The library is available through MavenCentral. Add the following dependency to your Gradle build
script :

```kotlin
dependencies {
    implementation("io.github.thibseisel:identikon:1.0.0")
}
```

## How to use

Create an instance of the `Identicon` class. You have to provide an object whose text representation
will be used to generate the icon.

### Writing icon to an SVG file

```kotlin
// Create a new instance of the Identicon class with an hash string and the given size
val icon = Identicon.fromValue("Hello World!", iconSize = 300)
// Writes the icon to a SVG file
Path("my-icon.svg").outputStream().use {
    icon.saveAsSvg(it)
}
```

### Drawing the icon onto an Android Bitmap

```kotlin
// Create a new instance of the Identicon class with an hash string and the given size
val icon = Identicon.fromValue("Hello World!", iconSize = 300)
// Start the rendering
val targetBitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)
icon.drawToBitmap(targetBitmap)
```
