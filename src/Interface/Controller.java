package Interface;
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
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
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
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    private Reseau reseau;
    private LinkedHashMap<coordonnee,MeshView> quadrillage;
    private int annee = 1880;
    private int speed = 1;

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
    RadioButton vit1;
    @FXML
    RadioButton vit5;
    @FXML
    RadioButton vit10;
    @FXML
    Slider slidAnnee;
    @FXML
    Button buttonPlay;
    @FXML
    Button buttonStop;
    @FXML
    Button buttonPause;
    @FXML
    Label latVar;
    @FXML
    Label lonVar;
    @FXML
    CheckBox graphique;


    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<ArrayList<String>> donnees = new ArrayList<ArrayList<String>>();
        FileReader.getDataFromCSVFile("src/data/tempanomaly_4x4grid.csv", donnees);
        Group root3D = new Group();
        Group leg = new Group();
        this.reseau = new Reseau(donnees, this);
        this.quadrillage = new LinkedHashMap<>();

        //initialisation du quadrillage
        Quadrillage(root3D,reseau);

        // Chargement de le geometry de la terre
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

        //On les ajoute sur les cubes
        cube.setMaterial(c8);
        cube1.setMaterial(c7);
        cube2.setMaterial(c6);
        cube3.setMaterial(c5);
        cube4.setMaterial(c4);
        cube5.setMaterial(c3);
        cube6.setMaterial(c2);
        cube7.setMaterial(c1);

        //placement des cubes de la légende
        cube.setTranslateY(-2.3);
        cube1.setTranslateY(-1.65);
        cube2.setTranslateY(-1);
        cube3.setTranslateY(-0.35);
        cube4.setTranslateY(0.3);
        cube5.setTranslateY(0.95);
        cube6.setTranslateY(1.6);
        cube7.setTranslateY(2.25);

        //on ajoute les cubes à leg
        leg.getChildren().add(cube);
        leg.getChildren().add(cube1);
        leg.getChildren().add(cube2);
        leg.getChildren().add(cube3);
        leg.getChildren().add(cube4);
        leg.getChildren().add(cube5);
        leg.getChildren().add(cube6);
        leg.getChildren().add(cube7);


        //mise à jour de l'état des radio boutons
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
        vit1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { vit1Click(mouseEvent); }
        });
        vit5.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { vit5Click(mouseEvent); }
        });
        vit10.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { vit10Click(mouseEvent); }
        });
        graphique.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { graphClick(mouseEvent); }
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

        //ANIMATION
        //création de l'animation
        AnimationTimer ani = new AnimationTimer() {
            //final long startNanoTime = System.nanoTime();
            @Override
            public void handle(long currentNanoTime) {
                slidAnnee.setValue((double)annee);
                //on teste quel mode est choisi si l'année n'est pas trop grande
                if(reseau.isCarre() && annee<2021 ){
                    System.out.println(annee);
                    System.out.println(speed);
                    slidAnnee.setValue(annee);
                    textAnnee.textProperty().setValue(String.valueOf(annee));
                    nettoyage(root3D);
                    dessinCarre(root3D,reseau,annee,c1,c2,c3,c4,c5,c6,c7,c8);
                    int tmp = speed;
                    //C'est pas très fonctionnel ça fait beaucoup tourner le pc pour pas grand chose
                    while(10-speed!=0){
                        speed = speed -1;
                    }
                    annee ++;
                    speed=tmp;
                }
                if(reseau.isHisto() && annee<2021 ){
                    slidAnnee.setValue(annee);
                    nettoyage(root3D);
                    dessinHisto(root3D,reseau,annee,c1,c2,c3,c4,c5,c6,c7,c8);
                    annee ++;
                }
                if(annee>=2021){
                    this.stop();
                    annee = 1880;
                }
            }
        };

        //Ajout des événements liés aux boutons
        buttonPlay.setOnAction(event -> { ani.start(); });
        buttonPause.setOnAction(event -> { ani.stop(); });
        buttonStop.setOnAction(event -> {
            ani.stop();
            annee = 1880;
        });

        Pane graph = new Pane();
        Scene secondScene = new Scene(graph, 500, 300);
        //on crée les axes du graphique
        final NumberAxis xAxis = new NumberAxis(1880, 2020, 20);
        final NumberAxis yAxis = new NumberAxis();
        //on crée le graphique
        final AreaChart<Number, Number> areaChart = new AreaChart<Number, Number>(xAxis, yAxis);
        areaChart.setMaxSize(500,300);

        //event qui permet de récupérer les coordonnées sur le globe pointées par la souris et si graphique coché alors nouvelle fenêtre avec le graphique
        root3D.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                PickResult pick = event.getPickResult();
                sourisToCoords(pick,reseau);
                latVar.setText(reseau.getLatValue()+"");
                lonVar.setText(reseau.getLonValue()+"");
                //nettoyer la fenêtre de graphique

                areaChart.setLegendVisible(false);

                //on teste si la case graphique est cochée
                if(reseau.isGraphique()) {
                    graph.getChildren().clear();
                    int lat = Integer.valueOf(latVar.getText());
                    int lon = Integer.valueOf(lonVar.getText());
                    afficheGraph(graph,reseau,areaChart,secondScene,lat,lon);
                }
            }
        });

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
     * Fonction qui permet de créer une nouvelle fenêtre dans lequelle on a mis le graphe associé à la zone selectionnée
     * @param parent pane dans lequel on va dessiner le graphique
     * @param res le réseau dans lequel nous avonstoutes les données
     * @param areaChart le graphique nous allons implémenter avec les bonnes valeurs
     * @param secondScene scene dans laquelle tout est ajouté
     * @param lat valeur de la latitude associée au pointeur de la souris
     * @param lon valeur de la longitude associée au pointeur de la souris
     */
    public void afficheGraph(Pane parent, Reseau res, AreaChart areaChart, Scene secondScene,int lat, int lon){
        //nettoyage du graphique
        areaChart.getData().clear();
        XYChart.Series<Number, Number> serie = new XYChart.Series<Number, Number>();

        //on récupère les valeurs d'anomalies pour la zone selectionnée
        Float[] tabVal = res.recAnomalieZone(lat,lon);
        //on ajoute toutes les valeurs du tableau au graphique
        for (int i = 0; i < tabVal.length; i++) {
            serie.getData().add(new XYChart.Data<Number, Number>(1880 + i, tabVal[i]));
        }
        areaChart.setTitle("Coordonnées : "+lat+" "+lon);

        //on ajoute la série ainsi créée au graphique
        areaChart.getData().add(serie);

        //on ajoute le graphe au pane
        parent.getChildren().add(areaChart);

        //On crée une nouvelle fenêtre
        Stage newWindow = new Stage();
        newWindow.setTitle("Fenêtre du graphique");
        newWindow.setScene(secondScene);
        newWindow.show();
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

    /**
     * Fonction qui crée le quadrillage de carrés et le mets en mémoire
     * @param parent groupe auquel on ajoute les carrés
     * @param res représente le réseau qui contient toutes les données
     */
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
     * Fonction qui colorie les carrés avec la bonne couleur en fonction de la valeur de l'anomalie pour une année donnée
     * @param parent représente le groupe auquel sont associés les carrés
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
        Float[] tabAno = res.recAnomalieAnnee(an);
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
       //on récupère les valeurs des anomalies pour l'année donnée
       Float[] tabAno = res.recAnomalieAnnee(an);
       //on parcourt toutes les coordonnées et on transforme les coor en pt3D
       for (coordonnee key : res.listeAnnee.get(an).listeEtatZone.keySet()) {
           //on récupère la valeur de l'anomalie pour chaque coordonnées
           float valAno = tabAno[compteur];
           Point3D cibleCylindre = geoCoordTo3dCoord(key.getLat(),key.getLon(),1.2f);
           Point3D origin = geoCoordTo3dCoord(key.getLat(),key.getLon(),1f);
           //Point3D origin = new Point3D(0,0,0);

           Point3D yAxis = new Point3D(0, 1, 0);
           Point3D diff = cibleCylindre.subtract(origin);
           //valAno = valAno/100;
           double hauteur = valAno;

           Point3D mid = cibleCylindre.midpoint(origin);
           Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

           Point3D axisOfRotation = diff.crossProduct(yAxis);
           double angle = Math.acos(diff.normalize().dotProduct(yAxis));
           Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
           if(tabAno[compteur]>-7 && tabAno[compteur]<=-5){
               hauteur = -valAno;
               mat = c8;
           }
           else if(tabAno[compteur]>-5 && tabAno[compteur]<=-3){
               hauteur = -valAno;
               mat = c7;
           }
           else if(tabAno[compteur]>-3 && tabAno[compteur]<=-1){
               hauteur = -valAno;
               mat = c6;
           }
           else if(tabAno[compteur]>-1 && tabAno[compteur]<0){
               hauteur = -valAno;
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

    /**
     * Fonction qui va dessiner des carrés à l'aide des objets passés en paramètre
     * @param parent
     * @param topRight point3D qui représente l'angle en haut à droite du carré
     * @param bottomRight point3D qui représente l'angle en bas à droite du carré
     * @param bottomLeft point3D qui représente l'angle en bas à gauche du carré
     * @param topLeft point3D qui représente l'angle en haut à gauche du carré
     * @return un MeshView avec tout les carrés dedans
     */
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

    /**
     * Fonction qui transforme les coordonnées du pointeur de la souris en coordonnées (longitude et latitude)
     * @param result valeur du pointeur de la souris
     * @param res représente le réseau dans lequel est stocké la valeur des données récupérées
     */
    public static void sourisToCoords(PickResult result, Reseau res)
    {
        Point3D coor = result.getIntersectedPoint();
        res.setLatValue((int)  Math.round(-Math.toDegrees(Math.asin(coor.getY() / Math.sqrt(Math.pow(coor.getX(), 2) + Math.pow(coor.getY(), 2) + Math.pow(coor.getZ(), 2))) ) /4)*4);
        res.setLonValue((int)  Math.round(-Math.toDegrees(Math.atan2(coor.getX(), coor.getZ()) ) /4)*4+2);
    }


    private void carreClick(MouseEvent event) {
        reseau.setCarre(true);
        reseau.setHisto(false);
    }
    private void histoClick(MouseEvent event) {
        reseau.setCarre(false);
        reseau.setHisto(true);
    }

    private void graphClick(MouseEvent event){
        if (reseau.isGraphique()){
            reseau.setGraphique(false);
        }else{
            reseau.setGraphique(true);
        }
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private void vit1Click(MouseEvent event) {
        setSpeed(1);
    }
    private void vit5Click(MouseEvent event) {
        setSpeed(5);
    }
    private void vit10Click(MouseEvent event) {
        setSpeed(10);
    }
}

