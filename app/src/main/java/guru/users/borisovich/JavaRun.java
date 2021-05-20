package guru.users.borisovich;

import java.util.stream.Stream;

public class JavaRun {

    public static void main(String[] args) {
        String test1 = "abc";
        String test2 = "aaaxaaa";

        System.out.println(PalindromeCheck.check(test1));
        System.out.println(PalindromeCheck.check(test2));
    }

    public static class PalindromeCheck {

        public static boolean check(String text) {
            boolean  isPalindrome = true;
            int      size         = text.length();

            Stream.of(text.split(""))
            .parallel();

            for (int i = 0; i < size; i++) {
                isPalindrome = text.charAt(size - i - 1) == text.charAt(i);

                if (!isPalindrome) {
                    break;
                }
            }

            return isPalindrome;
        }

    }

}
