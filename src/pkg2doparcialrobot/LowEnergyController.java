package pkg2doparcialrobot;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LowEnergyController {

    @FXML private TableView<Robot> tablaLow;
    @FXML private TableColumn<Robot, String> colNombre;
    @FXML private TableColumn<Robot, Integer> colEnergia;
    @FXML private TableColumn<Robot, Integer> colSerie;
    @FXML private TableColumn<Robot, String> colTipo;

    private RobotRepository repo = new RobotRepository();

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEnergia.setCellValueFactory(new PropertyValueFactory<>("energia"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
        colTipo.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo().toString())
        );

        tablaLow.getItems().setAll(repo.getLowEnergyRobots());
    }
}
