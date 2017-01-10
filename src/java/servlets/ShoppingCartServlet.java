/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.CartService;
import java.io.PrintWriter;
import javax.ejb.EJB;


/**
 *
 * @author hp
 */
@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
    
    @EJB private CartService service;
     
   
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException{
        String validate = req.getSession().getAttribute("name").toString();
        System.out.print("Session"+validate);
        if(validate!=null){
            String user= validate;
          
           String item= req.getParameter("Items");
           String qty= req.getParameter("Quantity");
           
           try{
               boolean test= service.Shope(item, qty, user);
               if(test){
                     res.setContentType("text/html");
                PrintWriter pw = res.getWriter();
                pw.println("<html>");
                pw.println("<head><title>Success</title></title>");
                pw.println("<body>");
                pw.println("<h1>"+"Processed Your Request "+user+"</h1>");
                pw.println("</body></html>");
               }
           }catch(Exception e){
               e.printStackTrace();
           }
                
            
        }
        else{
            res.sendRedirect("index.html");
        }
    }
    
    
}
