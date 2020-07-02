import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashPasswd {
        /** パスワードを安全にするためのアルゴリズム */
        private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
        /** ストレッチング回数 */
        private static final int ITERATION_COUNT = 15000;
        /** 生成される鍵の長さ */
        private static final int KEY_LENGTH = 256;
        /** ソルト用のハッシュアルゴリズム*/
        private static final String HASH_ALG = "SHA-256";

        // 平文のパスワードとソルトからハッシュ化されたパスワードを生成する
        public static String getHashedPasswd(String passwd, String salt){
            char[] passwdCharAry = passwd.toCharArray();
            byte[] hashedSalt = getHashedSalt(salt);

            PBEKeySpec keySpec = new PBEKeySpec(passwdCharAry, hashedSalt, ITERATION_COUNT, KEY_LENGTH);

            SecretKeyFactory skf;
            try{
                skf = SecretKeyFactory.getInstance(ALGORITHM);
            }catch(NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }

            SecretKey secretKey;
            try{
                secretKey = skf.generateSecret(keySpec);
            }catch(InvalidKeySpecException e){
                throw new RuntimeException(e);
            }
            byte[] passwdByteAry = secretKey.getEncoded();

            // 生成されたバイト配列を16進数の文字列に変換
            StringBuilder sb = new StringBuilder(64);
            for (byte b : passwdByteAry) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }
        public static byte[] getHashedSalt(String salt){
            MessageDigest messageDigest;
            try{
                messageDigest = MessageDigest.getInstance(HASH_ALG);
            }catch(NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }
            messageDigest.update(salt.getBytes());
            return messageDigest.digest();
        }
}