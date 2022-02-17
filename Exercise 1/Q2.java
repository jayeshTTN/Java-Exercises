import java.util.Scanner;
public class Q2{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        System.out.println("Enter the String :");
        String str1 = sc.nextLine();
        while(!str1.equals("XDONE")){
            str.append(str1);
            str.append(" ");
            str1 = sc.nextLine();
        }
        System.out.println(str);
    }
}
