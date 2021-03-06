package Application;

import java.util.LinkedHashMap;

public class Annee {
    private int annee;
    public LinkedHashMap<coordonnee,Float> listeEtatZone;

    /**
     * Constructeur d'année
     * @param annee entier qui représente l'année crée
     */
    public Annee(int annee) {
        this.listeEtatZone = new LinkedHashMap<>();
        this.annee = annee;
    }

    public int getAnnee() {
        return annee;
    }

    /**
     * Fonction qui cherche les anomalies pour une zone précise à l'aide des
     * longitude et latitude passées en paramètres
     * @param lat entier qui représente la latitude
     * @param lon entier qui représente la longitude
     * @return un float qui correspond à l'anomalie
     */
    public Float recAnomalie(int lat, int lon){
       float anomalie = 0f;
       for (coordonnee key : listeEtatZone.keySet()) {
            //on teste si les lat et lon sont égaux à ceux de la clé et si non on continue à chercher
            if (key.getLat() == lat && key.getLon() == lon) {
                anomalie = listeEtatZone.get(key);
            }
       }
       return anomalie;
    }

    /**
     * Fonction crée un tableau contenant toutes les valeurs d'anomalie pour une année
     * @return un tableau de float contenant toutes les anomalies
     */
    public Float[] etatZone(){
        Float[] tab = new Float[4050];
        int i = 0;
        //on parcourt les coordonnées
        for (coordonnee key : listeEtatZone.keySet()){
            tab[i]=recAnomalie(key.getLat(),key.getLon());
            i++;
        }
        return tab;
    }
}
