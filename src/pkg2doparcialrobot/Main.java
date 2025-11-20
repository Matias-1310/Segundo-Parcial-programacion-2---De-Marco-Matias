
package pkg2doparcialrobot;


import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pkg2doparcialrobot/views/main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Gestión de Robots");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}