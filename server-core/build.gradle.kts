plugins {
	kotlin("jvm")
	`maven-publish`
}

group = rootProject.group
version = "0.0.1"

sourceSets {
	val main by getting

	main.java.srcDirs("src")
	main.resources.srcDirs("res")
}

publishing {
	repositories {
		maven {
			name = "SASKotlinOAuth2-ServerCore"
			url = uri("https://maven.pkg.github.com/SASTeknologi/Kotlin-OAuth2")
			credentials {
				username = System.getenv("GITHUB_USER")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}
	publications {
		register<MavenPublication>("gpr") {
			from(components["java"])
		}
	}
}