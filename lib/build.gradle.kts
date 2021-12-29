/*
 * Copyright 2021 Thibault Seisel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.dokka)
    `maven-publish`
}

group = "com.github.thibseisel.kdenticon"
version = "1.0.0"

kotlin {
    explicitApi()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    android {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        publishLibraryVariants("release")
    }

    sourceSets {
        val jvmMain by getting
        getByName("androidMain") {
            dependsOn(jvmMain)
        }

        getByName("commonTest") {
            dependencies {
                implementation(libs.kotest.engine)
                implementation(libs.kotest.assertions)
                implementation(libs.kotest.framework.datatest)
            }
        }

        getByName("jvmTest") {
            dependencies {
                runtimeOnly(libs.kotest.runner.junit5)
            }
        }

        getByName("androidTest") {
            dependencies {
                runtimeOnly(libs.kotest.runner.junit5)
            }
        }

        all {
            languageSettings {
                progressiveMode = true
                optIn("kotlin.RequiresOptIn")
            }
        }
    }
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }

    testOptions {
        unitTests.all(Test::useJUnitPlatform)
    }
}

val javadocJar by tasks.registering(Jar::class) {
    group = "documentation"
    description = "Pack generated javadoc into a jar"
    dependsOn(tasks.dokkaJavadoc)

    from(tasks.dokkaJavadoc)
    archiveClassifier.set("javadoc")
}

fun stringExtraOrNull(propertyName: String): String? =
    if (ext.has(propertyName)) ext[propertyName]?.toString() else null

val sonatypeUsername: String? = stringExtraOrNull("sonatype.username")
    ?: System.getenv("SONATYPE_USERNAME")
val sonatypePassword: String? = stringExtraOrNull("sonatype.password")
    ?: System.getenv("SONATYPE_PASSWORD")

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(javadocJar)

        pom {
            name.set("Kdenticon")
            description.set("A Kotlin multiplatform library for generating highly recognizable identicons.")
            url.set("https://github.com/thibseisel/kdenticon")

            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://opensource.org/licenses/Apache-2.0")
                }
            }

            developers {
                developer {
                    id.set("thibseisel")
                    name.set("Thibault Seisel")
                    email.set("seisel.thibault@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/thibseisel/kdenticon")
            }
        }
    }
}
