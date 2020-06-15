package Interface;
import Application.Annee;
import Application.Reseau;
import Application.coordonnee;
import data.FileReader;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    private Reseau reseau;

    @FXML
    Pane pane3D;
    @FXML
    TextField textAnnee;
    @FXML
    RadioButton carreRB;
    @FXML
    RadioButton histoRB;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>();
        FileReader.getDataFromCSVFile("src/data/tempanomaly_4x4grid.csv", donnees);
        Group root3D = new Group();
        this.reseau = new Reseau(donnees, this);

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

        //cube de couleurs
        Box cube = new Box(1, 1, 1);
        Box cube1 = new Box(1,1,1);
        Box cube2 = new Box(1,1,1);
        Box cube3 = new Box(1,1,1);

        //creation des couleurs
        final PhongMaterial c5 = new PhongMaterial();
        c5.setDiffuseColor(new Color(0.5,0.5,0.5, 0.01));
        c5.setSpecularColor(new Color(0.5,0.5,0.5, 0.01));
        final PhongMaterial c6 = new PhongMaterial();
        c6.setDiffuseColor(new Color(0.5,0.2,0, 0.01));
        c6.setSpecularColor(new Color(0.5,0.2,0, 0.01));
        final PhongMaterial c7 = new PhongMaterial();
        c7.setDiffuseColor(new Color(0.5,0,0, 0.01));
        c7.setSpecularColor(new Color(0,0.1,0.2, 0.01));
        final PhongMaterial c8 = new PhongMaterial();
        c8.setDiffuseColor(new Color(0,0,0.5, 0.01));
        c8.setSpecularColor(new Color(0,0,0.2, 0.01));

        //Set it to the cube
        cube.setMaterial(c5);
        cube1.setMaterial(c6);
        cube2.setMaterial(c7);
        cube3.setMaterial(c8);

        //placement des cubes
        cube.setTranslateX(-3);
        cube1.setTranslateX(-3);
        cube1.setTranslateY(1);
        cube2.setTranslateX(-3);
        cube2.setTranslateY(2);
        cube3.setTranslateX(-3);
        cube3.setTranslateY(3);

        //Add the cube to this node
        root3D.getChildren().add(cube);
        root3D.getChildren().add(cube1);
        root3D.getChildren().add(cube2);
        root3D.getChildren().add(cube3);


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

        //Ajout d'evennt sur la zone de texte
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
                        dessinCarre(root3D,reseau,valeurAnnee);
                    }
                    else if(reseau.isHisto()){
                        //dessinHisto(root3D,reseau,valeurAnnee);
                    }

                }
                catch (Exception e){
                    System.out.println("Vous devez entrer un entier");
                    return;
                }
            }
        });

        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);

        //Build camera manager
        new CameraManager(camera, pane3D, root3D);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        PointLight lumiere = new PointLight(Color.WHITE);
        light.setTranslateX(-150);
        light.setTranslateY(-50);
        light.setTranslateZ(-10);
        light.getScope().addAll(root3D);
        lumiere.getScope().addAll(root3D);
        root3D.getChildren().add(light);
        root3D.getChildren().add(lumiere);

        //Create the subscene
        SubScene subscene = new SubScene(root3D, 452.0,393.0, true, SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);
    }



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

    private void dessinCarre(Group parent, Reseau res, int an){
        //on initilalise les couleurs
        final PhongMaterial c1 = new PhongMaterial();
        c1.setDiffuseColor(new Color(0.5,0,0, 0.01));
        c1.setSpecularColor(new Color(0.5,0,0, 0.01));
        final PhongMaterial c2 = new PhongMaterial();
        c2.setDiffuseColor(new Color(0.5,0.2,0, 0.01));
        c2.setSpecularColor(new Color(0.5,0.2,0, 0.01));
        final PhongMaterial c3 = new PhongMaterial();
        c3.setDiffuseColor(new Color(0.2,0.2,0, 0.01));
        c3.setSpecularColor(new Color(0.2,0.2,0, 0.01));
        final PhongMaterial c4 = new PhongMaterial();
        c4.setDiffuseColor(new Color(0.4,0.6,0.4, 0.01));
        c4.setSpecularColor(new Color(0.4,0.6,0.4, 0.01));
        final PhongMaterial c5 = new PhongMaterial();
        c5.setDiffuseColor(new Color(0.1,0.2,0.5, 0.01));
        c5.setSpecularColor(new Color(0.1,0.2,0.5, 0.01));
        final PhongMaterial c6 = new PhongMaterial();
        c6.setDiffuseColor(new Color(0,0.2,0.2, 0.01));
        c6.setSpecularColor(new Color(0,0.2,0.2, 0.01));
        final PhongMaterial c7 = new PhongMaterial();
        c7.setDiffuseColor(new Color(0,0.2,0.5, 0.01));
        c7.setSpecularColor(new Color(0,0.2,0.5, 0.01));
        final PhongMaterial c8 = new PhongMaterial();
        c8.setDiffuseColor(new Color(0,0,0.5, 0.01));
        c8.setSpecularColor(new Color(0,0,0.5, 0.01));

        Point3D topRight ;
        Point3D bottomRight;
        Point3D topLeft;
        Point3D bottomLeft;
        int compteur =1;
        PhongMaterial mat=c4;

        //on récupère les valeurs des anomalies pour l'anne donnée
        Float[] tabAno = res.RecAnomalieAnnee(an);
        //on parcourt toutes les coordonnées et on transforme les coor en pt3D
        for (coordonnee key : res.listeAnnee.get(an).listeEtatZone.keySet()) {

            bottomLeft = geoCoordTo3dCoord(key.getLat() - 2, key.getLon() - 2, 1.05f);
            bottomRight = geoCoordTo3dCoord(key.getLat() - 2, key.getLon()+2, 1.05f);
            topLeft = geoCoordTo3dCoord(key.getLat()+2, key.getLon() - 2, 1.05f);
            topRight = geoCoordTo3dCoord(key.getLat()+2, key.getLon()+2, 1.05f);

            //on choisit la couleur en fonction de la valeur de l'anomalie
            if(tabAno[compteur]>-7 && tabAno[compteur]<=-5){
                mat = c8;
                System.out.println("bleu foncé");
            }
            else if(tabAno[compteur]>-5 && tabAno[compteur]<=-3){
                mat = c7;
                System.out.println("bleu moins foncé");
            }
            else if(tabAno[compteur]>-3 && tabAno[compteur]<=-1){
                mat = c6;
                System.out.println("bleu clair");
            }
            else if(tabAno[compteur]>-1 && tabAno[compteur]<=0){
                mat = c5;
                System.out.println("vert bleu");
            }
            else if(tabAno[compteur]>0 && tabAno[compteur]<=1){
                mat = c4;
                System.out.println("vert rouge");
            }
            else if(tabAno[compteur]>1 && tabAno[compteur]<=3){
                mat = c3;
                System.out.println("jaune");
            }
            else if(tabAno[compteur]>3 && tabAno[compteur]<=5){
                mat = c2;
                System.out.println("orange");
            }
            else if(tabAno[compteur]>5 && tabAno[compteur]<=9){
                mat = c1;
                System.out.println("rouge");
            }
            AddQuadrilateral(parent, topRight, bottomRight, bottomLeft, topLeft, mat);
            compteur++;
        }
    }

    private void AddQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft, PhongMaterial material)
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
        meshView.setMaterial(material);
        parent.getChildren().addAll(meshView);
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

