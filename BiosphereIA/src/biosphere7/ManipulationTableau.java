package biosphere7;

class ManipulationTableau{
    
    static void AjouteSiPlusGrand(Point[] tab, Point ajout){
        if (tab[0] == null){
            tab[0] = ajout;
        }
        else if (tab[1] == null){
            tab[1] = ajout;
        }
        else if (ajout.point >tab[1].point){
            tab[1] =ajout;
            if(tab[1].point>tab[0].point){
                 echange(tab);
            }
        }
    }
    static void echange(Point[] tab){
        Point temp = tab[0];
        tab[0] = tab[1];
        tab[1] = temp;
    }
    
    static void SupprimeSiEstDansLeTab(Point[]tab, Coordonnees element){
        if(element.equals(tab[0].coord)){
            tab[0] = null;
            echange(tab);
        }
        else if (element.equals(tab[1].coord)){
            tab[1] = null;
        }
    }
    

}