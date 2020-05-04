<%-- 
    Document   : inscription
    Created on : 6 Apr 2020, 13:26:12
    Author     : oisinnolan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscription</title>
    </head>
    <body>
        <h1>PredictIF</h1>
        <h3>Inscription</h3>
        <p>Découvrez votre profil zodiaque !</p>
         <form action="inscription" method="post">
            <p>
                <input name="nom" value="${param.nom}" placeholder="Nom">
                <span class="error">${messages.nom}</span>
            </p>
            <p>
                <input name="prenom" value="${param.prenom}" placeholder="Prénom">
                <span class="error">${messages.prenom}</span>
            </p>
            <p>
                <input type="date" name="dateNaissance" value="${param.dateNaissance}" placeholder="Date de naissance">
                <span class="error">${messages.dateNaissance}</span>
            </p>
            <p>
                <input type="radio" name="genre" value="M" checked>
                <label for="masc">Homme</label>
                <input type="radio" name="genre" value="F">
                <label for="fem">Femme</label>
                <span class="error">${messages.genre}</span>
            </p>
            <p>
                <input name="adresse" value="${param.adresse}" placeholder="Adresse postale">
                <span class="error">${messages.adresse}</span>
            </p>
            <p>
                <input name="noTelephone" value="${param.noTelephone}" placeholder="Numéro Telephone">
                <span class="error">${messages.noTelephone}</span>
            </p>
            <p>
                <input name="mail" value="${param.mail}" placeholder="Adresse e-mail">
                <span class="error">${messages.mail}</span>
            </p>
            <p>
                <input name="motDePasse" value="${param.motDePasse}" placeholder="Mot de passe">
                <span class="error">${messages.motDePasse}</span>
            </p>
            <p>
                <input name="confirmMotDePasse" value="${param.confirmMotDePasse}" placeholder="Confirmer mot de passe">
                <span class="error">${messages.confirmMotDePasse}</span>
            </p>
            <p>
                <input type="submit" value="Enregistrer">
                <span class="success">${messages.success}</span>
            </p>
        </form>
    </body>
</html>
