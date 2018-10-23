/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import com.sun.webkit.PageCache;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DSemling
 */
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        session.setAttribute("kategorieGewaehlt", false);
        String kategorie = (String) session.getAttribute("kategorie");
        
        if(kategorie != null) {
            session.setAttribute("kategorieGewaehlt", true);
            
            GoogleSearchAPI api = new GoogleSearchAPI();
            HashMap<Integer,String> api_antwort = api.gibAntwortenZuString(kategorie);
            
            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = new  HashMap<Integer, Map.Entry<String, Boolean>>();
            
            for(Map.Entry<Integer,String> e : api_antwort.entrySet()) {
                vorschlaege.put(e.getKey(), new AbstractMap.SimpleEntry(e.getValue(), false));
            }
                     
            session.setAttribute("vorschlaege", vorschlaege);
            session.setAttribute("treffer", api);
        }
        
        session.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        
        //Kategorie-Auswahl
        String btn1 = request.getParameter("btn1");
        String btn2 = request.getParameter("btn2");
        String btn3 = request.getParameter("btn3");
        
        String kategorie = null;
        if(btn1 != null) {
            kategorie ="btn1";
        }
        
        if(btn2 != null) {
            kategorie = "btn2";
        }
        
        if(btn3 != null) {
            kategorie = "btn3";
        }
        
        session.setAttribute("kategorie", kategorie);
        
        //Eingabe
        String eingabe = request.getParameter("eingabe");
        
        if(eingabe != null) {
            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute("vorschlaege");
        }
        
        
        //Reset
        String reset = request.getParameter("reset");
        if(reset != null) {
            session.invalidate();
        }
        
        response.sendRedirect(request.getRequestURI());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
