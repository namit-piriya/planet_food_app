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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Order;
import planetfood.pojo.OrderDetail;


public class OrderDao {
    
    public static ArrayList<Order> getOrdersByDate(Date startDate,Date endDate) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String qry = "Select * from Orders where ord_date between ? and ?";
        PreparedStatement ps = conn.prepareStatement(qry);
        long ms1 = startDate.getTime();
        long ms2 = endDate.getTime();
        java.sql.Date d1 = new java.sql.Date(ms1);
        java.sql.Date d2 = new java.sql.Date(ms2);
        ps.setDate(1,d1);
        ps.setDate(2,d2);
        ArrayList<Order> orderList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            {
                Order obj = new Order();
                obj.setOrdId(rs.getString("ord_id"));
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");         
                java.sql.Date d = rs.getDate("Ord_Date");
                String dateStr = sdf.format(d);
                
                obj.setOrdDate(dateStr);
                obj.setOrdAmount(rs.getDouble("ord_amount"));
                obj.setGst(rs.getDouble("gst"));
                obj.setGstAmount(rs.getDouble("gst_amount"));
                obj.setGrandTotal(rs.getDouble("grand_total"));
                obj.setDiscount(rs.getDouble("discount"));
                obj.setUserId(rs.getString("userid"));
                
                orderList.add(obj);
            }
        
        return orderList;
    }
    
    
    public static ArrayList<Order> getAllOrders() throws SQLException{
        
        Connection conn = DBConnection.getConnection();
        String qry = "Select * from Orders ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry);
        ArrayList<Order> orderList = new ArrayList<>();
        while(rs.next())
            {
                Order obj = new Order();
                obj.setOrdId(rs.getString("ord_id"));
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");         
                java.sql.Date d = rs.getDate("Ord_Date");
                String dateStr = sdf.format(d);
                
                obj.setOrdDate(dateStr);
                obj.setOrdAmount(rs.getDouble("ord_amount"));
                obj.setGst(rs.getDouble("gst"));
                obj.setGstAmount(rs.getDouble("gst_amount"));
                obj.setGrandTotal(rs.getDouble("grand_total"));
                obj.setDiscount(rs.getDouble("discount"));
                obj.setUserId(rs.getString("userid"));
                
                orderList.add(obj);
            }
        return orderList;
    }
    
    public static String getNewId() throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        String Query = "SELECT COUNT(*) FROM ORDERS";
        int id =101;
        ResultSet rs = st.executeQuery(Query);
        if(rs.next())
            id= id + rs.getInt(1);
        
        String newid = "ORD"+id;
        return newid;
    }
    
    public static boolean addOrder(Order order , ArrayList<OrderDetail> orderList) throws SQLException,ParseException{
        
        Connection conn = DBConnection.getConnection();
        String qry1 = "Insert into  Orders values(?,?,?,?,?,?,?,?)";
        String qry2 = "Insert into  Order_Details values(?,?,?,?)";
        PreparedStatement ps1 = conn.prepareStatement(qry1);
        PreparedStatement ps2 = conn.prepareStatement(qry2);
        
        ps1.setString(1,order.getOrdId());
        
        String DateStr = order.getOrdDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date dl = sdf.parse(DateStr);
        long ms = dl.getTime();
        java.sql.Date date = new java.sql.Date(ms);
  
        ps1.setDate(2,date);
        ps1.setDouble(3,order.getGst());
        ps1.setDouble(4, order.getGstAmount());
        ps1.setDouble(5, order.getDiscount());
        ps1.setDouble(6,order.getGrandTotal());
        ps1.setString(7, order.getUserId());
        ps1.setDouble(8, order.getOrdAmount());
        
        int x = ps1.executeUpdate();
        int count=0,y;
        for( OrderDetail detail: orderList)
            {
                ps2.setString(1, detail.getOrdId());
                ps2.setString(2, detail.getProdId());
                ps2.setDouble(3, detail.getQuantity());
                ps2.setDouble(4, detail.getCost());
                y = ps2.executeUpdate();
                if(y>0)
                    count=count+y;
            }
        
        if(x>0 && count==orderList.size())
            return true;
        else
            return false;
        
        
    }
    
    public static ArrayList<Order> getOrdersByUserId(Date startDate,Date endDate,String userId) throws SQLException{
         Connection conn = DBConnection.getConnection();
        String qry = "Select * from Orders where ord_date between ? and ? and userid =? ";
        PreparedStatement ps = conn.prepareStatement(qry);
        long ms1 = startDate.getTime();
        long ms2 = endDate.getTime();
        java.sql.Date d1 = new java.sql.Date(ms1);
        java.sql.Date d2 = new java.sql.Date(ms2);
        ps.setDate(1,d1);
        ps.setDate(2,d2);
        ps.setString(3,userId);
        ArrayList<Order> orderList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            {
                Order obj = new Order();
                obj.setOrdId(rs.getString("ord_id"));
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");         
                java.sql.Date d = rs.getDate("Ord_Date");
                String dateStr = sdf.format(d);
                
                obj.setOrdDate(dateStr);
                obj.setOrdAmount(rs.getDouble("sub_total"));
                obj.setGst(rs.getDouble("gst"));
                obj.setGstAmount(rs.getDouble("gst_amount"));
                obj.setGrandTotal(rs.getDouble("grand_total"));
                obj.setDiscount(rs.getDouble("discount"));
                obj.setUserId(rs.getString("userid"));
                
                orderList.add(obj);
            }
        
        return orderList;
    
    
    }
}
