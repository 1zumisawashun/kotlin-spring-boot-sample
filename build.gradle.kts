plugins {
    // spring-boot
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    // jooq
    id("org.jooq.jooq-codegen-gradle") version "3.19.11"
    // detekt
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // spring-boot
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // db
    runtimeOnly("org.postgresql:postgresql")
    // jooq
    implementation("org.jooq:jooq:3.19.11")
    implementation("org.jooq:jooq-meta:3.19.11")
    implementation("org.jooq:jooq-codegen:3.19.11")
    implementation("org.jooq:jooq-postgres-extensions:3.19.11")
    jooqCodegen("org.postgresql:postgresql:42.7.4")
    // test
    // https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/html/boot-features-testing.html
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.8")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    // https://qiita.com/gumimin/items/f15eaede3e0e5b7a11a5
    // こちらの記述でJUnit5を有効化します。
    useJUnitPlatform()
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/sample-db"
            user = "sample-user"
            password = "sample-pass"
        }
        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                includes = ".*"
            }

            target {
                packageName = "com.example.kotlinSpringBootSample.jooq.jooq"
            }
        }
    }
}

sourceSets["main"].java {
    srcDir("build/generated-sources/jooq")
}



detekt {
    autoCorrect = true
    config.setFrom(file("config/detekt/detekt.yml"))
    // 上書きしたいものだけを、追加の設定ファイルに記載する
    buildUponDefaultConfig = true
    /**
     * detekt プラグインに使う Kotlin バージョンを固定する
     * https://detekt.dev/docs/gettingstarted/gradle/#dependencies
     */
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
            }
        }
    }
}
