package RSA;

import java.util.Scanner;

public class RSA
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int p,q;

        System.out.print("Enter the two prime numbers = ");
        p=sc.nextInt();     //first prime number
        q=sc.nextInt();     //second prime number
        int N=p*q;
        int phi_N = (p-1)*(q-1);
        int e;              //encryption key
        int d;              //private key

        while(true)
        {
            System.out.print("\nEnter the encryption key = ");
            e=sc.nextInt();
            if(e>1 && gcd(e,phi_N) == 1)
                break;
            else
                System.out.println("Invalid Key");
        }

        d=getModularMultiplicativeInverse(e, phi_N);
        System.out.println("===============================");
        System.out.println("Public Encryption Key = ");
        System.out.println("e = "+ e);
        System.out.println("N = "+ N);
        System.out.println("phi_N = "+ phi_N);
        System.out.println("===============================");
        System.out.println("Private Encrytion Key = ");
        System.out.println("d = "+ d);
        System.out.println("p = "+ p);
        System.out.println("q = "+ q);
        System.out.println("===============================");
        System.out.print("\nEnter Message = ");
        int msg=sc.nextInt();

        // Encryption
        System.out.println("==========Encryption==========");
        System.out.print("Encrypted Text = ");
        int encrypt = powMod(msg, e, N);
        System.out.println(encrypt);

        // Decryption
        System.out.println("==========Decryption==========");
        System.out.print("Decrypted Text = ");
        int decrypt = powMod(encrypt, d, N);
        System.out.println(decrypt);

    }

    static int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    static int getModularMultiplicativeInverse(int e, int phi_N)
    {
        int q, r1, r2, r, t1, t2, t;
        r1 = phi_N;
        r2 = e % r1;
        t1 = 0;
        t2 = 1;

        while (r2 != 0)
        {
            q = r1 / r2;
            r = r1 % r2;
            t = t1 - t2 * q;

            // swapping
            r1 = r2;
            r2 = r;
            t1 = t2;
            t2 = t;
        }
        return t1;
    }

    static int powMod(int a, int b, int m)
    {
        int ans = a;
        int mul = ans;
        for (int i = 1; i < b; i++)
        {
            ans = (ans * mul) % m;
        }
        return ans;
    }
}
