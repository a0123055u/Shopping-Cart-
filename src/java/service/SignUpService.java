package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.ejb.Stateless;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
@Stateless
public class SignUpService {
    public boolean storeUser(String userName, String passWord, String emailId) throws SQLException{
        
         Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
         Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        
        String sql= "insert into APP.\"USER\" (USERNAME,PASSWORD,EMAIL) values(?,?,?)";
         PreparedStatement  stmt= conn.prepareStatement(sql); 
         stmt.setString(1,userName);
         stmt.setString(2,passWord);
         stmt.setString(3,emailId);
         stmt.execute();
        conn.commit();
         conn.close();
         
        return false;
    }
    
}
