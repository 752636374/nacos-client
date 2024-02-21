public class StringTest {
    public static void main(String[] args) {
        String s = "220105300023001690117422159523840_0_12";

        String[] ss = s.split("_");
        System.out.println(ss[0]);
    }
}
