/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

/**
 *
 * @author hp
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet{
    @EJB service.SignUpService service;
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res ) throws  IOException, ServletException{
        
         JSONParser parser = new JSONParser();
          JSONObject valuesObject=null;
        String userName="";
        String passWord="";
        String emailId="";
        try{
            valuesObject = (JSONObject) parser.parse(req.getReader());
            userName = valuesObject.get("sUserName").toString();
            passWord=valuesObject.get("sPassWord").toString();
            emailId=valuesObject.get("sEmailId").toString();
            
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        try {
            service.storeUser(userName, passWord, emailId);
            res.getWriter().write(1);
            
            // return true;
        } catch (SQLException ex) {
            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
