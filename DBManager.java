import java.sql.Connection;
import java.sql.DriverManager;

class DBManager {   
    private static String url = "jdbc:mysql://localhost:3306/endeza_group10";
    private static String user = "dev";
    private static String password = "endeza";
    
    public static Connection getUserConnection(String urlIn){
        try{
            /**
             * jdk9以降非推奨
             * Class.forName("com.mysql.jdvc.Driver").newInstance();
             */
            Class.forName("com.mysql.jdvc.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection(urlIn,user, password);
            return connection;
        }catch(Exception e){
            throw new IllegalStateException(e);
        }
    }
    public static Connection getUserConnection(){
        try{
            return getUserConnection(url);
        }catch(IllegalStateException e){
            throw e;
        }
    }
}