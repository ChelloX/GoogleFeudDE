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
import java.util.Iterator;
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

        HttpSession session = null;
        if (request.getSession(false) == null) {
            session = request.getSession(true);
            session.setAttribute("kategorieGewaehlt", false);
            session.setAttribute("counterVersuche", 3);
            session.setAttribute("counterRunde", 0);
            session.setAttribute("counterPunkte", 0);
        } else {
            session = request.getSession();
        }

        /**
         * HttpSession session = request.getSession(true);
         * session.setAttribute("kategorieGewaehlt", false);
         * session.setAttribute("counterVersuche", 3);
         * session.setAttribute("counterRunde", 0);
         * session.setAttribute("counterPunkte", 0); *
         */
        if (session.getAttribute("fehlversuch") != null) {
            int versuche = (int) session.getAttribute("counterVersuche");
            session.setAttribute("counterVersuche", versuche - 1);
        }

        if (session.getAttribute("trefferKey") != null) {
            session.setAttribute("kategorieGewaehlt", true);
            int treffer = (int) session.getAttribute("trefferKey");
            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute("vorschlaege");
            Map.Entry<String, Boolean> valueNeu = new AbstractMap.SimpleEntry<String, Boolean>(vorschlaege.get(treffer).getKey(), true);
            vorschlaege.replace(treffer, valueNeu);
            session.setAttribute("vorschlaege", vorschlaege);

        }

        if (session.getAttribute("kategorie") != null) {
            String kategorie = (String) session.getAttribute("kategorie");
            session.setAttribute("kategorieGewaehlt", true);

            GoogleSearchAPI api = new GoogleSearchAPI();
            GoogleTrendsAPI t_api = new GoogleTrendsAPI();
            String zuSuchenderString = t_api.gibZufaelligeFrageZuKategorie(kategorie);
            HashMap<Integer, String> api_antwort = api.gibAntwortenZuString(zuSuchenderString);

            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = new HashMap<Integer, Map.Entry<String, Boolean>>();

            for (Map.Entry<Integer, String> e : api_antwort.entrySet()) {
                vorschlaege.put(e.getKey(), new AbstractMap.SimpleEntry(e.getValue(), false));
            }

            session.setAttribute("zuSuchenderString", zuSuchenderString);
            session.setAttribute("vorschlaege", vorschlaege);
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
        if (btn1 != null) {
            kategorie = "Was";
        }

        if (btn2 != null) {
            kategorie = "Wann";
        }

        if (btn3 != null) {
            kategorie = "Wo";
        }

        session.setAttribute("kategorie", kategorie);

        //Eingabe
        String eingabe = request.getParameter("eingabe");

        if (eingabe != null) {
            eingabe = session.getAttribute("zuSuchenderString") + " " + eingabe;
            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute("vorschlaege");

            Map.Entry entry = new AbstractMap.SimpleEntry(eingabe, false);

            boolean treffer = vorschlaege.containsValue(entry);
            if (treffer) {
                Iterator<Map.Entry<Integer, Map.Entry<String, Boolean>>> it = vorschlaege.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Map.Entry<String, Boolean>> entry2 = it.next();
                    if (entry.equals(entry2.getValue())) {
                        Integer key = entry2.getKey();

                        session.setAttribute("trefferKey", key);
                    }
                }
            } else {
                session.setAttribute("fehlversuch", true);
            }
        }

        //Reset
        String reset = request.getParameter("reset");
        if (reset != null) {
            session.invalidate();
        }

        response.sendRedirect(request.getRequestURI());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
