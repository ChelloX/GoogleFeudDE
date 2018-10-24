package de.dhbw.webprog;

import java.io.IOException;
import java.util.AbstractMap;
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
public class IndexServlet1 extends HttpServlet {

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

            DatenbankVerbindung dbv = new DatenbankVerbindung();
            dbv.updatePunkte("daniel", 100);
            dbv.getPunkte();

        } else {
            session = request.getSession();
        }

        if (session.getAttribute("fehlversuch") != null) {
            int versuche = (int) session.getAttribute("counterVersuche");
            session.setAttribute("counterVersuche", versuche - 1);

            if (versuche - 1 == 0) {
                if (session.getAttribute("spielerName") != null) {
                    DatenbankVerbindung dbv = new DatenbankVerbindung();
                    dbv.updatePunkte((String) session.getAttribute("spielerName"), (int) session.getAttribute("counterPunkte"));

                    session.setAttribute("fehlversuche", null);
                    session.setAttribute("kategorieGewaehlt", null);
                }
            }
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

        String spielerName = request.getParameter("spielerName");

        if (spielerName != null) {
            session.setAttribute("spielerName", spielerName);
        }

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

                session.setAttribute("counterPunkte", (int) session.getAttribute("counterPunkte") + 1000);
                Iterator<Map.Entry<Integer, Map.Entry<String, Boolean>>> it = vorschlaege.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Map.Entry<String, Boolean>> entry2 = it.next();
                    if (entry.equals(entry2.getValue())) {
                        Integer key = entry2.getKey();

                        HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege2 = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute("vorschlaege");
                        Map.Entry<String, Boolean> valueNeu = new AbstractMap.SimpleEntry<>(vorschlaege2.get(key).getKey(), true);
                        vorschlaege.replace(key, valueNeu);
                        session.setAttribute("vorschlaege", vorschlaege);

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
