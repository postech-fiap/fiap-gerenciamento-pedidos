import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "1.8.21"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("org.sonarqube") version "4.4.1.3373"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "br.com.fiap"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.sonarqube")

    sonar {
        properties {
            property("sonar.projectKey", "postech-fiap_pagamento-api")
            property("sonar.organization", "postech-fiap")
            property("sonar.host.url", "https://sonarcloud.io")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.mysql:mysql-connector-j")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

sonar {
    properties {
        property("sonar.projectKey", "postech-fiap_fiap-gerenciamento-pedidos")
        property("sonar.organization", "postech-fiap")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

kover {
    isDisabled = false
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)
    intellijEngineVersion.set("1.0.656")
    jacocoEngineVersion.set("0.8.8")
    generateReportOnCheck = true
    disabledProjects = setOf()
    instrumentAndroidPackage = false
    runAllTestsForProjectTask = false
}

val includeCoverage = listOf(
    "br.com.fiap.gerenciamentopedidos.*",
)

val excludeCoverage = listOf(
    "**/models/**",
    "**/dtos/**",
    "**/valueobjects/**",
    "**/enums/**",
    "**/entities/**",
    "**/exceptions/**",
    "**/requests/**",
    "**/responses/**"
)

tasks.test {
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        isDisabled = false
        binaryReportFile.set(file("${layout.buildDirectory}/custom/result.bin"))
        includes = includeCoverage
        excludes = excludeCoverage
    }
}

tasks.koverMergedHtmlReport {
    isEnabled = true
    htmlReportDir.set(layout.buildDirectory.dir("reports/jacoco/test/html"))
    includes = includeCoverage
    excludes = excludeCoverage
}

tasks.koverMergedXmlReport {
    isEnabled = true
    xmlReportFile.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml"))
    includes = includeCoverage
    excludes = excludeCoverage
}

tasks.register("jacocoTestReport") {
    dependsOn("test", "koverMergedHtmlReport", "koverMergedXmlReport")
}