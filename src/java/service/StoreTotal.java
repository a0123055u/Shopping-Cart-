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
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import javax.ejb.Stateless;
import org.json.simple.JSONObject;

/**
 *
 * @author hp
 */
@Stateless
public class StoreTotal {
    
    public boolean StoreTotalQty(int total, int qty, String userName) throws SQLException{
        
        System.out.println(total+" "
                + qty+" "+userName);
        
         Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
         Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        String uuid = UUID.randomUUID().toString();
        
        String sql= "insert into APP.\"CART\" (CART_ID,CART_QTY,CART_TOTAL,USERNAME) values(?,?,?,?)";
         PreparedStatement  stmt= conn.prepareStatement(sql); 
         stmt.setString(1,uuid);
         stmt.setInt(2,qty);
         stmt.setInt(3,total);
         stmt.setString(4,userName);
         stmt.execute();
        conn.commit();
         conn.close();
        
        return true;
    }
    
    public boolean storeCartDetails(JSONObject productJson,String userName) throws SQLException{
        
        Properties prop = new Properties();
        prop.put("user","root");
        prop.put("password","root");
        

        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Customer",prop);
        conn.setSchema("APP");
        
        String sql = "select CART_ID from APP.\"CART\" where USERNAME=?";
        PreparedStatement  stmt= conn.prepareStatement(sql);
        stmt.setString(1,userName);
       
        ResultSet rs = stmt.executeQuery();
        String uuid="";
         String uuid_ID = UUID.randomUUID().toString();
        while(rs.next())
        {
            
                uuid=rs.getString("CART_ID");
           // =rs.last();
            System.out.println("uuid in cart_details"+uuid);
        }
         String products= productJson.get("prd").toString();
        
      
         
           
        
        ArrayList<String> productsList = new ArrayList<String>();
       String[] arr= products.split("\\{|\\}");
       
        
           
            System.out.println("name"+products+"arr"+arr);
            for(int i=0;i<arr.length;i++){
                if(i>0&& i%2==1){
                     System.out.println(arr[i]);
                     productsList.add(arr[i]);
                }               
            }
            String a="";
            String b="";
            String c="";
            for(String g:productsList){
                 String[] price =g.split("\\:|\\,");
                 int k=0;
                 for(String h: price){
                     System.out.println(h);
                 }
                  
                 
                 for(int j=0;j<productsList.size();j++){
                    
                    
                    // if(j>0&& j%2==1 && j!=5){
                          k=j;
                     k=k+2;
                     a =price[j+1].replace("\"", "");
                      b =price[j+3].replace("\"", "");
                     c = price[j+5].replace("\"","");
                     System.out.println(a+" "+b);
                     
                        
                     //}
                 }
              //  System.out.println("total"+total+"qty"+qty);;
            }
         String sqlInsert= "insert into APP.\"CART_DETAILS\" (CART_ID,QTY,PRICE,NAMEPROD,USERNAME) values(?,?,?,?,?)";
         PreparedStatement  stmt1= conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
         
        
       //  stmt1.setInt(1,ID);
         stmt1.setString(1,uuid);
         stmt1.setInt(2,Integer.parseInt(b));
          stmt1.setInt(3,Integer.parseInt(a));
           stmt1.setString(4,c);
         stmt1.setString(5,userName);
         stmt1.execute();
        conn.commit();
         conn.close();
        JSONObject object = new JSONObject(productJson);
        
      
//        for (String key : keys)
//        {
//            Object value = object.get(key);
//            // Determine type of value and do something with it...
//        }

        
        
//        for(String test: productJson)
//        {
//            System.out.println("productJson"+test);
//        }
        
        return true;
    }
}
