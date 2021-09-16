# Amazon SQS + Localstack メモ

WSL2 + Ubuntu + Docker + localstack で動かす。

## 準備
1. dockerを起動
    ```
    sudo service docker start
    ```
1. docker-composeがあるフォルダに移動して実行
    ```
    cd /mnt/c/wsl2/docker/localstack
    docker-compose up -d
    ```
1. キューを作成
    ```
    aws --region us-east-1 --endpoint-url http://localhost:4566 sqs create-queue --queue-name 'foo-queue'
    ```

## アプリ起動

1. Springアプリを起動
1. キューに値を入れる
    ```
    aws --region us-east-1 --endpoint-url http://localhost:4566 sqs send-message --queue-url 'http://localhost:4566/000000000000/foo-queue' --message-body 'hogehoge'
    ```
1. リスナーが動いて、SQSからデータを受信する。
1. `restTemplate`でURLアクセス

## docker-composeのサンプル

docker-compose.yml を作る

```yml
version: '3'
 
services:
  # LocalStack
  localstack:
    image: localstack/localstack:latest
    environment:
      - SERVICES=s3,ses,sqs # 使いたいAWSサービスカンマ区切りで設定する
      - DEFAULT_REGION=us-east-1 # リージョンを設定
      - DATA_DIR=/tmp/localstack/data # データ保存するディレクトリ
      - DEBUG=1
      - LS_LOG=trace
    volumes:
      - ./localstack:/tmp/localstack # ローカルディレクトリをデータ保存ディレクトリへマウント
    ports:
      - 4566:4566 # サービスへのアクセスポートは4566
```
