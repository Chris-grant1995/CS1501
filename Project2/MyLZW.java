/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependenominatorcies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/
/*
Chris Grant
CS 1501 Project 2
MyLZW.java


*/



import java.lang.Math;
public class MyLZW {
    private static int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    private static int W = 9;         // codeword width
    private static double sRatio = 0;
    private static double numerator = 0;
    private static double denominator = 0;
    private static double cRatio = 0;
    private static boolean ratio = true;
    private static char mode = 0;
    public static void compressNormal() {
        String input = BinaryStdIn.readString();
        BinaryStdOut.write('n');
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            if(code == L && W!=16){
                W++;
                L = (int) Math.pow(2,W);

            }
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            //System.out.println("Hello World");
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);            // Scan past s in input.
            ////System.err.println(code);
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expandNormal() {
        String[] st = new String[L];
        if(mode == 0){
            mode = BinaryStdIn.readChar();
        }
        //System.err.println(mode);
        if(mode == 'r'){
            ////System.err.println("Running Reset Mode");
            expandReset();
            return;
        }
        else if(mode == 'm'){
            //System.err.println("Running Monitor Mode");
            expandMonitor();
            return;
        }
        ////System.err.println("Running Normal");
        int i; // next available codeword value
        int z =0; //run count
        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            ////System.err.println(i);
            if(i == L-1 && W!=16){
                ////System.err.println(st.length);
                W++;
                String[] temp = new String[L];
                L = (int) Math.pow(2,W);
                for(int x =0; x<temp.length; x++ ){
                    temp[x]= st[x];
                }
                st = new String[L];
                ////System.err.println(st.length);
                for(int x =0; x<temp.length; x++ ){
                    st[x] = temp[x];
                    ////System.err.println(st[x]);
                }

                ////System.err.println(st.length);

            }
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            //if(s == null) continue;
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
            //z++;
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if (args[0].equals("-")){
            if(args[1].equals("n")){
                compressNormal();
            }
            else if(args[1].equals("r")){
                compressReset();
            }
            else if(args[1].equals("m")){
                compressMonitor();
            }
        }
        else if (args[0].equals("+")){
            expandNormal();
        }
        else throw new IllegalArgumentException("Illegal command line argument");
    }

    public static void compressReset(){
        String input = BinaryStdIn.readString();
        BinaryStdOut.write('r');
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {

            String s = st.longestPrefixOf(input);  // Find max prefix match s.

            //System.out.println("Hello World");
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            if (code == 65536)
            {
                ////System.err.println("Resetting Dict");
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                code = R+1;  // R is codeword for EOF
                W = 9;
                L = 512;
            }
            if( (W < 16) && ( (int)Math.pow(2, W) == code) )
            {
                ////System.err.println("Expanding Dict");
                W++;
                L = (int)Math.pow(2, W);
                st.put(input.substring(0, t + 1), code++);

            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }
    public static void compressMonitor(){
        String input = BinaryStdIn.readString();
        BinaryStdOut.write('m');
        ////System.err.println(input.length());
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {


            String s = st.longestPrefixOf(input);  // Find max prefix match s.

            //System.out.println("Hello World");
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.


            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            if (code == 65536)
            {
                ////System.err.println(input.length() );
                numerator += s.length() * 8;
                denominator += W;
                cRatio = numerator/denominator;
                if(ratio)
                {
                    ////System.err.println(input.length() );
                    ////System.err.println(cRatio);
                    sRatio = cRatio;
                    ratio = false;
                }
                //System.err.println(sRatio/cRatio );
                if(sRatio/cRatio > 1.1) {
                    ////System.err.println(sRatio);
                    //System.err.println("Resetting Dict");
                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++)
                        st.put("" + (char) i, i);
                    code = R + 1;  // R is codeword for EOF
                    W = 9;
                    L = 512;
                    sRatio = 0;
                    cRatio = 0;
                    ratio = true;
                }
            }
            if( (W < 16) && ( (int)Math.pow(2, W) == code) )
            {
                //System.err.println("Expanding Dict");
                W++;
                L = (int)Math.pow(2, W);
                st.put(input.substring(0, t + 1), code++);

            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();

    }
    public static void expandReset()
    {
        boolean toBreak = false;
        String[] st = new String[(int) Math.pow(2,16)];
        int i; // next available codeword value
        if(mode == 0){
            mode = BinaryStdIn.readChar();
        }
        if(mode == 'n'){
            //System.err.println("Running normal Mode");
            expandNormal();
            return;
        }
        else if(mode == 'm'){
            //System.err.println("Running Monitor Mode");
            expandMonitor();
            return;
        }
        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {

            ////System.err.println("Saving " + val);
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);

            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L-1) st[i++] = val + s.charAt(0);

            if(i == L-1 && W < 16)
            {
                //System.err.println("Expanding Dict");
                st[i++] = val + s.charAt(0);
                W++;
                L = (int)Math.pow(2, W);
            }
            val = s;

            if( i == 65535)
            {
                //System.err.println("Resetting Dict");
                W=9;
                L=512 ;
                st = new String[(int)Math.pow(2, 16)];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";

                BinaryStdOut.write(val);

                codeword = BinaryStdIn.readInt(W);
                if (codeword == R) return;           // expanded message is empty string
                val = st[codeword];
            }

        }
        BinaryStdOut.close();
    }
    public static void expandMonitor(){
        boolean toBreak = false;
        String[] st = new String[(int) Math.pow(2,16)];
        int i; // next available codeword value
        if(mode == 0){
            mode = BinaryStdIn.readChar();
        }
        if(mode == 'n'){
            //System.err.println("Running normal Mode");
            expandNormal();
            return;
        }
        else if(mode == 'r'){
            //System.err.println("Running Reset Mode");
            expandReset();
            return;
        }
        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
        st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {

            ////System.err.println("Saving " + val);
            BinaryStdOut.write(val);

            codeword = BinaryStdIn.readInt(W);

            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L-1) st[i++] = val + s.charAt(0);

            if(i == L-1 && W < 16)
            {
                //System.err.println("Expanding Dict");
                st[i++] = val + s.charAt(0);
                W++;
                L = (int)Math.pow(2, W);
            }
            val = s;

            if( i == 65535)
            {
                numerator += val.length() * 8;
                denominator += W;
                cRatio = numerator/denominator;
                ////System.err.println("Dict Full");
                if(ratio)
                {
                    ////System.err.println("Saved ration is: " + cRatio);
                    sRatio = cRatio;
                    ratio = false;
                }
                ////System.err.println(sRatio + "/" + cRatio + "= " + sRatio/cRatio);
                if(sRatio/cRatio > 1.1) {
                    //System.err.println("Resetting Dict");
                    W = 9;
                    L = 512;
                    st = new String[(int) Math.pow(2, 16)];
                    for (i = 0; i < R; i++)
                        st[i] = "" + (char) i;
                    st[i++] = "";

                    BinaryStdOut.write(val);

                    codeword = BinaryStdIn.readInt(W);
                    if (codeword == R) return;           // expanded message is empty string
                    val = st[codeword];
                    sRatio = 0;
                    cRatio = 0;
                    ratio = true;
                }
            }

        }
        BinaryStdOut.close();
    }
}
