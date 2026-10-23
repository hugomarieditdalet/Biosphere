package biosphere7;

class MonIA{
    static char couleur ;
    static Case[][] plateau ;
    static String coupsAdvairse;
    static int nbToursJeu = 1;
    
    static int bonusBord = 0;
    static int bonusPlanteStack = 0;
    static int bonusFertiliser = 0;
    static int bonusCouper = 0 ;
    static int bonusPlanteTueuse = 0 ;
    
    
    
    static String actionIA(){
        attributionBonus();
        MonIAMJ meilleursChoix = new MonIAMJ(plateau, couleur);
        meilleursChoix.afficherChoix();
        Point reponse = meilleursChoix.MeilleurChoix();
        return ""+reponse.action+""+reponse.coord.carLigne()+""+reponse.coord.carColonne();
    }
    
    static void attributionBonus(){
        if (nbToursJeu==39 ||nbToursJeu==40 ){
            bonusBord = 0;
            bonusPlanteStack = 0;
            bonusFertiliser = 0;
            bonusCouper = 0 ;
            bonusPlanteTueuse = 0 ;
        }
        else{
            Coordonnees coordCoupAdvairse = new Coordonnees(Coordonnees.carLigneVersNum(coupsAdvairse.charAt(1)),
                    Coordonnees.carColonneVersNum(coupsAdvairse.charAt(2)));
            if (coupsAdvairse.charAt(0)=='C'){
                bonusFertiliser-=2;
                bonusPlanteStack+=1;
                bonusBord+=1;
            }
            else if(coupsAdvairse.charAt(0)=='P'){
                if (auBord(coordCoupAdvairse.ligne,coordCoupAdvairse.colonne)){
                    bonusPlanteStack+=1;
                }
                Voisines infosVoisines = new Voisines(coordCoupAdvairse,plateau,couleur);
                int i=0;
                while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
                    if (infosVoisines.casesVoisines[i].couleur == couleur){
                        bonusBord+=1;
                        Voisines infosVoisinesDeLAVoisine = new Voisines(infosVoisines.coordcasesVoisines[i], plateau, couleur);
                        if (infosVoisinesDeLAVoisine.nbVoisines == 3){
                            bonusPlanteStack-=20;
                        }
                    }
                    i++;
                }
                
            }
        }
        
        
    }
    
    
    static boolean auBord(int y, int x){
        return y==0 || x==0 || y==13 || x==13;
    }
   
    
    
    
    
    
    
    
    
  

}