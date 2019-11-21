import java.util.Properties;
import java.sql.*;
import java.util.Scanner;
import java.util.Date;

public class PittSocial{
        public static int user_id; // global variable so there's no need to search for user's ID everytime

    public static void main(String args[]) throws
            SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "19990406")
        System.out.println("=========================WELCOME TO PITT SOCIAL=========================");
        System.out.println("Here's is the menu, please enter the number of the service you are looking for:");
        System.out.println("1. Login");
        System.out.println("2. Doesn't have an account? Create one");
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
        if(res.equals(pwd)){
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
            System.out.pritnln("10. Search for a friend. ");
            System.out.println("11. Search for three Degress. ");
            System.out.println("12. List the messages in the past months. ");
            System.out.println("13. Log out. ");
            System.out.println("========================================================================");
            Scanner scanner = new Scanner(System. in);
            String input = scanner. nextLine();
        }else{
            System.out.println("Password not matched, sorry");
            System.exit(0);
        }

    }

    private static void createAccount(){

    }

    private static void sendMessageToUser(Connection conn)throws
            SQLException, ClassNotFoundException{
        System.out.println("Please enter the iD of the user you are sending message to: ");
        Scanner scanner = new Scanner(System. in);
        String id = scanner. nextLine();
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();
        Statement st = conn.createStatement();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String query = "INSERT INTO message values (DEFALUT," + user_id +","+ msg + ","+id +",NULL," + formatter.format(date)+ ")";
        ResultSet res = st.executeQuery(query);
    }

    private static void sendMessageToGroup(connection conn)throws 
            SQLException, ClassNotFoundException{
        System.out.println("Please enter the ID of the group you are sending message to: ");
        Scanner scanner = new Scanner(System. in);
        String id = scanner. nextLine();
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();
        Statement st = conn.createStatement();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String query = "INSERT INTO message values (DEFALUT," + user_id +","+ msg + ",NULL," + id + formatter.format(date)+ ")";
        ResultSet res = st.executeQuery(query);

    }

    private static void displayMessages(conenction conn)throws 
            SQLException, ClassNotFoundException{
        Statement st = conn.createStatement();
        String query = "SELECT * FROM messages where touserid = " + user_id;
        ResultSet res = st.executeQuery(query);
        String sender, msg;
        while (res.next()) {
            res.getString();
            sender = res.getString();
            msg = res.getString();
            System.out.println( "From user " + sender + ", cotent: " + msg);
            res.getString();
            res.getString();
            res.getString();
        }
    }

    private static void displayNewMessages(connection conn)throws
            SQLException, ClassNotFoundException{
        Statement st = conn.createStatement();
        String query = "SELECT lastlogin FROM profile where userid = " + user_id;
        ResultSet res = st.executeQuery(query);
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = formatter.format(res);
        query = "SELECT * from messages where timeSent >" + date + " or timeSent = " + date;
    }

    private static void 
}