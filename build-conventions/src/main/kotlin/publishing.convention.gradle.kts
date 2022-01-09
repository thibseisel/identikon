/*
 * Copyright 2022 Thibault Seisel
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

plugins {
    `maven-publish`
    signing
}

val javadocJar by tasks.registering(Jar::class) {
    group = "publishing"
    description = "Generates an empty Javadoc Jar."
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = findProperty("sonatype.username") as String?
                password = findProperty("sonatype.password") as String?
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(javadocJar)

        pom {
            name.set("identikon")
            description.set("A Kotlin multiplatform library for generating highly recognizable identicons.")
            url.set("https://github.com/thibseisel/identikon")

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
                url.set("https://github.com/thibseisel/identikon")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
