package hello;

import java.util.Scanner;

class Hello {
    public static void main(String[] args) {
        GenString greeter = new GenString();
        System.out.print(HelloConstants.ANSI_GREEN + "Enter any whole number from 1 to 100: " + HelloConstants.ANSI_RESET);
        Scanner scan = new Scanner(System.in);
        try {
            int lsize = scan.nextInt();
            System.out.println(HelloConstants.ANSI_YELLOW + "Hello, " + greeter.getRandomString(lsize) + "!" + HelloConstants.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(HelloConstants.ANSI_RED + "are you OK?" + HelloConstants.ANSI_RESET);
        }
    }
}