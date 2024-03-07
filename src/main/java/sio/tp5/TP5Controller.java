package sio.tp5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sio.tp5.Tools.ConnexionBDD;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TP5Controller implements Initializable {

    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;
    HashMap<String,Double> datasGraphique1;
    XYChart.Series<String,Number> serieGraph1;
    XYChart.Series<String,Double> serieGraph3;
    @FXML
    private Button cmdGraph1;
    @FXML
    private Button cmdGraph2;
    @FXML
    private Button cmdGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private Label lblTitre;
    @FXML
    private LineChart graph1;
    @FXML
    private PieChart graph2;
    @FXML
    private BarChart graph3;
    @FXML
    private ComboBox cboActions;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblTitre.setText("TP5 : Graphique n°1");
        apGraph1.toFront();
        try {
            maCnx = new ConnexionBDD();
            graphiqueController = new GraphiqueController();

            for (String nomAction : graphiqueController.GetAllActions())
            {
                cboActions.getItems().add(nomAction);
            }
            cboActions.getSelectionModel().selectFirst();
            datasGraphique1 = new HashMap<>();
            datasGraphique1 =  graphiqueController.GetDatasGraphique1(cboActions.getSelectionModel().getSelectedItem().toString());
            //datasGraphique1 =  graphiqueController.GetDatasGraphique1("FB");
            serieGraph1 = new XYChart.Series();
            serieGraph1.setName("Nom des traders");

            // Remplir la série nécessaire au graphique à partir des données provenant de la HashMap
            for (String valeur : datasGraphique1.keySet())
            {
                serieGraph1.getData().add(new XYChart.Data(valeur,datasGraphique1.get(valeur)));
            }
            graph1.getData().add(serieGraph1);

            graph1.setTitle("Prix de l'action "+ cboActions.getSelectionModel().getSelectedItem().toString());
            for (XYChart.Data<String,Number> entry : serieGraph1.getData()) {
                Tooltip t = new Tooltip(entry.getYValue().toString()+ " : "+entry.getXValue().toString());
                t.setStyle("-fx-background-color:#3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void menuClicked(Event event) {
        if(event.getSource() == cmdGraph1)
        {
            lblTitre.setText("TP5 : Graphique n°1");
            apGraph1.toFront();
            graph1.getData().clear();
            datasGraphique1 = new HashMap<>();
            datasGraphique1 =  graphiqueController.GetDatasGraphique1(cboActions.getSelectionModel().getSelectedItem().toString());
            //datasGraphique1 =  graphiqueController.GetDatasGraphique1("FB");
            serieGraph1 = new XYChart.Series();
            serieGraph1.setName("Nom des traders");

            // Remplir la série nécessaire au graphique à partir des données provenant de la HashMap
            for (String valeur : datasGraphique1.keySet())
            {
                serieGraph1.getData().add(new XYChart.Data(valeur,datasGraphique1.get(valeur)));
            }
            graph1.getData().add(serieGraph1);

            graph1.setTitle("Prix de l'action "+ cboActions.getSelectionModel().getSelectedItem().toString());
            for (XYChart.Data<String,Number> entry : serieGraph1.getData()) {
                Tooltip t = new Tooltip(entry.getYValue().toString()+ " : "+entry.getXValue().toString());
                t.setStyle("-fx-background-color:#3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }
        }
        if(event.getSource() == cmdGraph2)
        {
            lblTitre.setText("TP5 : Graphique n°2");
            apGraph2.toFront();

            graph2.getData().clear();

            ObservableList<PieChart.Data> datasGraph2 =FXCollections.observableArrayList();
            HashMap<String,Integer> datasGraphique2 =  graphiqueController.GetDatasGraphique2();

            for (String valeur : datasGraphique2.keySet())
            {
                datasGraph2.add(new PieChart.Data(valeur,datasGraphique2.get(valeur) ));
            }
            graph2.setData(datasGraph2);

            for (PieChart.Data entry : graph2.getData()) {
                Tooltip t = new Tooltip(entry.getPieValue()+ " : "+entry.getName());
                t.setStyle("-fx-background-color:#3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }
        }
        if(event.getSource() == cmdGraph3)
        {
            lblTitre.setText("TP5 : Graphique n°3");
            apGraph3.toFront();
            graph3.getData().clear();

            HashMap<String,ArrayList<String>> datasGraphique3 =  graphiqueController.GetDatasGraphique3();
            for (String valeur : datasGraphique3.keySet())
            {
                serieGraph3 = new XYChart.Series<>();
                serieGraph3.setName(valeur);
                for(int i = 0;i< datasGraphique3.get(valeur).size();i+=2)
                {
                    serieGraph3.getData().add(new XYChart.Data<>(datasGraphique3.get(valeur).get(i),Double.parseDouble(datasGraphique3.get(valeur).get(i+1))));
                }
                graph3.getData().add(serieGraph3);
            }
        }
    }

    @FXML
    public void cboActionsClicked(Event event) {
        graph1.getData().clear();
        datasGraphique1 = new HashMap<>();
        datasGraphique1 =  graphiqueController.GetDatasGraphique1(cboActions.getSelectionModel().getSelectedItem().toString());
        //datasGraphique1 =  graphiqueController.GetDatasGraphique1("FB");
        serieGraph1 = new XYChart.Series();
        serieGraph1.setName("Nom des traders");

        // Remplir la série nécessaire au graphique à partir des données provenant de la HashMap
        for (String valeur : datasGraphique1.keySet())
        {
            serieGraph1.getData().add(new XYChart.Data(valeur,datasGraphique1.get(valeur)));
        }
        graph1.getData().add(serieGraph1);

        graph1.setTitle("Prix de l'action "+ cboActions.getSelectionModel().getSelectedItem().toString());
        for (XYChart.Data<String,Number> entry : serieGraph1.getData()) {
            Tooltip t = new Tooltip(entry.getYValue().toString()+ " : "+entry.getXValue().toString());
            t.setStyle("-fx-background-color:#3D9ADA");
            Tooltip.install(entry.getNode(), t);
        }
    }
}