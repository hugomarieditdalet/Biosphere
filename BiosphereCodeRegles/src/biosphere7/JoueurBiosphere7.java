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
        Vitalites vitalites = new Vitalites(plateau);
        System.out.println("vitalites rouge : "+vitalites.vitalitesRouge);
        System.out.println("vitalites bleu : "+vitalites.vitalitesBleu);
        // ajout des actions "planter pommier"
        char[] listeEspeces = {'P', 'S', 'B', 'D','T','H'};
        ajoutOmbre(actions,vitalites,plateau);
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                if (! plateau[lig][col].plantePresente() && plateau[lig][col].nature != 'E'){
                    Voisines infosVoisines = new Voisines(coord,plateau, couleurJoueur);
                    if(infosVoisines.nbVoisines!=4){
                        for (char espece : listeEspeces) { 
                            ajoutActionPlanter(coord, actions, vitalites, couleurJoueur,plateau,espece,infosVoisines);
                        }
                    }
                }
                else if(plateau[lig][col].plantePresente()){
                    if(plateau[lig][col].couleur == couleurJoueur){
                        for (char espece : listeEspeces) {
                            if (categorie(espece)!=categorie(plateau[coord.ligne][coord.colonne].espece)){
                                ajoutRotation(coord,actions,vitalites,plateau,couleurJoueur,espece);
                            }
                        }
                    }
                    ajoutCouper(coord,actions,vitalites,plateau);
                    ajoutFertiliser(coord,actions,vitalites,plateau);
                    ajoutDisseminer(coord,actions,vitalites,plateau,couleurJoueur);
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }


    /**
     * Ajout d'une action de plantation de plante dans l'ensemble des actions possibles.
     *
     * @param coord coordonnées de la case où planter la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
     * @param couleur la couleur de l'espèce à ajouter
     * @param plateau le plateau où les plantes vivent
     * @param espece le type d'éspèce que nous souhaitons ajouter
     * @param infosVoisines des informations sur les cases voisines, comme par exemple le nombre de cases voisines avec une plante (voir class voisines pour plus d'informations)
     */
    static void ajoutActionPlanter(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,
            char couleur,Case[][]plateau,char espece,Voisines infosVoisines) {
        int ajout = infosVoisines.nbPointsEau >0 ? 2 : 1;
        ajout =  espece =='B' && (EffetLisière(infosVoisines,plateau)) ? 4 :ajout;
        vitalites.ajouter(ajout+infosVoisines.nbVoisinesMemeCouleur, couleur);
        int i=0;
        while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
            Voisines infosVoisinesDeLaVoisine = new Voisines(infosVoisines.coordcasesVoisines[i],plateau,infosVoisines.casesVoisines[i].couleur);
            if (infosVoisinesDeLaVoisine.nbVoisines == 3){
                vitalites.ajouter(-infosVoisines.casesVoisines[i].vitalite,infosVoisines.casesVoisines[i].couleur);
            }
            i++;
        }
        ecrireAction(espece,coord,actions,vitalites);
    }
    
    /*static void a(Coordonnees[] tab){
        System.out.print("[");
        for(int i=0;i<tab.length;i++){
            if (tab[i]!=null){
                System.out.print(+tab[i].ligne+""+tab[i].colonne);
            }
        }
        System.out.println("]");
    }*/
    /**
     * Vérifie si l'effet lisière est appliqué sur la plante
     * @param infosVoisines Les informations qui a autour de la plante.
     * @param plateau le plateau où les plantes vivent
     * @return true si si l'effet lisière est appliqué sur la plante false sinon
     */
    static boolean EffetLisière(Voisines infosVoisines,Case[][]plateau){
        int i=0;
        Coordonnees coordPlantesLisieres[] =new Coordonnees[6];
        boolean effetLisiere=false;
        while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null && !effetLisiere){
            Voisines infosVoisinesDeLaVoisine = new Voisines(infosVoisines.coordcasesVoisines[i],plateau,infosVoisines.casesVoisines[i].couleur);
            if (infosVoisinesDeLaVoisine.coordcasesVoisines != null &&
                    categorie(infosVoisines.casesVoisines[i].espece)==1){
                coordPlantesLisieres[0]=infosVoisinesDeLaVoisine.coordCases;
                if (EffetLisièreRecursive(infosVoisinesDeLaVoisine.coordCases,plateau,coordPlantesLisieres,infosVoisines.coordCases)){
                    effetLisiere = true;
                }
                else{
                resetTab(coordPlantesLisieres);
                }
            }
            i++;
        }
        return effetLisiere;
    }
    /**
     * Remplace toute les valeurs du tableaux par des null
     * 
     * @param coordPlantesLisieres le tableau avec les coordonnées des plantes qui sont voisines entre elles
     */
    static void resetTab(Coordonnees[] coordPlantesLisieres){
        int i=0;
        do{
            coordPlantesLisieres[i] =null;
            i++;
        }while (i<coordPlantesLisieres.length && coordPlantesLisieres[i]!=null);
    }
    
    /**
     * Ajoute les coordonnées d'une nouvelle plante dans le tableau
     * 
     * @param coordPlantesLisieres le tableau avec les coordonnées des plantes qui sont voisines entre elles
     * @param ajout les coordonnées d'une nouvelle plante
     */
    
    static void ajouter(Coordonnees[] coordPlantesLisieres,Coordonnees ajout){
        int i=0;
        boolean estAjoute = false;
        do{
            if(coordPlantesLisieres[i] == null){
                coordPlantesLisieres[i] = ajout;
                estAjoute = true;
            }
            i++;
        }while (i<coordPlantesLisieres.length && !estAjoute);
    }
    
    /**
     * Vérifie si une coordonnées est dans la tab ou non
     * @param coordPlantesLisieres le tableau avec les coordonnées des plantes qui sont voisines entre elles
     * @param element l'élément dont on veut savoir s'il est dans la tab
     * @return true si l'élément est dans la tab false sinon
     */
    static boolean estDansLeTab(Coordonnees[] coordPlantesLisieres,Coordonnees element){
        int i=0;
        boolean estDansLaTab = false;
        boolean nestPasDansLaTab = false;
        do{
            if(coordPlantesLisieres[i]==null){
                nestPasDansLaTab = true;
            }
            else if(coordPlantesLisieres[i].equals(element)){
                estDansLaTab = true;
            }
            i++;
        }while (i<coordPlantesLisieres.length && !estDansLaTab && !nestPasDansLaTab);
        return estDansLaTab;
    }
    
    /**
     * Vérifie de manière récursive si la plante a un bonus de l'effet lisière ou non.
     * Il parcourt chaque case en testant toutes les possibilités
     * 
     * @param caseCourante Les coordonnées de la cases où le curseur est avant de vérifier les informations des cases voisines
     * @param plateau le plateau où les plantes vivent
     * @param coordPlantesLisieres le tableau avec les coordonnées des plantes qui sont voisines entre elles
     * @param coordCasePrecedente Les coordonnées de la case précédente.
     * @return true si la plante qu'on souhaite planter a un effet lisière false sinon.
     */
    static boolean EffetLisièreRecursive(Coordonnees caseCourante,Case[][]plateau,Coordonnees[]coordPlantesLisieres,Coordonnees coordCasePrecedente){
        if (coordPlantesLisieres[5]!=null){
            return true;
        }
        Voisines infosVoisines = new Voisines(caseCourante,plateau,coordCasePrecedente);
        if (infosVoisines.nbVoisines ==3){
            if(!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0])&& categorie(infosVoisines.casesVoisines[0].espece)==1 &&
               !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]) && categorie(infosVoisines.casesVoisines[1].espece)==1 &&
               !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]) && categorie(infosVoisines.casesVoisines[2].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0]);
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]);
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[0],plateau,coordPlantesLisieres,caseCourante)||
                        EffetLisièreRecursive(infosVoisines.coordcasesVoisines[1],plateau,coordPlantesLisieres,caseCourante) ||
                        EffetLisièreRecursive(infosVoisines.coordcasesVoisines[2],plateau,coordPlantesLisieres,caseCourante);
            }
            else if(!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]) && categorie(infosVoisines.casesVoisines[1].espece)==1 &&
               !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]) && categorie(infosVoisines.casesVoisines[2].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]);
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[1],plateau,coordPlantesLisieres,caseCourante)||
                        EffetLisièreRecursive(infosVoisines.coordcasesVoisines[2],plateau,coordPlantesLisieres,caseCourante);
            }
            else if(!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0]) && categorie(infosVoisines.casesVoisines[0].espece)==1 &&
               !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]) && categorie(infosVoisines.casesVoisines[2].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0]);
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[0],plateau,coordPlantesLisieres,caseCourante)||
                        EffetLisièreRecursive(infosVoisines.coordcasesVoisines[2],plateau,coordPlantesLisieres,caseCourante);
            }
            else if (!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]) && categorie(infosVoisines.casesVoisines[2].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[2]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[2],plateau,coordPlantesLisieres,caseCourante);
            }
        }
        if (infosVoisines.nbVoisines >1){
            if(!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0])&& categorie(infosVoisines.casesVoisines[0].espece)==1 &&
                    !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]) && categorie(infosVoisines.casesVoisines[1].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0]);
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[0],plateau,coordPlantesLisieres,caseCourante)||
                        EffetLisièreRecursive(infosVoisines.coordcasesVoisines[1],plateau,coordPlantesLisieres,caseCourante);
            }
            else if(!estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]) && categorie(infosVoisines.casesVoisines[1].espece)==1){
                ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[1]);
                return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[1],plateau,coordPlantesLisieres,caseCourante);
            }
        }
        if((infosVoisines.nbVoisines >0) && 
                !estDansLeTab(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0])&& categorie(infosVoisines.casesVoisines[0].espece)==1){
            ajouter(coordPlantesLisieres,infosVoisines.coordcasesVoisines[0]);
            return EffetLisièreRecursive(infosVoisines.coordcasesVoisines[0],plateau,coordPlantesLisieres,caseCourante);
        }
    return false;
    }
    
    /**
     * Ajout d'une action couper dans l'ensemble des actions possibles.
     * 
     * @param coord coordonnées de la case où couper la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
     * @param plateau le plateau où les plantes vivent
     */
     static void ajoutCouper(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau){
        vitalites.ajouter(-plateau[coord.ligne][coord.colonne].vitalite,plateau[coord.ligne][coord.colonne].couleur);
        Voisines infosVoisines = new Voisines(coord,plateau, plateau[coord.ligne][coord.colonne].couleur);
        int i=0;
        while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
            if (infosVoisines.casesVoisines[i].vitalite<9){
                vitalites.ajouter(1, infosVoisines.casesVoisines[i].couleur);
            }
            i++;
        }
        ecrireAction('C',coord,actions,vitalites);
     }
     
     /**
     * Ajout d'une action Fertiliser dans l'ensemble des actions possibles.
     * L'algorithme fonctionne de la manière suivante :
     * 
     * Partie 1 innitialisation :
     * 
     * On part du principe qu'il est êimpossible qu'une plante courante puisse se relier avec une plante qui se situe à une distance de Manhattan
     * au-delà de plante.vitalite+9. De plus, une plante qui a 9 en vitalité ne peut pas croître, donc nous allons quadriller la zone à 
     * plante.vitalite+8 distances autour de la plante Courante (Dans mon code, les variables propagations est l'équivalent à la distance de 
     * Manhattan dans l'énoncé).
     * Pour commencer, nous allons placer le plus en haut à gauche possible le curseur de façon où propagation = propagation Max, ou que le plateau
     * nous empêche d'aller plus loin.
     * 
     * Partie 2 parcours horizontal :
     * 
     * Tout d'abord, il retient dans une variable la position de sa colonne (colonneDebut) ainsi que sa propagation (propagationDebut).
     * Le curseur parcours les cases de la ligne, il vérifie s'il n'y a pas une plante de la même espèce, si c'est le cas, il vérifie que la vitalité
     * de cette plante + la vitalité de la plante Courante est supérieure ou égale à la propagation de la case, si c'est le cas la plante gagne
     * le nombre de vitalité qu'il a le droit (cela dépend de son espèce et de sa vitalité de base).
     * A chaque fois qu'il parcourt une case, la propagation prend -1 s'il est à gauche de la plante (il se rapproche) et +1 
     * s'il est à droite ou sur la même colonne que la plante (il s'éloigne). milieuColonne permet de savoir si le curseur est à gauche ou à droite.
     * Le parcours horizontal se termine quand la propagation > propagationMax (quand toutes les cases de propagation ont été vérifiées) ou quand
     * le plateau nous empêche d'aller plus loin
     * 
     * Partie 3 parcours Vertical :
     * 
     * Après avoir parcouru toutes les cases du tableau,le curseur revient au début de la colonne grâce à colonneDebut, 
     * il y a donc 4 cas à traiter :
     * -Nous sommes dans la partie haute de la zone de la plante et la case à gauche est dans le Plateau :
     *      Nous décalons le curseur de 1 vers le bas et 1 vers la gauche, et la propagation est égale à la même que la propagation du début de la 
     *      ligne précédente, c'est-à-dire la propagation max.
     * -Nous sommes dans la partie haute de la zone de la plante et la case à gauche n'est pas dans le Plateau :
     *      Nous décalons le curseur de 1 vers le bas et la propagation est égale la même que la propagation du début de la 
     *      ligne précédente-1 car on se rapproche de la plante.
     * -Nous sommes dans la partie basse de la zone de la plante et la propagation du début de la ligne précédente = propagationMax:
     *      Nous décalons le curseur de 1 vers le bas et 1 vers la droite, et la propagation est égale à la même que la propagation du début de la 
     *      ligne précédente, c'est-à-dire la propagation max.
     * -Nous sommes dans la partie basse de la zone de la plante et la propagation du début de la ligne précédente est différente de la propagationMax:
     *      Nous décalons le curseur de 1 vers le bas et la propagation est égale à la même que la propagation du début de la 
     *      ligne précédente +1 car on se rapproche de la plante.
     * Dans les 4 cas, on recommence le parcours horizontal jusqu'à la condition de fin.
     * 
     * Le parcours vertical se termine dans deux cas :
     *      -Les cases en bas ne sont pas dans le plateau (quand le plateau nous empêche dê'aller plus loin)
     *      -Ou quand toutes les cases de propagations ont été vérifiées, c'est-à-dire quand le nombre de cases que nous parcourons à l'horizontal = 1
     *      (variable nbtour) et quand nous sommes dans la partie basse de la plante. En effet, il n'y a que deux cas au maximum où le parcours maximal
     *      horizontal est égal à 1, au début quand nous sommes dans la partie haute de la plante et à la toute fin quand on est dans la partie basse.
     *  
     *      
     * 
     * @param coord coordonnées de la case où fertiliser la plante
     * @param actions l'ensemble des actions possibles (en construction)
     * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
     * @param plateau le plateau où les plantes vivent
     */
     static void ajoutFertiliser(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau){
         int ajout=0;
         char espece =plateau[coord.ligne][coord.colonne].espece;
         switch(plateau[coord.ligne][coord.colonne].espece){
             case 'P','S' -> ajout+=1;
             case 'B' -> ajout+=2;
             default -> ajout+=3;
         }
         Case plante=plateau[coord.ligne][coord.colonne];
         int propagationDebut = 0;
         int colonneDebut =0;
         int propagationMax = plante.vitalite+8;
         int propagation =0;
         int milieuColonne=coord.colonne;
         int milieuLigne=coord.ligne;
         int nbtour=0;
         int[][] racine = new int[Coordonnees.NB_LIGNES][Coordonnees.NB_COLONNES];
         //Partie innitialisation
         while(Voisines.dansLePlatau(coord,plateau,-1,0) && propagation<propagationMax){
             propagation++;
             coord.ligne--;
         }
         while(Voisines.dansLePlatau(coord,plateau,0,-1) && propagation<propagationMax){
             propagation++;
             coord.colonne--;
         }
         //Partie parcours horizontal:
         do{
            colonneDebut = coord.colonne;
            propagationDebut = propagation; 
            nbtour=0;
            do{
                if (plateau[coord.ligne][coord.colonne].espece ==espece && plateau[coord.ligne][coord.colonne].vitalite+plante.vitalite>=propagation){
                    if (ajout + plateau[coord.ligne][coord.colonne].vitalite >9){
                        vitalites.ajouter(9-plateau[coord.ligne][coord.colonne].vitalite,plateau[coord.ligne][coord.colonne].couleur);
                    }
                    else{
                        vitalites.ajouter(ajout,plateau[coord.ligne][coord.colonne].couleur);
                    }   
                }
                if(coord.colonne >= milieuColonne){
                    propagation++;
                }
                else{
                    propagation--;
                }
                coord.colonne++;
                nbtour++;
            }while(Voisines.dansLePlatau(coord,plateau,0,0)&& propagation<=propagationMax);
            //Partie 3 parcours vertical
            if(coord.ligne < milieuLigne){
                if(dansLePlateauBis(coord.ligne,colonneDebut,plateau,1,-1)){
                    coord.colonne = colonneDebut-1;
                    propagation= propagationDebut;
                }
                else{
                    coord.colonne = colonneDebut;
                    propagation= propagationDebut-1;
                }
            }
            else{
                if(propagationDebut==propagationMax){
                    coord.colonne = colonneDebut+1;
                    propagation= propagationDebut;
                }
                else{
                    coord.colonne = colonneDebut;
                    propagation= propagationDebut+1;
                }
            }
            coord.ligne++;
         }while(Voisines.dansLePlatau(coord,plateau,0,0)&& (nbtour!=1 || coord.ligne < milieuLigne));
         //System.out.println("");
         coord.colonne = milieuColonne;
         coord.ligne = milieuLigne;
         ecrireAction('F',coord,actions,vitalites);
     }
     
     /**
      * Ajout d'une action Disseminer dans l'ensemble des actions possibles.
      * Si la plante ne peut pas disseminer aucune action n'est ajouté à l'ensemble des actions.
      * @param coord coordonnées de la case de la plante disseminé
      * @param actions l'ensemble des actions possibles (en construction)
      * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
      * @param couleur la couleur de l'espèce à ajouter
      * @param plateau le plateau où les plantes vivent
      */
     static void ajoutDisseminer(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau,char couleur){
         int ajout=0;
         boolean peutDisseminer = false;
         Case plante = plateau[coord.ligne][coord.colonne];
         Voisines infosVoisines = new Voisines(coord,plateau,plante.couleur);
         if  (autoSteriles(plante.espece)){
             int plantePlusFaible = plante.vitalite;
             int i=0;
             while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
                 if(infosVoisines.casesVoisines[i].espece == plante.espece){
                     peutDisseminer = true;
                     if (infosVoisines.casesVoisines[i].vitalite < plantePlusFaible){
                         plantePlusFaible = infosVoisines.casesVoisines[i].vitalite;
                     }
                 }
                 i++;
             }
             if (peutDisseminer){
                 ajout=infosVoisines.nbVoisinesVides*plantePlusFaible;
             }
         }
         else {
             ajout =infosVoisines.nbVoisinesVides;
             peutDisseminer = true;
         }
         vitalites.ajouter(ajout, couleur);
         if (peutDisseminer){
                 ecrireAction('I',coord,actions,vitalites);
             }
     }
     
     /**
      * Ajout d'une action Ombre dans l'ensemble des actions possibles.
      * Il parcourt le tableau de bas en haut et de gauche à droite.
      * Dès que le curseur tombe sur un arbre, il met à jour la matrice IO qui a la même taille que le plateau.
      * Autrement dit, il indique l'intensité de l'ombre sur chaque case qu'il affecte.
      * Dès que le curseur tombe sur une plante, elle regarde l'intensité de l'ombre qu'il y a sur cette case. Plus l'intensité de l'ombre est grande
      * plus la plante va perdre de la vitalité
      * Cette fonction n'écrit pas d'action si elle n'affecte aucune plante
      * @param actions l'ensemble des actions possibles (en construction)
      * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
      * @param plateau le plateau où les plantes vivent
      */
     static void ajoutOmbre(ActionsPossibles actions,Vitalites vitalites,Case[][]plateau){
         int retirer=0,y2=0,distance=0; //y2: variable qui permet de se déplacer sur un ligne sans affecter la variable y
         int[] tabIO = new int[7]; // IO = Intensité Ombre
         int[][] IO = new int[Coordonnees.NB_LIGNES][Coordonnees.NB_COLONNES];
         for (int y = Coordonnees.NB_LIGNES-1; y>=0 ; y--) {
             for (int x = 0; x < Coordonnees.NB_COLONNES; x++) {
                 Case plante = plateau[y][x];
                 if (plante.plantePresente()){
                     if(categorie(plante.espece)==1 && plante.vitalite>2 && y>0){
                        y2 = y-1;
                        distance =1;
                        do{
                            IO[y2][x] +=(plante.vitalite-distance)/2;
                            distance++;
                            y2--;
                        }while(y2>=0 && (plante.vitalite-distance)/2>0);
                     }
                     if(IO[y][x]>0){
                         if(plante.vitalite<IO[y][x]){
                             retirer =plante.vitalite;
                         }
                         else{
                             retirer = IO[y][x];
                         }
                         vitalites.ajouter(-retirer, plante.couleur);
                     }
                 }
             }
         }
         if(retirer !=0){
             String action = 'O' +","+
                (vitalites.vitalitesRouge)+ ","+
                (vitalites.vitalitesBleu);
             actions.ajouterAction(action);
             vitalites.reset();
         }
     }
     
     /**
      * Ajout d'une action Rotaion dans l'ensemble des actions possibles.
      * @param coord coordonnées de la case où la rotation va être fait
      * @param actions l'ensemble des actions possibles (en construction)
      * @param vitalites la somme des vitalités sur le plateau avant de jouer l'action
      * @param couleur la couleur de l'espèce à ajouter
      * @param plateau le plateau où les plantes vivent
      * @param espece le type d'éspèce que nous souhaitons ajouter
      */
     static void ajoutRotation(Coordonnees coord, ActionsPossibles actions,Vitalites vitalites,Case[][]plateau,char couleur,char espece){
         Case plante = plateau[coord.ligne][coord.colonne];
         Voisines infosVoisines = new Voisines(coord, plateau, couleur);
         int ajout = infosVoisines.nbPointsEau >0 ? 4 : 3;
         vitalites.ajouter(ajout+ infosVoisines.nbVoisinesMemeCouleur-plante.vitalite, couleur);
         String action = 'R'+""+espece +""+ coord.carLigne() +""+ coord.carColonne()+","+
                (vitalites.vitalitesRouge)+ ","+
                (vitalites.vitalitesBleu);
             actions.ajouterAction(action);
             vitalites.reset();

     }
     
     /**
      * Ecris et ajoute une action dans ActionsPossibles
      * @param Choix Permet de savoir le type d'action ( 'P' pour planter un pommier par exemple)
      * @param coord coordonnées de la case où a été fait l'action
      * @param actions Toutes les actions possibles que nous avons calculé depuis le début.
      * @param vitalites Les points que vont avoir les rouges et les bleues après l'action
      */
     static void ecrireAction(char Choix,Coordonnees coord, ActionsPossibles actions,Vitalites vitalites){
         String action = Choix +""+ coord.carLigne() +""+ coord.carColonne()+","+
                (vitalites.vitalitesRouge)+ ","+
                (vitalites.vitalitesBleu);
        actions.ajouterAction(action);
        vitalites.reset();
     }
     
     /**
      * Vérifie que l'espèce est auto stérille ou non
      * @param espece l'espèce de la plante
      * @return true si la plante est autoSteriles false sinon
      */
     static boolean autoSteriles(char espece){
         return espece =='T' || espece =='H';
         
     }
     /**
      * Permet de savoir la catégorie d'une plante.
      * @param espece l'espèce de la plante.
      * @return 1 si c'est un arbre 2 si c'est un arbustes et 3 si c'est un légumes.
      */
     static int categorie(char espece){
         return switch (espece){
             case 'P','S' -> 1;
             case 'B' -> 2;
             default -> 3;
         };
     }
     
     /**
      * Permets de savoir si une case est dans le tableau sans utiliser la classe Coordonnees.
      * @param y la ligne du tableau
      * @param x la colonne du tableau
      * @param plateau le plateau où les plantes vivent
      * @param incrementLigne l'incrément de la ligne
      * @param incrementColonne l'incrément de la colonne
      * @return true si la case est dans le tableau false sinon
      */
     static boolean dansLePlateauBis(int y,int x,Case[][] plateau,int incrementLigne,int incrementColonne){
         return y+incrementLigne>=0 && y+incrementLigne<plateau.length && x+incrementColonne >=0 && x+incrementColonne <plateau[0].length;
     }
     
            
     
}

