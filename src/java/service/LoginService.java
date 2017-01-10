/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author hp
 */
@Stateless
public class LoginService {
    public boolean checkUser(String user, String pass)throws SQLException{
//        User userobject = new User();
//        if(user!="" && pass!=""){
//            
//        }
         boolean result=false;
        Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
        

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        
        String sql = "select * from APP.\"USER\" where USERNAME=?";
        PreparedStatement  stmt= conn.prepareStatement(sql);
        stmt.setString(1, user);
        ResultSet rs= stmt.executeQuery();
       
        while(rs.next()){
            String userName=rs.getString("USERNAME");
            String passWord=rs.getString("PASSWORD");
            if((userName.equals(user))&&(passWord.equals(pass))){
                result= true;
            }
            else
                result= false;
        }
        rs.close();
        conn.close();
//      
        return result;
        
        
        
    }
    
}
