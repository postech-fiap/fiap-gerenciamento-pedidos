dependencies {
    implementation(project(":app:domain"))
    implementation(project(":app:infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.3.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("com.h2database:h2:2.2.224")
}
