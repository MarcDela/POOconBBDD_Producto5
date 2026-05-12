package InnerJoinConElCafe.vista;

//Importación de funciones de JavaFX
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inicio.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Online Store App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}