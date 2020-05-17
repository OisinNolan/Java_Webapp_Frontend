/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pitf9
 */
public class GenererPredictionAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        
        int amour = Integer.parseInt(request.getParameter("amour"));
        int sante = Integer.parseInt(request.getParameter("sante"));
        int travail = Integer.parseInt(request.getParameter("travail"));
        
        HttpSession session = request.getSession();
        Employe employe = (Employe)session.getAttribute("employe");
        
        Service service = new Service();
        Consultation consultation = service.rechercherConsultationParId(employe.getTravailActuel());
        
        String prediction = service.genererPrediction(consultation, amour, sante, travail);
        if (prediction != null) {
            request.setAttribute("message", prediction);
            
        } else {
            request.setAttribute("message", "500");
        }
    }
    
}
