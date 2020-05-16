/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.ihm.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import fr.insalyon.dasi.ihm.web.serialisation.Serialisation;
import fr.insalyon.dasi.ihm.web.serialisation.ProfilClientSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.EmployeSerialisation;
import fr.insalyon.dasi.ihm.web.action.Action;
import fr.insalyon.dasi.ihm.web.action.AuthentifierClientAction;
import fr.insalyon.dasi.ihm.web.action.GetClientConnecte;
import fr.insalyon.dasi.ihm.web.action.AuthentifierEmployeAction;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.ihm.web.action.DemanderConsultationAction;
import fr.insalyon.dasi.ihm.web.action.GetMediumsDisponiblesAction;
import fr.insalyon.dasi.ihm.web.action.GetProfilAstralAction;
import fr.insalyon.dasi.ihm.web.action.GetTravailEnCoursAction;
import fr.insalyon.dasi.ihm.web.action.SInscrireAction;
import fr.insalyon.dasi.ihm.web.serialisation.MediumsSerialisation;
import fr.insalyon.dasi.ihm.web.serialisation.ProfilAstralSerialisation;
/**
 *
 * @author oisinnolan
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        
        String todo = request.getParameter("todo");

        Action action = null;
        Serialisation serialisation = null;
        
        if (todo != null) {
            switch (todo) {
                case "connecter":
                    action = new AuthentifierClientAction();
                    serialisation = new ProfilClientSerialisation();
                    break;
                case "inscrire":
                    action = new SInscrireAction();
                    serialisation = new ProfilClientSerialisation();
                    break;
                case "getClientConnecte":
                    action = new GetClientConnecte();
                    serialisation = new ProfilClientSerialisation();
                    break;
                case "getProfilAstral":
                    action = new GetProfilAstralAction();
                    serialisation = new ProfilAstralSerialisation();
                    break;
                case "getMediumsDisponibles":
                    action = new GetMediumsDisponiblesAction();
                    serialisation = new MediumsSerialisation();
                    break;
                case "demanderConsultation":
                    action = new DemanderConsultationAction();
                    serialisation = new MediumsSerialisation();
                    break;
                case "connecterEmploye":
                    action = new AuthentifierEmployeAction();
                    serialisation = new EmployeSerialisation();
                    break;
                case "getTravailEnCours":
                    action = new GetTravailEnCoursAction();
                    serialisation = new EmployeSerialisation();
                    break;
            }
        }
        
        if (action != null && serialisation != null) {
            action.executer(request);
            serialisation.serialiser(request, response);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur dans les paramètres de la requête");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
