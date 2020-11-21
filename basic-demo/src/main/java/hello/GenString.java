package hello;

import java.util.Random;

public class GenString {
    private static final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public String getRandomString(int lsize) throws Exception {

        try {

            if (lsize < 1)
                throw new Exception(HelloConstants.ANSI_RED + "The line length is less than 1!" + HelloConstants.ANSI_RESET);
            if (lsize > 100)
                throw new Exception(HelloConstants.ANSI_RED + "The line length is more than 100!" + HelloConstants.ANSI_RESET);

            StringBuilder sb = new StringBuilder(lsize);
            Random random = new Random();
            for (int i = 0; i < lsize; i++) {
                sb.append(chars[random.nextInt(chars.length)]);
            }
            return (sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ("world");
        }
    }
}
