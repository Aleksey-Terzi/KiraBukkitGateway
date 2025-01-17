/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.3.1"
	id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "net.civmc"
version = "2.0.1"
description = "KiraBukkitGateway"

repositories {
    fun civRepo(name: String) {
    	maven {
    	    url = uri("https://maven.pkg.github.com/CivMC/${name}")
    		credentials {
    			// These need to be set in the user environment variables
    			username = System.getenv("GITHUB_ACTOR")
    			password = System.getenv("GITHUB_TOKEN")
    		}
    	}
    }

    mavenCentral()

	maven("https://repo.aikar.co/content/groups/aikar/")
	maven("https://repo.civmc.net/repository/maven-public/")

	maven("https://jitpack.io")

    civRepo("NameLayer")
    civRepo("CivChat2")
    civRepo("JukeAlert")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    implementation("com.rabbitmq:amqp-client:5.6.0")
    compileOnly("net.civmc:civmodcore:2.0.0-SNAPSHOT:dev-all")
	compileOnly("net.civmc:namelayer-spigot:3.0.0-SNAPSHOT:dev")
	compileOnly("net.civmc:civchat2:2.0.0-SNAPSHOT:dev")
	compileOnly("net.cimc.jukealert:paper:3.0.0-SNAPSHOT:dev")
	compileOnly("net.luckperms:api:5.0")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
	build {
		dependsOn(reobfJar)
	}

	compileJava {
		options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

		// Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
		// See https://openjdk.java.net/jeps/247 for more information.
		options.release.set(17)
	}
	javadoc {
		options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
	}
	processResources {
		filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
		filesMatching("plugin.yml") {
			expand(mapOf(
				"name" to "KiraBukkitGateway",
				"version" to version,
			))
		}
	}

	test {
		useJUnitPlatform()
	}
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/CivMC/KiraBukkitGateway")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
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
