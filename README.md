[ ![JCenter](https://api.bintray.com/packages/nihilus/maven/kdenticon/images/download.svg) ](https://bintray.com/nihilus/maven/kdenticon/_latestVersion)

# KDenticon

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
While the icon generation API is functional, the only way to display and save icons into file is
to use the [Android extension](kdenticon-android).

## Setup

The library is available through JCenter.
Add the following dependency to your Gradle build script :

```gradle
dependencies {
    implementation 'com.github.thibseisel:kdenticon:1.0.0-alpha1'
}
```

## How to use

Create an instance of the `Identicon` class. You have to provide an object whose text representation 
will be used to generate the icon.

Identicons are drawn by an implementation of the `Renderer` class. It should delegates shape drawing 
to any kind of surface : directly onto a Swing window, into a PNG file or a network connection, etc.
For the time being, you have to define your own as no built-in Renderer is available.

```java
public class Main {
    
    public static void main(String[] args){
        // Create a new instance of the Identicon class with an hash string and the given size
        int iconSize = 300;
        Identicon icon = Identicon.fromValue("Hello World!", iconSize);
        
        // Instantiate your own Renderer implementation
        // PngRenderer and SvgRenderer will be available in the future
        Renderer renderer = new MyRenderer();
        icon.draw(renderer, icon.getIconBounds());
    }
}
```

## Note about the author

I do not own the original idea. If you like the look of the icons, consider leaving a tip to the original author,
[Daniel Mester Pirttijärvi](https://github.com/dmester).
