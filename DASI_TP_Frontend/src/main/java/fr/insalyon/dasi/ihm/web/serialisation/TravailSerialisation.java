/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pitf9
 */
public class TravailSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Consultation travail = (Consultation)request.getAttribute("travail");
        
        String errorMessage = (String)request.getAttribute("errorMessage");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (travail != null && errorMessage == null);
        container.addProperty("connexion", connexion);
        
        if (travail != null) {
            JsonObject jsonTravail = new JsonObject();
            jsonTravail.addProperty("id", travail.getId());
            
            // Formatting the date for display on frontend
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateDemande = simpleDateFormat.format(travail.getDateCreation());
            
            // The time also formatted
            pattern = "HH:mm";
            simpleDateFormat = new SimpleDateFormat(pattern);
            String heureDemande = simpleDateFormat.format(travail.getDateCreation());
            
            // Making a new object to store both date and time
            JsonObject jsonDemande = new JsonObject();
            jsonDemande.addProperty("date", dateDemande);
            jsonDemande.addProperty("heure", heureDemande);
            jsonTravail.add("demande", jsonDemande);
            
            jsonTravail.addProperty("medium", travail.getMedium().getDenomination());
            
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("id", travail.getClient().getId());
            jsonClient.addProperty("nom", travail.getClient().getNom());
            jsonClient.addProperty("prenom", travail.getClient().getPrenom());
            
            jsonTravail.add("client", jsonClient);
            container.add("travail", jsonTravail);
        }
        
        if(errorMessage != null) {
            container.addProperty("errorMessage", errorMessage);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}