/**
 * La class voisines permet d'avoir des informations sur les cases voisines d'une case
 * @author hmarieditdal
 */
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
     * Les coordonnées de la case qui prend les informations de ses voisines
     */
    Coordonnees coordCases ;
    
    /**
     * Nombres de plantes voisines.
     */
    int nbVoisines = 0;
    
    /**
     * Nombres de cases voisines vides
     */
    int nbVoisinesVides = 0;
    
    /**
     * Nombres de plantes voisines avec la même couleur
     */
    int nbVoisinesMemeCouleur = 0;
    
    /**
     * Nombres de points d'eaux voisines.
     */
    int nbPointsEau = 0;
    
    
    /**
     * Le builder de la case voisine. Il prends toute les informations utiles des cases voisines.
     * @param coord  les coordonnées de la case qui prend les informations de ses voisines.
     * @param plateau le  plateau le plateau où les plantes vivent
     * @param couleur la couleur de la case qui prend les informations de ses voisines.
     */
    public Voisines (Coordonnees coord,Case[][] plateau,char couleur){
        coordCases = coord;
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if ((i+j)%2!=0 && dansLePlatau(coord, plateau, i, j)){
                    if (plateau[coord.ligne+i][coord.colonne+j].plantePresente()){
                        casesVoisines[nbVoisines] = plateau[coord.ligne+i][coord.colonne+j];
                        coordcasesVoisines[nbVoisines]= new Coordonnees(coord.ligne+i,coord.colonne+j);
                        nbVoisines ++;
                        if(couleur ==plateau[coord.ligne+i][coord.colonne+j].couleur){
                            nbVoisinesMemeCouleur++;
                        }
                    }
                    else {
                        if(plateau[coord.ligne+i][coord.colonne+j].nature=='E'){
                            nbPointsEau++;
                        }
                        else{
                            nbVoisinesVides++;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Un builder de la case voisine légèrement différent de son précédent.
     * Il prend en paramètre les coordonnées de la case du curseur avant d'aller sur la case courante pour éviter que cette case
     * apparaisse dans les informations des voisines. Ainsi, quand nous faisons un parcours récursif, cela évite la surcharge d'informations et
     * les allées retour inutiles.
     * Ce builder n'est utilisé que pour la fonction du niveau 13.
     * @param coord  les coordonnées de la case qui prend les informations de ses voisines.
     * @param plateau le  plateau le plateau où les plantes vivent
     * @param origine les Coordonnees la case du curseur avant d'aller sur la caseCourante
     */
    public Voisines (Coordonnees coord,Case[][] plateau,Coordonnees origine){
        coordCases = coord;
        Coordonnees coordCase;
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if ((i+j)%2!=0 && dansLePlatau(coord, plateau, i, j)){
                    if (plateau[coord.ligne+i][coord.colonne+j].plantePresente()){
                        coordCase = new Coordonnees(coord.ligne+i,coord.colonne+j);
                        if (!coordCase.equals(origine)){
                            casesVoisines[nbVoisines] = plateau[coord.ligne+i][coord.colonne+j];
                            coordcasesVoisines[nbVoisines]= new Coordonnees(coord.ligne+i,coord.colonne+j);
                            nbVoisines ++;
                        }
                    }
   
                    else {
                        if(plateau[coord.ligne+i][coord.colonne+j].nature=='E'){
                            nbPointsEau++;
                        }
                        else{
                            nbVoisinesVides++;
                        }
                    }
                }
            }
        }
    }
   
    /**
     * Permets de savoir si une case est dans le tableau.
     * Je n'avais pas remarqué que cette fonction était déjà codé dans la classe Coordonnees. 
     * @param coord les coordonnées de la case
     * @param plateau le plateau où les plantes vivent
     * @param incrementLigne l'incrément de la ligne
     * @param incrementColonne l'incrément de la colonne
     * @return true si la case est dans le tableau false sinon
     */
    static boolean dansLePlatau(Coordonnees coord,Case[][] plateau,int incrementLigne,int incrementColonne){
         return coord.ligne+incrementLigne>=0 && coord.ligne+incrementLigne<plateau.length && coord.colonne+incrementColonne >=0 && coord.colonne+incrementColonne <plateau[0].length;
     }
}

