/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import planetfood.dbutil.DBConnection;
import planetfood.pojo.Emp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class EmpDao {
    
    public static boolean addEmployee(Emp e) throws SQLException
    {
        
    Connection con = DBConnection.getConnection();
    PreparedStatement ps = con.prepareStatement("insert into employees values(?,?,?,?)");
    ps.setString(1,e.getEmpid());
    ps.setString(2,e.getEname());
    ps.setString(3,e.getJob());
    ps.setDouble(4,e.getSal());
    int count = ps.executeUpdate();
    if(count==1)
        return true;
    else
        return false;
    }
    
    
    public static ArrayList<Emp> getAllEmployees() throws SQLException
    {
     ArrayList<Emp> empList = new ArrayList<>();
     Connection con = DBConnection.getConnection();
     Statement st = con.createStatement();
     ResultSet rs =st.executeQuery("SELECT * FROM EMPLOYEES");
     while(rs.next())
        {
            Emp e = new Emp();
            e.setEmpid(rs.getString("EMPID"));
            e.setEname(rs.getString("ENAME"));
            e.setJob(rs.getString("JOB"));
            e.setSal(rs.getInt("SAL"));
            empList.add(e);
        }
     return empList;
    }     
    
    public static Emp getEmpById(String EmpId) throws SQLException
        {
        Connection conn = DBConnection.getConnection();
        String qry = "Select * from Employees where Empid = ?";
        PreparedStatement ps = conn.prepareStatement(qry);
        Emp e = new Emp();
        ps.setString(1,EmpId);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            {
                e.setEmpid(rs.getString("EMPID"));
                e.setEname(rs.getString("ENAME"));
                e.setJob(rs.getString("JOB"));
                e.setSal(rs.getInt("SAL"));
                
            }
        return e;
        }
    
    
    public static ArrayList<String> getAllCashierId() throws SQLException
    {
     ArrayList<String> cashierList = new ArrayList<>();
     Connection con = DBConnection.getConnection();
     Statement st = con.createStatement();
     ResultSet rs =st.executeQuery("SELECT EMPID FROM EMPLOYEES Where job ='Cashier'");
     while(rs.next())
        {
             cashierList.add(rs.getString("EMPID"));
        }
     return cashierList;
    }
    
    public static String getEmployeeName(String empId) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String qry = "Select ENAME from Employees where Empid = ? ";
        PreparedStatement ps = conn.prepareStatement(qry);  
        ps.setString(1, empId);
        ResultSet rs = ps.executeQuery();
        String empName= null;
        if(rs.next()){
            empName = rs.getString("Ename"); 
        }
        return empName;
       
    }
    
    public static boolean removeEmployee(String EmpId) throws SQLException
    {
        
     Connection con = DBConnection.getConnection();
     PreparedStatement ps = con.prepareStatement("Delete from employees where EmpId=?");
     ps.setString(1,EmpId);
     int count = ps.executeUpdate();
        if(count==1)
            return true;
        else
            return false;
    }
    
    public static boolean updateEmployee(Emp e) throws SQLException
    {
     Connection con = DBConnection.getConnection();
     String qry = "UPDATE employees SET Ename=?,Job = ?,Sal=? where EmpId=?";
     PreparedStatement ps = con.prepareStatement(qry);
     ps.setString(1,e.getEname());
     ps.setString(2,e.getJob());
     ps.setDouble(3,e.getSal());
     ps.setString(4,e.getEmpid());
     int count = ps.executeUpdate();
     if(count==1)
        return true;
     else
        return false;
    }
 
}

    