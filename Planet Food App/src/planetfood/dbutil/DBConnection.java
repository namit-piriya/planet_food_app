/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBConnection {
    private static Connection conn;
    static
    {
        try
        {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-H7ASN73:1521/XE","MYPROJECT","abc");
         //JOptionPane.showMessageDialog(null,"Connected Successfully to the Database","Success!",JOptionPane.INFORMATION_MESSAGE);
         }
        catch(ClassNotFoundException ex)
        {
                JOptionPane.showMessageDialog(null,"Error Loading Driver Class"+ex,"Error",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
        }
        catch(SQLException ex)
        {
                JOptionPane.showMessageDialog(null,"Error in DB"+ex,"Error",JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return conn;
    }
    public static void closeConnection()
    {
       try
       {
           if(conn!=null)
           {
               conn.close();
               JOptionPane.showMessageDialog(null,"Database successfully closed","Success",JOptionPane.INFORMATION_MESSAGE);
       
           }
       }
       catch(SQLException ex)
       {
       JOptionPane.showMessageDialog(null,"Error in closing DB"+ex,"Error",JOptionPane.ERROR_MESSAGE);
       ex.printStackTrace();
       }
        //TO Be Done
    }
}
