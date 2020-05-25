/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
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
        List<Consultation> historique = (List<Consultation>)request.getAttribute("historique");
        
        String errorMessage = (String)request.getAttribute("errorMessage");
        
        JsonObject container = new JsonObject();

        Boolean connexion = (profilAstral != null && errorMessage == null);
        container.addProperty("connexion", connexion);

        // Profil Astral
        if (profilAstral != null) {
            JsonObject jsonClient = new JsonObject();
            jsonClient.addProperty("animalTotem", profilAstral.getAnimalTotem());
            jsonClient.addProperty("couleur", profilAstral.getCouleur());
            jsonClient.addProperty("signeChinois", profilAstral.getSigneChinois());
            jsonClient.addProperty("signeZodiaque", profilAstral.getSigneZodiaque());                    

            container.add("profilAstral", jsonClient);
        }
        
        // historique
        if(historique != null){
            JsonArray jsonHistorique = new JsonArray();
            
            for (Consultation c : historique) {
                JsonObject jsonConsultation = new JsonObject();
                
                jsonConsultation.addProperty("id", c.getId());
                
                // Formatting the date for display on frontend
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String dateDemande = simpleDateFormat.format(c.getDateCreation());

                // The time also formatted
                pattern = "HH:mm";
                simpleDateFormat = new SimpleDateFormat(pattern);
                String heureDemande = simpleDateFormat.format(c.getDateCreation());
            
                // Making a new object to store both date and time
                JsonObject jsonDemande = new JsonObject();
                jsonDemande.addProperty("date", dateDemande);
                jsonDemande.addProperty("heure", heureDemande);
                jsonConsultation.add("demande", jsonDemande);
                
                jsonConsultation.addProperty("medium", c.getMedium().getDenomination());
                
                jsonHistorique.add(jsonConsultation);
            }
            
            container.add("historique", jsonHistorique);
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
