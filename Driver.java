public class Driver{
    public static final String password = "19990406";
    public static Connection conn;
    public static Statement st;
    public static void main(String args[]) throws SQLException, ClassNotFoundException
    {	
    	try{
		Class.forName("org.postgresql.Driver");
    	Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", password);
        conn = DriverManager.getConnection(url, props);
        st = conn.createStatement();
        String query="Select * from profile";
        PittSocial test = new PittSocial();
        System.out.println("=================This is the driver program=================");
        System.out.println("///////Here is the test for function 'createUser'");
        test.createUser("Connor", "connor@pitt.edu", "cpwd", 1);
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getdate(4)+","+rs.getdate(5));
        }
        }catch(Exception e){
			System.out.println(e);
		}

		String query="Select * from friend";
        System.out.println("///////Here is the test for function 'initiateFriendship'");
        test.initiateFriendship("1", "Hi, It's me Connor");
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3));
        }

		String query="Select * from goupInfo";
		System.out.println("///////Here is the test for function 'createGroup'");
		test.createGroup("Friends", "My friends", 10);
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
        }		

       	String query="Select * from pendingGroupMember";
		System.out.println("///////Here is the test for function 'initiateAddingGroup'");
		test.initiateAddingGroup("1", "Hi friends");
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
        }	

        String query="Select * from pendingFriend";
		System.out.println("///////Here is the test for function 'confirmRequest'");
		//test.confirmRequest("1", "Hi friends");
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3));
        }	

      	String query="Select * from MessageInfo";
		System.out.println("///////Here is the test for function 'sendMessageToUser'");
		test.sendMessageToUser("Hi, It's me ", 1);
        rs = st.executeQuery(query);
        while(rs.next()){
        	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5)+","+	rs.getdate(6));
        }	

       	String query="Select * from MessageInfo";
		System.out.println("///////Here is the test for function 'sendMessageToGroup'");
		test.createGroup("Hi, It;s me again", 1);
        rs = st.executeQuery(query);
        while(rs.next()){
       	System.out.println(rs.getInt(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getInt(4)+","+rs.getInt(5)+","+	rs.getdate(6));
        }	

        	

}