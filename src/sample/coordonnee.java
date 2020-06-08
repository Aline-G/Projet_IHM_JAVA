package sample;

public class coordonnee {
    private int lat;
    private int lon;

    /**
     * Constructeur de coordonnées
     * @param lat entier qui représente la latitude
     * @param lon entier qui représente la longitude
     */
    public coordonnee(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public int getLon() {
        return lon;
    }


}
