/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author hp
 */
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    
    @EJB service.ProductService service;
    @EJB service.ValidateSession sessionService;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
       
       boolean validate= sessionService.validateSession(req,res);
     
       System.out.println("Session =========="+validate);
       if(validate){
           
         ResultSet rs=null;
        try {
             rs = service.getProducts();
        } catch (SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Map<String,String> namePrice= new HashMap<String, String>();
        
        try {         
                  
            while(rs.next()){                
                String name= rs.getString("NAME");
                String price= rs.getString("PRICE");
                namePrice.put(name, price);
                    System.out.println("aaaaa");                             
            }
//           Set <String> key;
//               key = namePrice.keySet();
//               for(String t : key){
//                   
//               }
          
           JSONObject object = new JSONObject(namePrice);
           
            res.setContentType("application/json");
            PrintWriter write= res.getWriter();
            write.print(object);
            write.flush();
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    else{
    res.sendRedirect("index.html");
    }
        
    }
    
    
}
