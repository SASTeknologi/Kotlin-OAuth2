plugins {
	kotlin("jvm")
}

group = rootProject.group

sourceSets {
	val main by getting

	main.java.srcDirs("src")
	main.resources.srcDirs("res")
}