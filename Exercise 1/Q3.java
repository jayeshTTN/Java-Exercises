import java.util.Scanner;
public class Q3{
    public final static float PI = 3.14f;
    public static void Area(int a){
        System.out.print(PI*a*a);
    }
    public static void Circumference(int a){
        System.out.print(PI*a*2);
    }
    public static void main(String[] args){
        System.out.println("1.Calculate Area of Circle");
        System.out.println("2.Calculate Circumference of a Circle");
        System.out.println("3.Exit");
        Scanner sc = new Scanner(System.in);
        int b = sc.nextInt();
        int r=0;
        switch(b){
            case 1:
                System.out.print("Enter the radius :");
                r = sc.nextInt();
                Area(r);
		System.out.println();
                break;
            case 2:
                System.out.print("Enter the radius :");
                r = sc.nextInt();
                Circumference(r);
		System.out.println();
                break;
            case 3:
                break;
            default:System.out.print("Invalid Output"); break;
    }
  } 
}

