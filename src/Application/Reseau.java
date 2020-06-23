package Application;

import Interface.Controller;

import java.util.ArrayList;
import java.util.HashMap;

public class Reseau {


    public HashMap<Integer,Annee> listeAnnee;
    private float minimum;
    private float maximum;
    Controller controller;
    private boolean Carre =false;
    private boolean Histo = false;
    private int latValue=-88;
    private int lonValue=-176;

    public void setGraphique(boolean graphique) {
        this.graphique = graphique;
    }

    public boolean isGraphique() {
        return graphique;
    }

    private boolean graphique = false;

    /**
     * Constructeur du réseau
     * implémente les attributs du réseau
     * @param collec tableau de données contenant les valeurs des anomalies, les coordonnées et les années
     */
    public Reseau(ArrayList<ArrayList<String>> collec, Controller controller){
        listeAnnee = new HashMap<>();
        String s ="NA";
        float etat;
        this.controller = controller;

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
    }

    /**
     * Fonction qui met à jour le minimum et le maximum du réseau
     */
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
        //Mise à jour des données du réseau
        this.setMaximum(max);
        this.setMinimum(min);
    }

    /**
     * Fonction qui cherche une anomalie à partir de l'année, la latitude et la longitude
     * passées en paramètres
     * @param annee entier qui représente l'année
     * @param lat entier qui représente la latitude
     * @param lon entier qui représente la longitude
     * @return un float qui correspond à l'anomalie
     */
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

    /**
     * Fonction qui affiche les années présentent dans le réseau
     */
    public void afficherListeAnnee(){
        String s ="";
        //on parcourt la liste d'annee
        for (int i=0; i<listeAnnee.size();i++){
            int lieu = 1880 + i;
            s= s+" "+listeAnnee.get(lieu).getAnnee();
        }
        System.out.println(s);
    }

    /**
     * Fonction qui cherche les anomalies pour une année passée en paramètre
     * @param annee entier qui représente l'année demandée
     * @return un tableau de float contenant toutes les anomalies
     */
    public Float[] recAnomalieAnnee(int annee){
        Float[] tab = new Float[140];
        //on parcourt la liste d'années
        for(int key : listeAnnee.keySet()){
            //on cherche la bonne année
            if(listeAnnee.get(key).getAnnee()==annee){
                //on appelle la fonction qui retourne le tableau de valeur pour l'année demandée
                tab = listeAnnee.get(key).etatZone();
            }
        }
        return tab;
    }

    /**
     * Fonction qui cherche les anomalies pour une zone précise à l'aide des
     * longitude et latitude passées en paramètres
     * @param lat entier qui représente la latitude
     * @param lon entier qui représente la longitude
     * @return un tableau de float contenant toutes les anomalies
     */
    public Float[] recAnomalieZone(int lat, int lon){
        Float[] tab = new Float[141];
        int i = 0;
        String s = "";
        //on parcourt la liste d'années
        for(int key : listeAnnee.keySet()){
            tab[i]=listeAnnee.get(key).recAnomalie(lat,lon);
            //pour l'affichage
            s = s +" "+ tab[i];
            //pour avancer dans le tableau tab
            i++;
        }
        //on affiche les valeurs du tableau
        System.out.println(s);
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

    public boolean isCarre() {
        return Carre;
    }

    public boolean isHisto() {
        return Histo;
    }

    public void setCarre(boolean carre) {
        Carre = carre;
    }

    public void setHisto(boolean histo) {
        Histo = histo;
    }

    public int getLatValue() { return latValue; }

    public int getLonValue() { return lonValue; }

    public void setLatValue(int latValue) { this.latValue = latValue; }

    public void setLonValue(int lonValue) { this.lonValue = lonValue; }
}
