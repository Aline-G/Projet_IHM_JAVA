package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class Reseau {

    private HashMap<Integer,Zone> listeZone;
    private HashMap<Integer,Annee> listeAnnee;
    private float minimum;
    private float maximum;

    public Reseau(ArrayList<ArrayList<String>> collec){
        listeZone = new HashMap<>();
        listeAnnee = new HashMap<>();

        int lat= 0;
        int lon= 1;
        String s ="NA";
        float etat;

        //on parcourt chaque colonne à partir de la colonne 2 pck avant c'est lat et lon
       for(int j=2; j<143; j++){
           //on rentre la bonne année
           int an = 1880+j-2;
           //on crée une nouvelle année
           Annee annee = new Annee(an);
           //on parcourt toutes les lignes de la collection
           for(int i=1; i<collec.size();i++){
               //on regarde si l'etat vaut NA et on le transforme en float
               if (collec.get(i).get(j).equals(s)){
                   etat = 0f;
               }else{
                   etat = Float.parseFloat(collec.get(i).get(j));
               }
               //récupération des lat et lon
               int latitude = Integer.valueOf(collec.get(i).get(0));
               int longitude = Integer.valueOf(collec.get(i).get(1));
               coordonnee coor = new coordonnee(latitude,longitude);
               //on implémente la liste zone de cette année juste crée
               annee.listeEtatZone.put(coor,etat);
           }
           this.listeAnnee.put(annee.getAnnee(),annee);
        }


/*
        for(int i=1; i<collec.size();i++){
            Zone z = new Zone(collec.get(i).get(lat),collec.get(i).get(lon));
            //System.out.println(collec.get(0).get(5));
            for(int j=0; j<143; j++){
                //System.out.println(collec.get(0).get(1));
                if (collec.get(i).get(j).equals(s)){
                    z.setEtat(collec.get(0).get(j), 0f);
                    //System.out.println("dans le if de reseau");
                }else{
                    //System.out.println("dans le else de reseau");
                    z.setEtat(collec.get(0).get(j),Float.parseFloat(collec.get(i).get(j)));
                }
            }
            listeZone.put(z.getIdZone(),z);
        }

*/
    }


    public void rechercheMinMax() {
        float min = 0;
        float max = 0;
        //on parcourt la liste d'annee et pour chaque annee on regarde toutes les valeurs
        for (int an : listeAnnee.keySet()) {
            //on parcourt la liste d'etat de chaque zone
            for (coordonnee key : listeAnnee.get(an).listeEtatZone.keySet()) {
                if (listeAnnee.get(an).listeEtatZone.get(key) < min) {
                    //si oui on modifie min
                    min = listeAnnee.get(an).listeEtatZone.get(key);
                }
                //on teste si le max est inférieur à la valeur de l'état à la coordonnée i,j
                else if (listeAnnee.get(an).listeEtatZone.get(key) > max) {
                    //si oui on modifie max
                    max = listeAnnee.get(an).listeEtatZone.get(key);

                }
            }
        }
        //Mise à jour des donnéée du réseau
        this.setMaximum(max);
        this.setMinimum(min);
    }

    public Float rechercheValAnomalie(int annee, int lat, int lon){
        float anomalie = 0f;
        //on parcourt la liste d'année
        for(int an : listeAnnee.keySet()){
            //System.out.println(listeAnnee.get(an).getAnnee());
            int ann = listeAnnee.get(an).getAnnee();
            //si on est dans la bonne année on rentre dans la boucle
            if (ann == annee) {
                //parcours de la liste etat zone pour trouver l'anomalie demandée
                for (coordonnee key : listeAnnee.get(an).listeEtatZone.keySet()) {
                    //on teste si les lat et lon sont égaux à ceux de la clé si non on continue à chercher
                    if (key.getLat() == lat && key.getLon() == lon) {
                        anomalie = listeAnnee.get(an).listeEtatZone.get(key);
                    }
                }
            }
        }
        return anomalie;
    }

    public void afficherListeZone(){
        for (int i=0; i<listeZone.size();i++){
            int lieu = 1880 + i;
            System.out.println(listeZone.get(lieu));
        }
    }
    public void afficherListeAnnee(){
        //on parcourt la liste d'annee
        for (int i=0; i<listeAnnee.size();i++){
            int lieu = 1880 + i;
            System.out.println(listeAnnee.get(lieu));
        }
    }
    public Float[] RecAnomalieAnnee(int annee){
        Float[] tab = new Float[140];
        for(int key : listeAnnee.keySet()){
            if(listeAnnee.get(key).getAnnee()==annee){
                tab = listeAnnee.get(key).etatZone();
            }
        }
        return tab;
    }

    public void setMinimum(float minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(float maximum) {
        this.maximum = maximum;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }

}
