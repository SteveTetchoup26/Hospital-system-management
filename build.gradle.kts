plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "2.1.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "4.2.0"
}

group = "com.st"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://nexus.gluonhq.com/nexus/content/repositories/releases/")
    }
}

val junitVersion = "5.10.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.st.hospitalmanagement")
    mainClass.set("com.st.hospitalmanagement.Main")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("com.gluonhq:charm-glisten:6.0.2")
    // Requis par le module charm-glisten (sinon le module ne se résout pas au lancement)
    implementation("com.gluonhq.attach:util:4.0.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    // jdk.crypto.ec : indispensable à la connexion TLS vers MySQL depuis l'exécutable
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages",
        "--add-modules", "jdk.crypto.ec"))
    launcher {
        name = "HospitalManagement"
    }

    // Exécutable natif : ./gradlew jpackageImage (dossier portable) ou ./gradlew jpackage (installateur).
    // jpackage ne sait produire que pour le système sur lequel il tourne
    // (.exe sous Windows, .deb sous Linux).
    jpackage {
        imageName = "HospitalManagement"
        installerName = "HospitalManagement"
        appVersion = "1.0.0"
        vendor = "ST"

        val os = org.gradle.internal.os.OperatingSystem.current()
        if (os.isWindows) {
            icon = "src/main/resources/logo.ico"
            installerType = "exe" // nécessite WiX Toolset
            installerOptions = listOf("--win-menu", "--win-shortcut", "--win-dir-chooser")
        } else if (os.isLinux) {
            icon = "src/main/resources/logo.png"
            installerType = "deb"
            installerOptions = listOf("--linux-shortcut")
        }
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
}

// Jar autonome (avec dépendances) : ./gradlew fatJar
// Séparé du jar principal, sinon les classes JavaFX se retrouvent dans le module
// de l'application et entrent en conflit avec javafx.base au lancement.
tasks.register<Jar>("fatJar") {
    archiveClassifier.set("all")
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get()
        )
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
