/**
 * Created by Chris on 9/24/15.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class passwordTester {
    public static void main(String[] args) throws FileNotFoundException{
        File passwords = new File("good_Passwords.txt");
        File dictionary = new File("my_dictionary.txt");
        Scanner scan = new Scanner(dictionary);
        ArrayList<String> dict = new ArrayList<>();
        while(scan.hasNext()){
            dict.add(scan.nextLine());
        }
        scan = new Scanner(passwords);
        while (scan.hasNext()){
            String s = scan.nextLine();
            if(dict.contains(s)){

            }
        }
    }
}
