val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	application
	kotlin("jvm") version "1.6.0"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
}

group = "ru.kheynov"
version = "0.0.1"
application {
	mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
	mavenCentral()
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

val exposedVersion: String by project

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
	implementation("io.ktor:ktor-server-core:$ktor_version")
	implementation("io.ktor:ktor-server-sessions:$ktor_version")
	implementation("io.ktor:ktor-serialization:$ktor_version")
	implementation("io.ktor:ktor-server-netty:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")

	implementation("org.litote.kmongo:kmongo-serialization:4.4.0")
	implementation("org.litote.kmongo:kmongo-coroutine:4.4.0")

	testImplementation("io.ktor:ktor-server-tests:$ktor_version")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}