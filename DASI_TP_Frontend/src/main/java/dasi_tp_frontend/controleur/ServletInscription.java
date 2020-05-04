/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi_tp_frontend.controleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.insalyon.dasi.metier.service.ServiceUtilisateur;
import fr.insalyon.dasi.metier.modele.Client;

/**
 *
 * @author oisinnolan
 */
@WebServlet(name = "ServletInscription", urlPatterns = {"/inscription"})
public class ServletInscription extends HttpServlet {

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
        String testMessage = "Hello world!";
        request.setAttribute("testMessage", testMessage);
        request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
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
        // Prepare messages.
        Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);

        // Get and validate name.
        String nom = request.getParameter("nom");
        if (nom == null || nom.trim().isEmpty()) {
            messages.put("nom", "Veuillez saisir votre nom");
        }

        // Get and validate age.
        String prenom = request.getParameter("prenom");
        if (prenom == null || prenom.trim().isEmpty()) {
            messages.put("prenom", "Veuillez saisir votre prénom");
        }
        
        
        String dateNaissanceStr = request.getParameter("dateNaissance");
        if(dateNaissanceStr == null || dateNaissanceStr.trim().isEmpty()){
            messages.put("dateNaissance", "Veuillez saisir votre date de naissance");
        } else {
            try {
                Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr); 
            } catch(ParseException ex) {
                messages.put("dateNaissance", "Erreur lors de la lecture de la date");
            }
        }
        
        boolean genreM;
        boolean genreF;
        
        if(request.getParameter("genre") != null) {
            genreM = request.getParameter("genre").equals("M");
            genreF = request.getParameter("genre").equals("F");
        } else {
            messages.put("genre", "Veuillez saisir votre genre");
        }

       
        String adresse = request.getParameter("adresse");
        if(adresse == null || adresse.trim().isEmpty()){
            messages.put("adresse", "Veuillez saisir votre adresse");
        }
        
        String noTelephone = request.getParameter("noTelephone");
        if(noTelephone == null || noTelephone.trim().isEmpty()){
            messages.put("noTelephone", "Veuillez saisir votre numéro de téléphone");
        }
        
        String mail = request.getParameter("mail");
        if(mail == null || mail.trim().isEmpty()){
            messages.put("noTelephone", "Veuillez saisir votre numéro de téléphone");
        }
        
        String motDePasse = request.getParameter("motDePasse");
        if(motDePasse == null || motDePasse.trim().isEmpty()){
            messages.put("motDePasse", "Veuillez saisir votre mot de passe");
        }
        
        String confirmMotDePasse = request.getParameter("confirmMotDePasse");
        if(confirmMotDePasse == null || confirmMotDePasse.trim().isEmpty()){
            messages.put("confirmMotDePasse", "Veuillez confirmer votre mot de passe");
        }
        
        if(motDePasse != null && !motDePasse.equals(confirmMotDePasse)) {
            messages.put("confirmMotDePasse", "Les mots de passes sont différents!");
        }
        
        // No validation errors if messages is empty
        if (messages.isEmpty()) {
            ServiceUtilisateur su = new ServiceUtilisateur();
            //Client client = new Client(nom, prenom, mail, motDePasse, noTelephone,);
        }

        request.getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
    
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
