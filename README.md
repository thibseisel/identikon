[ ![JCenter](https://api.bintray.com/packages/nihilus/maven/kdenticon/images/download.svg) ](https://bintray.com/nihilus/maven/kdenticon/_latestVersion)

# Kdenticon

KDenticon is a Java library for generating highly recognizable identicons.
Written in Kotlin, it is a port of [Jdenticon](https://github.com/dmester/jdenticon) to the JVM platform.

![](screenshots/sample_1.png)
![](screenshots/sample_2.png)
![](screenshots/sample_3.png)
![](screenshots/sample_4.png)

![](screenshots/sample_5.png)
![](screenshots/sample_6.png)
![](screenshots/sample_7.png)
![](screenshots/sample_8.png)

## Purpose

An Identicon is a visual representation of a hash value, usually of an IP address, that serves to identify 
a user of a computer system as a form of avatar while protecting the users' privacy.

You may also use those icons in any other context, for example as a placeholder image.

## Project status

Kdenticon is still in active development.
While the icon generation API is functional, at the moment it is only possible to save icons as SVG files.
You can also use the [Kdenticon-Android extension](kdenticon-android).

## Setup

The library is available through JCenter.
Add the following dependency to your Gradle build script :

```gradle
dependencies {
    implementation 'com.github.thibseisel:kdenticon:1.0.0-alpha3'
}
```

## How to use

Create an instance of the `Identicon` class. You have to provide an object whose text representation 
will be used to generate the icon.

Identicons are drawn by an implementation of the `Renderer` class. It should delegates shape drawing 
to any kind of surface : directly onto a Swing window, into a PNG file or a network connection, etc.
For the time being, the only built-in `Renderer` implementation is `SvgRenderer`, 
that saves generated icons to SVG files.

For any other use-case, you have to define your own Renderer subclass.

### Writing icon to a SVG file

```java
public class Main {
    
    public static void main(String[] args) {
        // Create a new instance of the Identicon class with an hash string and the given size
        int iconSize = 300;
        Identicon icon = Identicon.fromValue("Hello World!", iconSize);
        
        // Creates a new file with the given name
        icon.saveAsSvg("kdenticon.svg");
    }
}
```

### With your own Renderer subclass

```java
public class Main {
    
    public static void main(String[] args) {
        // Create a new instance of the Identicon class with an hash string and the given size
        int iconSize = 300;
        Identicon icon = Identicon.fromValue("Hello World!", iconSize);
        
        // Instantiate your own Renderer implementation
        Renderer renderer = new MyRenderer();
        
        // Start the rendering
        icon.draw(renderer, icon.getIconBounds());
    }
}
```

## Note about the author

Even if I did all the work of translating from JavaScript / C# to Kotlin, 
the algorithm used to generate icons and their look is not my own work. 
If you like the look of the icons, consider leaving a tip to the original author,
[Daniel Mester Pirttijärvi](https://github.com/dmester).
