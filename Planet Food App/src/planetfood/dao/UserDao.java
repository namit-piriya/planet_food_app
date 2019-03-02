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
import planetfood.dbutil.DBConnection;
import planetfood.pojo.User;


public class UserDao {
    public static String validateUser(User user) throws SQLException{
       
        Connection conn = DBConnection.getConnection();
        String Query = "SELECT USERNAME from Users where userid = ? And password = ? and usertype =?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1,user.getUserId());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUsertype());
        ResultSet rs =ps.executeQuery();
        String username = null;
        if(rs.next())
            {
              username = rs.getString(1);
            }
        return username;
    }
    
    public static boolean registerCashier(User u) throws SQLException{
        
        Connection conn = DBConnection.getConnection();
        String Query = "insert into users values(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1,u.getUserId());
        ps.setString(2,u.getUsername());
        ps.setString(3,u.getPassword());
        ps.setString(4,u.getEmpId());
        ps.setString(5,u.getUsertype());
        int x = ps.executeUpdate();
        if(x>0)
            return true;
        else 
            return false;
        
    }

    public static User getUserById(String userId) throws SQLException{
        
        Connection conn = DBConnection.getConnection();
        String Query = "Select UserName,EmpId from Users where userId = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        User u = new User();
        if(rs.next()){
            u.setEmpId(rs.getString("empid"));
            u.setUsername(rs.getString("username"));
            u.setUserId(userId);
        }
        return u;
    }
    
    public static boolean removeCashier(String userId) throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        String Query = "delete from Users where userId = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, userId);
        int x = ps.executeUpdate();
        if(x>0)
            return true;
        else
            return false;
    }
}
    
