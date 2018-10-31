package de.dhbw.webprog;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
            session.setAttribute(Statics.getZEIGE_AUSWAHL_KATEGORIE(), true);
            session.setAttribute(Statics.getCOUNTER_VERSUCHE(), 3);
            session.setAttribute(Statics.getCOUNTER_RUNDE(), 0);
            session.setAttribute(Statics.getCOUNTER_PUNKTE(), 0);
        } else {
            session = request.getSession();
        }

        if (session.getAttribute(Statics.getKATEGORIE()) != null) {
            session.setAttribute(Statics.getZEIGE_AUSWAHL_KATEGORIE(), false);

            String kategorie = (String) session.getAttribute(Statics.getKATEGORIE());
            GoogleSearchAPI api = new GoogleSearchAPI();
            GoogleTrendsAPI t_api = new GoogleTrendsAPI();
            String zuSuchenderString = t_api.gibZufaelligeFrageZuKategorie(kategorie);
            HashMap<Integer, String> api_antwort = api.gibAntwortenZuString(zuSuchenderString);

            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = new HashMap<Integer, Map.Entry<String, Boolean>>();

            for (Map.Entry<Integer, String> e : api_antwort.entrySet()) {
                vorschlaege.put(e.getKey(), new AbstractMap.SimpleEntry(e.getValue(), false));
            }

            session.setAttribute(Statics.getZU_SUCHENDER_STRING(), zuSuchenderString);
            session.setAttribute(Statics.getVORSCHLAEGE(), vorschlaege);
            session.setAttribute(Statics.getCOUNTER_VERSUCHE(), 3);
            session.setAttribute(Statics.getCOUNTER_RUNDE(), (int) session.getAttribute(Statics.getCOUNTER_RUNDE()) + 1);
        }

        DatenbankVerbindung dbv = new DatenbankVerbindung();
        List<Map.Entry<String, Integer>> bestenliste = dbv.getPunkte();
        if (bestenliste != null) {
            session.setAttribute(Statics.getBESTENLISTE(), bestenliste);
        }

        session.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        //Kategorie-Auswahl
        String btn1 = request.getParameter(Statics.getBTN1());
        String btn2 = request.getParameter(Statics.getBTN2());
        String btn3 = request.getParameter(Statics.getBTN3());

        String spielerName = request.getParameter(Statics.getSPIELER_NAME());

        if (spielerName != null) {
            session.setAttribute(Statics.getSPIELER_NAME(), spielerName);
        }

        String kategorie = null;
        if (btn1 != null) {
            kategorie = Statics.getWAS();
        }

        if (btn2 != null) {
            kategorie = Statics.getWANN();
        }

        if (btn3 != null) {
            kategorie = Statics.getWO();
        }

        session.setAttribute(Statics.getKATEGORIE(), kategorie);

        //Eingabe
        String eingabe = request.getParameter(Statics.getEINGABE());

        if (eingabe != null) {
            String eingabe_con = session.getAttribute(Statics.getZU_SUCHENDER_STRING()) + " " + eingabe;
            HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute(Statics.getVORSCHLAEGE());

            Map.Entry entry = new AbstractMap.SimpleEntry(eingabe_con, false);

            boolean treffer = vorschlaege.containsValue(entry);
            if (treffer) {

                session.setAttribute(Statics.getCOUNTER_PUNKTE(), (int) session.getAttribute(Statics.getCOUNTER_PUNKTE()) + 1000);
                Iterator<Map.Entry<Integer, Map.Entry<String, Boolean>>> it = vorschlaege.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Map.Entry<String, Boolean>> entry2 = it.next();
                    if (entry.equals(entry2.getValue())) {
                        Integer key = entry2.getKey();

                        HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege2 = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute(Statics.getVORSCHLAEGE());
                        Map.Entry<String, Boolean> valueNeu = new AbstractMap.SimpleEntry<>(vorschlaege2.get(key).getKey(), true);
                        vorschlaege.replace(key, valueNeu);
                        session.setAttribute(Statics.getVORSCHLAEGE(), vorschlaege);

                        session.setAttribute(Statics.getTREFFER_KEY(), key);
                    }
                }
            } else {
                int versuche = (int) session.getAttribute(Statics.getCOUNTER_VERSUCHE());
                session.setAttribute(Statics.getCOUNTER_VERSUCHE(), versuche - 1);

                if (versuche - 1 == 0) {
                    String tempName = (String) session.getAttribute(Statics.getSPIELER_NAME());
                    if (session.getAttribute(Statics.getSPIELER_NAME()) != null && !session.getAttribute(Statics.getSPIELER_NAME()).equals("")) {
                        DatenbankVerbindung dbv = new DatenbankVerbindung();
                        dbv.updatePunkte((String) session.getAttribute(Statics.getSPIELER_NAME()), (int) session.getAttribute(Statics.getCOUNTER_PUNKTE()));
                    }
                    session.setAttribute(Statics.getZEIGE_AUSWAHL_KATEGORIE(), true);
                    session.setAttribute(Statics.getKATEGORIE(), null);
                }
            }
        }

        //Reset
        String reset = request.getParameter(Statics.getRESET());
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
