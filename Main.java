public class Main {
    public static void main(String[] args) {
        String safetyPassword1 = PasswordUtil.getSafetyPassword("password", "USERID0001");
        System.out.println(safetyPassword1);
        String safetyPassword2 = PasswordUtil.getSafetyPassword("password", "USERID0002");
        System.out.println(safetyPassword2);
    }
}