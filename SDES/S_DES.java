package S_DES;

import java.util.Scanner;

public class S_DES
{
    //Substitution Boxes
    int s0[][]= {{1,0,3,2},
                 {3,2,1,0},
                 {0,2,1,3},
                 {3,1,3,2}};

    int s1[][]= {{0,1,2,3},
                 {2,0,1,3},
                 {3,0,1,0},
                 {2,1,0,3}};

    int key[]=new int[10];  //Key accepted from user
    int key1[]=new int[8];
    int key2[]=new int[8];

    int P8[]={6,3,7,4,8,5,10,9};
    int P10[]={3,5,2,7,4,10,1,9,8,6};

    int IP[]={2,6,3,1,4,8,5,7};
    int EP[]={4,1,2,3,2,3,4,1};
    int P4[]={2,4,3,1};
    int IP_inv[]={4,1,3,5,7,2,8,6};

    void key_generation()
    {
        Scanner sc=new Scanner(System.in);

        System.out.print("Enter the 10 bit key = ");
        for(int i=0;i<10;i++)
        {
            key[i]=sc.nextInt();
        }

        int temp_key[] =new int[10];

        for(int i=0;i<10;i++)
        {
            temp_key[i] = key[P10[i]-1];
        }

        int left_sub[]=new int[5];
        int right_sub[]=new int[5];

        for(int i=0;i<5;i++)
        {
            left_sub[i]=temp_key[i];
            right_sub[i]=temp_key[i+5];
        }

        int left_shift_1[] = shift(left_sub,1);
        int right_shift_1[] = shift(right_sub,1);

        for(int i=0;i<5;i++)
        {
            temp_key[i]=left_shift_1[i];
            temp_key[i+5]=right_shift_1[i];
        }

        for(int i=0;i<8;i++)
        {
            key1[i]=temp_key[P8[i]-1];
        }

        int left_shift_2[] = shift(left_sub,2);
        int right_shift_2[] = shift(right_sub,2);

        for(int i=0;i<5;i++)
        {
            temp_key[i]=left_shift_2[i];
            temp_key[i+5]=right_shift_2[i];
        }

        for(int i=0;i<8;i++)
        {
            key2[i]=temp_key[P8[i]-1];
        }


        System.out.println("\n======================================");
        System.out.print("\nYour Key 1 = ");
        for (int i=0;i<8;i++)
        {
            System.out.print(key1[i]+ " ");
        }

        System.out.print("\nYour Key 2 = ");
        for (int i=0;i<8;i++)
        {
            System.out.print(key2[i]+ " ");
        }
    }

    //Circular shift cipher
    int[] shift(int arr[],int n)
    {
        while(n>0)
        {
            int temp = arr[0];
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[arr.length - 1] = temp;
            n--;
        }
        return arr;
    }

    int[] encryption(int plaintext[])
    {
        int arr[]=new int[8];

        for(int i=0;i<8;i++)
        {
            arr[i]=plaintext[IP[i]-1];
        }

        int arr1[]=core(arr,key1);


        int[] after_swap = swap(arr1, arr1.length / 2);

        int[] arr2 = core(after_swap, key2);

        int[] ciphertext = new int[8];

        for (int i = 0; i < 8; i++) {
            ciphertext[i] = arr2[IP_inv[i] - 1];
        }

        return ciphertext;

    }

    int[] core(int[] ar,int temp_key[])
    {
        int left[]=new int[4];
        int right[]=new int [4];

        for(int i=0;i<4;i++)
        {
            left[i]=ar[i];
            right[i]=ar[i+4];
        }

        int ep[]=new int[8];

        for(int i=0;i<8;i++)
        {
            ep[i]=right[EP[i]-1];
        }

        for(int i=0;i<8;i++)
        {
            ar[i]=temp_key[i]^ep[i];    //XOR
        }

        int[] l_1 = new int[4];
        int[] r_1 = new int[4];

        for (int i = 0; i < 4; i++) {
            l_1[i] = ar[i];
            r_1[i] = ar[i + 4];
        }

        int row, col, val;

        row=Integer.parseInt(""+l_1[0]+l_1[3],2);
        col=Integer.parseInt(""+l_1[1]+l_1[2],2);
        val=s0[row][col];
        String left_string = dec_binary(val);

        row=Integer.parseInt(""+r_1[0]+r_1[3],2);
        col=Integer.parseInt(""+r_1[1]+r_1[2],2);
        val=s1[row][col];
        String right_string = dec_binary(val);

        int right_sub[]=new int[4];

        for(int i=0;i<2;i++)
        {
            char ch1 = left_string.charAt(i);
            char ch2 = right_string.charAt(i);

            right_sub[i]=Character.getNumericValue(ch1);
            right_sub[i+2]=Character.getNumericValue(ch2);
        }

        int right_P4[] = new int [4];
        for(int i=0;i<4;i++)
        {
            right_P4[i]=right_sub[P4[i]-1];
        }

        for(int i=0;i<4;i++)
        {
            left[i]=left[i] ^ right_P4[i];
        }

        int output[]=new int[8];
        for(int i=0;i<4;i++)
        {
            output[i]=left[i];
            output[i+4]=right[i];
        }

        return output;
    }

    //Convert decimal to binary
    String dec_binary(int val)
    {
        if(val==0)
        {
            return "00";
        }
        else if(val==1)
        {
            return "01";
        }
        else if(val==2)
        {
            return "10";
        }
        else
        {
            return "11";
        }
    }

    int[] swap(int[] array, int n)
    {
        int[] l = new int[n];
        int[] r = new int[n];

        for (int i = 0; i < n; i++) {
            l[i] = array[i];
            r[i] = array[i + n];
        }

        int[] output = new int[2 * n];
        for (int i = 0; i < n; i++) {
            output[i] = r[i];
            output[i + n] = l[i];
        }

        return output;
    }

    int[] decryption(int[] ar)
    {
        int[] arr = new int[8];

        for (int i = 0; i < 8; i++) {
            arr[i] = ar[IP[i] - 1];
        }

        int[] arr1 = core(arr, key2);

        int[] after_swap = swap(arr1, arr1.length / 2);

        int[] arr2 = core(after_swap, key1);

        int[] decrypted = new int[8];

        for (int i = 0; i < 8; i++) {
            decrypted[i] = arr2[IP_inv[i] - 1];
        }

        return decrypted;
    }

    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);

        System.out.print("\nEnter 8 bit Plain Text = ");
        int plaintext[]=new int [8];

        for(int i=0;i<8;i++)
        {
            plaintext[i]=sc.nextInt();
        }

        S_DES obj = new S_DES();
        obj.key_generation();

        System.out.print("\n\nYour plain text is = ");
        for(int i=0;i<8;i++)
        {
            System.out.print(plaintext[i]+" ");
        }

        int ciphertext[]=obj.encryption(plaintext);
        System.out.print("\n\nYour cipher text is = ");
        for(int i=0;i<8;i++)
        {
            System.out.print(ciphertext[i]+" ");
        }

        int decrypted[]=obj.decryption(ciphertext);
        System.out.print("\n\nDecrypted cipher text is = ");
        for(int i=0;i<8;i++)
        {
            System.out.print(decrypted[i]+" ");
        }

        System.out.println("\n======================================");

    }
}
