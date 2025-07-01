plugins {
    id("java")
}

group = "dev.kofeychi.packer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.19.0")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("com.google.code.gson:gson:2.13.1")
}

file("${rootDir.path}/run").mkdirs()