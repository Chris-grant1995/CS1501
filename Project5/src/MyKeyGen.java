/**
 * Created by Chris on 12/3/15.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import java.security.MessageDigest;
public class MyKeyGen {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
        BigInteger p = new BigInteger(1024,1,new Random());
        //System.out.println(p);
        BigInteger q = new BigInteger(1024,1,new Random());

        BigInteger n = p.multiply(q);

        BigInteger pminus1 = p.subtract(new BigInteger("1"));
        BigInteger qminus1 = q.subtract(new BigInteger("1"));

        BigInteger phi = qminus1.multiply(pminus1);



        BigInteger e =  new BigInteger(1024,1,new Random());

        while(phi.compareTo(e) !=1 || !phi.gcd(e).equals(new BigInteger("1"))){
            e =  new BigInteger(1024,1,new Random());
        }
        //System.out.println(phi.gcd(e));

        BigInteger d = e.modInverse(phi);
        //System.out.println("Public key");
        //System.out.println(e);
        //System.out.println(n);

        //System.out.println("Private key");
        //System.out.println(d);
        //System.out.println(n);

        FileWriter writer = new FileWriter("pubkey.rsa");
        writer.write(e.toString() + "\n");
        writer.write(n.toString());
        writer.close();

        writer = new FileWriter("privkey.rsa");
        writer.write(d.toString() + "\n");
        writer.write(n.toString());
        writer.close();

        System.out.println("Public Key Saved to pubkey.rsa");
        System.out.println("Private Key Saved to privkey.rsa");
       /* MessageDigest m = MessageDigest.getInstance("SHA-256");
        byte [] b = m.digest("Hello".getBytes());
        BigInteger hash = new BigInteger(b);
        System.out.println("Hash " + hash);
        BigInteger encrypted = hash.modPow(e,n);
        System.out.println("Encrypted: " + encrypted);

        BigInteger decrypted = encrypted.modPow(d,n);
        System.out.println("Decrypted Hash: " + decrypted);

        //System.out.println(m.getProvider().elements().nextElement());
        */


    }
}
