package SAES;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class S_AES
{

    static int[] plainText = new int[16];
    static int[] key = new int[16];
    static int[] w0 = new int[8];
    static int[] w1 = new int[8];
    static int[] w2 = new int[8];
    static int[] w3 = new int[8];
    static int[] w4 = new int[8];
    static int[] w5 = new int[8];
    static int[] k1 = new int[16];
    static int[] k2 = new int[16];
    static int[] k3 = new int[16];
    static int [] temp = {1,0,0,0,0,0,0,0};
    static int [] temp1 = {0,0,1,1,0,0,0,0};

    static int[] subNibble = new int[8];

    static HashMap<String,String> mp = new HashMap<String,String>();


    public static void main(String[] args) {

        mp.put("0000","1001");
        mp.put("0001","0100");
        mp.put("0010","1010");
        mp.put("0011","1011");
        mp.put("0100","1101");
        mp.put("0101","0001");
        mp.put("0110","1000");
        mp.put("0111","0101");
        mp.put("1000","0110");
        mp.put("1001","0010");
        mp.put("1010","0000");
        mp.put("1011","0011");
        mp.put("1100","1100");
        mp.put("1101","1110");
        mp.put("1110","1111");
        mp.put("1111","0111");

        Scanner s =new Scanner(System.in);

        System.out.println("Enter 16 bit Plain text : ");
        for(int i =0;i<16;i++){
            plainText[i]=s.nextInt();
        }

        System.out.println("Enter 16 bit key : ");
        for(int i =0;i<16;i++){
            key[i]=s.nextInt();
        }

        for(int i=0;i<4;i++){
            w0[i]=key[i];
            w0[i+4]=key[i+4];
            w1[i]=key[i+8];
            w1[i+4]=key[i+12];
        }
//        System.out.print("W0 : ");
//        Print(w0);
//        System.out.print("W1 : ");
//        Print(w1);
        k1 = key;
        w2 = Xor(w0,temp)   ;
        subNibble = sbox(rotNIb(w1),0);
//        Print(subNibble);
        w2 = Xor(w2, subNibble);
//        System.out.print("W2 : ");
//        Print(w2);


        w3 = Xor(w2,w1);
//        System.out.print("W3 : ");
//        Print(w3);

        w4 = Xor(w2,temp1);


        subNibble = sbox(rotNIb(w3),0);
        w4 = Xor(w4,subNibble);
//        System.out.print("W4 : ");
//        Print(w4);

        w5 = Xor(w4,w3);
//        System.out.print("W5 : ");
//        Print(w5);
        for(int i=0;i<8;i++){
            k2[i] = w2[i];
            k2[i+8]=w3[i];
            k3[i] = w4[i];
            k3[i+8] = w5[i];
        }
        System.out.print("k1 : ");
        Print(k1);
        System.out.print("k2 : ");
        Print(k2);
        System.out.print("k3 : ");
        Print(k3);
        int[] cipherText =new int[16];
        cipherText = encryption();
        System.out.print("Cipher Text :");
        Print(cipherText);
        decrpytion(cipherText);
    }
    public static void decrpytion(int[] cipherText){
        cipherText = Xor(cipherText , k3);
        cipherText =AddRound(cipherText,k2);

        int[] two ={0,0,1,0};
        int[] nine= {1,0,0,1};
        cipherText = mixcol(cipherText,nine,two);
//        System.out.print("MixCol Result:");
//        Print(cipherText);
        plainText =AddRound(cipherText,k1);
        System.out.print("PlainText:");
        Print(plainText);
    }

    public static int[] encryption(){
        int[] one ={0,0,0,1};
        int[] four = {0,1,0,0};
        plainText = roundFunc(plainText,k1);
        plainText =mixcol(plainText,one,four);
//        System.out.print("MixCol Result:");
//        Print(plainText);

        plainText = roundFunc(plainText,k2);
        plainText=Xor(plainText,k3);
        return plainText;
    }
    public static int[] AddRound(int[] cipherText , int[] k){
        cipherText = swap2n4(cipherText);
//        System.out.print("Swap Nibble: ");
//
//        Print(cipherText);
        cipherText = sbox(cipherText,1);
//        System.out.print("Inverse Sbox:");
//        Print(cipherText);
        cipherText=Xor(cipherText,k);

        return cipherText;
    }
    public static int[] roundFunc(int[] plainText,int[] k){
        plainText=Xor(plainText,k);

        plainText=sbox(plainText,0);
//        System.out.print("S Box: ");
//        Print(plainText);
        plainText=swap2n4(plainText);
//        System.out.print("Swap Nibble: ");
//
//        Print(plainText);
        return plainText;
    }
    public static int[]  mixcol(int[]a, int[]x , int[]y) {
        int[][] Me = {{1, 4}, {4, 1}};
        int[] s00 = new int[4];
        int[] s01 = new int[4];
        int[] s10 = new int[4];
        int[] s11 = new int[4];
        int[] S00 = new int[4];
        int[] S01 = new int[4];
        int[] S10 = new int[4];
        int[] S11 = new int[4];
        for (int i = 0; i < 4; i++) {
            s00[i] = a[i];
            s10[i] = a[i + 4];
            s01[i] = a[i + 8];
            s11[i] = a[i + 12];
        }

        int [] f = {0,1,0,0};
        S00 = Xor(multiply(s00,x),multiply(s10,y));
        S01 = Xor(multiply(s01,x),multiply(s11,y));
        S10 = Xor(multiply(s00,y),multiply(s10,x));
        S11 = Xor(multiply(s01,y),multiply(s11,x));

        for(int i =0;i<4;i++){
            a[i]=S00[i];
            a[i+4]=S10[i];
            a[i+8]=S01[i];
            a[i+12]=S11[i];
        }
        return a;
    }
    public static int[] multiply(int A[] , int B[])
    {

        int m=A.length;
        int n = B.length;
        int[] prod = new int[7];
        // Initialize the product polynomial
        for (int i = 0; i < m + n - 1; i++)
        {
            prod[i] = 0;
        }

        // Multiply two polynomials term by term
        // Take ever term of first polynomial
        for (int i = 0; i < m; i++)
        {
            // Multiply the current term of first polynomial
            // with every term of second polynomial.
            for (int j = 0; j < n; j++)
            {
                prod[i + j] += A[i] * B[j];
            }
        }
//            System.out.print("Main ");
//            Print(A);
//            Print(B);
//            Print(prod);
//            for(int i:prod)
//            System.out.print(i);
//            for(int i=0;i<prod.length;i++){
//                if(prod[i]>1)prod[i]=1;
//            }
//            Print(prod);
        boolean check =false;
        for (int i=0;i<prod.length-4;i++ ){
            if(prod[i]==1) check=true;
        }

        //polynomial reduction
        if(check) {
            int[] temp ={1,0,0,1,1};

            if(prod[0]==1){
                for (int i = 0; i < temp.length; i++) {
                    if(prod[i]==2 && temp[i]==1) prod[i]=1;
                    else
                        prod[i ] = prod[i ] ^ temp[i];                    }
            }
//                Print(prod);
            if(prod[1]==1) {
                for (int i = 0; i < temp.length; i++) {
                    if(prod[i+1]==2 && temp[i]==1)
                        prod[i+1]=1;
                    else
                        prod[i + 1] = prod[i + 1] ^ temp[i];
                }
            }
//                Print(prod);
            if(prod[2]==1) {
                for (int i = 0; i < temp.length; i++) {
                    if(prod[i+2]==2 && temp[i]==1) prod[i+2]=1;

                    else
                        prod[i + 2] = prod[i + 2] ^ temp[i];                    }
            }
//                Print(prod);
//                for (int i : prod)
//                    System.out.print(i);
        }
        int s[] = new int[4];
        for (int i = 0; i < 4; i++) {
            s[i] = prod[prod.length - 4 + i];
        }



        return s;
    }





    public static int[] swap2n4(int[] a){
        int temp= 0;
        for(int i=0;i<4;i++){
            temp=a[i+4];
            a[i+4]=a[i+12];
            a[i+12]=temp;
        }
        return a;

    }
    public static int[] rotNIb(int[]a){
        int[] temp = new int[8];
        for(int i =0;i<4;i++){
            temp[i]=a[i+4];
            temp[i+4]=a[i];
        }
        return temp;
    }
    public static int[] sbox(int[] a , int check){

        int temp[]= new int[a.length];
        int k=0;
        for(int i=0;i<a.length;i+=4){
            int l =((a[i]*1000)+(a[i+1]*100)+(a[i+2]*10)+a[i+3]);
            String  l1 = String.valueOf(l);
            if(l<1000 && l>=100){
                l1 = "0"+String.valueOf(l);
            }
            else if(l<100 && l>=10){
                l1 = "00"+String.valueOf(l);
            }
            else if(l<10){
                l1 = "000"+String.valueOf(l);
            }
            //System.out.println(l1);
            if(check==0)
                l1=mp.get(l1);
            else if(check==1) {
                for (Map.Entry<String, String> entry : mp.entrySet()) {
                    if (l1.equals(entry.getValue())) {
                        l1=entry.getKey();
//                        System.out.println(l1);
                        break;
                    }
                }
            }
//            System.out.println(l1);
            k=i;
            for(int j=0;j<4;j++){

                if(l1.charAt(j)=='0'){
                    temp[k+j]=0;

                }
                else if(l1.charAt(j)=='1'){
                    temp[k+j]=1;
                }

            }
        }
        return temp;
    }
    public static int[]Xor(int[]a , int[] b){
        int[] xor = new int[a.length];
        for(int i=0;i<a.length;i++){
            xor[i] = a[i]^b[i];
        }
        return xor;

    }
    public static void Print(int[] a){
        for (int i=0;i<a.length;i++){
            if((i+1)%4==0)
                System.out.print(a[i]+" ");
            else
                System.out.print(a[i]);

        }
        System.out.println();
    }
}