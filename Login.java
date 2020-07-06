import java.sql.*;
import java.lang.Exception;

class Login extends HashPasswd{
    private String savedPasswd;
    // 例外
    class NullStringReceiveException extends Exception{
        private static final long serialVersionUID = 1L;
        NullStringReceiveException(String msg){
            super(msg + "argument received an empty string as an argument.");
        }
    }
    class SQLNoMatchRecordException extends Exception{
        private static final long serialVersionUID = 1L;
        SQLNoMatchRecordException(){
            super("seacrch string record is not exsist in this Table");
        }
    }
    // コンストラクタ
    void HashPasswd(){
        init();
    }
    // 初期化: savedPasswd
    public void init(){
        savedPasswd = "";
    }
    public void init(String userID){
        try{
            sqlSelectUserSavedPasswd(userID);
        }catch(SQLNoMatchRecordException e){
            e.printStackTrace();
        }
    }
    // パスワードの認証
    // 認証が通るとtrueが返ってくる
    public boolean hashedPasswordVerificate(String received){
        try{
            if(savedPasswd == null) throw new NullStringReceiveException("");
        }catch(NullStringReceiveException e){
            e.printStackTrace();
            return false;
        }
        if(savedPasswd.compareTo(received) == 0)return true;
        return false;
    }
    // DBからハッシュされたパスワードを拾う->this.savedPasswdに送る
    // DBにユーザIDもしくはパスワードがないならSQLNoMtchRecordExceptionを投げる
    // つまり、そんな人がいないを知らせる
    public void sqlSelectUserSavedPasswd(String userID) throws SQLNoMatchRecordException{
        Connection connection = DBManager.getUserConnection();
        String sql = "SELECT HashedPassword FROM LoginInfo WHERE UserID=?";
        String result = "";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet res = ps.executeQuery();
            if(getResultSetRows(res) > 0){
                result = res.getString(1);
            }else{
                throw new SQLNoMatchRecordException();
            }
            //接続終了
            ps.close();
            connection.close();
        }catch(SQLException e){
            //デバッグ用
            e.printStackTrace();
        }
        savedPasswd = result;
    }
    // DBの検索結果が何行あるかを返す
    private int getResultSetRows(ResultSet res){
        try{
            if(res.next()){
                res.last();
                int tmp = res.getRow();
                res.beforeFirst();
                return tmp;
            }else return 0;
        }catch(SQLException e){
            //デバッグ用
            e.printStackTrace();
            return 0;
        }
    }
}