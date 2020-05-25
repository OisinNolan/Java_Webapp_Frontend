/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pitf9
 */
public class GetDonneesConsultationAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employe employe = (Employe) session.getAttribute("employe");
        
        Service service = new Service();
        Consultation travailEnCours = service.getConsultationEnCours(employe);
        
        Client client = travailEnCours.getClient();
        ProfilAstral profilAstral = client.getProfilAstral();
        String nomMedium = travailEnCours.getMedium().getDenomination();
        
        List<Consultation> historique = service.listerHistoriqueConsultations(client);
        
        request.setAttribute("client", client);
        request.setAttribute("profilAstral", profilAstral);
        request.setAttribute("historique", historique);
        request.setAttribute("nomMedium", nomMedium);
        
    }
    
}
