package sample;

public class coordonnee {
    private int lat;
    private int lon;
    //private int idCoor;
    //private int compteur=0;

    public coordonnee(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
       // compteur++;
        //this.idCoor=compteur;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }


}
