import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt{
    final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    final static String ALGORITHM = "AES";
    private static byte[] secretKey;
    private static byte[] iv;

    Encrypt(){//
        try{
            //初期化ベクタiv, 暗号化キーは別のクラスもしくはsqlで記述
            //デバッグ用
            byte[] secretKeyInit = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
            byte[] ivInit = {0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f };
            secretKey = secretKeyInit;
            iv = ivInit;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public byte[] getsecretKey(){
        return secretKey;
    }
    public byte[] getiv(){
        return iv;
    }
    public byte[] inputEncrypt(byte[] input){
        try{
            Cipher c = Cipher.getInstance(TRANSFORMATION);
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, ALGORITHM), new IvParameterSpec(iv));
            byte encrypted[] = c.doFinal(input);
            return encrypted;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] inputEncrypt(String input){
        return inputEncrypt(input.getBytes());
    }
    public byte[] outputDecrypted(byte[] encrypted){
        byte output[] = "".getBytes();
        try{
            Cipher c = Cipher.getInstance(TRANSFORMATION);
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, ALGORITHM), new IvParameterSpec(iv));
            output = c.doFinal(encrypted);
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }
    public String ToString(){
        return "secretKey:" + secretKey + " iv:" + iv;
    }

    public static void main(String args[]){
        Encrypt encrypter = new Encrypt();

        Scanner scanner = new Scanner(System.in);
        System.out.print("input : ");
        String input = scanner.nextLine();
        byte[] encrypted = encrypter.inputEncrypt(input);
        System.out.println("encrypted:" + encrypted.length);
        String decrypted = new String(encrypter.outputDecrypted(encrypted));
        System.out.println(decrypted);

        scanner.close();
    }
}

