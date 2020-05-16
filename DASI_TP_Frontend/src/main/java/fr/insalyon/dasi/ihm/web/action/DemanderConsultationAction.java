/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pitf9
 */
public class DemanderConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Long idClient = (Long)session.getAttribute("idClient");
        Long idMediumChoisi = Long.parseLong(request.getParameter("idMediumChoisi"));
        
        Service service = new Service();
        
        Client client = service.rechercherClientParId(idClient);
        Medium medium = service.rechercherMediumParId(idMediumChoisi);
        Consultation consultation = service.demanderConsultation(client, medium);
        
        if (consultation != null) {
            request.setAttribute("message", "200");
        } else {
            request.setAttribute("message", "500");
        }
    }
    
}
