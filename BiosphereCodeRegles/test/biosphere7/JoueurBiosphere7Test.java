package biosphere7;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe JoueurBiosphere7.
 */
public class JoueurBiosphere7Test {

    /**
     * Test de la fonction actionsPossibles. Commentez les appels aux tests des
     * niveaux inférieurs, n'activez que le test du niveau à valider.
     */
    @Test
    public void testActionsPossibles() {
       //testActionsPossibles_niveau1();
       //testActionsPossibles_niveau2();
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 1.
     */
    public void testActionsPossibles_niveau1() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // un plateau sur lequel on veut tester actionsPossibles()
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        // on choisit la couleur du joueur
        char couleur = 'R';
        // on choisit le niveau
        int niveau = 1;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // on peut afficher toutes les actions possibles calculées :
        actionsPossibles.afficher();
        // on peut aussi tester si une action est dans les actions possibles :
        assertTrue(actionsPossibles.contient("PaB,1,0"));
        // on peut aussi tester si une action n'est pas dans les actions 
        // possibles :
        assertFalse(actionsPossibles.contient("PaO,1,0"));
        assertFalse(actionsPossibles.contient("PaA,0,0"));
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,1,0"));
        assertTrue(actionsPossibles.contient("PnA,1,0"));
        assertTrue(actionsPossibles.contient("PaN,1,0"));
        assertTrue(actionsPossibles.contient("PnN,1,0"));        
        // vérifions s'il y a le bon nombre d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);
    }
    
