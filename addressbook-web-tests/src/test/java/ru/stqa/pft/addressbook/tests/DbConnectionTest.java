package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnectionTest {

    @Test
    public void testDBConnection(){

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=");
           /* Statement st = new conn.createStatement();
            ResultSet rs = st.executeQuery("select group_id,group_name, group_header, group_footer from group_list");
            Groups groups =new Groups();
            while (rs.next()){
                groups.add(new GroupData().withId(rs.getInt("group_id"))
                .withName(rs.getString("group_name")).withHeader(rs.getString("group_header")).withFooter(rs.getString("group_footer")));
            }
            rs.close();
            st.close();*/
            conn.close();


        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }



}
