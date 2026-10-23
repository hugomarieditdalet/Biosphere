package biosphere7;

/**
 * Total des vitalités, pour les plantes de chaque joueur.
 */
public class Vitalites {
    
    /**
     * Somme des vitalités des plantes du joueur rouge.
     */
    int vitalitesRouge;
    
    /**
     * Somme des vitalités des plantes du joueur bleu.
     */
    int vitalitesBleu;
    
    /**
     * Somme des vitalités des plantes du joueur rouge avant de faire une action.
     */
    int vitalitesRougePlateau ;
    
    /**
     * Somme des vitalités des plantes du joueur bleu avant de faire une action.
     */
    int vitalitesBleuPlateau ;
    
    /**
     * Constructeur vide.
     */
    Vitalites() {
        vitalitesRouge = 0;
        vitalitesBleu = 0;
        vitalitesRougePlateau=0;
        vitalitesBleuPlateau=0;
    }
    /**
     *Constructeur à partir des paramètres du plateau 
     * @param plateau le plateau où vivent les plantes
     */
    
    Vitalites (Case[][] plateau) {
        int b=0,r=0;
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                if (plateau[lig][col].couleur=='R'){
                    vitalitesRouge+=plateau[lig][col].vitalite;
                }
                else if (plateau[lig][col].couleur=='B'){
                    vitalitesBleu+=plateau[lig][col].vitalite;
                }
            }
        }
        vitalitesBleuPlateau=vitalitesBleu;
        vitalitesRougePlateau=vitalitesRouge;
    }
    
    /**
     * Constructeur à partir de valeurs connues.
     * 
     * @param vitaRouge somme des vitalités du joueur rouge
     * @param vitaBleu somme des vitalités du joueur bleu
     */
    Vitalites(int vitaRouge, int vitaBleu) {
        vitalitesRouge = vitaRouge;
        vitalitesBleu = vitaBleu;
    }

    /**
     * Constructeur par copie : permet d'obtenir une copie de cet objet.
     * 
     * @param vitalites l'objet à copier
     */
    Vitalites(Vitalites vitalites) {
        this.vitalitesRouge = vitalites.vitalitesRouge;
        this.vitalitesBleu = vitalites.vitalitesBleu;
    }
    
    /**
     * Remets la somme des vitalités des plantes du joueur bleu et rouge avant qu'il fasse l'action
     */
    
    void reset(){
        vitalitesRouge=vitalitesRougePlateau;
        vitalitesBleu=vitalitesBleuPlateau;
    }
    
    /**
     * ajoute (ou retire si ajout est négatif) un nombres de vitalitées à la couleur choisie.
     * @param valeur la valeur de vitalité a ajouté.
     * @param couleur la couleur dont on va ajouter une vitalité.
     */
    
    void ajouter(int valeur,char couleur){
        if(couleur=='R'){
            vitalitesRouge+=valeur;
        }
        else{
            vitalitesBleu+=valeur;
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.vitalitesRouge;
        hash = 89 * hash + this.vitalitesBleu;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vitalites other = (Vitalites) obj;
        if (this.vitalitesRouge != other.vitalitesRouge) {
            return false;
        }
        if (this.vitalitesBleu != other.vitalitesBleu) {
            return false;
        }
        return true;
    }
}
