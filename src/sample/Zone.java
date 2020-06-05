package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class Zone {

    private String latitude;
    private String longitude;
    private int idZone;
    public HashMap<String,Float> etat;
    private ArrayList<String> annee;
    private int compteur=0;

    public Zone(String lat, String lon) {

        this.etat = new HashMap<>();
        this.annee = new ArrayList<>();
        this.latitude = lat;
        this.longitude = lon;
        for(int i=0; i<140; i++){
            String date = Integer.toString(1880 + i);
            this.annee.add(i,date);
        }
        compteur++;
        this.idZone=compteur;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setEtat(String lieu, float etat) {
        this.etat.put(lieu, etat);
    }
    public Float getEtat(String lieu) {
        return this.etat.get(lieu);
    }
   public String getAnnee(int lieu) {
        return this.annee.get(lieu);
    }
}
