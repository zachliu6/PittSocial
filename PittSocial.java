import java.util.Properties;
import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PittSocial{
        public static int user_id; // global variable so there's no need to search for user's ID everytime
        public static final String password = "19990406";
        public static Connection conn;
        public static Statement st;
        public static PreparedStatement stmt;

    public static void main(String args[]) throws
            SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        home();
    }

    private static void home() throws ClassNotFoundException, SQLException{
    while(true){
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
                createUser();
            }else if(input.equals("3")){
                System.out.println("Have a good day, bye!");
                System.exit(0);
            }else{
                System.out.println("invalid input!");
            }
    }
    }

    private static void login()throws
            SQLException, ClassNotFoundException{
        while(true){
        System.out.println("////// Please enter your email: //////");
        Scanner scanner = new Scanner(System. in);
        String email = scanner.nextLine();
        System.out.println("////// Please enter your password: //////");
        String pwd = scanner.nextLine();
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
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
                    String inputID = scanner.nextLine();
                    System.out.println("Please enter a message for your friend request to " + inputID + " (200 char max)");
                    String inputMessage = scanner.nextLine();
                    inputMessage = inputMessage.substring(0, Math.min(input.length(), 200));//shortens message if more than 200 chars
                    initiateFriendship(Integer.parseInt(inputID), inputMessage);
                }else if(input.equals("2")){
                    System.out.println("Please enter a name for your group");
                    String name = scanner.nextLine();
                    System.out.println("Enter a description for your group");
                    String description = scanner.nextLine();
                    System.out.println("Enter a max size for your group");
                    int maxSize = scanner.nextInt();
                    createGroup(name, description, maxSize);
                }else if(input.equals("3")){
                    System.out.println("Please enter the gID of the group you would like to join");
                    int gid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter a message for your group request");
                    String message = scanner.nextLine();
                	initiateAddingGroup(gid, message);
                }else if(input.equals("4")){
                	confirmRequests();
                }else if(input.equals("5")){
                    System.out.println("Please enter the message you want to send: ");
                    String msg = scanner. nextLine();
                    System.out.println("Please enter the iD of the user you are sending message to: ");
                    String id = scanner. nextLine();
                    sendMessageToUser(msg, id);
                }else if(input.equals("6")){
                    System.out.println("Please enter the message you want to send: ");
                    String msg = scanner.nextLine();
                    System.out.println("Please enter the ID of the group you are sending message to: ");
                    String id = scanner.nextLine();
                    sendMessageToGroup(msg, id);
                }else if(input.equals("7")){
                    displayMessages();
                }else if(input.equals("8")){
                    displayNewMessages();
                }else if(input.equals("9")){
                    displayFriends(-1);
                }else if(input.equals("10")){
                    System.out.println("Please enter the name/email of the user you are seaching for: ");
                    String str = scan.nextLine();
                    searchForUser(str);
                }else if(input.equals("11")){
                    threeDegrees();
                }else if(input.equals("12")){
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Please enter the number of top users you would like to display: ");
                    int k = scan.nextInt();
                    System.out.println("Please enter the number of past months you would like to display messages from: ");
                    int x = scan.nextInt();
                    topMessages(k, x);
                }else if(input.equals("13")){
                    logout();
                    System.exit(0);
                }
            }
        }else{
            System.out.println("Password not matched, sorry");
            System.out.println("Do you want to log out? (Y/N)");
            String response = scanner.nextLine();
            if(response == "Y"){
                System.exit(0);
            }
        }
    }
    }

    private static void createUser() throws SQLException, ClassNotFoundException{
    	Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        stmt = conn.prepareStatement("INSERT INTO profile(name, email, password, date_of_birth) VALUES (?,?,?,?)");
        System.out.println("Enter your name");
        Scanner scanner = new Scanner(System.in);
        stmt.setString(1, scanner.nextLine());
        System.out.println("Enter your email");
        stmt.setString(2, scanner.nextLine());
        System.out.println("Enter a password");
        stmt.setString(3, scanner.nextLine());
        System.out.println("Enter your birtday in the format yyyy-MM-DD");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD"); 
        java.sql.Date date = java.sql.Date.valueOf(scanner.nextLine());
        stmt.setDate(4, date);
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("SQL Error retreiving friend requests");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.rollback();
            return;
        }
        conn.commit();
        System.out.println("Account Created");
        login();
    }

    private static void initiateFriendship(int friendID, String message) throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
        String query = "SELECT name FROM profile WHERE userID = " + friendID;
        ResultSet res = st.executeQuery(query);
        if(res.next()){
            stmt = conn.prepareStatement("INSERT INTO pendingFriend VALUES (" + user_id + ", " + friendID + ", " + message + ")");
            try{
                stmt.execute();
            }catch (SQLException e1) {
                System.out.println("Friend request already pending");
                conn.rollback();
                return;
            }
        }else{
            conn.commit();
            System.out.println("User does not exist");
            conn.close();
        }
    }

    private static void createGroup(String name, String description, int maxSize) throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        stmt = conn.prepareStatement("INSERT INTO groupInfo(name, description, size) VALUES (" + name + ", " + description + ", " + maxSize + ")", Statement.RETURN_GENERATED_KEYS);
        conn.setAutoCommit(false);
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("Group with that name already exists");
            //scanner.close();
            conn.rollback();
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
                //scanner.close();
                conn.rollback();
                conn.close();                
                return;
            }
        }
        //add the current user as a manager
        stmt = conn.prepareStatement("INSERT INTO groupmember VALUES(" + gid + ", " + user_id + ", 'manager')");
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("Failed to add to user as group manager");
            conn.rollback();
            conn.close();
            //scanner.close();
            return;
        }  
        conn.commit();
        System.out.println("Group created");
    }

    private static void initiateAddingGroup(int gid, String message) throws SQLException{
    	stmt = conn.prepareStatement("SELECT name FROM groupInfo WHERE gid = " + gid);
    	ResultSet rs;
    	try{
    		rs = stmt.executeQuery();
    	}catch(SQLException e){
    		System.out.println("SQL Error: Failed to retreive group");
    		//scanner.close();
            return;
    	}
    	if(rs.next()){
    		System.out.println("Enter a message for your group request");
    		stmt = conn.prepareStatement("INSERT INTO pendingGroupMember VALUES(" + gid + ", " + user_id + ", " + message + ")");
    		try{
    			stmt.execute();
    		}catch(SQLException e1){
    			System.out.println("SQL Error: Group request already pending");
                conn.rollback();
    			return;
    		}

    		System.out.println("Request sent to " + rs.getString(1) + " with message: " + message.substring(0, Math.min(message.length(), 200)));

            conn.commit();

    	}else{
    		System.out.println("No group with that ID");
    		return;
    	}
    }
    
    private static void confirmRequests() throws SQLException{
    	System.out.println("==Friend Requests==");
    	stmt = conn.prepareStatement("SELECT name, message FROM (pendingfriend full outer join profile p on pendingfriend.fromid = p.userid) WHERE toid = " + user_id);
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
            System.out.println("SQL Error retreiving group requests");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            return;
        }
    	while(gres.next()){
    		System.out.println(i + ") " + gres.getString(1) + ": " + gres.getString(2));
    		i++;
    	}
        conn.commit();
    }
    
    private static void sendMessageToUser(String msg, int id)throws
            SQLException, ClassNotFoundException{
        //Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //Connection conn = DriverManager.getConnection(url, props);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        stmt = conn.prepareStatement("INSERT INTO messageInfo values (DEFALUT, " + user_id +", " + msg + ", " + id + ",NULL," + formatter.format(date)+ ")");
        int found = 0;
        String query = "select * from friend f where f.userid1 = " + user_id;
        Statement st2 = conn.createStatement();
        ResultSet res = st2.executeQuery(query);
        while(res.next()){
            if(res.getString(1) == id){
                found = 1;
            }
        }
        if(found != 1){
            query = "select * from friend f where f.userid2 = " + user_id;
        st2 = conn.createStatement();
        res = st2.executeQuery(query);
        }
        while(res.next()){
            if(res.getString(1) == id){
                found = 1;
            }
        }
        if(found != 1){
            System.out.println("The user you want to send a message to is not your frined, execution failed!");
            conn.rollback();
            return;
        }
        query = "select name from profile where userid = " + id;
        st2 = conn.createStatement();
        res = st2.executeQuery(query);
        System.out.println("The user's name you want to send a message to is " + res.getString(1));
        try{
            stmt.execute();
        }catch (SQLException e1) {
            System.out.println("SQL Error, sending failed!");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            //conn.close();
            conn.rollback();
            scanner.close();
            return;
        }
        scanner.close();
        conn.commit();
        System.out.println("Message Sent!");
    }

    private static void sendMessageToGroup(String msg, int id)throws
            SQLException, ClassNotFoundException{
        //Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //Connection conn = DriverManager.getConnection(url, props);
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        stmt = conn.prepareStatement("INSERT INTO messageInfo values (DEFALUT," + user_id +"," + msg + ",NULL," + id + ","+ formatter.format(date)+ ")");
        int found = 0;
        String query = "select userid from groupmember where gid = " + id;
        Statement st2 = conn.createStatement();
        ResultSet res = st2.executeQuery(query);
        while(res.next()){
            if(res.getInt(1) == user_id){
                found = 1;
            }
        }
        if(found != 1){
            conn.rollback();
            System.out.println("You are not in the group you are sending the message to, execution failed!");
            return;
        }
        try{
             stmt.execute();
        }catch (SQLException e1) {
            System.out.println("SQL Error, message sending failed!");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            conn.rollback();
            //conn.close();
            scanner.close();
            return;
        }
        scanner. close();
        conn.commit();
        System.out.println("Message sent!");
    }

    private static void displayMessages()throws 
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //conn = DriverManager.getConnection(url, props);
        System.out.println("==Messages==");
        String query = "SELECT * FROM messagerecipient where userid = " + user_id;
        st = conn.createStatement();
        try{
            ResultSet res = st.executeQuery(query);
            int msgId;
            String msg;
            while (res.next()) {
                msgId = res.getInt(1);
                msg = res.getString(2);
                System.out.println( "Message number " + msgId + ": " + msg);
            }
       }catch (SQLException e1) {
            conn.rollback();
            System.out.println("SQL Error, Please try again!");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            return;
        } 
        System.out.println("   ==End==   ");
        conn.commit();
    }

    private static void displayNewMessages()throws
            SQLException, ClassNotFoundException{
        //Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //conn = DriverManager.getConnection(url, props);        
        st = conn.createStatement();
        System.out.println("==New Messages==");
        String query = "SELECT lastlogin FROM profile where userid = " + user_id;
        ResultSet res = st.executeQuery(query);
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = res.getDate("lastlogin");
        //st = conn.createStatement();
        query = "SELECT msgid, message from messageInfo full outer join messagerecipient m on messageInfo.msgid = m.msgid where timeSent >" + date + " or timeSent = " + date + " AND m.userid = " + user_id;
        try{
            ResultSet res2 = st.executeQuery(query);
            int msgId;
            String msg;
            while (res2.next()) {
                msgId = res2.getInt(1);
                msg = res2.getString(2);
                System.out.println( "Message number " + msgId + ": " + msg);
            }
       }catch (SQLException e1) {
            //conn.rollback();
            System.out.println("SQL Error, Please try again!");
            while (e1 != null) {
                System.out.println("Message = " + e1.getMessage());
                System.out.println("SQLState = "+ e1.getSQLState());
                System.out.println("SQL Code = "+ e1.getErrorCode());
                e1 = e1.getNextException();
            }
            return;
        } 
        conn.commit();
        System.out.println("    ==End==     ");
    }

    private static void searchForUser(String str) throws
            SQLException, ClassNotFoundException{
                st = conn.createStatement();
        String[] splitStr = str.split("\\s+");
                System.out.println("===Here are the users found:===");
        for(int i=0; i<splitStr.length; i++){
        String query = "SELECT name, email FROM profile where name like '%" + splitStr[i] + "%' AND email like '%" + splitStr[i] + "%'";
            stmt = conn.prepareStatement(query);
            try{
                ResultSet res2 = st.executeQuery(query);
                String name;
                String email;
                while (res2.next()) {
                    name = res2.getString(1);
                    email = res2.getString(2);
                    System.out.println( "Name: " + name + "Email: " + email);
            }
            }catch (SQLException e1){
                System.out.println("SQL Error, Please try again!");
                while (e1 != null) {
                    System.out.println("Message = " + e1.getMessage());
                    System.out.println("SQLState = "+ e1.getSQLState());
                    System.out.println("SQL Code = "+ e1.getErrorCode());
                    e1 = e1.getNextException();
                }
            return;
            }
        }
        conn.commit();
        System.out.println("======End=======");
    }

    private static void displayFriends(int id)throws
            SQLException, ClassNotFoundException{
          //      Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //Connection conn = DriverManager.getConnection(url, props);
        st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        System.out.println("==Friends==");
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
                if(id == -1)
                {
                    System.out.println("Please enter the ID of the user's profile you'd like to see(enter 0 to exit): ");
                    Scanner scan = new Scanner(System.in);
                    id = scan.nextInt();
                }
                PreparedStatement st2 = conn.prepareStatement("SELECT name, email FROM profile where userid = " + id);

                if(id==0){
                    return;
                }else{
                            ResultSet res2 = st2.executeQuery();
                            while(res2.next()){
                                name = res2.getString(1);
                                email = res2.getString(2);
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
            conn.commit();
            //conn.close();
            return;
        } 
    }

    private static void threeDegrees()throws
            SQLException, ClassNotFoundException
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
        props.setProperty("password", password);
        Connection conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
        String query = "SELECT * from friend where userID1 = " + String.valueOf(user_id) + ";";
        ResultSet rs1 = st.executeQuery(query);
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

    private static void topMessages(int k, int y)throws
            SQLException, ClassNotFoundException{   
        Scanner scan = new Scanner(System.in);
        String numUsers = String.valueOf(k);
        String numMonths = String.valueOf(y);
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
        String query = "SELECT fromID, COUNT(fromID) from messageInfo where toUserID = " + String.valueOf(user_id) +
                " group by fromID order by count(fromID) desc limit " + numUsers + ";";
        String query1 = "SELECT count(*) from messageInfo where timeSent > (current_timestamp - (" + numMonths + " * interval '1 month'))";
        ResultSet rs1 = st.executeQuery(query);
        //ResultSet rs2 = st.executeQuery(query1);
        System.out.println("Top users: ");
        while(rs1.next())
        {
            String user = rs1.getString(1);
            System.out.println(user);
        }
        ResultSet rs2 = st.executeQuery(query1);

        rs2.next();
        String messages = rs2.getString(1);
        System.out.println("Number of messages in the past " + numMonths + " months: " + messages);

        conn.commit();


    }

    private static void logout() throws ClassNotFoundException, SQLException
    {
        //Class.forName("org.postgresql.Driver");
        //String url = "jdbc:postgresql://localhost/postgres";
        //Properties props = new Properties();
        //props.setProperty("user", "postgres");
        //props.setProperty("password", password);
        //conn = DriverManager.getConnection(url, props);
        st = conn.createStatement();
        String query = "UPDATE profile set lastlogin = CURRENT_TIMESTAMP where userID = " + user_id;
        st.executeUpdate(query);
        conn.commit();
    }

    private static void dropUser()throws
            SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
        String query = "DELETE FROM profile where userID = " + String.valueOf(user_id) + ";";
        String query1 = "DELETE FROM groupMember where userID = " + String.valueOf(user_id) + ";";
        String query2 = "DELETE FROM messageInfo where fromID = " + String.valueOf(user_id) + " or toUserID = " + String.valueOf(user_id) + ";";
        try{
         st.executeQuery(query1);
        }catch (SQLException e1){
                System.out.println("SQL Error, Please try again!");
                while (e1 != null) {
                    System.out.println("Message = " + e1.getMessage());
                    System.out.println("SQLState = "+ e1.getSQLState());
                    System.out.println("SQL Code = "+ e1.getErrorCode());
                    e1 = e1.getNextException();
                }
                conn.rollback();
            return;
         }
        st.executeQuery(query2);
        logout();
    }

    private static void exit() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        conn.setAutoCommit(false);
        st = conn.createStatement();
        String query = "UPDATE profile set lastlogin = CURRENT_TIMESTAMP where userID = " + String.valueOf(user_id) + ";";
        try{
        st.executeQuery(query);
        }catch (SQLException e1){
                System.out.println("SQL Error, Please try again!");
                while (e1 != null) {
                    System.out.println("Message = " + e1.getMessage());
                    System.out.println("SQLState = "+ e1.getSQLState());
                    System.out.println("SQL Code = "+ e1.getErrorCode());
                    e1 = e1.getNextException();
                }
                conn.rollback();
            return;
            }
        System.exit(0);
    }

}
