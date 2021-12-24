val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

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
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
	implementation("io.ktor:ktor-server-core:$ktorVersion")
	implementation("io.ktor:ktor-server-netty:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	implementation("io.ktor:ktor-auth:$ktorVersion")
	implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

	implementation("org.litote.kmongo:kmongo:4.4.0")
	implementation("io.ktor:ktor-gson:$ktorVersion")


}