    /**
     * Test de la fonction actionsPossibles, au niveau 2.
     */
    public void testActionsPossibles_niveau2() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        // plateau, couleur et niveau
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        char couleur = 'B';
        int niveau = 2;
        // on lance actionsPossibles
        String[] actionsPossiblesDepuisPlateau
                = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        // testons les 4 coins :
        assertTrue(actionsPossibles.contient("PaA,2,3"));
        assertTrue(actionsPossibles.contient("PnA,2,3"));
        assertFalse(actionsPossibles.contient("PaN,2,3"));
        assertTrue(actionsPossibles.contient("PnN,2,3"));        
        // on peut poser sur une case quelconque vide :
        assertTrue(actionsPossibles.contient("PkD,2,3"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        // nombre correct d'actions possibles :
        actionsPossibles.afficher();
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES - 4,
                actionsPossiblesDepuisPlateau.length);
    }
    /**
     * Test de la fonction actionsPossibles, au niveau 3.
     */
    @Test
    public void testActionsPossibles_niveau3() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        char couleur = 'B';
        int niveau = 3;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //Testons sur les 4 plantes :
        assertTrue(actionsPossibles.contient("CaN,2,1"));
        assertTrue(actionsPossibles.contient("CeI,1,2"));
        assertTrue(actionsPossibles.contient("CfA,1,2"));
        assertTrue(actionsPossibles.contient("ChJ,2,1"));
        //On ne peut pas couper une case vide :
        assertFalse(actionsPossibles.contient("CeA,2,3"));
        assertFalse(actionsPossibles.contient("CeJ,2,3"));
        // nombre correct d'actions possibles :
        actionsPossibles.afficher();
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);   
    }
    /**
     * Test de la fonction actionsPossibles, au niveau 3.
     */
    @Test
    public void testActionsPossibles_niveau4() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU4);
        char couleur = 'B';
        int niveau = 4;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //24
        //13
        //Testons sur les 4 plantes :
        assertTrue(actionsPossibles.contient("CaN,2,1"));
        assertTrue(actionsPossibles.contient("CeI,1,2"));
        assertTrue(actionsPossibles.contient("CfA,1,2"));
        assertTrue(actionsPossibles.contient("ChJ,2,1"));
        //On ne peut pas couper une case vide :
        assertFalse(actionsPossibles.contient("CeA,2,3"));
        assertFalse(actionsPossibles.contient("CeJ,2,3"));
        //Testons les deux coins
        assertTrue(actionsPossibles.contient("CaN,25,12"));
        assertTrue(actionsPossibles.contient("CnA,24,13"));
        //Testons aux hasard
        assertTrue(actionsPossibles.contient("CiJ,15,14"));
        assertTrue(actionsPossibles.contient("CbN,15,15"));
        assertTrue(actionsPossibles.contient("CeI,23,13"));
        //On ne peut pas couper une case vide :
        assertFalse(actionsPossibles.contient("CeA,2,3"));
        assertFalse(actionsPossibles.contient("CeJ,2,3"));
        // nombre correct d'actions possibles :
        actionsPossibles.afficher();
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);   
    }
    
    @Test
    public void testActionsPossibles_niveau5() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU4);
        char couleur = 'B';
        int niveau = 5;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //24
        //13
        assertTrue(actionsPossibles.contient("PiI,24,16"));
        assertTrue(actionsPossibles.contient("PmB,24,16"));
        assertTrue(actionsPossibles.contient("PdI,24,15"));
        assertTrue(actionsPossibles.contient("PaA,24,14"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        actionsPossibles.afficher();
        // nombre correct d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);   
    }
    @Test
    public void testActionsPossibles_niveau6() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU4);
        char couleur = 'B';
        int niveau = 5;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //24
        //13
        assertTrue(actionsPossibles.contient("PiI,24,14"));
        assertTrue(actionsPossibles.contient("PmB,24,15"));
        assertTrue(actionsPossibles.contient("PdI,24,14"));
        assertTrue(actionsPossibles.contient("PaA,24,14"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        actionsPossibles.afficher();
        // nombre correct d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossiblesDepuisPlateau.length);   
    }
    
    @Test
    public void testActionsPossibles_niveau7() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU7);
        char couleur = 'B';
        int niveau = 5;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //50
        //44
        //On ne peut pas placer dans une case ou il y a 4 plantes voisines
        assertFalse(actionsPossibles.contient("PeH,50,44"));
        assertFalse(actionsPossibles.contient("PeH,5,45"));
        //Une plante meurt quand elle est étoufée :
        assertTrue(actionsPossibles.contient("PcH,48,45"));
        //Le cas où 3 plantes meurent étouffées
        assertTrue(actionsPossibles.contient("PcC,38,43"));
        // on ne peut pas poser sur une case occupée :
        assertFalse(actionsPossibles.contient("PfA,2,3"));
        assertFalse(actionsPossibles.contient("PeI,2,3"));
        assertFalse(actionsPossibles.contient("PhJ,2,3"));
        actionsPossibles.afficher();
        // nombre correct d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES-1,
                actionsPossiblesDepuisPlateau.length);   
    }
    
    @Test
    public void testActionsPossibles_niveau8() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU8);
        char couleur = 'B';
        int niveau = 8;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //50
        //44
        //On ne peut pas placer dans une case ou il y a 4 plantes voisines
        assertFalse(actionsPossibles.contient("HeH,50,44"));
        assertFalse(actionsPossibles.contient("PeH,5,45"));
        //Une plante meurt quand elle est étoufée :
        assertTrue(actionsPossibles.contient("ScH,48,45"));
        //Le cas où 3 plantes meurent étouffées
        assertTrue(actionsPossibles.contient("DcC,38,43"));
        //Les cas de fertilisations :
        assertTrue(actionsPossibles.contient("FbB,50,47"));
        assertTrue(actionsPossibles.contient("FbG,50,47"));
        assertTrue(actionsPossibles.contient("FcB,50,47"));
        assertTrue(actionsPossibles.contient("FaC,51,44"));
        //Les cas où la vitalité est au dessus de 9 après une fertilisation
        assertTrue(actionsPossibles.contient("FcA,50,45"));
        //assertTrue(actionsPossibles.contient("FbN,50,44"));
        
        actionsPossibles.afficher();
    }
    @Test
    public void testActionsPossibles_niveau9() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        char couleur = 'B';
        int niveau = 9;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //10
        //14
        // Si deux plantes autostériles de la même espèce Dissémine.
        assertTrue(actionsPossibles.contient("IcD,10,18"));
        assertTrue(actionsPossibles.contient("IcC,10,20"));
        //Une plante autostéles qui n'a pas de voisins ne peut pas Disséminen.
        assertFalse(actionsPossibles.contient("IbF,10,14"));
        assertFalse(actionsPossibles.contient("IbI,10,14"));
        assertFalse(actionsPossibles.contient("IbJ,10,14"));
        //une plante autofécondes peut se Disséminer elle même
        assertTrue(actionsPossibles.contient("IbF,10,18"));
        assertTrue(actionsPossibles.contient("IhF,10,17"));
        assertTrue(actionsPossibles.contient("IhG,10,17"));
        
        actionsPossibles.afficher();
    }
    @Test
    public void testActionsPossibles_niveau10() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10);
        char couleur = 'B';
        int niveau = 10;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //60
        //56
        // Si deux plantes autostériles de la même espèce Dissémine.
        assertTrue(actionsPossibles.contient("O,26,34"));
        plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        assertFalse(actionsPossibles.contient("O,6,506"));
        
        actionsPossibles.afficher();
    }
    @Test
    public void testActionsPossibles_niveau11() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU11);
        char couleur = 'B';
        int niveau = 11;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //5
        //8
        // On fait une rotaion sur une case qui n'a pas d'eau
        assertTrue(actionsPossibles.contient("RBaD,5,10"));
        assertTrue(actionsPossibles.contient("RBaE,5,11"));
        //On fait une rotaion sur une case qui a de l'eau
        assertTrue(actionsPossibles.contient("RBcB,5,11"));
        assertTrue(actionsPossibles.contient("RBbC,5,12"));
        //On plante proche d'un point d'eau
        assertTrue(actionsPossibles.contient("TaG,5,10"));
        assertTrue(actionsPossibles.contient("HaI,5,10"));
        //On ne peut pas planter dans un point d'eau
        assertFalse(actionsPossibles.contient("PaH"));
        assertFalse(actionsPossibles.contient("PbB"));
        //Pour faire une rotation la nouvelle espèce doit être d'une autre catégorie que celle de la plante remplacée
        assertFalse(actionsPossibles.contient("RHaD"));
        assertFalse(actionsPossibles.contient("RTbA"));
        //ON ne peut pas faire une rotation sur une plante de couleur adverse.
        assertFalse(actionsPossibles.contient("RTaB"));
        assertFalse(actionsPossibles.contient("RHbF"));
        
    }
    
    @Test
    public void testActionsPossibles_niveau12() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU12);
        char couleur = 'R';
        int niveau = 12;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //12
        //0
        // Quand la plante est de vitalité 8 qui est connecté avec 2 autres plantes
        assertTrue(actionsPossibles.contient("FfE,18,0"));
        // la plante est de vitalite 1, elle est connecté avec la plante de vitalite 8 
        assertTrue(actionsPossibles.contient("FgG,15,0"));
        // la plante est de vitalite 2, elle est connecté avec la plante de vitalite 8 
        assertTrue(actionsPossibles.contient("FiI,15,0"));
    }
    @Test
    public void testActionsPossibles_niveau13() {
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU13);
        char couleur = 'R';
        int niveau = 13;
        String[] actionsPossiblesDepuisPlateau = joueur.actionsPossibles(plateau, couleur, niveau);
        ActionsPossibles actionsPossibles= new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        //12
        //0
        assertTrue(actionsPossibles.contient("BcD,11,0"));
        assertTrue(actionsPossibles.contient("PaA,7,0"));
        assertTrue(actionsPossibles.contient("SaB,8,0"));
    }
    
    
    
    
    @Test
    public void a(){
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU12);
        assertTrue(JoueurBiosphere7.dansLePlateauBis(12, 12, plateau, 1, 1));
    }
    
    
    
    /**
     * Test de la fonction ajoutActionPlanter.
     */
    @Test
    public void testAjoutActionPlanter() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(0, 0);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        Voisines infosVoisines = new Voisines(Coordonnees.depuisCars('f', 'D'), plateau, Case.CAR_BLEU);
        // on crée le tableau d'actions et on en ajoute une
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('f', 'D'), actions, 
                vitalites, Case.CAR_ROUGE,plateau,'P',infosVoisines);
        // l'action est devenue possible
        assertTrue(actions.contient("PfD,1,0"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        infosVoisines = new Voisines(Coordonnees.depuisCars('b', 'H'), plateau, Case.CAR_BLEU);
        joueur.ajoutActionPlanter(Coordonnees.depuisCars('b', 'H'), actions, 
                vitalites, Case.CAR_ROUGE,plateau,'P',infosVoisines);
        // l'action a bien été ajoutée
        assertTrue(actions.contient("PbH,1,0"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }
    /**
     * Test de la fonction ajoutACouper.
     */
    @Test
    public void testAjoutCouper() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(24, 13);
        //24
        //13
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        joueur.ajoutCouper(Coordonnees.depuisCars('e', 'I'), actions, vitalites, plateau);
        // l'action est devenue possible
        assertTrue(actions.contient("CaN,28,13"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutCouper(Coordonnees.depuisCars('e', 'I'), actions, vitalites, plateau);
        // l'action a bien été ajoutée
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }
    
    /**
     * Test de la fonction ajoutACouper.
     */
    @Test
    public void testAjoutFertiliser() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(2, 2);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        joueur.ajoutFertiliser(Coordonnees.depuisCars('a', 'N'), actions, vitalites, plateau);
        // l'action est devenue possible
        assertTrue(actions.contient("FaN,2,3"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        // ajout d'une deuxième action possible
        joueur.ajoutCouper(Coordonnees.depuisCars('e', 'I'), actions, vitalites, plateau);
        // l'action a bien été ajoutée
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }
    
    /**
     * Test de la fonction ajoutDisseminer.
     */
    @Test
    public void testAjoutDisseminer() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU9);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(10, 14);
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        joueur.ajoutDisseminer(Coordonnees.depuisCars('b', 'F'), actions, vitalites, plateau, Case.CAR_BLEU);
        // l'action est devenue possible
        assertTrue(actions.contient("IbF,10,18"));
        // une action possible mais qui n'a pas encore été ajoutée
        assertFalse(actions.contient("PbH,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
        vitalites = new Vitalites(10, 14);
        joueur.ajoutDisseminer(Coordonnees.depuisCars('h', 'G'), actions, vitalites, plateau, Case.CAR_BLEU);
        actions.afficher();
        assertTrue(actions.contient("IhG,10,17"));
        // désormais, deux actions possibles
        assertEquals(2, actions.nbActions);
    }
    
    /**
     * Test de la fonction ajoutACouper.
     */
    @Test
    public void testAjoutOmbre() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU10);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(60,56);
        //60
        //56
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        joueur.ajoutOmbre(actions, vitalites, plateau);
        // l'action est devenue possible
        actions.afficher();
        assertTrue(actions.contient("O,25,16"));
        assertFalse(actions.contient("0,1,0"));
        // pour l'instant une seule action possible
        assertEquals(1, actions.nbActions);
    }
    
    @Test
    public void testAjoutajoutRotation() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU11);
        JoueurBiosphere7 joueur = new JoueurBiosphere7();
        ActionsPossibles actions = new ActionsPossibles();
        Vitalites vitalites = new Vitalites(5,8);
        //5
        //8
        // pour l'instant pas d'action possible
        assertEquals(0, actions.nbActions);
        joueur.ajoutRotation(Coordonnees.depuisCars('a', 'G'), actions, vitalites, plateau,Case.CAR_BLEU, 'P');
        actions.afficher();
        assertTrue(actions.contient("RPaG,5,12"));
        joueur.ajoutRotation(Coordonnees.depuisCars('c', 'B'), actions, vitalites, plateau,Case.CAR_BLEU, 'B');
        actions.afficher();
        assertTrue(actions.contient("RBcB,0,3"));
        assertFalse(actions.contient("0,1,0"));
        // pour l'instant une seule action possible
        assertEquals(2, actions.nbActions);
    }
    
    @Test
    public void testResetTab(){
        Coordonnees[] tab ={new Coordonnees(15,2),new Coordonnees(11,5),new Coordonnees(11,5)};
        JoueurBiosphere7.resetTab(tab);
        assertArrayEquals(new Coordonnees[]{null,null,null},tab);
        JoueurBiosphere7.resetTab(tab);
        assertArrayEquals(new Coordonnees[]{null,null,null},tab); 
    }
    
    @Test 
    public void testAjouter(){
        Coordonnees[] tab ={new Coordonnees(15,2),new Coordonnees(11,5),null,null};
        JoueurBiosphere7.ajouter(tab,new Coordonnees(15,8));
        assertArrayEquals(new Coordonnees[]{new Coordonnees(15,2),new Coordonnees(11,5),new Coordonnees(15,8),null},tab);
        JoueurBiosphere7.ajouter(tab,new Coordonnees(17,8));
        assertArrayEquals(new Coordonnees[]{new Coordonnees(15,2),new Coordonnees(11,5),new Coordonnees(15,8),new Coordonnees(17,8)},tab);
    }
    @Test
    public void testEstDansLeTab(){
        Coordonnees[] tab ={new Coordonnees(15,2),new Coordonnees(11,5),null,null};
        assertFalse(JoueurBiosphere7.estDansLeTab(tab,new Coordonnees(15,8)));
        JoueurBiosphere7.ajouter(tab,new Coordonnees(15,8));
        assertTrue(JoueurBiosphere7.estDansLeTab(tab,new Coordonnees(15,8)));
    }
    @Test
    public void testAutoSteriles(){
        assertTrue(JoueurBiosphere7.autoSteriles('T'));
        assertTrue(JoueurBiosphere7.autoSteriles('H'));
        assertFalse(JoueurBiosphere7.autoSteriles('P'));
    }
    
    @Test
    public void testCategorie(){
        assertEquals(1,JoueurBiosphere7.categorie(('P')));
        assertEquals(1,JoueurBiosphere7.categorie(('S')));
        assertEquals(2,JoueurBiosphere7.categorie(('B')));
        assertEquals(3,JoueurBiosphere7.categorie(('D')));
        assertEquals(3,JoueurBiosphere7.categorie(('T')));
        assertEquals(3,JoueurBiosphere7.categorie(('H')));
    }
    

    @Test
    public void testVitalitesPlateau() {
        // plateau : rouge 0, bleu 0
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Vitalites vita1 = new Vitalites(plateau1);
        assertEquals(0, vita1.vitalitesRouge);
        assertEquals(0, vita1.vitalitesBleu);
        // plateau : rouge 2, bleu 2
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        Vitalites vita2 = new Vitalites(plateau2);
        assertEquals(2, vita2.vitalitesRouge);
        assertEquals(2, vita2.vitalitesBleu);
    }
    
    @Test
    public void testansLePlatau(){
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Coordonnees coord1 = new Coordonnees(0, 0);
        Coordonnees coord2 = new Coordonnees(13, 13);
        Coordonnees coord3 = new Coordonnees(5, 6);
        //On test d
        assertFalse(Voisines.dansLePlatau(coord1, plateau1, -1, 0));
        assertFalse(Voisines.dansLePlatau(coord1, plateau1, 0, -1));
        assertFalse(Voisines.dansLePlatau(coord2, plateau1, 1, 0));
        assertFalse(Voisines.dansLePlatau(coord2, plateau1, 0, 1));
        assertTrue(Voisines.dansLePlatau(coord3, plateau1, 0, 1));
        assertTrue(Voisines.dansLePlatau(coord3, plateau1, -1, 1));
    }
    
    @Test
    public void testDansLePlateauBis(){
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        Coordonnees coord1 = new Coordonnees(0, 0);
        Coordonnees coord2 = new Coordonnees(13, 13);
        Coordonnees coord3 = new Coordonnees(5, 6);
        //On test d
        assertFalse(JoueurBiosphere7.dansLePlateauBis(0,0,plateau1, -1, 0));
        assertFalse(JoueurBiosphere7.dansLePlateauBis(0,0,plateau1, 0, -1));
        assertFalse(JoueurBiosphere7.dansLePlateauBis(13,13, plateau1, 1, 0));
        assertFalse(JoueurBiosphere7.dansLePlateauBis(13,13, plateau1, 0, 1));
        assertTrue(JoueurBiosphere7.dansLePlateauBis(5,6, plateau1, 0, 1));
        assertTrue(JoueurBiosphere7.dansLePlateauBis(5,6, plateau1, -1, 1));
    }

    
    /**
     * Un plateau de base, sous forme de chaîne. Pour construire une telle
     * chaîne depuis votre sortie.log, déclarez simplement : final String
     * MON_PLATEAU = ""; puis copiez le plateau depuis votre sortie.log, et
     * collez-le entre les guillemets. Puis Alt+Shift+f pour mettre en forme.
     */
    final String PLATEAU_VIDE
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    /**
     * Un plateau pour tester le niveau 2 et 3.
     */
    final String PLATEAU_NIVEAU2
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU4
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |PR1|PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |PB2|PR9|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|PR1|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |PR1|PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |PR9|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|PB9|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|PR1|PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU7
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |SR7|   |   |   |   |   |   |   |   |   |PR1|PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |PB1|PR5|PB4|   |   |PB1|   |   |   |   |   |PB2|PR9|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|PT8|PB3|   |PR7|PB5|PB4|PR2|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |PB2|   |PB1|   |   |PB1|PR3|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |PR2|   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|PR1|   |   |   |   |   |   |PB1|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |PR1|PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |PR9|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|PB9|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|PR1|PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU8
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |PR7|   |   |   |   |   |   |   |   |   |PR1|PB1|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |HB1|HR5|PB4|   |   |TB1|   |   |   |   |   |PB2|PR9|
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|PB8|DB3|   |PR7|PB5|PB4|PR2|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |PB2|   |PB1|   |   |PB1|PR3|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |SR2|   |PR1|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|PR1|   |   |   |   |   |   |PB1|   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |PR1|PB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |PR9|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|PB9|   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|PR1|PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    final String PLATEAU_NIVEAU9
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |PB1|   |   |TR2|HB3|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |TB2|TB3|TR7|   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |BR1|DB5|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU10
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|PB9|HR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |PB9|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |SB9|   |   |   |   |   |   |   |TR2|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |SR9|   |   |   |   |   |   |   |TR2|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |SB9|   |   |   |   |   |   |   |TR2|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |SR9|   |   |   |   |   |   |   |TR2|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |SB9|   |   |   |   |HR3|   |   |BB1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |SR9|   |   |   |   |PB4|   |   |DB3|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |SB9|   |   |   |   |SR3|   |   |HR1|   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |SR9|   |   |   |   |PB3|   |   |PR9|   |   |PB9|   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU11
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+E--+---+---+---+---+---+---+
              a|   |HR2|   |HB3|TB1|   |   |   |   |   |   |   |   |   |
               +---+E--+---+---+---+---+---+---+---+---+---+---+---+---+
              b|TR1|   |PB1|TB2|   |TR2|   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |HB1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    
    final String PLATEAU_NIVEAU12
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |TR8|   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |TR1|   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |TR2|   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;
    final String PLATEAU_NIVEAU13
            = """
                 A   B   C   D   E   F   G   H   I   J   K   L   M   N 
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              b|   |PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              c|PR1|PR1|PR1|   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              d|PR1|PR1|   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |
               +---+---+---+---+---+---+---+---+---+---+---+---+---+---+
              """;

    
    
    
   
}
