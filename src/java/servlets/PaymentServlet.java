/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hp
 */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet{
   
    @EJB service.ValidateSession sessionService;
    @EJB service.StoreTotal totalService;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
         boolean validate=sessionService.validateSession(req,res);
         
        if(validate){
            
              Map<String,String> paymentUrl= new HashMap<String, String>();
              String urlBase = req.getRequestURI();
              String[] urlNeeded= urlBase.split("/payment");
                String newUrl=(urlNeeded[0]+"/payment.html");
              paymentUrl.put("url", newUrl);
               JSONObject object = new JSONObject(paymentUrl);
              
                 res.setContentType("application/json");
                 PrintWriter write= res.getWriter();
                write.print(object);
                 write.flush();
        }
        else
        {
           res.sendRedirect("index.html");
        }
         
    }
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
     
        boolean validate=sessionService.validateSession(req,res);
        if(validate){
             JSONParser parser = new JSONParser();
            JSONObject object=null;
            try {
                object=(JSONObject)parser.parse(req.getReader());
            } catch (ParseException ex) {
                Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
          
           
           
        String products= object.get("prd").toString();
        
        String total1 = object.get("totalNet").toString();
        String qty1= object.get("qtys").toString();
          int total=0;
          int qty=0;
            System.out.println("total"+total1+" "+"qtys "+qty1);
        
        ArrayList<String> productsList = new ArrayList<String>();
       String[] arr= products.split("\\{|\\}");
       
        
           
            System.out.println("name"+products+"arr"+arr);
            for(int i=0;i<arr.length;i++){
                if(i>0&& i%2==1){
                     System.out.println(arr[i]);
                     productsList.add(arr[i]);
                }               
            }
            
            for(String g:productsList){
                 String[] price =g.split("\\:|\\,");
                 int k=0;
                 for(String h: price){
                     System.out.println(h);
                 }
                 
                 for(int j=0;j<1;j++){
                    
                    
                    // if(j>0&& j%2==1 && j!=5){
                          k=j;
                     k=k+2;
                     String a =price[1].replace("\"", "");
                     String b=price[3].replace("\"", "");
                     System.out.println(a+" "+b);
                     qty+=Integer.parseInt(b);
                         total+=(Integer.parseInt(a)*Integer.parseInt(b));
                     //}
                 }
                System.out.println("total"+total+"qty"+qty);;
            }
            if(qty==Integer.parseInt(qty1) && total==Integer.parseInt(total1)){
                String name = req.getSession().getAttribute("name").toString();
               boolean insertIntoCart=false;
                boolean insertIntoCartDetails=false;
                 try {
                     insertIntoCart = totalService.StoreTotalQty(total, qty,name );
                     insertIntoCartDetails= totalService.storeCartDetails(object,name);
                 } catch (SQLException ex) {
                     Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
                 }
               
                
               if(insertIntoCart ){
                            Map<String,String> successMsg= new HashMap<String, String>();
                       // successMsg.put("Success","200");

                         String urlBase = req.getRequestURI();
                      String[] urlNeeded= urlBase.split("/payment");
                        String newUrl=(urlNeeded[0]+"/payment.html");
                      successMsg.put("url", newUrl);
                         res.setContentType("application/json");

                          res.getWriter().write(newUrl);
               }
               
           
              
                
            }
            
        }
    }
            
}
