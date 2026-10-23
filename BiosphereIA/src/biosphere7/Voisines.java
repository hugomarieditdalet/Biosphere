
package biosphere7;

class Voisines {
    /**
     * Cases des plantes voisines d'une case
     */
    Case[] casesVoisines = new  Case[4];
    
    /**
     * Coordonnees des plantes voisines d'une case
     */
    Coordonnees[] coordcasesVoisines = new  Coordonnees[4];
    
    /**
     * Nombrese de plantes voisines.
     */
    int nbVoisines = 0;
    
    /**
     * Nombres de plantes voisines avec la même couleur
     */
    int nbVoisinesMemeCouleur = 0;
    
    
    public Voisines (Coordonnees coord,Case[][] plateau,char couleur){
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if ((i+j)%2!=0 && dansLePlatau(coord, plateau, i, j)&&plateau[coord.ligne+i][coord.colonne+j].plantePresente()){
                    casesVoisines[nbVoisines] = plateau[coord.ligne+i][coord.colonne+j];
                    coordcasesVoisines[nbVoisines]= new Coordonnees(coord.ligne+i,coord.colonne+j);
                    nbVoisines ++;
                    if(couleur ==plateau[coord.ligne+i][coord.colonne+j].couleur){
                        nbVoisinesMemeCouleur++;
                    }
                }
            }
        }
    }
    static boolean dansLePlatau(Coordonnees coord,Case[][] plateau,int incrementLigne,int incrementColonne){
         return coord.ligne+incrementLigne>=0 && coord.ligne+incrementLigne<plateau.length && coord.colonne+incrementColonne >=0 && coord.colonne+incrementColonne <plateau[0].length;
     }
}