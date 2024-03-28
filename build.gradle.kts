val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
  kotlin("jvm") version "1.9.23"
  id("io.ktor.plugin") version "2.3.9"
}

group = "com.example"
version = "0.0.2"

application {
  mainClass.set("com.example.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-netty-jvm")
  /* API */
  implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
  implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
//  implementation("io.ktor:ktor-serialization-kotlinx-xml:$ktor_version")
//  implementation("io.ktor:ktor-serialization-kotlinx-cbor:$ktor_version")
//  implementation("io.ktor:ktor-serialization-kotlinx-protobuf:$ktor_version")
  /* Template Engine*/
  implementation("io.ktor:ktor-server-freemarker:$ktor_version")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
