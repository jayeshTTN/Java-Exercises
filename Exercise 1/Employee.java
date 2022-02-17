import java.util.Scanner;
public class Employee
{
    String firstname,lastname,designation;
    int age;
    Employee(){
        this.firstname = "firstname";
        this.lastname = "lastname";
        this.age =0;
        this.designation="designation";
    }
    Employee(String firstname,String lastname,int age,String designation){
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.designation = designation;
    }
    public void setfirstname(String firstname){
        this.firstname = firstname;
    }
    public void setlastname(String lastname){
        this.lastname = lastname;
    }
    public void setage(int age){
        this.age = age;
    }
    public void setdesignation(String designation){
        this.designation = designation;
    }
    public String toString(){
        return firstname+" " +lastname+ " age is " + age +". and the their designation is " + designation;
    }
    
    public static void main(String[] args) {
        Employee E1 = new Employee();
	System.out.println("Before parameter are set :");
        System.out.println(E1.toString());
        E1.setfirstname("Jayesh");
        E1.setlastname("Gupta");
        E1.setage(21);
        E1.setdesignation("JVM");
	System.out.println("After parameter are set :");
        System.out.println(E1.toString());
        Employee E2 = new Employee("Lakshya","Rawat",22,"MEAN");
        System.out.println(E2.toString());
    }
}

