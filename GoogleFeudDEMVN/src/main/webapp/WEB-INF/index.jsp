<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="de.dhbw.webprog.IndexServlet" %>
<%@page import="de.dhbw.webprog.GoogleSearchAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoogleFeudDE</title>

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

        <style>
            <%@include file="/WEB-INF/myCss.css"%>
        </style>
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
                    <!-- Name eingabe -->
                    <c:if test="${zeigeAuswahlKategorie == true}">
                        <form method="post">
                            <div class="row">
                                <div class="col-md-4" style="margin-top: 5px">
                                    <button type="submit" id="btn1" name="btn1" value="btn1" class="btn btn-success btn-block">
                                        Was-Fragen
                                    </button>
                                </div>

                                <div class="col-md-4" style="margin-top: 5px">
                                    <button type="submit" id="btn2" name="btn2" value="btn2" class="btn btn-success btn-block">
                                        Wann-Fragen
                                    </button>
                                </div>
                                <div class="col-md-4" style="margin-top: 5px">
                                    <button type="submit" id="btn3" name="btn3" value="btn3" class="btn btn-success btn-block">
                                        Wo-Fragen
                                    </button>
                                </div>
                            </div>

                            <div class="row" style="margin-top: 15px">

                                <div class="col-md-2"> </div>
                            </div>

                            <div class="row">
                                <div class="col-md-2"> </div>
                                <c:if test="${spielerName == null}">
                                    <input class="form-control col-md-8" style="margin-top: 5px" name="spielerName" id="spielerName" type="text" placeholder="Dein Name"/>
                                </c:if>
                                <c:if test="${spielerName != null}">
                                    <input class="form-control col-md-8" style="margin-top: 5px" name="spielerName" id="spielerName" type="text" value="${spielerName}" readonly/>
                                </c:if>
                                <div class="col-md-2"> </div>
                            </div>
                        </form>

                        <!-- Anzeige Bestenliste -->
                        <div class="justify-content-center" style="margin-top: 20px">
                            <table class="table table-striped table-bordered" >
                                <thead>
                                    <tr>
                                        <td>
                                            Name
                                        </td>
                                        <td>
                                            Punkte
                                        </td>
                                    </tr>
                                </thead>

                                <c:forEach items="${bestenliste}" var="eintrag">
                                    <tr>
                                        <td><div> ${eintrag.key} </div></td>
                                        <td><div> ${eintrag.value} </div></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>

                    <!-- Eingabefeld -->
                    <c:if test="${zeigeAuswahlKategorie == false}">
                        <div class="row justify-content-center" id="suche" style="margin-top: 20px" >
                            <div class="col-12 col-md-10 col-lg-8">
                                <form class="card c ard-sm" method="post">
                                    <div class="card-body row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-search h4 text-body"></i>
                                        </div>
                                        <div class="col">
                                            <input  autocomplete=\"off\" class="form-control form-control-lg form-control-borderless" name="eingabe" type="search" placeholder="${zuSuchenderString}" />
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

                                <c:forEach items="${vorschlaege}" var="eintrag">
                                    <tr>
                                        <c:if test="${eintrag.value.value == false}" >
                                            <td></td>
                                        </c:if>
                                        <c:if test="${eintrag.value.value == true}" >
                                            <c:if test="${letzterTreffer == eintrag.value.key}" >
                                                <td> <div class="slide-left">${eintrag.value.key} </div> </td>
                                            </c:if>
                                            <c:if test="${letzterTreffer != eintrag.value.key}">
                                                <td> <div>${eintrag.value.key} </div> </td>
                                            </c:if>                                     
                                        </c:if>
                                    </tr>
                                </c:forEach>
                        </div>
                        </table>   
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
<footer>

    <div class = "row fixed-bottom" >
        <div class="col-md-2">
        </div>
        <div class="col-md-8">
            <div class="text-center">
                <div class="col-md-2 badge badge-info" id="roundnumber">RUNDE<br /><span id="roundnumberspan">${counterRunde}</span></div>
                <div class="col-md-2 badge badge-info" id="trynumber">VERSUCHE<br /><span id="trynumber">${counterVersuche}</span></div>
                <div class="col-md-2 badge badge-info" id="totalscore">GESAMTPUNKTZAHL<br /><span id="totalscore">${counterPunkte}</span></div>
                <br><br></div>
        </div>
        <div class="col-md-2">
        </div>
    </div>        

</footer>
</html>
