package InnerJoinConElCafe.vista;

//Importación de funciones de JavaFX
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
     
        Label label = new Label("Prueba funcionamiento");
        StackPane layout = new StackPane(label);
        Scene scene = new Scene(layout, 640, 480);
        stage.setTitle("Online Store");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {launch();}
}

