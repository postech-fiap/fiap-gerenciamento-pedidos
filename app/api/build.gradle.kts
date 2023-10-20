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
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
