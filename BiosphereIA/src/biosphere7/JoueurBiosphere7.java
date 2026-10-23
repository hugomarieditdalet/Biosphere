package biosphere7;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un
 * niveau donné.
 */
public class JoueurBiosphere7 implements IJoueurBiosphere7 {

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes
     * les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param couleurJoueur couleur du joueur
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, char couleurJoueur, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        // se préparer à stocker les actions possibles
        ActionsPossibles actions = new ActionsPossibles();
        // calculer les vitalités sur le plateau initial
        Vitalites vitalites = vitalitesPlateau(plateau);
        System.out.println("vitalites rouge : "+vitalites.vitalitesRouge);
        System.out.println("vitalites bleu : "+vitalites.vitalitesBleu);
        // ajout des actions "planter pommier"
        char[] listeEspeces = {'P', 'S', 'B', 'D','T','H'};
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                if (! plateau[lig][col].plantePresente()){
                    for (char espece : listeEspeces) {
                        ajoutActionPlanter(coord, actions, vitalites, couleurJoueur,plateau,espece);
                    }
                }
                else{
                    ajoutCouper(coord,actions,vitalites,plateau);
                    ajoutFertiliser(coord,actions,vitalites,plateau);
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }

    /**
     * Somme des vitalités des plantes de chaque joueur sur le plateau.
     *
     * @param plateau le plateau
     * @return la somme des vitalités des plantes de chaque joueur
     */
    static Vitalites vitalitesPlateau(Case[][] plateau) {
        int b=0,r=0;
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                if (plateau[lig][col].couleur=='B'){
                    b+=plateau[lig][col].vitalite;
                }
                else if (plateau[lig][col].couleur=='R'){
                    r+=plateau[lig][col].vitalite;
                }
            }
        }
        return new Vitalites(r,b);
    }

    /**
     * Ajout d'une action de plantation de pommier dans l'ensemble des actions
     * possibles.
     *
     * @param coord coordonnées de la case où planter le pommier
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
     * @param couleur la couleur du pommier à ajouter
     */
    static void ajoutActionPlanter(Coordonnees coord, ActionsPossibles actions,
            Vitalites vitalites, char couleur,Case[][]plateau,char Espece) {
        Voisines infosVoisines = new Voisines(coord,plateau, couleur);
        if(infosVoisines.nbVoisines!=4){
            int r=0,b=0;
            if (couleur=='R'){
                r=1+infosVoisines.nbVoisinesMemeCouleur;
            } 
            else {
                b=1 +infosVoisines.nbVoisinesMemeCouleur;
            }
            int i=0;
            while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
                Voisines infosVoisinesDeLaVoisine = new Voisines(infosVoisines.coordcasesVoisines[i],plateau,infosVoisines.casesVoisines[i].couleur);
                if (infosVoisinesDeLaVoisine.nbVoisines == 3){
                    if (infosVoisines.casesVoisines[i].couleur =='R'){
                        r-=infosVoisines.casesVoisines[i].vitalite;
                    }
                    else {
                        b-=infosVoisines.casesVoisines[i].vitalite;
                    }
                }
                i++;
            }
            
            ecrireAction(Espece,coord,actions,vitalites,b,r);
        }
    }
     static void ajoutCouper(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau){
        int r=0,b=0;
        Voisines infosVoisines = new Voisines(coord,plateau, plateau[coord.ligne][coord.colonne].couleur);
        if (plateau[coord.ligne][coord.colonne].couleur =='R'){
            r= -plateau[coord.ligne][coord.colonne].vitalite;
        }
        else {
            b= -plateau[coord.ligne][coord.colonne].vitalite;
        }
        int i=0;
        while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
            if (infosVoisines.casesVoisines[i].vitalite<9){
                if (infosVoisines.casesVoisines[i].couleur =='R'){
                    r++;
                }
                else {
                    b++;
                }
            }
            i++;
        }
        ecrireAction('C',coord,actions,vitalites,b,r);
     }
     
     static void ajoutFertiliser(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau){
         int b=0,r=0,ajout=0;
         switch(plateau[coord.ligne][coord.colonne].espece){
             case 'P','S' -> ajout+=1;
             case 'B' -> ajout+=2;
             default -> ajout+=3;
         }
         if (ajout + plateau[coord.ligne][coord.colonne].vitalite >9){
             ajout = 9-plateau[coord.ligne][coord.colonne].vitalite;
         }
         if (plateau[coord.ligne][coord.colonne].couleur =='R'){
             r=ajout;
             //System.out.println(espece + " ajout " + ajout);
         }
         else {
             b=ajout;
             //System.out.println(espece + " ajout " + ajout);
         }
         
         ecrireAction('F',coord,actions,vitalites,b,r);
     }
     
     
     
     static void ecrireAction(char Choix,Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,int b,int r){
         String action = Choix +""+ coord.carLigne() +""+ coord.carColonne()+","+
                (vitalites.vitalitesRouge+r)+ ","+
                (vitalites.vitalitesBleu+b);
        actions.ajouterAction(action);
     }
     
     
     
     
     
}