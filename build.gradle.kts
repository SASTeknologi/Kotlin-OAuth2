buildscript {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		google()
	}

	dependencies {
		classpath(kotlin("gradle-plugin", "1.5.31"))
	}
}

group = "sas.teknologi"

allprojects {
	repositories {
		mavenCentral()
		google()
	}
}

tasks.register("cleanAll", Delete::class).configure {
	delete(rootProject.buildDir)
}