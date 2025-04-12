# kotlin-spring-boot-sample

サーバーサイド Kotlin + Spring Boot の素振りで使用しています

## Installation

```bash
$ gh repo clone 1zumisawashun/kotlin-spring-boot-sample
$ cd kotlin-spring-boot-sample
```

## Tech Stack

```bash
BE: Kotlin
FW: Spring Boot
DB: PostgreSQL
ORM: jOOQ
```

## How to use

- jOOQ を使ってデータベースのスキーマから Kotlin のコードを自動生成する

```bash
$ ./gradlew jooqCodegen
```

- Gradle を使って Spring Boot アプリケーションを起動する

```bash
$ ./gradlew bootRun
```

http://localhost:8080/

- 上記の手順で失敗する場合 [Troubleshoot](#Troubleshoot)を確認してください

## Troubleshoot

- なし

## その他ドキュメント

See [Configuration Reference](https://kotlinlang.org/).