package biosphere7;

class MonIAMJ {
    
    Coordonnees parDefaut = new Coordonnees(-1, -1);
    Point pointFertiliser = new Point(0,parDefaut,'F');
    Point pointCouper = new Point(0,parDefaut,'C');
    Point pointPlanter = new Point(0,parDefaut,'H');
    Point pointPlanteTueuse = new Point(0,parDefaut,'H');
    
    MonIAMJ(Case [][]plateau, char couleur){
        for(int y=0;y<plateau.length;y++){
            for(int x=0; x<plateau.length;x++){
                if(plateau[y][x].plantePresente()){
                    if (plateau[y][x].couleur == couleur){
                        MJFertiliser(plateau[y][x],new Coordonnees(y,x));
                    }
                    else{
                        Coordonnees CoordPlante = new Coordonnees(y,x);
                        MJCouper(plateau[y][x],CoordPlante,plateau,couleur);
                        Voisines infosVoisines = new Voisines(CoordPlante, plateau, couleur);
                        if (infosVoisines.nbVoisines == 3) {
                            MJplanteTueuse(CoordPlante,plateau,couleur);
                        }
                    }
                }
                else {
                    MJplanter(new Coordonnees(y,x), plateau,couleur);
                }
            }
        }
    }
    Point MeilleurChoix(){
        Point [] tabChoix = new Point[]{pointFertiliser,pointCouper,pointPlanter,pointPlanteTueuse};
        Point meilleurChoix = pointFertiliser;
        for(int i=1; i < tabChoix.length;i++){
            if(tabChoix[i].point > meilleurChoix.point){
                meilleurChoix =tabChoix[i];
            }
        }
        return meilleurChoix;
     }
    
    void afficherChoix(){
        if(!pointFertiliser.coord.equals(parDefaut)){
            System.out.println("Fertiliser : point :"+pointFertiliser.point +" Coordonnees : ("+pointFertiliser.coord.carLigne()+","+pointFertiliser.coord.carColonne()+")");   
        }
        if(!pointCouper.coord.equals(parDefaut)){
            System.out.println("Couper : point :"+pointCouper.point +" Coordonnees : ("+pointCouper.coord.carLigne()+","+pointCouper.coord.carColonne()+")");

        }
        if(!pointPlanter.coord.equals(parDefaut)){
            System.out.println("Planter : point :"+pointPlanter.point +" Coordonnees : ("+pointPlanter.coord.carLigne()+","+pointPlanter.coord.carColonne()+")");

        }
        if(!pointPlanteTueuse.coord.equals(parDefaut)){
            System.out.println("PlanteTueuse : point :"+pointPlanteTueuse.point +" Coordonnees : ("+pointPlanteTueuse.coord.carLigne()+","+pointPlanteTueuse.coord.carColonne()+")");
        }
    }
    
    void MJFertiliser(Case laCase,Coordonnees coord){
        Point pointFertiliser = new Point(0,coord,'F');
                        switch(laCase.espece){
                             case 'P','S' -> pointFertiliser.point=1;
                             case 'B' -> pointFertiliser.point =2;
                             default -> pointFertiliser.point =3;
                        }
                        if (pointFertiliser.point + laCase.vitalite >9){
                            pointFertiliser.point= 9-laCase.vitalite;
                        }
                        pointFertiliser.point += MonIA.bonusFertiliser;
                        if(pointFertiliser.point>this.pointFertiliser.point){
                           this.pointFertiliser = pointFertiliser;
                        }
        
    }
    
    void MJCouper(Case laCase,Coordonnees coord, Case [][] plateau,char couleur){
        Point pointCouper = new Point(0,coord,'C');
        pointCouper.point += laCase.vitalite;
        Voisines infosVoisines = new Voisines(coord,plateau,couleur);
        int i=0;
        while (i<infosVoisines.casesVoisines.length && infosVoisines.casesVoisines[i] != null){
            if (infosVoisines.casesVoisines[i].vitalite<9){
                if (infosVoisines.casesVoisines[i].couleur == couleur){
                    pointCouper.point += 1;
                }
                else {
                    pointCouper.point -= 1;
                }
            }
            i++;
        }
        pointCouper.point += MonIA.bonusCouper;
        if(pointCouper.point>this.pointCouper.point){
            this.pointCouper = pointCouper;
        }
    }
    
    void MJplanteTueuse(Coordonnees coord,Case [][] plateau,char couleur){
        Point pointplanteTueuse = new Point(0,null,'H');
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if ((i+j)%2!=0 && Voisines.dansLePlatau(coord,plateau, i, j) && !plateau[coord.ligne+i][coord.colonne+j].plantePresente()){
                    Coordonnees coordCase= new Coordonnees(coord.ligne+i, coord.colonne+j);
                    pointplanteTueuse.coord = coordCase;
                    Case laCase = plateau[coord.ligne+i][coord.colonne+j];
                    pointplanteTueuse.point = pointPlanter(coordCase,plateau,couleur) +plateau[coord.ligne][coord.colonne   ].vitalite;
                    pointplanteTueuse.point += MonIA.bonusPlanteTueuse;
                    if(pointplanteTueuse.point>this.pointPlanteTueuse.point){
                            this.pointPlanteTueuse = pointplanteTueuse;
                    }
                }
            }
        }
    }
    
    void MJplanter(Coordonnees coord,Case [][]plateau,char couleur){
        Point pointPlanter = new Point(0,coord,'H');
        if (MonIA.auBord(coord.ligne, coord.colonne)){
            pointPlanter.point +=  MonIA.bonusBord;
        }
        else {
            pointPlanter.point += MonIA.bonusPlanteStack;
        }
        pointPlanter.point +=  pointPlanter(coord,plateau,couleur);
        if(pointPlanter.point>this.pointPlanter.point){
            this.pointPlanter = pointPlanter;
        }
        
        
    }
     static int pointPlanter(Coordonnees coord,Case [][]plateau,char couleur){
        Voisines infosVoisines = new Voisines(coord,plateau,couleur);
        return 1+infosVoisines.nbVoisinesMemeCouleur;
    }
     
     
    
    
}