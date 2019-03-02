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
import java.util.ArrayList;
import java.util.HashMap;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Product;


public class ProductDao {
    
    public static String getNewId() throws SQLException
    {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        String Query = "SELECT COUNT(*) FROM PRODUCTS";
        int id =101;
        ResultSet rs = st.executeQuery(Query);
        if(rs.next())
            id= id + rs.getInt(1);
        
        String newid = "P"+id;
        return newid;
    }
    
    public static boolean AddProduct(Product p) throws SQLException{
        
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO PRODUCTS VALUES(?,?,?,?,?)");
        ps.setString(1,p.getProdId());
        ps.setString(2,p.getCatId());
        ps.setString(3,p.getProdName());
        ps.setDouble(4,p.getPrice());
        ps.setString(5, p.getIsActive());
        int x = ps.executeUpdate();
        
        if(x == 1)
            return true;
        else
            return false;
    }
    
    public static HashMap<String,Product> getProductsByCategory(String catId) throws SQLException
        {
        Connection conn = DBConnection.getConnection();
        String qry = "Select * from Products where cat_id = ?";
        PreparedStatement ps = conn.prepareStatement(qry);
        HashMap<String,Product> productList = new HashMap<>();
        ps.setString(1,catId);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            {
                Product p = new Product();
                p.setCatId(catId);
                p.setProdId(rs.getString("prod_id"));
                p.setProdName(rs.getString("prod_name"));
                p.setPrice(rs.getDouble("prod_price"));
                p.setIsActive(rs.getString("active"));
                productList.put(p.getProdId(),p);
            }
        
        return productList;
        }
    
    public static ArrayList<Product> getAllData() throws SQLException{
    
        Connection conn = DBConnection.getConnection();
        String qry = "Select * from Products";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(qry);
        ArrayList<Product> AllProducts = new ArrayList<>(); 
        while(rs.next())
            {   
                Product p = new Product();
                p.setProdName(rs.getString("Prod_Name"));
                p.setProdId(rs.getString("Prod_Id"));
                p.setCatId(rs.getString("Cat_Id"));
                p.setPrice(rs.getDouble("prod_price"));
                p.setIsActive(rs.getString("active"));
                AllProducts.add(p);
            }
        return AllProducts;
    }
    
    
    public static boolean UpdateProduct(Product p ) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String qry = "UPDATE Products Set prod_name = ?,prod_price =?,Active = ? where prod_id = ? ";
        PreparedStatement ps = conn.prepareStatement(qry); 
        ps.setString(1, p.getProdName());
        ps.setDouble(2, p.getPrice());
        ps.setString(3, p.getIsActive());
        ps.setString(4, p.getProdId());
        int x = ps.executeUpdate();
        if(x==0)
            return false;
        else
            return true;
    }
    
    public static boolean removeProduct(String ProdId) throws SQLException{
      
        Connection conn = DBConnection.getConnection();
        String qry = "UPDATE Products Set Active ='N' where prod_id = ? ";
        PreparedStatement ps = conn.prepareStatement(qry); 
        ps.setString(1,ProdId);
        int x = ps.executeUpdate();
        if(x==0)
            return false;
        else
            return true;
    }
    
    public static HashMap<String,String> getActiveProductsByCategory(String catId) throws SQLException{
    
        Connection conn = DBConnection.getConnection();
        String qry = "SELECT prod_name,prod_id from products where cat_id=? and Active ='Y' ";
        PreparedStatement ps = conn.prepareStatement(qry); 
        ps.setString(1,catId);
        ResultSet rs = ps.executeQuery();
        HashMap<String,String> prodList = new HashMap<>(); 
        while(rs.next())
            {
                String prodName = rs.getString("prod_name");
                String prodId = rs.getString("prod_id");
                prodList.put(prodName, prodId);
            }
        return prodList;
    }
    
}


