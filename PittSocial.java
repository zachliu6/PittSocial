import java.util.Properties;
import java.sql.*;
import java.util.Scanner;

public class PittSocial{
    public static void main(String args[]) throws
            SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "19990406");
        System.out.println("===============================WELCOME TO PITT SOCIAL===============================");
        System.out.println("Here's is the menu, please enter the number of the service you are looking for:");
        System.out.println("1. Login");
        System.out.println("2. Doesn't have an account? Create one");
        System.out.println("3. Exit");
        System.out.println("====================================================================================");
        Scanner scanner = new Scanner(System. in);
        String input = scanner. nextLine();
            if(input.equals("1")){
                login();
            }else if(input.equals("2")){
                createAccount();
            }else if(input.equals("3")){
                System.out.println("Have a good day, bye!");
                System.exit(0);
            }else{
                System.out.println("invalid input!");
                System.exit(0);
            }
    }

    private static void login()throws
            SQLException, ClassNotFoundException{
        System.out.println("////// Please enter your email: //////");
        Scanner scanner = new Scanner(System. in);
        String email = scanner.nextLine();
        System.out.println("////// Please enter your password: //////");
        String pwd = scanner.nextLine();
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "19990406");
         Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement();
        String query = "SELECT password FROM profile where email = " + email;
        ResultSet res = st.executeQuery(query);
        if(res.equals(pwd)){
            System.out.println(" WELCOME !");
        }else{
            System.out.println("Password not matched, sorry");
            System.exit(0);
        }

    }

    private static void createAccount(){

    }
}