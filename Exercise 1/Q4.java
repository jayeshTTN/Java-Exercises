import java.util.Scanner;
public class Q4
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the row :");
        int m = sc.nextInt();
        System.out.println("Enter the column :");
        int n = sc.nextInt();
        int [][] arr = new int[m][n];
        System.out.println("Enter the elements :");
        for(int i=0 ;i<m ; i++){
            for(int j=0 ; j<n ; j++){
                arr[i][j] = sc.nextInt();
            }
        }
        int sum =0;
        for(int i=0 ; i<m ; i++){
            for(int j=0 ; j<n ; j++){
                sum = sum + arr[i][j];
            }
            System.out.println("Sum of the row " + i + " = " + sum);
            sum =0;
        }
        sum =0;
        for(int j=0 ; j<n; j++){
            for(int i=0 ; i<m ; i++){
                sum = sum + arr[i][j];
            }
            System.out.println("Sum of the column " + j + " = " + sum);
            sum =0;
        }
    }
}

