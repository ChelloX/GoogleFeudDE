<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="de.dhbw.webprog.GoogleSearchAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoogleFeudDE</title>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">

                <div class="col-md-12">

                    <!-- Titel-Bild -->
                    <!-- OnClick Session-Reset + Shortcut über r -->
                    <div class="row">
                        <div class="col-md-12" style="margin-top: 20px">
                            <div class="text-center">
                                <form method="post">
                                    <input type="hidden" name="reset" value="reset" type="text" />
                                    <input type="image" src="IMAGES/Google_img.png" width="300" height="100" accesskey="r"/>
                                </form>

                                <!--<img type="submit" src="IMAGES/Google_img.png" width="300" height="100" />-->
                            </div>
                        </div>
                    </div>

                    <!-- Buttons für Kategorien -->    
                    <c:if test="${kategorieGewaehlt == false}">
                        <div class="row">
                            <div class="col-md-4" style="margin-top: 5px">
                                <form method="post">
                                    <!-- Input als type hidden: dient nur zur Unterscheidung der Buttons in IndexServlet -->
                                    <input type="hidden" name="btn1" type="text" value="btn1"/>
                                    <button type="submit" id="btn1" class="btn btn-success btn-block">
                                        Was-Fragen
                                    </button>
                                </form>
                            </div>

                            <div class="col-md-4" style="margin-top: 5px">
                                <form method="post">
                                    <input type="hidden" name="btn2" type="text" value="btn2"/>
                                    <button type="submit" id="btn2" class="btn btn-success btn-block" onClick="btn2()">
                                        Wann-Fragen
                                    </button>
                                </form>
                            </div>
                            <div class="col-md-4" style="margin-top: 5px">
                                <form method="post">
                                    <input type="hidden" name="btn3" type="text" value="btn3"/>
                                    <button type="submit" class="btn btn-success btn-block">
                                        Wo-Fragen
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:if>

                    <!-- Eingabefeld -->
                    <c:if test="${kategorieGewaehlt == true}">
                        <div class="row justify-content-center" id="suche" style="margin-top: 20px" >
                            <div class="col-12 col-md-10 col-lg-8">
                                <form class="card c ard-sm" method="post">
                                    <div class="card-body row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-search h4 text-body"></i>
                                        </div>
                                        <div class="col">
                                            <%
                                                StringBuilder sb1 = new StringBuilder();
                                                sb1.append("<input  class=\"form-control form-control-lg form-control-borderless\" name=\"eingabe\" type=\"search\" placeholder=\"");
                                                sb1.append(session.getAttribute("zuSuchenderString"));
                                                sb1.append("\">");
                                                %>
                                                
                                                <%= sb1.toString()%>
                                            <!--<input  class="form-control form-control-lg form-control-borderless" name="eingabe" type="search" placeholder="Hier steht ein text">-->
                                        </div>
                                        <div class="col-auto">
                                            <button class="btn btn-lg btn-success" type="submit">Eingabe</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!-- Anzeige der Google-Vorschläge -->
                        <div class="justify-content-center" style="margin-top: 20px">
                            <table class="table table-striped table-bordered" >
                                <thead>
                                    <tr>
                                        <td>
                                            Google-Vorschläge
                                        </td>
                                    </tr>
                                </thead>

                                <%
                                    StringBuilder sb = new StringBuilder();

                                    HashMap<Integer, Map.Entry<String, Boolean>> vorschlaege = (HashMap<Integer, Map.Entry<String, Boolean>>) session.getAttribute("vorschlaege");

                                    for (Map.Entry<Integer, Map.Entry<String, Boolean>> e : vorschlaege.entrySet()) {
                                        Integer key = e.getKey();
                                        Map.Entry<String, Boolean> value = e.getValue();

                                        String key2 = value.getKey();
                                        Boolean value2 = value.getValue();

                                        sb.append("<tr> \n");
                                        if (value2 == false) {
                                            sb.append("<td style=\"background-color: red\" id=\"" + key + "\"> \n");
                                            sb.append(key2 + "\n");
                                            sb.append("</td>");
                                        }

                                        if (value2 == true) {
                                            sb.append("<td style=\"background-color: green\" id=\"" + key + "\"> \n");
                                            sb.append(key2 + "\n");
                                            sb.append("</td>");
                                        }
                                    }
                                %>

                                <%= sb.toString()%>
                        </div>

                        </table>   
                    </c:if>

                </div>
            </div>
        </div>
    </div>
</body>
<footer>
    <!--
    <div class ="container" style="    bottom: 0;    position: fixed;    width: 100%; height: 120px;
    margin: auto;" -->

    <div class = "row fixed-bottom" >
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <div class="text-center">
                    <div class="col-md-2 badge badge-info" id="roundnumber">RUNDE<br /><span id="roundnumberspan">1</span></div>
                <div class="col-md-2 badge badge-info" id="trynumber">VERSUCHE<br /><span id="trynumber">1</span></div>
                <div class="col-md-2 badge badge-info" id="totalscore">GESAMTPUNKTZAHL<br /><span id="totalscore">1</span></div>
            </div>
        </div>
        <div class="col-md-2">
        </div>
    </div>        


</footer>

</html>
