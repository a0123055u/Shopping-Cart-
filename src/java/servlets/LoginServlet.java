/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.LoginService;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.*;
import org.json.simple.parser.ParseException;
/**
 *
 * @author hp
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    @EJB private LoginService login;
   
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{
     //  req.getP
         System.out.println("Entered");
       
         JSONParser parser = new JSONParser();
          JSONObject object=null;
        try {
             object = (JSONObject) parser.parse(req.getReader());
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
//         JSONObject test = (JSONObject)(req.getReader());
         //StringBuilder jb = new StringBuilder();
           //BufferedReader br = req.getReader();
//           String line;
           //JSONObject jsonObject=null;
           //while((line=br.readLine())!=null){
              // jb.append(line);
              // System.out.println("LINE========================================="+line);
             
           
//        try {
//            //jsonObject= (JSONObject) new JSONParser().parse(line);
//        } catch (ParseException ex) {
//            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
       //Object val1 = jsonObject.get("userName");
           //String val = jsonObject.getAsJsonObject().get("userName").getAsJsonArray().get(1).getAsJsonObject()
           
            
          //  JSONArray jarry= new JSONArray();
            //jarry.add(br.toString());
            
           // jsonObject.getString("dd");
        //String nameCheck= (String)object.;//req.getParameter("userName");
       // System.out.println("name"+nameCheck+"val1"+val1);
        //String passWord=(String)object.get("passWord");
        String CONS_UNAME = "userName";
        String CONS_PASS ="passWord";
        String nameCheck =object.get(CONS_UNAME).toString();
        String passWord=object.get(CONS_PASS).toString();
//        for(Object key : object.keySet() )
//        {  String  str = key.toString();
//           if (str.equals(CONS_UNAME) && object.containsKey(CONS_UNAME))
//           {
//              nameCheck = 
//           }
//           if (str.equals(CONS_PASS) && object.containsKey(CONS_PASS))
//           {
//              passWord = 
//           }
//        }
        
        
        
        
//        LoginService login = new LoginService();
        try{
             boolean result= login.checkUser(nameCheck,passWord);
             if(result){
                 HttpSession session = req.getSession(true);
                if(session!=null)
                session.setAttribute("name", nameCheck);
                
                res.setContentType("application/json");
                String urlBase = req.getRequestURI();
               String[] urlNeeded= urlBase.split("/login");
                res.getWriter().write(urlNeeded[0]+"/Shopping.html");
                //res.sendRedirect("Shopping.html");
                 //RequestDispatcher rd = req.getRequestDispatcher("Shopping.html");
                 //rd.forward(req, res);
                   
//                res.setContentType("text/html");
//                PrintWriter pw = res.getWriter();
//                pw.println("<html>");
//                pw.println("<head><title>WelCome</title></title>");
//                pw.println("<body>");
//                pw.println("<h1>"+nameCheck+"</h1>");
//                pw.println("</body></html>");
             }
             else{
                 res.setContentType("application/json");
                 res.getWriter().write(0);
//                 res.setHeader("error","Invalid PassWord or UserName");
//                  res.sendRedirect(req.getRequestURI());
               
             }
                 
             System.out.println("@@@@@@@"+result);
        }catch(Exception e){
            System.err.println(e);
                    
        }
      
       
        
        
        
    }
    
}
