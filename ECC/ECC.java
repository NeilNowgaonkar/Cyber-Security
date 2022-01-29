package ECC;
import java.util.Scanner;

public class ECC
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);

        int p,x1,y1;    //Related to Elliptical Curve Equation
        System.out.println("For Elliptical Curve Equation Ep(x1,y1):-");
        System.out.print("Enter the prime number P = ");
        p=sc.nextInt();
        System.out.print("Enter the coordinates (x1,y1) = ");
        x1=sc.nextInt();
        y1=sc.nextInt();

        System.out.println("=================================================================");
        int x2,y2;  //Base point
        System.out.print("Enter the coordinates B(x2,y2) for base point = ");
        x2=sc.nextInt();
        y2=sc.nextInt();

        System.out.println("=================================================================");
        int m1,m2;  //Message point
        System.out.print("Enter the coordinates Pm(m1,m2) for message point = ");
        m1=sc.nextInt();
        m2=sc.nextInt();

        System.out.println("=================================================================");
        int min=1,max=p-1;  //  Min and max value to generate random values

        //Person 1
        int a = (int)Math.floor(Math.random()*(max-min+1)+min);     //Private key of person 1
        System.out.println("Private key of Person 1 = "+a);

        int x3=a*x2,y3=a*y2;  //Coordinates of public key
        System.out.println("Public key of Person 1 = ("+x3+","+y3+")");

        int b = (int)Math.floor(Math.random()*(max-min+1)+min);     //Private key of person 2
        System.out.println("\nPrivate key of Person 2 = "+b);

        int x4=b*x2,y4=b*y2;  //Coordinates of public key
        System.out.println("Public key of Person 2 = ("+x4+","+y4+")");

        System.out.println("=================================================================");
        int k = (int)Math.floor(Math.random()*(max-min+1)+min); //Another random integer
        System.out.println("\nRandom Key k = "+k);
        //Cipher Text Pc=[(kB),(Pm+kPb)]
        int c1,c2;  //first pair of cipher text
        c1=k*x2;
        c2=k*y2;

        int c3,c4;  //second pair of cipher text
        c3=m1+(k*x4);
        c4=m2+(k*y4);

        System.out.println("Cipher Text = Pc = [("+c1+","+c2+") ,("+c3+","+c4+")]");
        System.out.println("=================================================================");

        int x5,y5;  //To calc product of the first point from Pc and his private key of person 2
        x5=b*c1;
        y5=b*c2;

        int d1,d2;  //Decrypted points (Pm + kPb) – [b(kB)]
        d1=c3-x5;
        d2=c4-y5;

        System.out.println("Decrypted Text = Pm = ("+d1+","+d2+")");
        System.out.println("=================================================================");


    }
}
