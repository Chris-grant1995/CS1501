/*
Chris Grant

DLB.java
Created on 9/20/15
For Pitt's CS 1501 Project 1
2015 Fall Semester
Prof. Bill Garrison
 */
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
public class pw_check {
   static HashMap<Character, Integer> charMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {

        DLB [] pwDLB = new DLB[38];
        if(args.length > 0){
            for(int i =0; i< args.length; i++){
                if(!args[i].equals("-g")){
                    System.out.println("Flag not found, please run again either with no flags or the '-g' flag");
                    return;
                }
                else{
                    generate();
                    return;
                }
            }
        }

        String posChar = "bcdefghjklmnopqrstuvwxyz23567890!@$%&*";
        for(int i =0; i< posChar.length(); i++){
            charMap.put(posChar.charAt(i), i);
            pwDLB[i] = new DLB();
        }

        charMap.put('a',0);
        charMap.put('4',26);
        charMap.put('i',7);
        charMap.put('1',24);

        File passwords = new File("good_Passwords.txt");
        File dictionary = new File("my_dictionary.txt");
        Scanner scan = new Scanner(passwords);
        if(passwords.length() == 0 || dictionary.length() == 0){
            System.out.println("Either good_Passwords.txt or my_dictionary was not found. Please run 'java pw_check -g' to generate these files. ");
            return;
        }
        DLB dict = new DLB();

        scan = new Scanner(dictionary);
        DLB d = new DLB();
        d.add("a");
        while(scan.hasNext()){
            String s = scan.nextLine();
            d.add(s);
        }

        Scanner kb = new Scanner(System.in);

        while (true){
            String totalChar = "abcdefghijklmnopqrstuvwxyz1234567890!@$%&*";
            System.out.print("Enter a password or 'stop' to stop: ");
            String pw = kb.next();
            if (pw.equals("stop")){
                break;
            }

            if(pw.length() != 5){
                System.out.println("Invaild Length! Enter a 5 character password");
            }
            else{
                boolean flag = false;
                for(int i =0; i< pw.length(); i++){
                    if(!(totalChar.contains(Character.toString(pw.charAt(i))))){
                        System.out.println("Invalid Character Entered!");
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    continue;
                }
                if(pw.contains("4") ||pw.contains("a") ||pw.contains("1") ||pw.contains("i") ) {
                    gen10PW(pw, d);
                    continue;
                }
                else {
                    dict = loadList(pw.charAt(0));


                }
                System.out.println("DLB Loaded, Searching...");
                if(dict.contains(pw)){
                    System.out.println(pw + " is a good password");
                }
                else{
                    gen10PW(pw, d);
                }
            }

        }


    }

    public static DLB loadList(char letter) throws FileNotFoundException{
        File file = new File("good_passwords.txt");
        System.out.println("Loading " +letter + " portion of good_passwords.txt");
        Scanner scan = new Scanner(file);
        DLB dict = new DLB();
        boolean flag = false;
        while (scan.hasNext()) {
            String s = scan.nextLine();
            if(s.charAt(0) == letter){
                flag = true;
                dict.add(s);
            }
            if(s.charAt(0) != letter && flag){
                scan.close();
                break;
            }




        }
        return dict;
    }

    public static void gen10PW(String password, DLB dictionary){
        System.out.println("Invalid Password");
        System.out.println("See Suggested Similar Passwords Below");
        String posChar = "bcdefghjklmnopqrstuvwxyz23567890!@$%&*";
        int counter =0;
        char [] pw = password.toCharArray();
        int fTime0 = 1;
        int fTime1 = 1;
        int fTime2 = 1;
        int fTime3 = 1;
        int fTime4 = 1;



        for (int c0 = 0; c0 < posChar.length(); c0++) {
            for(int i =0; i< 2; i++) {
                for (int c1 = 0; c1 < posChar.length(); c1++) {
                    for(int j =0; j<2; j++) {
                        for (int c2 = 0; c2 < posChar.length(); c2++) {
                            for(int k = 0; k<2; k++) {
                                for (int c3 = 0; c3 < posChar.length(); c3++) {
                                    for(int l =0; l<2; l++) {
                                        for (int c4 = 0; c4 < posChar.length(); c4++) {

                                            if (fTime0 == 1) {
                                                c0 = charMap.get(password.charAt(0));
                                                fTime0 = 0;
                                            }
                                            if (fTime1 == 1) {
                                                c1 = charMap.get(password.charAt(1));
                                                fTime1 = 0;
                                            }
                                            if (fTime2 == 1) {
                                                c2 = charMap.get(password.charAt(2));
                                                fTime2 = 0;
                                            }
                                            if (fTime3 == 1) {
                                                c3 = charMap.get(password.charAt(3));
                                                fTime3 = 0;
                                            }
                                            if (fTime4 == 1) {
                                                c4 = charMap.get(password.charAt(4));
                                                fTime4 = 0;
                                            }


                                            pw[0] = posChar.charAt(c0);
                                            pw[1] = posChar.charAt(c1);
                                            pw[2] = posChar.charAt(c2);
                                            pw[3] = posChar.charAt(c3);
                                            pw[4] = posChar.charAt(c4);
                                            int numLetter = getNumLetters(pw);
                                            if (numLetter > 0 && numLetter < 4) {
                                                int numNum = getNumNumbers(pw);
                                                if (numNum > 0 && numNum < 3) {
                                                    int numSymbol = getNumSymbols(pw);
                                                    if (numSymbol > 0 && numSymbol < 3) {
                                                        if (checkForWords(pw, dictionary)) {
                                                            System.out.println(pw);
                                                            counter++;
                                                            if (counter == 10) {
                                                                return;
                                                            }
                                                        }

                                                    }
                                                }
                                            }


                                        }
                                    }

                                }
                            }

                        }
                    }

                }

            }
        }

    }
    public static void generate() throws FileNotFoundException{
        DLB myDLB = new DLB();
        File file = new File("dictionary.txt");
        System.out.println("Test2");
        myDLB.add("a");
        Scanner scan = new Scanner(file);
        PrintWriter textFile = new PrintWriter("my_dictionary.txt");
        System.out.println("Generating my_dictionary.txt");
        while (scan.hasNext()) {
            String s = scan.nextLine();
            if (s.length() < 5) {
                myDLB.add(s);
                textFile.println(s);
            }


        }
        textFile.close();
        System.out.println("my_dictionary.txt Generated");
        System.out.println("Generating good_Passwords.txt");
        generateGoodPasswords(myDLB);
        System.out.println("good_Passwords.txt Generated");

    }
    public static void generateGoodPasswords(DLB dictionary) throws FileNotFoundException {
        int counter =0;
        PrintWriter textFile = new PrintWriter("good_Passwords.txt");
        char[] password = new char[5];
        String posChar = "bcdefghjklmnopqrstuvwxyz23567890!@$%&*";
        for (int c0 = 0; c0 < posChar.length(); c0++) {
            for (int c1 = 0; c1 < posChar.length(); c1++) {
                for (int c2 = 0; c2 < posChar.length(); c2++) {
                    for (int c3 = 0; c3 < posChar.length(); c3++) {
                        for (int c4 = 0; c4 < posChar.length(); c4++) {
                            password[0] = posChar.charAt(c0);
                            password[1] = posChar.charAt(c1);
                            password[2] = posChar.charAt(c2);
                            password[3] = posChar.charAt(c3);
                            password[4] = posChar.charAt(c4);
                            String pw = new String(password);
                            //System.out.println(pw);
                            int numLetter = getNumLetters(password);
                            if (numLetter > 0 && numLetter < 4) {
                                int numNum = getNumNumbers(password);
                                if (numNum > 0 && numNum < 3) {
                                    int numSymbol = getNumSymbols(password);
                                    if (numSymbol > 0 && numSymbol < 3) {

                                        if (checkForWords(password, dictionary)) {
                                            textFile.println(pw);
                                        }

                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        textFile.close();
    }

    public static int getNumLetters(char[] password) {
        String letters = "bcdefghjklmnopqrstuvwxyz";
        int counter = 0;
        for (int i = 0; i < password.length; i++) {
            if (letters.contains(Character.toString(password[i]))) {
                counter++;
            }
        }
        return counter;
    }

    public static int getNumNumbers(char[] password) {
        String letters = "1234567890";
        int counter = 0;
        for (int i = 0; i < password.length; i++) {
            if (letters.contains(Character.toString(password[i]))) {
                counter++;
            }
        }
        return counter;
    }

    public static int getNumSymbols(char[] password) {
        String letters = "!@$%&*";
        int counter = 0;
        for (int i = 0; i < password.length; i++) {
            if (letters.contains(Character.toString(password[i]))) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean checkForWords(char[] password, DLB dicitionary) {
        char[] pwCopy = new String(password).toCharArray();
        for (int i = 0; i < password.length; i++) {
            if (password[i] == '7') {
                pwCopy[i] = 't';
                //pwCopy2[i] = 't';
            } else if (password[i] == '4') {
                pwCopy[i] = 'a';
                //pwCopy2[i] = 'a';
            } else if (password[i] == '0') {
                pwCopy[i] = 'o';
                //pwCopy2[i] = 'o';
            } else if (password[i] == '5') {
                pwCopy[i] = 's';
                //pwCopy2[i] = 's';
            }
            else if (password[i] == '3') {
                pwCopy[i] = 'e';
                //pwCopy2[i] = 'e';
            }
        }
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String otherChars = "1234567890!@$%&*";
        StringBuilder s;
        for (int i = 0; i < password.length; i++) {
            s = new StringBuilder();
           if(isALetter(pwCopy[i])){
               s.append(pwCopy[i]);
               int val = dicitionary.searchPrefix(s.toString());
               if(val > 1)
                   return false;
               else if (val == 0 )
                   continue;
               for(int j = i + 1; j<password.length; j++){
                   if(isALetter(pwCopy[j])){
                       s.append(pwCopy[j]);
                       val = dicitionary.searchPrefix(s.toString());
                       if(val > 1)
                           return false;
                        else if (val == 0 )
                           break;
                   }
                   else
                       break;
               }
               if(s.length()>1){
                   val = dicitionary.searchPrefix(s.toString());
                   if(val > 1)
                       return false;
                   else if (val == 0 )
                       continue;
               }

           }
        }
        return true;
    }
    public static boolean isALetter(char c){
        String letters = "abcdefghijklmnopqrstuvwxyz";
        return letters.contains(Character.toString(c));
    }
}

