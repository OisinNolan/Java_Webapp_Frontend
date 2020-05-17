/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pitf9
 */
public class ValiderConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employe employe = (Employe) session.getAttribute("employe");
        
        String commentaire = request.getParameter("commentaire");
        
        Service service = new Service();
        Consultation consultation = service.rechercherConsultationParId(employe.getTravailActuel());
        
        Long consultationId = service.validerConsultation(consultation, commentaire);
        if (consultationId != null && consultationId.equals(consultation.getId())) {
            request.setAttribute("message", "200");
            
        } else {
            request.setAttribute("message", "500");
        }
    }
    
}
