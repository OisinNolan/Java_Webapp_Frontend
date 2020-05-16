/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pitf9
 */
public class GetMediumsDisponiblesAction extends Action{
    
    @Override
    public void executer(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Long idClient = (Long) session.getAttribute("idClient");
        
        Service service = new Service();
        
        List<Medium> mediums = service.listerMediums();
        request.setAttribute("mediums", mediums);
        
        Client client = service.rechercherClientParId(idClient);
        Long idMediumChoisi = service.getMediumConsultationEnCours(client);
        request.setAttribute("idMediumChoisi", idMediumChoisi);
    }
}
