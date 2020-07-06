/**参考にしたwebサイト: samurai blog https://www.sejuku.net/blog/30245
 * ユーザー登録の際に、「既に使われているユーザーは使えないよ」ができる
 */
var xhr = new XMLHttpRequest(); //ajax(非同期双方向通信)を使うためにAPI"XMLHttpRequest"のインスタンスを作成する
var requestURL = "https://localhost:8443/webapp/testServlet"

xhr.open("POST", requestURL);/** リクエストメゾットと通信するサーバーの場所
                              * (普通のfromを使ったやつのurlバーに出てくるやつと一緒)
                              * "GET" / "POST"
                              */
xhr.setRequestHeader('content-type', 'aplication/x-www-form-urlencode;charset=UTF-8');//"POST"のときに記述
/**リクエストヘッダ
 */
xhr.send();// 通信を開始

xhr.onreadystatechange = function(){
    if(1){//条件式は、いつデータを取得するかの条件式を書く
        /**
         * xhr.readyStateを使って通信状態を見る
         * 条件式例: shr.readyState === 4
         * -----------------------------------------
         * UNSET            = 0 : まだ通信は行われていない
         * OPENED           = 1 : 通信準備完了
         * HEADERS_RECIEVED = 2 : サーバーとの通信が始まっている
         * LOADING          = 3 : サーバーから目的のデータを取得している
         * DONE             = 4 : データを取得して通信終了
         * 
         * xhr.statusを使ってエラーメッセージを取得
         * つまりhttpステータスコード
         * 条件式例: shr.status === 200
         */
        console.log(xhr.responseText);
        /**xhr.responseTextはレスポンスの内容を取得できる
         * 中身はtextデータなのでデータの種類に応じて読み替えをする
         * 例: JSONなら
         * console.log(JSON.parse(xhr.responseText));
         * とするとjson形式で取得できる
         * console.logは標準出力?
         */
    }
}