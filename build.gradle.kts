import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.22"
    id("org.flywaydb.flyway") version "9.14.1"
    idea
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

idea {
    module {
        val kaptMain = file("$buildDir/generated/source/kapt/main")
        sourceDirs.plusAssign(kaptMain)
        generatedSourceDirs.plusAssign(kaptMain)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Query SQL
    implementation(group = "com.querydsl", name = "querydsl-jpa", classifier = "jakarta")
    kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")
    // FIXME: https://github.com/querydsl/querydsl/discussions/3036
    // kapt("com.querydsl:querydsl-kotlin-codegen")

    runtimeOnly("com.h2database:h2")
    implementation("org.flywaydb:flyway-core")

    // devtools
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")

// 	testCompileOnly("com.querydsl:querydsl-jpa")
//  testAnnotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")
// 	testImplementation("org.projectlombok:lombok")
// 	testAnnotationProcessor("org.projectlombok:lombok")
// 	testCompileOnly("org.projectlombok:lombok")
//  kaptTest(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")
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

flyway {
    url = "jdbc:h2:./build/database/db;AUTO_SERVER=TRUE"
    user = "user"
    password = "password"
}