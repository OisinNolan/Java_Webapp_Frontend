/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author oisinnolan
 */
public class ProfilAstralSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProfilAstral profilAstral = (ProfilAstral)request.getAttribute("profilAstral");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (profilAstral != null);
        container.addProperty("connexion", connexion);

        if (profilAstral != null) {
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("animalTotem", profilAstral.getAnimalTotem());
            jsonClient.addProperty("couleur", profilAstral.getCouleur());
            jsonClient.addProperty("signeChinois", profilAstral.getSigneChinois());
            jsonClient.addProperty("signeZodiaque", profilAstral.getSigneZodiaque());                    

            container.add("profilAstral", jsonClient);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}
