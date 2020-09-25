## Overview

amazon出品者向けのシステムです。

amazonサーバーから注文情報を取得して購入者のメールアドレスにお礼メールを送信します。

[Web画面](https://maidomail.biz/)

## Description

### １．環境図

[![img](https://camo.githubusercontent.com/efabe4c256e2e980f6be3e787dfe148c4065bf3e/68747470733a2f2f6d6169646f6d61696c397075626c69636275636b65742e73332d61702d6e6f727468656173742d312e616d617a6f6e6177732e636f6d2f7368696e2e626d70)](https://camo.githubusercontent.com/efabe4c256e2e980f6be3e787dfe148c4065bf3e/68747470733a2f2f6d6169646f6d61696c397075626c69636275636b65742e73332d61702d6e6f727468656173742d312e616d617a6f6e6177732e636f6d2f7368696e2e626d70)

### ２．各処理について

実行するjavaファイルは src/exec にまとめています

#### ①～⑤

 別プロジェクト参照 [Link](https://github.com/kota-imai/amznmail_order_report/blob/master/README.md)

#### ⑥開封・リンククリックログ取得処理（SaveStaticsFromAthena.java）

s3に保存される開封イベント・クリックイベントのログをAthenaを使って取り込みを行い、

MySQLにDB保存してBIツールでKPIを監視できるようにする



## Demo

![maidomail_demo](https://user-images.githubusercontent.com/56163213/94266146-ad7a8b00-ff74-11ea-983c-376cb4ce7bb6.png)

## VS.

[メルゾン](https://mailzon.net/ja/)

開封イベントやクリックイベントの取得可能、メールへの画像挿入、HTML形式のメール送信可能という点で差別化を行った。



## Author

Kota Imai



## License

MIT
