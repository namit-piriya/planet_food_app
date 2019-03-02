/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Category;


public class CategoryDao {
    public static HashMap<String,String> getAllCategoryId()throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        String Query = "SELECT CAT_NAME,CAT_ID FROM Categories";
        ResultSet rs = st.executeQuery(Query);
        HashMap<String,String> categories = new HashMap<>();
        while(rs.next())
            {
                String catName = rs.getString(1);
                String catId = rs.getString(2);
                categories.put(catName, catId);
            }
        return categories;
    }
    public static boolean updateCategory(Category c) throws SQLException{
     
        Connection con = DBConnection.getConnection();
        String qry = "UPDATE categories SET cat_Name =? where cat_id = ?";
        PreparedStatement ps = con.prepareStatement(qry);
        ps.setString(1,c.getCatName());
        ps.setString(2,c.getCatId());
        int count = ps.executeUpdate();
        if(count==1)
           return true;
        else
           return false;

    }
    public static String getNewId() throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        String Query = "SELECT COUNT(*) FROM Categories";
        int id =101;
        ResultSet rs = st.executeQuery(Query);
        if(rs.next())
            id= id + rs.getInt(1);
        
        String newid = "C"+id;
        return newid;
    }
    public static boolean AddCategory(Category c) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Categories VALUES(?,?)");
        ps.setString(1,c.getCatId());
        ps.setString(2,c.getCatName());
        int x = ps.executeUpdate();
        
        if(x == 1)
            return true;
        else
            return false;
    }
  
}
