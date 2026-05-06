package InnerJoinConElCafe.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// 1. Heredamos de Application para que Java sepa que esto es una ventana
public class App extends Application {

    // 2. El método start es el "nuevo main". Stage es la ventana principal (el marco).
    @Override
    public void start(Stage stage) {

        try{
            // 1. Cargamos el archivo FXML desde la carpeta de recursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaMain.fxml"));
        
            // 2. Creamos el contenedor principal cargando el FXML
            Parent root = loader.load();

            // 3. Creamos la escena con nuestro diseño
            Scene scene = new Scene(root);

            // 4. Configuramos el marco (Stage)
            stage.setTitle("Online Store - Gestor de Negocio");
            stage.setScene(scene);
        
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cargar la ventana principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 3. launch() se encarga de arrancar todo el motor gráfico
        launch(args);
    }
}