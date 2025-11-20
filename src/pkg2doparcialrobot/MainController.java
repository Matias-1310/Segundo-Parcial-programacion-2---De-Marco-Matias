
package pkg2doparcialrobot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {

    @FXML private TableView<Robot> tablaRobots;
    @FXML private TableColumn<Robot, String> colNombre;
    @FXML private TableColumn<Robot, Integer> colEnergia;
    @FXML private TableColumn<Robot, Integer> colSerie;
    @FXML private TableColumn<Robot, String> colTipo;
    @FXML private TableColumn<Robot, String> colExtra;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEnergia;
    @FXML private TextField txtSerie;
    @FXML private TextField txtExtra;
    @FXML private ComboBox<TipoRobot> cmbTipo;

    private RobotRepository repo = new RobotRepository();

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEnergia.setCellValueFactory(new PropertyValueFactory<>("energia"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo().toString()));
        colExtra.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDatoExtra()));

        cmbTipo.getItems().addAll(TipoRobot.DOMESTICO, TipoRobot.INDUSTRIAL);

        tablaRobots.getItems().setAll(repo.getRobots());

        // 🔥 AL SELECCIONAR UN ROBOT, CARGA LOS DATOS EN LOS CAMPOS
        tablaRobots.getSelectionModel().selectedItemProperty().addListener((obs, old, r) -> {
            if (r != null) {
                txtNombre.setText(r.getNombre());
                txtEnergia.setText(String.valueOf(r.getEnergia()));
                txtSerie.setText(String.valueOf(r.getNumeroSerie()));
                cmbTipo.setValue(r.getTipo());
                txtExtra.setText(r.getDatoExtra());
            }
        });
    }

    @FXML
    public void agregarRobot() {
        try {
            String nombre = txtNombre.getText();
            int energia = Integer.parseInt(txtEnergia.getText());
            int serie = Integer.parseInt(txtSerie.getText());
            int extra = Integer.parseInt(txtExtra.getText());
            TipoRobot tipo = cmbTipo.getValue();

            if (tipo == null)
                throw new RuntimeException("Seleccioná un tipo de robot.");

            Robot r;
            if (tipo == TipoRobot.DOMESTICO)
                r = new RobotDomestico(nombre, energia, serie, extra);
            else
                r = new RobotIndustrial(nombre, energia, serie, extra);

            repo.agregar(r);
            tablaRobots.getItems().setAll(repo.getRobots());

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void eliminarRobot() {
        Robot r = tablaRobots.getSelectionModel().getSelectedItem();
        if (r != null) {
            repo.eliminar(r);
            tablaRobots.getItems().setAll(repo.getRobots());
        }
    }

    @FXML
    public void modificarRobot() {
        Robot seleccionado = tablaRobots.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccioná un robot para modificar.");
            return;
        }

        try {
            String nombre = txtNombre.getText();
            int energia = Integer.parseInt(txtEnergia.getText());
            int serie = Integer.parseInt(txtSerie.getText());
            int extra = Integer.parseInt(txtExtra.getText());
            TipoRobot tipo = cmbTipo.getValue();

            if (tipo == null)
                throw new RuntimeException("Seleccioná un tipo.");

            if (energia < 0 || energia > 100)
                throw new EnergiaInvalidaException("La energía debe estar entre 0 y 100.");

            if (serie < 0)
                throw new NumeroSerieInvalidoException("El número de serie debe ser positivo.");

            if (serie != seleccionado.getNumeroSerie()) {
                for (Robot r : repo.getRobots())
                    if (r.getNumeroSerie() == serie)
                        throw new NumeroSerieDuplicadoException("Número de serie duplicado.");
            }

            int index = repo.getRobots().indexOf(seleccionado);

            Robot nuevo;
            if (tipo == TipoRobot.DOMESTICO)
                nuevo = new RobotDomestico(nombre, energia, serie, extra);
            else
                nuevo = new RobotIndustrial(nombre, energia, serie, extra);

            repo.getRobots().set(index, nuevo);
            repo.guardar();
            tablaRobots.getItems().setAll(repo.getRobots());

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    public void abrirLowEnergy() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pkg2doparcialrobot/views/low_energy_view.fxml"));
            Parent root = loader.load();
            Stage st = new Stage();
            st.setTitle("Robots con energía baja");
            st.setScene(new Scene(root));
            st.show();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Error");
        a.setContentText(msg);
        a.showAndWait();
    }
}
