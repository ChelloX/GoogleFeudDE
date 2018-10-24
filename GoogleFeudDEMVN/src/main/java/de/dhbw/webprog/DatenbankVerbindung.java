package de.dhbw.webprog;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author DSemling
 */
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

    public HashMap<String, Integer> getPunkte() {
        HashMap<String, Integer> spielerPunkteMap = null;
        try {
            PreparedStatement prepstmt = conn.prepareStatement("SELECT spieler_name, punkte FROM bestenliste;");
            ResultSet rs = prepstmt.executeQuery();

            spielerPunkteMap = new HashMap<>();

            while (rs.next()) {
                spielerPunkteMap.put(rs.getString(1), rs.getInt(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatenbankVerbindung.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return spielerPunkteMap;
        }
    }

}
