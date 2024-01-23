tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":app:domain"))
    implementation(project(":app:infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("com.h2database:h2:2.2.224")
}
