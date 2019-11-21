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
        props.setProperty("password", "19990406");
        home();
    }
    private static void home() throws ClassNotFoundException, SQLException{
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
        props.setProperty("password", "password");
         Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement();
        String query = "SELECT password, userID FROM profile where email = '" + email + "'";
        ResultSet res = st.executeQuery(query);
        res.next();
        if(res.getString(1).equals(pwd)){
            user_id = res.getInt(2);    
            while(1 == 1){
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
                String input = scanner.nextLine();
                if(input.equals("1")){
                    System.out.println("Enter the ID of the user you would like to send a request to");
                    input = scanner.nextLine();
                    initiateFriendship(Integer.parseInt(input));
                }else if(input.equals("2")){
                    createGroup();
                }else if(input.equals("5")){
                    sendMessageToUser();
                }else if(input.equals("6")){
                    sendMessageToGroup();
                }else if(input.equals("7")){
                    displayMessages();
                }else if(input.equals("8")){
                    displayNewMessages();
                }else if
            }
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
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement();
        String query = "SELECT name FROM profile WHERE userID = " + friendID;
        ResultSet res = st.executeQuery(query);
        if(res.next()){
            System.out.println("Please enter a message for your friend request to " + res.getString(1) + " (200 char max)");
            Scanner scanner = new Scanner(System. in);
            String input = scanner.nextLine();
            input = input.substring(0, Math.min(input.length(), 200));//shortens message if more than 200 chars
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO pendingFriend VALUES (" + user_id + ", " + friendID + ", ?)");
            stmt.setString(1, input);
            try{
                stmt.execute();
            }catch (SQLException e1) {
                System.out.println("Friend request already pending");
                conn.close();
                return;
            }
        }else{
            System.out.println("User does not exist");
            conn.close();
        }
    }

    private static void createGroup() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO groupInfo(name, description, size) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        System.out.println("Please enter a name for your group");
        Scanner scanner = new Scanner(System. in);
        String input = scanner.nextLine();
        stmt.setString(1, input);
        System.out.println("Enter a description for your group");
        input = scanner.nextLine();
        stmt.setString(2, input);
        System.out.println("Enter a max size for your group");
        input = scanner.nextLine();
        stmt.setInt(3, Integer.parseInt(input));
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("Group with that name already exists");
            conn.close();
            scanner.close();
            return;
        }
        //get the autogenerated gid of the new group
        int gid = -1;
        try (ResultSet newGid = stmt.getGeneratedKeys()) {
            if (newGid.next()) {
                gid = newGid.getInt(1);
            }
            else {
                System.out.println("Creating group failed, no ID obtained.");
                conn.close();
                scanner.close();
                return;
            }
        }
        //add the current user as a manager
        stmt = conn.prepareStatement("INSERT INTO groupmember VALUES(" + gid + ", " + user_id + ", 'manager')");
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("Failed to add to user as group manager");
            conn.close();
            scanner.close();
            return;
        }  
        System.out.println("Group created");
    }

    private static void sendMessageToUser()throws
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        PreparedStatement st = conn.prepareStatement();
        String query = "INSERT INTO message values (DEFALUT, " + user_id +", ?,?,NULL," + formatter.format(date)+ ")";
        System.out.println("Please enter the iD of the user you are sending message to: ");
        Scanner scanner = new Scanner(System. in);
        String id = scanner. nextLine();
        st.setString(1, input);
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();
        st.setString(2, input);
        try{
            ResultSet res = st.executeQuery(query);
        }catch (SQLException e1) {
            System.out.println("SQL Error");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.close();
            scanner.close();
            return;
        }
    }

    private static void sendMessageToGroup()throws 
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        System.out.println("Please enter the ID of the group you are sending message to: ");
        Scanner scanner = new Scanner(System. in);
        String id = scanner. nextLine();
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();
        Statement st = conn.createStatement();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String query = "INSERT INTO message values (DEFALUT," + user_id +","+ msg + ",NULL," + id + formatter.format(date)+ ")";
        try{
            ResultSet res = st.executeQuery(query);
        }catch (SQLException e1) {
            System.out.println("SQL Error");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.close();
            scanner.close();
            return;
        }

    }

    private static void displayMessages()throws 
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT * FROM messages where touserid = " + user_id;
        try{
            ResultSet res = st.executeQuery(query);
            String sender, msg;
            while (res.next()) {
                sender = res.getInt(2);
                msg = res.getString(3);
                System.out.println( "From user " + sender + ", cotent: " + msg);
            }
       }catch (SQLException e1) {
            System.out.println("SQL Error");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.close();
            scanner.close();
            return;
        } 
    }

    private static void displayNewMessages(connection conn)throws
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);        
        Statement st = conn.createStatement();
        String query = "SELECT lastlogin FROM profile where userid = " + user_id;
        ResultSet res = st.executeQuery(query);
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = formatter.format(res);
        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        query = "SELECT * from messages where timeSent >" + date + " or timeSent = " + date + " AND touserid =" + user_id;
        try{
            ResultSet res = st.executeQuery(query);
            String sender, msg;
            while (res.next()) {
                sender = res.getInt(2);
                msg = res.getString(3);
                System.out.println( "From user " + sender + ", cotent: " + msg);
            }
       }catch (SQLException e1) {
            System.out.println("SQL Error");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.close();
            scanner.close();
            return;
        } 
    }

    private static void 
}