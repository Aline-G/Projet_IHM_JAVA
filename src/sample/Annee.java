package sample;

import java.util.HashMap;

public class Annee {
    private int annee;
    public HashMap<coordonnee,Float> listeEtatZone;

    public Annee(int annee) {
        this.listeEtatZone = new HashMap<>();
        this.annee = annee;
    }

    public int getAnnee() {
        return annee;
    }

    public HashMap<coordonnee, Float> getListeEtatZone() {
        return listeEtatZone;
    }
     public Float getEtat(coordonnee lieu){
        return listeEtatZone.get(lieu);
     }

     public Float RecAnomalie(coordonnee coor){
         for (coordonnee key : listeEtatZone.keySet()) {
             if (listeEtatZone.get(key).equals(coor)) {
                 return listeEtatZone.get(key);
             }
         }
         return 0f;
     }
}
