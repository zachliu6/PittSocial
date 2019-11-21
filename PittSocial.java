import java.util.Properties;
import java.sql.*;
import java.util.Scanner;

public class PittSocial{
        public static int user_id; // global variable so there's no need to search for user's ID everytime

    public static void main(String args[]) throws
            SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "19990406");
        System.out.println("=========================WELCOME TO PITT SOCIAL=========================");
        System.out.println("Here's is the menu, please enter the number of the service you are looking for:");
        System.out.println("1. Login");
        System.out.println("2. Don't have an account? Create one");
        System.out.println("3. Exit");
        System.out.println("========================================================================");
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
        if(res.getString(1).equals(pwd)){
            System.out.println(" WELCOME !");
            System.out.println("========================================================================");
            System.out.println("Here is the menu, please enter the number of the service you are looking for:");
            System.out.println("1. Send friend request.");
            System.out.println("2. Create group.");
            System.out.println("3. Send group join request. ");
            System.out.println("4. Check pending frend requests. ");
            System.out.println("5. Send message to a user. ");
            System.out.println("6. Send message to a group " );
            System.out.println("7. Check received messages ");
            System.out.println("8. Check the newest received messages. ");
            System.out.println("9. List current freinds. ");
            System.out.println("10. Search for a friend. ");
            System.out.println("11. Search for three Degress. ");
            System.out.println("12. List the messages in the past months. ");
            System.out.println("13. Log out. ");
            System.out.println("========================================================================");
            scanner = new Scanner(System. in);
            String input = scanner. nextLine();
        }else{
            System.out.println("Password not matched, sorry");
            System.exit(0);
        }

    }

    private static void createAccount(){

    }
    private static void initiateFriendship(int friendID) throws ClassNotFoundException, SQLException{
    	Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "19990406");
        Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement();
        String query = "SELECT name FROM profile WHERE userID = " + friendID;
        ResultSet res = st.executeQuery(query);
        if(res.next()){
        	System.out.println("Please enter a message for your friend request to " + res.getString(1) + " (200 char max)");
        	Scanner scanner = new Scanner(System. in);
            String input = scanner.nextLine();
        }else{
        	System.out.println("User does not exist");
        	conn.close();
        }
    }

    private static void sendMessageToUser(){
        System.out.println("Please enter the name of the user you are sending message to: ");
        Scanner scanner = new Scanner(System. in);
        String name = scanner. nextLine();
        System.out.println("Please enter the message you want to send: ");
        
    }
}