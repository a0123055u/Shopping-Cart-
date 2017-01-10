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
import java.util.Properties;
import javax.ejb.Stateless;

/**
 *
 * @author hp
 */
@Stateless
public class ProductService {
    public ResultSet getProducts() throws SQLException{
        Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
        

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        
        String sql = "select NAME,PRICE from APP.\"PRODUCTS\"";
        PreparedStatement  stmt= conn.prepareStatement(sql);
       
        ResultSet rs = stmt.executeQuery();
        String name="";
        String price="";
        String qty="";
        System.out.println("------------------------------------>>>>>>>>>Service"+rs);
        return rs;
    }
    
}
