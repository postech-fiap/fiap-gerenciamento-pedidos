dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":app:domain"))
    runtimeOnly("com.mysql:mysql-connector-j")
}
