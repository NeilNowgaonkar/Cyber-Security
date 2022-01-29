package Cyber_Security.Hill_Cipher;

import java.util.Scanner;

public class Hill_Cipher
{
    private static int size_matrix=2;

    static void generate_key_matrix(int key_matrix[][])
    {
        Scanner sc=new Scanner(System.in);

        System.out.println("Enter the key string");
        String key=sc.nextLine();

        int x=0;
        for(int i=0;i<key_matrix.length;i++)
        {
            for(int j=0;j<key_matrix[0].length;j++)
            {
                //Mod 65 because ASCII of a = 97
                key_matrix[i][j]=(key.charAt(x))%97;
                x++;
                //System.out.println("Checkpoint 1");
            }
        }

        System.out.println("\nKey Matrix:-");

        for(int i=0;i<size_matrix;i++)
        {
            for(int j=0;j<size_matrix;j++)
            {
                System.out.print(key_matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static String encryption(int key_matrix[][])
    {
        String s;
        String ans="";
        int k=0;

        Scanner sc=new Scanner(System.in);

        //Message
        System.out.println("Enter the message (prefer even characters) to be encrypted = ");
        s=sc.nextLine();

        if(s.length() %2==1)
        {
            s=s+"q";
        }

        while(k<s.length())
        {
            for(int i=0;i< size_matrix;i++)
            {
                int sum =0;
                int temp=k;
                for(int j=0;j<size_matrix;j++)
                {
                    sum=sum+((s.charAt(temp++) - 'a') * key_matrix[j][i]);
                }
                sum=sum%26;
                ans=ans+(char)(sum+'a');
            }
            k=k+size_matrix;
        }
        return ans;
    }

    static void inverse_key_matrix(int key_matrix[][])
    {
        int temp;

        //Swapping 0,0 and 1,1
        temp=key_matrix[0][0];
        key_matrix[0][0]=key_matrix[1][1];
        key_matrix[1][1]=temp;

        //Swapping 1,0 and 0,1
        temp=key_matrix[0][1];
        key_matrix[0][1]=(-1)*key_matrix[1][0];
        key_matrix[1][0]=(-1)*temp;
    }

    static int get_modular_inverse(int no)
    {
        int q,r1,r2,r,t1,t2,t;
        r1 = 26;
        r2 = no%26;
        t1 = 0;
        t2 = 1;

        while(r2!=0)
        {
            q = r1/r2;
            r = r1%r2;
            t = t1 - t2*q;

            // swapping
            r1 = r2;
            r2 = r;
            t1 = t2;
            t2 = t;
        }

        return t1;
    }

    static String decryption(int key_matrix[][], String encrypt)
    {
        String ans="";
        int k=0;
        int modular_inverse_value;
        int determinant = ((key_matrix[0][0]* key_matrix[1][1])-(key_matrix[1][0]* key_matrix[0][1]));

        //Inverse Matrix
        inverse_key_matrix(key_matrix);

        System.out.println("Inverse Key matrix :-");
        for (int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(key_matrix[i][j]+" ");
            }
            System.out.println();
        }

        //Modular inverse
        modular_inverse_value = get_modular_inverse(determinant);

        System.out.println("\nModular Inverse Value = "+modular_inverse_value);
        System.out.println();

        while (k < encrypt.length())
        {
            for (int i = 0; i < size_matrix; i++)
            {
                int sum = 0;
                int temp = k;
                for (int j = 0; j < size_matrix; j++)
                {
                    sum += ((modular_inverse_value) * (encrypt.charAt(temp++) - 'a')* key_matrix[i][j]);
                }
                sum %= 26;
                if(sum < 0)
                {
                    sum+=26;
                }
                sum %= 26;
                ans += (char)(sum + 'a');
            }
            k += size_matrix;
        }

        return ans;
    }

    public static void main(String args[])
    {
        int[][] key_matrix=new int[size_matrix][size_matrix];
        String encrypted_ans = null, decrypted_ans;

        Scanner sc=new Scanner(System.in);

        int choice=0;

        while(choice!=3)
        {
            System.out.println("==================================");
            System.out.println("Hill Cipher Program for (2X2)");
            System.out.println("==================================");
            System.out.println("1.) Encrypt Text");
            System.out.println("2.) Decrypt Text");
            System.out.println("3.) Exit");
            System.out.print("\n\nEnter your choice = ");
            choice = sc.nextInt();
            System.out.println();

            String text = "";

            switch (choice) {
                case 1:
                    //Input the key matrix
                    generate_key_matrix(key_matrix);
                    //Encryption of Text
                    encrypted_ans=encryption(key_matrix);
                    
                    System.out.println("\nEncrypted Text = "+encrypted_ans);
                    System.out.println();
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Enter text to be decrypted = ");
                    encrypted_ans=sc.nextLine();
                    generate_key_matrix(key_matrix);
                    System.out.println();
                    decrypted_ans=decryption(key_matrix,encrypted_ans);
                    System.out.println("Decrypted Text = "+decrypted_ans);
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Program Exited");
                    System.exit(0);
                default:
                    System.out.println("Incorrect Choice");
            }
        }
    }
}