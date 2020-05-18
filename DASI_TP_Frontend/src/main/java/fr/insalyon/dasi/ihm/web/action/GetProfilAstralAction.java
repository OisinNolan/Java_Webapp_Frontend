/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Consultation;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author oisinnolan
 */
public class GetProfilAstralAction extends Action  {
    @Override
    public void executer(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long idClient = (Long) session.getAttribute("idClient");
        
        Service service = new Service();
        
        Client client = service.rechercherClientParId(idClient);
        List<Consultation> historique = service.listerHistoriqueConsultations(client);
        
        request.setAttribute("profilAstral", client.getProfilAstral());
        request.setAttribute("historique", historique);
    }
}
