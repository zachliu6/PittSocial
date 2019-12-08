import java.util.Properties;
import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
public class Driver{
    public static final String password = "postgres";
    public static Connection conn;
    public static Statement st;
    public static ResultSet rs;
    public static String query;
    public static void main(String args[]) throws SQLException, ClassNotFoundException
    {
        PittSocial test = new PittSocial();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        st = conn.createStatement();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        java.sql.Date date = java.sql.Date.valueOf("1998-03-12");
        query = "Select * from profile";
        System.out.println("=================This is the driver program=================");
        System.out.println("///////Here is the test for function 'createUser'");
        test.createUser("Connor", "connor@pitt.edu", "cpwd", date, 1);
        try{
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getDate(4) + "," + rs.getDate(5));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

   		/*
	    query="Select * from friend";
        System.out.println("///////Here is the test for function 'initiateFriendship'");
        test.initiateFriendship(1, "Hi It's me Connor");
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3));
        }

   		 */

        query="Select * from groupInfo";
        System.out.println("///////Here is the test for function 'createGroup'");
        test.createGroup("Friends", "My friends", 10);
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
        }

        query="Select * from pendingGroupMember";
        System.out.println("///////Here is the test for function 'initiateAddingGroup'");
        test.initiateAddingGroup(1, "Hi friends");
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
        }	

        /*
        query="Select * from pendingFriend";
		System.out.println("///////Here is the test for function 'confirmRequest'");
		//test.confirmRequest("1", "Hi friends");
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3));
        }

        */

        query="Select * from MessageInfo";
        System.out.println("///////Here is the test for function 'sendMessageToUser'");
        test.sendMessageToUser("Hi, It's me ", 1);
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5)+","+	rs.getDate(6));
        }

        query="Select * from MessageInfo";
        System.out.println("///////Here is the test for function 'sendMessageToGroup'");
        test.createGroup("Pitt Comp Sci","Hi, It's me again", 5);
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5)+","+	rs.getDate(6));
        }

        System.out.println("///////Here is the test for function 'displayMessages'");
        test.displayMessages();

        System.out.println("///////Here is the test for function 'displayNewMessages'");
        test.displayNewMessages();

        System.out.println("///////Here is the test for function 'searchForUser'");
        test.searchForUser("soc yaw");

        System.out.println("///////Here is the test for function 'displayFriends'");
        test.displayFriends(2);

        System.out.println("///////Here is the test for function 'topMessages'");
        test.topMessages(1, 3);

        query = "select * from profile";
        System.out.println("///////Here is the test for function 'logout'");
        test.logout();
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getDate(4)+","+rs.getDate(5));
        }

        query = "select * from profile";
        String query1 = "select * from groupMember";
        String query2 = "select * from messageInfo";
        System.out.println("///////Here is the test for function 'dropUser'");
        test.dropUser();
        rs = st.executeQuery(query);
        while(rs.next()){
            System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getDate(4)+","+rs.getDate(5));
        }
        rs = st.executeQuery(query1);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3));
        }
        rs = st.executeQuery(query2);
        while(rs.next()){
            System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5) + "," + rs.getDate(6));
        }

        System.out.println("///////Here is the test for function 'dropUser'");
        test.exit();


    }
}