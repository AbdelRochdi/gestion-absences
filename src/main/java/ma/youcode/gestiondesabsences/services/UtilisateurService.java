/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.youcode.gestiondesabsences.services;

import ma.youcode.gestiondesabsences.dao.UtilisateurDaoImpl;

/**
 *
 * @author Youcode
 */
public class UtilisateurService {
    
    private UtilisateurDaoImpl impl = new UtilisateurDaoImpl();
    
    public void ajouterService(){
        impl.ajouterUser();
    }
    
}
