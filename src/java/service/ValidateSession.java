/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author hp
 */
@Stateless
public class ValidateSession extends HttpServlet {
    
    public boolean validateSession(HttpServletRequest req, HttpServletResponse res) throws IOException{
         try{
             String validate="";
       validate= req.getSession().getAttribute("name").toString();
       }catch(Exception e){
           e.printStackTrace();
          
             Map<String,String> errorName= new HashMap<String, String>();
              String urlBase = req.getRequestURI();
              String[] urlNeeded= urlBase.split("/product");
                String newUrl=(urlNeeded[0]+"/index.html");
              errorName.put("error", newUrl);
               JSONObject object = new JSONObject(errorName);
              
                 res.setContentType("application/json");
                 PrintWriter write= res.getWriter();
                write.print(object);
                 write.flush();
           return false;
            //res.sendRedirect("index.html");
           
       }
        return true;
    }
}
