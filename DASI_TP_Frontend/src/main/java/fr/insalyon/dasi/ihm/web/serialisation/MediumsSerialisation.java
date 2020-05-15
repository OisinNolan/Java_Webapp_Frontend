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
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pitf9
 */
public class MediumsSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
        
        String errorMessage = (String)request.getAttribute("errorMessage");
        
        JsonObject container = new JsonObject();
        
        Boolean connexion = (mediums != null &&errorMessage == null);
        container.addProperty("connexion", connexion);
        
        JsonArray jsonMediums = new JsonArray();
        if (mediums != null){
            for (Medium m : mediums) {
                JsonObject jsonMedium = new JsonObject();
                
                Class c = m.getClass();
                // Add name of the Medium type
                String mediumType = mediumTypeString(c.getName());
                jsonMedium.addProperty("class", mediumType);

                // Iterate through the fields of each medium and add to jsonMedium
                
                // Shared fields
                Field[] fields = Medium.class.getDeclaredFields();
                for (Field field : fields) {

                    String fieldName = field.getName();
                    String getMethod = getMethodName(fieldName);
                    String fieldValue = null;
                    try {
                        // Get getter
                        Method getter = Medium.class.getMethod(getMethod, null);
                        // Get result of the getter
                        fieldValue = getter.invoke(m, null).toString();
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(Medium.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Save the value to jsonMedium
                    jsonMedium.addProperty(fieldName, fieldValue);
                }
                
                // Medium type specific fields
                fields = c.getDeclaredFields();
                for (Field field : fields) {

                    String fieldName = field.getName();
                    String getMethod = getMethodName(fieldName);
                    String fieldValue = null;
                    try {
                        // Get getter
                        Method getter = c.getMethod(getMethod, null);
                        // Get result of the getter
                        fieldValue = getter.invoke(m, null).toString();
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(c.getName()).log(Level.SEVERE, null, ex);
                    }
                    // Save the value to jsonMedium
                    jsonMedium.addProperty(fieldName, fieldValue);
                }

                // Append Medium to the list
                jsonMediums.add(jsonMedium);
            }
        }     
        container.add("mediums", jsonMediums);
        
        System.out.println(container);
        
        if (errorMessage != null) {
            container.addProperty("errorMessage", errorMessage);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
    private String getMethodName(String fieldName) {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String rest = fieldName.substring(1);
        return "get" + firstLetter + rest;
    }
    
    private String mediumTypeString(String className) {
        int dot = className.lastIndexOf('.') + 1;
        return className.substring(dot);
    }
    
}
