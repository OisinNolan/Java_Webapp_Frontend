/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pitf9
 */
public class GetStatistiquesAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        
        Service service = new Service();
        
        Map<Medium, Integer> mediumMap = service.listerNombreParMedium();
        Map<Employe, List<Client>> repartition = service.listerRepartitionClients();
        List<Medium> topCinque = service.listerTopCinqueMediums();
        
        if (mediumMap != null && repartition != null && topCinque != null) {
            request.setAttribute("mediumMap", mediumMap);
            request.setAttribute("repartition", repartition);
            request.setAttribute("topCinque", topCinque);
        }
    }
    
}
