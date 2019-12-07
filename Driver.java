    private static void searchForUser() throws
            SQLException, ClassNotFoundException{
                st = conn.createStatement();
        System.out.println("Please enter the name/email of the user you are seaching for: ");
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
        String str = scan.next();
        String query = "SELECT name, email FROM profile where name like '" + str + "%'";
        System.out.println("===Here are the users found:===");
        stmt = conn.prepareStatement();
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
        System.out.println("======End=======");
    }