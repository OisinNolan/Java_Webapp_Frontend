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
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pitf9
 */
public class StatistiquesSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Medium, Integer> mediumMap = (Map<Medium, Integer>)request.getAttribute("mediumMap");
        Map<Employe, List<Client>> repartition = (Map<Employe, List<Client>>) request.getAttribute("repartition");
        List<Medium> topCinque = (List<Medium>) request.getAttribute("topCinque");
        String errorMessage = (String)request.getAttribute("errorMessage");
        
        JsonObject container = new JsonObject();
        
        Boolean connexion = (mediumMap != null && repartition != null && topCinque != null && errorMessage == null);
        container.addProperty("connexion", connexion);
        
        // medium map
        if (mediumMap != null) {
            JsonArray jsonMediumMap = new JsonArray();
            
            for (Medium m : mediumMap.keySet()) {
                JsonObject jsonMedium = new JsonObject();
                
                jsonMedium.addProperty("id", m.getId());
                jsonMedium.addProperty("denomination", m.getDenomination());
                jsonMedium.addProperty("nbConsultations", mediumMap.get(m));
                
                jsonMediumMap.add(jsonMedium);
            }
            
            container.add("mediumMap", jsonMediumMap);
        }
        
        // repartition
        if (repartition != null) {
            JsonArray jsonRepartition = new JsonArray();
            
            for (Employe e : repartition.keySet()) {
                JsonObject jsonEmploye = new JsonObject();
                
                jsonEmploye.addProperty("id", e.getId());
                jsonEmploye.addProperty("nom", e.getNom());
                jsonEmploye.addProperty("prenom", e.getPrenom());

                JsonArray jsonClients = new JsonArray();                
                List<Client> clients = repartition.get(e);
                for (Client c : clients) {
                    JsonObject jsonClient = new JsonObject();
                    
                    jsonClient.addProperty("id", c.getId());
                    jsonClient.addProperty("nom", c.getNom());
                    jsonClient.addProperty("prenom", c.getPrenom());
                    
                    jsonClients.add(jsonClient);
                }
                jsonEmploye.add("clients", jsonClients);
                
                jsonRepartition.add(jsonEmploye);
            }
            
            container.add("repartition", jsonRepartition);
        }
        
        // top 5 médiums
        if (topCinque != null) {
            JsonArray jsonTopCinque = new JsonArray();
            
            for (Medium m : topCinque) {
                JsonObject jsonMedium = new JsonObject();
                
                jsonMedium.addProperty("id", m.getId());
                jsonMedium.addProperty("denomination", m.getDenomination());
                
                jsonTopCinque.add(jsonMedium);
            }
            
            container.add("topCinque", jsonTopCinque);
        }
        
        if (errorMessage != null) {
            container.addProperty("errorMessage", errorMessage);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}
