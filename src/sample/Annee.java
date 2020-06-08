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

    public Float RecAnomalie(int lat, int lon){
       float anomalie = 0f;
       for (coordonnee key : listeEtatZone.keySet()) {
            //on teste si les lat et lon sont égaux à ceux de la clé et si non on continue à chercher
            if (key.getLat() == lat && key.getLon() == lon) {
                anomalie = listeEtatZone.get(key);
            }
        }
       return anomalie;
    }
    public Float[] etatZone(){
        Float[] tab = new Float[4050];
        int i = 0;
        String s = "";
        //System.out.println(listeEtatZone.size());
        for(int k=-88; k<90; k+=4){
            for (int j = -178; j<180; j+=4){
                tab[i] = RecAnomalie(k,j);
                s = s +" "+ tab[i];
                i++;
            }
        }

        System.out.println(s);
        return tab;
    }
}
