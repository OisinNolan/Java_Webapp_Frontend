package fr.insalyon.dasi.ihm.web.action;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import fr.insalyon.dasi.util.Genre;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DASI Team
 */
public class SInscrireAction extends Action {

    @Override
    public void executer(HttpServletRequest request) {
                     
        String nom = getParam(request, "nom");
        String prenom = getParam(request, "prenom");
        String genreStr = getParam(request, "genre");
        String dateNaissanceStr = getParam(request, "dateNaissance");
        String adresse = getParam(request, "adresse");
        String numeroTelephone = getParam(request, "numeroTelephone");
        String mail = getParam(request, "mail");
        String mdp = getParam(request, "mdp");  
        
        System.out.println("DATE NAISSANCE " + dateNaissanceStr);
        
        // parse genreStr to Genre object
        Genre genre;
        if (genreStr.equals("male")){
            genre = Genre.M;
        } else {
            genre = Genre.F;
        } 
        
        // parse dateNaissanceStr to Date object
        Date dateNaissance = null;
        try {
            dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr);
        } catch (ParseException ex) {
            Logger.getLogger(SInscrireAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Service service = new Service();
        
        // We check here whether the mail provided has already been used to create
        // an account. If so, we send an appropriate error message.
        Client clientWithSameMail = service.rechercherClientParMail(mail);
        if(clientWithSameMail != null) {
            request.setAttribute("errorMessage", "Un compte avec l'adresse mail fournie existe déjà.");
        } else {
            
            Client client = new Client(nom, prenom, mail, mdp, numeroTelephone, genre, dateNaissance, adresse);
            Long idClient = service.inscrire(client);

            // Gestion de la Session: ici, enregistrer l'ID du Client authentifié
            HttpSession session = request.getSession();
            if (idClient != null) {
                session.setAttribute("idClient", idClient);
            }
            else {
                request.setAttribute("errorMessage", "Une erreur s'est produite lors de la création de votre compte.");
                session.removeAttribute("idClient");
            }
        }
    }
    
    private String getParam(HttpServletRequest request, String paramString){
        paramString = "data[" + paramString + "]";
        return request.getParameter(paramString);
    }
    
}
