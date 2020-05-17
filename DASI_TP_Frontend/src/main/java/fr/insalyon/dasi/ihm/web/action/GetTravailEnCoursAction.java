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
public class GetTravailEnCoursAction extends Action {
    
    @Override
    public void executer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("idEmploye");
        
        Service service = new Service();
        Employe employe = service.rechercherEmployeParId(employeId);
        Consultation travailEnCours = service.getConsultationEnCours(employe);
        request.setAttribute("travail", travailEnCours);
    }
    
}
