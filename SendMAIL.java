import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMAIL{
    static final String charset = "UTF-8";
    static final String encoding = "base64";// mime用エンコーディング
    //語尾のCは内部用を表す
    static String fromC = "sender@example.com";//送信元
    static String fromNameC= "Sender Name";//送信者

    static String toC = "recipient@example.com";//送信先

    static String smtpUserNameC = "smtp_username";//SMTPサーバーのユーザー名
    static String smtpPasswordC = "smtp_password";//SMIPサーバーのユーザー名

    static String configSetC = "ConfigSet";//構成セットの名前(使わないならいらない)

    static String hostC = "smtp.example.com";//SMTPサーバーの場所
    static int portC = 587;//最初のSMTPサーバのポート番号
    //メールの主題
    static String subjectC = "subject";
    //メールの本文
    static String bodyC ="body";

    //コンストラクタ
    void SendMAIL(){
        ;
    }

    //セッタ
    public void setFrom(String from){fromC = from;}
    public void setFromName(String fromName){fromNameC = fromName;}
    public void setTo(String to){toC = to;}
    public void setSmtpUserName(String smtpUserName){smtpUserNameC = smtpUserName;}
    public void setSmtpPassword(String smtpPassword){smtpPasswordC = smtpPassword;}
    public void setConfigSet(String ConfigSet){configSetC = ConfigSet;}
    public void setHost(String host){hostC = host;}
    public void setPort(int port){portC = port;}
    public void setSubject(String subject){subjectC = subject;}
    public void setBody(String body){bodyC = body;}

    public void setProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.host", hostC);
        props.put("mail.smtp.port", portC);
        props.put("mail.smtp.auth", true);
        //props.put("mail.smtp.connectiontimeout", "10000");
        //props.put("mail.smtp.timeout", "10000");
        //props.put("mail.debug", true);

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(smtpUserNameC, smtpPasswordC);
                }
            }
        );

        try{
            MimeMessage message = new MimeMessage(session);

            // Set From
            message.setFrom(new InternetAddress(fromC, fromNameC));
            // Set ReplyTo 
            message.setReplyTo(new Address[]{new InternetAddress(fromC)});
            // Set TO
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toC));

            // Set Subject
            message.setSubject(subjectC, charset);
            // Set Body
            message.setText(bodyC, charset);
            
            // Set Header
            message.setHeader("Content-Transfer-Encording", encoding);

            Transport.send(message);
        }catch(MessagingException e){
            throw new RuntimeException(e);
        }catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }
}