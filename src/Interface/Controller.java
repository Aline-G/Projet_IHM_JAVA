package Interface;
import Application.Annee;
import Application.Reseau;
import Application.coordonnee;
import data.FileReader;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    private Reseau reseau;
    private LinkedHashMap<coordonnee,MeshView> quadrillage;
    private int annee = 1880;

    @FXML
    Pane pane3D;
    @FXML
    Pane legende;
    @FXML
    TextField textAnnee;
    @FXML
    RadioButton carreRB;
    @FXML
    RadioButton histoRB;
    @FXML
    Slider slidAnnee;
    @FXML
    Button buttonPlay;
    @FXML
    Button buttonStop;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>();
        FileReader.getDataFromCSVFile("src/data/tempanomaly_4x4grid.csv", donnees);
        Group root3D = new Group();
        Group leg = new Group();
        this.reseau = new Reseau(donnees, this);
        this.quadrillage = new LinkedHashMap<>();

        //initialisation du slider
        slidAnnee.setMax(2020);
        slidAnnee.setMin(1880);
        slidAnnee.setShowTickLabels(true);
        slidAnnee.setShowTickMarks(true);
        slidAnnee.setBlockIncrement(10);
        slidAnnee.setMajorTickUnit(20);

        //initialisation du quadrillage
        Quadrillage(root3D,reseau);

        // Load geometry
        ObjModelImporter objImporter = new ObjModelImporter();
        try{
            URL modelUrl = this.getClass().getResource("Earth/earth.obj");
            objImporter.read(modelUrl);
        } catch (ImportException e){
            //handle exception
            System.out.println(e.getMessage());
        }
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);

        //cube de couleurs pour la légende
        Box cube = new Box(2.2, 0.65, 1);
        Box cube1 = new Box(2.2,0.65,1);
        Box cube2 = new Box(2.2,0.65,1);
        Box cube3 = new Box(2.2,0.65,1);
        Box cube4 = new Box(2.2,0.65,1);
        Box cube5 = new Box(2.2,0.65,1);
        Box cube6 = new Box(2.2,0.65,1);
        Box cube7 = new Box(2.2,0.65,1);

        //creation des couleurs
        double opacity = 0.05;
        final PhongMaterial c1 = new PhongMaterial();
        c1.setDiffuseColor(new Color(0.5,0,0, opacity));
        c1.setSpecularColor(new Color(0.5,0,0, opacity));
        final PhongMaterial c2 = new PhongMaterial();
        c2.setDiffuseColor(new Color(0.5,0.2,0, opacity));
        c2.setSpecularColor(new Color(0.5,0.2,0, opacity));
        final PhongMaterial c3 = new PhongMaterial();
        c3.setDiffuseColor(new Color(0.5,0.5,0, opacity));
        c3.setSpecularColor(new Color(0.5,0.5,0, opacity));
        final PhongMaterial c4 = new PhongMaterial();
        c4.setDiffuseColor(new Color(0.2,0.4,0.2, opacity));
        c4.setSpecularColor(new Color(0.2,0.4,0.2, opacity));
        final PhongMaterial c5 = new PhongMaterial();
        c5.setDiffuseColor(new Color(0.1,0.2,0.5, opacity));
        c5.setSpecularColor(new Color(0.1,0.2,0.5, opacity));
        final PhongMaterial c6 = new PhongMaterial();
        c6.setDiffuseColor(new Color(0,0,0.6, opacity));
        c6.setSpecularColor(new Color(0,0,0.6, opacity));
        final PhongMaterial c7 = new PhongMaterial();
        c7.setDiffuseColor(new Color(0,0,0.3, opacity));
        c7.setSpecularColor(new Color(0,0,0.3, opacity));
        final PhongMaterial c8 = new PhongMaterial();
        c8.setDiffuseColor(new Color(0,0,0.1, opacity));
        c8.setSpecularColor(new Color(0,0,0.1, opacity));

        //Set it to the cube
        cube.setMaterial(c8);
        cube1.setMaterial(c7);
        cube2.setMaterial(c6);
        cube3.setMaterial(c5);
        cube4.setMaterial(c4);
        cube5.setMaterial(c3);
        cube6.setMaterial(c2);
        cube7.setMaterial(c1);

        //placement des cubes de légende
        cube.setTranslateY(-2.3);
        cube1.setTranslateY(-1.65);
        cube2.setTranslateY(-1);
        cube3.setTranslateY(-0.35);
        cube4.setTranslateY(0.3);
        cube5.setTranslateY(0.95);
        cube6.setTranslateY(1.6);
        cube7.setTranslateY(2.25);

        //Add the cube to this node
        leg.getChildren().add(cube);
        leg.getChildren().add(cube1);
        leg.getChildren().add(cube2);
        leg.getChildren().add(cube3);
        leg.getChildren().add(cube4);
        leg.getChildren().add(cube5);
        leg.getChildren().add(cube6);
        leg.getChildren().add(cube7);


        //mise à jour de l'etat des rb boutons
        carreRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                carreClick(mouseEvent);
            }
        });
        histoRB.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                histoClick(mouseEvent);
            }
        });

        //Ajout d'event sur la zone de texte
        textAnnee.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try
                {
                    int valeurAnnee = Integer.parseInt(newValue);
                    if(valeurAnnee > 2020 || valeurAnnee < 1880){
                        System.out.println("Vous devez entrer une annee comprise entre 1880 et 2020");
                        return;
                    }
                    //Appel de la fonction qui modifie l'état de la terre en testant si les rb button
                    if(reseau.isCarre()){
                        nettoyage(root3D);
                        slidAnnee.setValue(valeurAnnee);
                        dessinCarre(root3D,reseau,valeurAnnee,c1,c2,c3,c4,c5,c6,c7,c8);
                    }
                    else if(reseau.isHisto()){
                        nettoyage(root3D);
                        slidAnnee.setValue(valeurAnnee);
                        dessinHisto(root3D,reseau,valeurAnnee,c1,c2,c3,c4,c5,c6,c7,c8);
                    }
                    else{
                        System.out.println("Sélectionnez un mode");
                    }

                }
                catch (Exception e){
                    System.out.println("Vous devez entrer un entier");
                    return;
                }
            }
        });


        //Marche pas !!!
        final long startNanoTime = System.nanoTime();
        int currentspeed = 1;
        //création de l'animation
        AnimationTimer ani = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime-startNanoTime)/1000000000.0;
                slidAnnee.setValue((double)annee);
                //on teste quel mode est choisi si l'année n'est pas trop grande
                if(reseau.isCarre() && annee<2021 ){
                    System.out.println(annee);
                    slidAnnee.setValue(annee);
                    nettoyage(root3D);
                    dessinCarre(root3D,reseau,annee,c1,c2,c3,c4,c5,c6,c7,c8);
                    annee ++;
                }
                if(reseau.isHisto() && t%currentspeed>0.98 && annee<2021 ){
                    slidAnnee.setValue(annee);
                    nettoyage(root3D);
                    dessinHisto(root3D,reseau,annee,c1,c2,c3,c4,c5,c6,c7,c8);
                    annee ++;
                }
                    /*if(reseau.isHisto()){
                        nettoyage(root3D);
                        dessinHisto(root3D,reseau,i,c1,c2,c3,c4,c5,c6,c7,c8);
                    }else if(reseau.isCarre()){
                        nettoyage(root3D);
                        dessinCarre(root3D,reseau,i,c1,c2,c3,c4,c5,c6,c7,c8);
                    }else{ // ça ne s'arrête jamais
                        System.out.println("Sélectionnez un mode");
                    }*/
                if(annee>=2021){
                    this.stop();
                }
            }
        };


        buttonPlay.setOnAction(event -> { ani.start(); });
        buttonStop.setOnAction(event -> { ani.stop(); });


        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        PerspectiveCamera cam = new PerspectiveCamera(true);

        //Build camera manager
        new CameraManager(camera, pane3D, root3D);
        new CameraManager(cam, legende, leg);


        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);


        //Creation de la subscene pour la legende
        SubScene subLeg = new SubScene(leg,50,118,true,SceneAntialiasing.BALANCED);
        subLeg.setFill(Color.GREY);
        subLeg.setCamera(cam);
        legende.getChildren().addAll(subLeg);

        //Create the subscene
        SubScene subscene = new SubScene(root3D, 452.0,393.0, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);
    }


    /**
     * Fonction qui prend en paramètres des coordonnées et retourne un point3D
     * @param lat représente la latitude
     * @param lon représente la longitude
     * @param radius représente le rayon de la sphère sur laquelle se situe les coordonnées
     * @return un point3D
     */
    public static Point3D geoCoordTo3dCoord(float lat, float lon, float radius) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor))*radius,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius);
    }

    private void Quadrillage(Group parent, Reseau res){
        Point3D topRight ;
        Point3D bottomRight;
        Point3D topLeft;
        Point3D bottomLeft;

        for (coordonnee key : res.listeAnnee.get(1880).listeEtatZone.keySet()) {

            bottomLeft = geoCoordTo3dCoord(key.getLat() - 2, key.getLon() - 2, 1.05f);
            bottomRight = geoCoordTo3dCoord(key.getLat() - 2, key.getLon() + 2, 1.05f);
            topLeft = geoCoordTo3dCoord(key.getLat() + 2, key.getLon() - 2, 1.05f);
            topRight = geoCoordTo3dCoord(key.getLat() + 2, key.getLon() + 2, 1.05f);

            coordonnee coor = new coordonnee(key.getLat(),key.getLon());
            this.quadrillage.put(coor,AddQuadrilateral(parent,topRight,bottomRight,bottomLeft,topLeft));
        }
    }

    /**
     * Fonction qui dessine les carrés avec la bonne couleur en fonction de la valeur de l'anomalie pour une année donnée
     *
     * @param res représente le réseau qui contient toutes les données
     * @param an représente l'année selectionnée
     * @param c1 représente la couleur 1
     * @param c2 représente lacouleur 2
     * @param c3 représente la couleur 3
     * @param c4 représente la couleur 4
     * @param c5 représente la couleur 5
     * @param c6 représente la couleur 6
     * @param c7 représente la couleur 7
     * @param c8 représnete la couleur 8
     */
    private void dessinCarre(Group parent,Reseau res, int an, PhongMaterial c1,PhongMaterial c2,PhongMaterial c3,PhongMaterial c4,PhongMaterial c5,PhongMaterial c6,PhongMaterial c7,PhongMaterial c8){

        int compteur =0;
        //on récupère les valeurs des anomalies pour l'année donnée
        Float[] tabAno = res.RecAnomalieAnnee(an);
        //on parcourt toutes les coordonnées dans le quadrillage
        for (coordonnee key : quadrillage.keySet()) {

            //on choisit la couleur en fonction de la valeur de l'anomalie
            if(tabAno[compteur]>-7 && tabAno[compteur]<=-5){
                quadrillage.get(key).setMaterial(c8);
            }
            else if(tabAno[compteur]>-5 && tabAno[compteur]<=-3){
                quadrillage.get(key).setMaterial(c7);
            }
            else if(tabAno[compteur]>-3 && tabAno[compteur]<=-1){
                quadrillage.get(key).setMaterial(c6);
            }
            else if(tabAno[compteur]>-1 && tabAno[compteur]<=0){
                quadrillage.get(key).setMaterial(c5);
            }
            else if(tabAno[compteur]>0 && tabAno[compteur]<=1){
                quadrillage.get(key).setMaterial(c4);
            }
            else if(tabAno[compteur]>1 && tabAno[compteur]<=3){
                quadrillage.get(key).setMaterial(c3);
            }
            else if(tabAno[compteur]>3 && tabAno[compteur]<=5){
                quadrillage.get(key).setMaterial(c2);
            }
            else if(tabAno[compteur]>5 && tabAno[compteur]<=9){
                quadrillage.get(key).setMaterial(c1);
            }
            parent.getChildren().add(quadrillage.get(key));
            compteur++;
        }
    }

    /**
     * Fonction qui permet d'enlever les affichages précédents de la Terre
     * @param parent représente le groupe que l'on va clear
     */
   public void nettoyage(Group parent){
       parent.getChildren().clear();
       ObjModelImporter objImporter = new ObjModelImporter();
       try{
           URL modelUrl = this.getClass().getResource("Earth/earth.obj");
           objImporter.read(modelUrl);
       } catch (ImportException e){
           //handle exception
           System.out.println(e.getMessage());
       }
       MeshView[] meshViews = objImporter.getImport();
       Group earth = new Group(meshViews);
       parent.getChildren().add(earth);

   }

    /**
     * Fonction qui dessine des histogrammes proportionnels à la valeurs des anomalies et de bonnes couleurs
     * @param parent représente le groupe auquel on va ajouter les carrés dessinés
     * @param res représente le réseau qui contient toutes les données
     * @param an représente l'année selectionnée
     * @param c1 représente la couleur 1
     * @param c2 représente lacouleur 2
     * @param c3 représente la couleur 3
     * @param c4 représente la couleur 4
     * @param c5 représente la couleur 5
     * @param c6 représente la couleur 6
     * @param c7 représente la couleur 7
     * @param c8 représnete la couleur 8
     */
   public void dessinHisto(Group parent, Reseau res, int an,PhongMaterial c1,PhongMaterial c2,PhongMaterial c3,PhongMaterial c4,PhongMaterial c5,PhongMaterial c6,PhongMaterial c7,PhongMaterial c8){
       PhongMaterial mat=c4;
       int compteur =0;
       //on récupère les valeurs des anomalies pour l'anne donnée
       Float[] tabAno = res.RecAnomalieAnnee(an);
       //on parcourt toutes les coordonnées et on transforme les coor en pt3D
       for (coordonnee key : res.listeAnnee.get(an).listeEtatZone.keySet()) {
           float valAno = tabAno[compteur];
           Point3D cibleCylindre = geoCoordTo3dCoord(key.getLat(),key.getLon(),0.01f);
           Point3D origin = new Point3D(0,0,0);

           Point3D yAxis = new Point3D(0, 1, 0);
           Point3D diff = cibleCylindre.subtract(origin);
           //valAno = valAno/100;
           double hauteur = 1 + 1*valAno;

           Point3D mid = cibleCylindre.midpoint(origin);
           Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

           Point3D axisOfRotation = diff.crossProduct(yAxis);
           double angle = Math.acos(diff.normalize().dotProduct(yAxis));
           Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
           if(tabAno[compteur]>-7 && tabAno[compteur]<=-5){
               hauteur = 1 + 1*-valAno;
               mat = c8;
           }
           else if(tabAno[compteur]>-5 && tabAno[compteur]<=-3){
               hauteur = 1 + 1*-valAno;
               mat = c7;
           }
           else if(tabAno[compteur]>-3 && tabAno[compteur]<=-1){
               hauteur = 1 + 1*-valAno;
               mat = c6;
           }
           else if(tabAno[compteur]>-1 && tabAno[compteur]<0){
               hauteur = 1 + 1*-valAno;
               mat = c5;
           }
           else if(tabAno[compteur]>=0 && tabAno[compteur]<=1){
               mat = c4;
           }
           else if(tabAno[compteur]>1 && tabAno[compteur]<=3){
               mat = c3;
           }
           else if(tabAno[compteur]>3 && tabAno[compteur]<=5){
               mat = c2;
           }
           else if(tabAno[compteur]>5 && tabAno[compteur]<=9){
               mat = c1;
           }

           Cylinder line = new Cylinder(0.01f, hauteur);
           line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
           line.setMaterial(mat);
           parent.getChildren().addAll(line);
           compteur++;
       }
   }

    private MeshView AddQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft)
    {
        final TriangleMesh triangleMesh = new TriangleMesh();
        final float[] points = {
                (float)topRight.getX(), (float)topRight.getY(), (float)topRight.getZ(),
                (float)topLeft.getX(), (float)topLeft.getY(), (float)topLeft.getZ(),
                (float)bottomLeft.getX(), (float)bottomLeft.getY(), (float)bottomLeft.getZ(),
                (float)bottomRight.getX(), (float)bottomRight.getY(), (float)bottomRight.getZ(),
        };
        final float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };
        final int[] faces = {
                0, 1, 1, 0, 2, 2,
                0, 1, 2, 2, 3, 3
        };

        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoords);
        triangleMesh.getFaces().setAll(faces);

        final MeshView meshView = new MeshView(triangleMesh);
        //meshView.setMaterial(material);
        //parent.getChildren().addAll(meshView);
        return meshView;

    }

    private void carreClick(MouseEvent event) {
        reseau.setCarre(true);
        reseau.setHisto(false);

    }
    private void histoClick(MouseEvent event) {
        reseau.setCarre(false);
        reseau.setHisto(true);

    }
}

