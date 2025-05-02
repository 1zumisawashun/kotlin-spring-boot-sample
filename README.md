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

- Detekt を使って静的解析を実施する

```bash
$ ./gradlew detekt 
```

http://localhost:8080/

```bash
# 取得
$ curl -H 'Content-Type:application/json' -X GET http://localhost:8080/book/detail/100

# 登録
$ curl -H 'Content-Type:application/json' -X POST -d '{"id":300,"title":"Spring入門","author":"コトリン太郎","releaseDate":"2025-04-20"}' http://localhost:8080/book/register

# 更新
$ curl -H 'Content-Type:application/json' -X PUT -d '{"id":300,"title":"SpringBoot入門","releaseDate":"2025-04-20"}' http://localhost:8080/book/update

# 削除
$ curl -H 'Content-Type:application/json' -X DELETE http://localhost:8080/book/delete/300
```

- spring-boot-starter-security を使った認証を検証する
```bash
$ curl -i http://localhost:8080/health
# HTTP/1.1 302

$ curl -i -c cookie.txt -H 'Content-Type:application/x-www-form-urlencoded' -X POST -d 'email=user@test.com' -d 'password=user' http://localhost:8080/login
# HTTP/1.1 200

$ cat cookie.txt
# Netscape HTTP Cookie File

$ curl -b cookie.txt http://localhost:8080/health
# Service is healthy
```

- 上記の手順で失敗する場合 [Troubleshoot](#Troubleshoot)を確認してください

## Troubleshoot

- シークレットモードでデバッグすると良さそう
- 

## その他ドキュメント

See [Configuration Reference](https://kotlinlang.org/).