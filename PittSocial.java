import java.util.Properties;
import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String query = "SELECT password, userID FROM profile where email = '" + email + "' AND password = '" + pwd + "'";
        ResultSet res = st.executeQuery(query);
        if(res.next()){
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
                }else if(input.equals("3")){
                	initiateAddingGroup(conn, scanner);
                }else if(input.equals("4")){
                	confirmRequests(conn, scanner);
                }else if(input.equals("5")){
                    sendMessageToUser();
                }else if(input.equals("6")){
                    sendMessageToGroup();
                }else if(input.equals("7")){
                    displayMessages();
                }else if(input.equals("8")){
                    displayNewMessages();
                }else if(input.equals("9")){
                    displayFriends();
                }else if(input.equals("10")){
                    //searchForUser();
                }else if(input.equals("11")){
                    threeFriends();
                }else if(input.equals("12")){
                    topMessages();
                }else if(input.equals("13")){
                    logout();
                }
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

    private static void initiateAddingGroup(Connection conn, Scanner scanner) throws SQLException{
    	PreparedStatement stmt = conn.prepareStatement("SELECT name FROM groupInfo WHERE gid = ?");
    	System.out.println("Please enter the gID of the group you would like to join");
    	int gid = Integer.parseInt(scanner.nextLine());
    	stmt.setInt(1, gid);
    	ResultSet rs;
    	try{
    		rs = stmt.executeQuery();
    	}catch(SQLException e){
    		System.out.println("SQL Error: Failed to retreive group");
    		scanner.close();
            return;
    	}
    	if(rs.next()){
    		System.out.println("Enter a message for your group request");
    		stmt = conn.prepareStatement("INSERT INTO pendingGroupMember VALUES(" + gid + ", " + user_id + ", ?)");
    		String input = scanner.nextLine();
    		stmt.setString(1, input.substring(0, Math.min(input.length(), 200)));
    		try{
    			stmt.execute();
    		}catch(SQLException e1){
    			System.out.println("SQL Error: Group request already pending");
    			return;
    		}
    		System.out.println("Request sent to " + rs.getString(1) + " with message: " + input.substring(0, Math.min(input.length(), 200)));
    	}else{
    		System.out.println("No group with that ID");
    		return;
    	}
    }
    
    private static void confirmRequests(Connection conn, Scanner scanner){
    	System.out.println("==Friend Requests==");
    	PreparedStatement stmt = conn.prepareStatement("SELECT name, message FROM (pendingfriend full outer join profile p on pendingfriend.fromid = p.userid) WHERE toid = " + user_id);
    	ResultSet res;
    	try{
            res = stmt.executeQuery();
        }catch (SQLException e1) {
            System.out.println("SQL Error retreiving friend requests");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            return;
        }
    	int i = 1;
    	while(res.next()){
    		System.out.println(i + ") " + res.getString(1) + ": " + res.getString(2));
    		i++;
    	}
    	System.out.println("==Group Requests==");
    	stmt = conn.prepareStatement("SELECT name, message FROM pendinggroupmember pg natural join (SELECT gid FROM groupmember WHERE role = 'manager' AND userid = " + user_id + ") AS pgg natural join profile"); 
    	ResultSet gres;
    	try{
            gres = stmt.executeQuery();
        }catch (SQLException e1) {
            System.out.println("SQL Error retreiving friend requests");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            return;
        }
    	while(gres.next()){
    		System.out.println(i + ") " + res.getString(1) + ": " + res.getString(2));
    		i++;
    	}
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
        PreparedStatement st = conn.prepareStatement("INSERT INTO message values (DEFALUT, " + user_id +", ?,?,NULL," + formatter.format(date)+ ")");
        Scanner scanner = new Scanner(System. in);
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();        
        st.setString(1, msg);
        System.out.println("Please enter the iD of the user you are sending message to: ");
        String id = scanner. nextLine();
        st.setString(2, id);
        try{
            ResultSet res = st.executeQuery();
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
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        PreparedStatement st = conn.prepareStatement("INSERT INTO message values (DEFALUT," + user_id +",?,NULL,?,"+ formatter.format(date)+ ")");
        System.out.println("Please enter the message you want to send: ");
        String msg = scanner. nextLine();
        st.setString(1,msg);
        System.out.println("Please enter the ID of the group you are sending message to: ");
        String id = scanner. nextLine();
        st.setString(2,id);
        try{
            ResultSet res = st.executeQuery();
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
            int sender;
            String msg;
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
            return;
        } 
    }

    private static void displayNewMessages()throws
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
        date = res.getDate("lastlogin");
        Statement st2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        query = "SELECT * from messages where timeSent >" + date + " or timeSent = " + date + " AND touserid =" + user_id;
        try{
            ResultSet res2 = st2.executeQuery(query);
            int sender;
            String msg;
            while (res2.next()) {
                sender = res2.getInt(2);
                msg = res2.getString(3);
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
            return;
        } 
    }

    private static void displayFriends()throws
            SQLException, ClassNotFoundException{
                Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        String query = "SELECT * FROM friend where userid1 = " + user_id + "OR userid2 = " + user_id;
        try{
            ResultSet res = st.executeQuery(query);
            int id1, id2;
            String name, email;
            System.out.println("Here is the list of your friends: ");
            while (res.next()) {
                id1 = res.getInt(1);
                id2 = res.getInt(2);
                    if(id1 == user_id){
                           System.out.println("User ID: " + id2 );
                    }else{
                        System.out.println("User ID: " + id1 );
                    }
            }
            while(true){
                PreparedStatement st2 = conn.prepareStatement("SELECT name, email FROM frofile where userid = ?");
                System.out.println("Please enter the ID of the user's profile you'd like to see: ");
                Scanner scan = new Scanner(System.in);
                int input = scan.nextInt();
                if(input==0){
                    return;
                }else{
                            st2.setInt(1,input);
                            ResultSet res2 = st2.executeQuery();
                            while(res2.next()){
                                name = res2.getString(2);
                                email = res2.getString(3);
                                System.out.println("Friend's name is " + name + " and email is " + email);
                            }
                }
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
            return;
        } 
    }

    private static void threeFriends()
    {   
        int friend2;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the number of the User ID");
        int targetID = scan.nextInt();
        boolean found = false;
        List<Integer> path = new ArrayList<>();
        path.add(user_id);
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        String query = "SELECT * from friend where userID1 = " + String.valueOf(user_id) + ";";
        ResultSet rs1 = stmt.executeQuery(query);
        while(rs1.next() && !found)
        {
            int friend = rs1.getInt(2);
            if(friend == targetID)
            {
                found = true;
                path.add(friend);
            }
            if(!found)
            {
                Statement stmt2 = conn.createStatement();
                String query1 = "SELECT * from friend where userID1 = " + String.valueOf(friend) + ";";
                ResultSet rs2 = stmt2.executeQuery(query1);
                while(rs2.next() && !found)
                {
                    friend2 = rs2.getInt(2);
                    if(friend2 == targetID)
                    {
                        found = true;
                        path.add(friend);
                        path.add(friend2);
                    }
                
                if(!found)
                {
                    Statement stmt3 = conn.createStatement();
                    String query2 = "SELECT * from friend where userID1 = " + String.valueOf(friend2) + ";";
                    ResultSet rs3 = stmt3.executeQuery(query2);
                    while(rs3.next() && !found)
                    {
                        int friend3 = rs3.getInt(2);
                        if(friend3 == targetID)
                        {
                            found = true;
                            path.add(friend);
                            path.add(friend2);
                            path.add(friend3);
                        }
                    }
                    }
                }
            }

        }
        if(path.size() > 1)
        {
            for (int i = 0; i < path.size(); i++) {
                System.out.println(path.get(i));
            }
        }
        else
        {
            System.out.println("No path found between those friends.");
        }

    }

    private static void topMessages()
    {   
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the number of user: ");
        int k = scan.nextInt();
        String numUsers = String.valueOf(k);
        System.out.println("Please enter the number of message: ");
        int x = scan.nextInt();
        String numMessages = String.valueOf(x);
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        String query = "SELECT fromID, COUNT(fromID) from messageInfo where toUserID = " + String.valueOf(user_id) +
                " group by fromID order by count(fromID) desc limit " + numUsers + ";";
        String query1 = "SELECT count(*) from messageInfo where timeSent > (timeSent::time - INTERVAL '" + numMessages + " month')::timestamp;";
        ResultSet rs1 = stmt.executeQuery(query);
        ResultSet rs2 = stmt.executeQuery(query1);
        System.out.println("Top users: ");
        while(rs1.next())
        {
            String user = rs1.getString(1);
            System.out.println(user);
        }
        int messages = rs2.getInt(1);
        System.out.println("Number of messages in the past " + numMessages + "months: ");
        System.out.println(messages);
    }

    private static void logout()
    {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        String query = "UPDATE profile set lastlogin = CURRENT_TIMESTAMP where userID = " + String.valueOf(user_id) + ";";
        stmt.executeQuery(query);
        home();
    }

    private static void dropUser()
    {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM profile where userID = " + String.valueOf(user_id) + ";";
        String query1 = "DELETE FROM groupMember where userID = " + String.valueOf(user_id) + ";";
        String query2 = "DELETE FROM messageInfo where fromID = " + String.valueOf(user_id) + " or toUserID = " + String.valueOf(user_id) + ";";
        stmt.executeQuery(query1);
        stmt.executeQuery(query2);
        logout();
    }

    private static void exit()
    {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");
        Connection conn = DriverManager.getConnection(url, props);
        Statement stmt = conn.createStatement();
        String query = "UPDATE profile set lastlogin = CURRENT_TIMESTAMP where userID = " + String.valueOf(user_id) + ";";
        stmt.executeQuery(query);
        System.exit(0);
    }

}