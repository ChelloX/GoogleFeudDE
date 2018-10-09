<%-- 
    Document   : index
    Created on : 02.10.2018, 16:32:40
    Author     : DSemling
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="de.dhbw.webprog.GoogleSearchAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoogleFeudDE</title>
    </head>
    <body>
        <div align="center">
            <img  src="IMAGES/Google_img.png" width="300" height="100" >
            <br><br>
            <h2>Wähle eine Kategorie:</h2>
            
            <input name="Input" type="text" size="53">
            <br><br>
            <b>
                <table style="width: 30%">
                    <tr>
                        <td align="center"><button>Kultur</button></td>
                        <td align="center"><button>Menschen</button></td>
                        <td align="center"><button>Namen</button></td>
                        <td align="center"><button>Fragen</button></td>
                    </tr>
                </table >
            </b>
            <br>
            <table border="1" style="width: 20%">

                <%
                    GoogleSearchAPI gsa = new GoogleSearchAPI();
                    ArrayList<String> antworten = gsa.gibAntwortenZuString("Karlsruhe");

                    StringBuffer sb = new StringBuffer();
                    int counter = 1;
                    sb.append("<tr> \n");

                    for (String s : antworten) {

                        sb.append("<td align=\"center\">" + s + "</td> \n");

                        if (counter % 2 == 0) {
                            sb.append("</tr> \n");
                             sb.append("<tr> \n");
                        }
                        counter++;
                    }

                    sb.append("<tr> \n");
                %>

                <%= sb.toString()%>
            </table>
            <br>
            <table style="width: 50%">
                <tr>
                    <td align="center"><big><font color="#0000FF">Runde</big></td>
                <td align="center"><big><font color="#0000FF">verbleibende Versuche</big></td>
                <td align="center"><big><font color="#0000FF">Punkte Gesamt</big></td>
                <td align="center"><big><font color="#0000FF">Punkte diese Runde</big></td>
                </tr>
                <tr>

                    <td align="center"><b><font color="#0000FF">Platzhalter</b></td>
                    <td align="center"><b><font color="#0000FF">Platzhalter</b></td>
                    <td align="center"><b><font color="#0000FF">Platzhalter</b></td>
                    <td align="center"><b><font color="#0000FF">Platzhalter</b></td>
                </tr>
            </table>
        </div>
    </body>
</html>
