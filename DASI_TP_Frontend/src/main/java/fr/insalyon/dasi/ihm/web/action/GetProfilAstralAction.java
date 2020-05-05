/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.ProfilAstral;
import fr.insalyon.dasi.metier.service.Service;
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
        request.setAttribute("profilAstral", client.getProfilAstral());
        
    }
}
