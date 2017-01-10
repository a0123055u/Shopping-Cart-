/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.ejb.Stateless;

/**
 *
 * @author hp
 */
@Stateless
public class CartService {
    public boolean Shope(String item, String quantity,String userName)throws SQLException{
        
         boolean result=false;
        Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
         Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        
        String sql = "insert into APP.\"ORDER\" (USERNAME,STATUS) values(?,?)";
        PreparedStatement  stmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);        
        stmt.setString(1, userName);
        stmt.setString(2,"PUR");
        stmt.execute();
         conn.commit();
      
       String sqlId="select * from APP.\"ORDER\" where USERNAME=?";
       PreparedStatement stmtId= conn.prepareStatement(sqlId);
       stmtId.setString(1, userName);
       ResultSet rs = stmtId.executeQuery();
        int valId=0;
       while(rs.next()){
             valId= rs.getInt("ORDER_ID");
       }
      
      
        
         String sql2 = "insert into APP.\"ORDERDETAILS\" (ORDER_ID,PRODUCT,QUANTITY) values(?,?,?)";
        PreparedStatement  stmt2= conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
        
        stmt2.setInt(1, valId);
        stmt2.setString(2,item);        
        stmt2.setInt(3,Integer.parseInt(quantity));
        stmt2.execute();
        
       
        conn.close();
       
         return true;
        
        
    }
}
