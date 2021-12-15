# Kdenticon

Kdenticon is a Kotlin multiplatform library for generating highly recognizable identicons. It is a
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

Kdenticon is still in active development. The following features are currently available:

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
    implementation("com.github.thibseisel:kdenticon:1.0.0")
}
```

## How to use

Create an instance of the `Identicon` class. You have to provide an object whose text representation
will be used to generate the icon.

Identicons are drawn by an implementation of the `Renderer` class. It should delegates shape drawing
to any kind of surface : directly onto a Swing window, into a PNG file or a network connection, etc.
For the time being, the only built-in `Renderer` implementation is `SvgRenderer`, that saves
generated icons to SVG files.

For any other use-case, you have to define your own Renderer subclass.

### Writing icon to an SVG file

```kotlin
// Create a new instance of the Identicon class with an hash string and the given size
val icon = Identicon.fromValue("Hello World!", iconSize = 300)
// Creates a new file with the given name
icon.saveAsSvg("kdenticon.svg")
```

### With your own Renderer subclass

```kotlin
// Create a new instance of the Identicon class with an hash string and the given size
val icon = Identicon.fromValue("Hello World!", iconSize = 300)
// Instantiate your own Renderer implementation
val renderer = MyRenderer()
// Start the rendering
icon.draw(renderer, icon.getIconBounds())
```

## Note about the author

Even if I did all the work of translating from JavaScript / C# to Kotlin, the algorithm used to
generate icons and their look is not my own work. If you like the look of the icons, consider
leaving a tip to the original author,
[Daniel Mester Pirttijärvi](https://github.com/dmester).
