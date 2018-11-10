package de.dhbw.webprog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DatenbankVerbindung {

    private static Connection conn;

    public DatenbankVerbindung() {
        if (conn == null) {
            try {
                InitialContext c = new InitialContext();
                DataSource ds = (DataSource) c.lookup("java:app/jdbc/googlefeudde");
                conn = ds.getConnection();
            } catch (NamingException | SQLException ex) {
                Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updatePunkte(String name, int punkte) {
        try {
            PreparedStatement prepstmt = conn.prepareStatement("INSERT INTO bestenliste (spieler_name, punkte) VALUES (?, ?) ON DUPLICATE KEY UPDATE punkte = ?;");
            prepstmt.setString(1, name);
            prepstmt.setInt(2, punkte);
            prepstmt.setInt(3, punkte);

            prepstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Entry<String, Integer>> getPunkte() {
        
        try {
            List<Entry<String, Integer>> spielerPunkteList = new ArrayList<>();
            PreparedStatement prepstmt = conn.prepareStatement("SELECT spieler_name, punkte FROM bestenliste ORDER BY punkte desc LIMIT 10;");
            ResultSet rs = prepstmt.executeQuery();

            while (rs.next()) {
                spielerPunkteList.add(new AbstractMap.SimpleEntry<>(rs.getString(1), rs.getInt(2)));
            }

            return spielerPunkteList;
        } catch (SQLException ex) {
            Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
