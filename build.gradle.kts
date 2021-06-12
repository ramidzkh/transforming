plugins {
    java
    `maven-publish`
    signing
    id("org.cadixdev.licenser") version "0.6.0"
}

group = "me.ramidzkh"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains", "annotations", "21.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

license {
    setHeader(file("HEADER"))
    include("**/*.java")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"

        if (JavaVersion.current().isJava9Compatible) {
            options.release.set(8)
        } else {
            sourceCompatibility = "8"
            targetCompatibility = "8"
        }
    }

    withType<Javadoc> {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }

    test {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            extensions.getByType<SigningExtension>().apply {
                if (signatory != null) {
                    sign(this@create)
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("${rootProject.buildDir}/repos/releases")
            val snapshotsRepoUrl = uri("${rootProject.buildDir}/repos/snapshots")
            name = "Project"
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}
