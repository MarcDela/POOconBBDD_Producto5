package InnerJoinConElCafe.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// 1. Heredamos de Application para que Java sepa que esto es una ventana
public class App extends Application {

    // 2. El método start es el "nuevo main". Stage es la ventana principal (el marco).
    @Override
    public void start(Stage stage) {
        // Creamos un componente visual (un texto) para comprobar que la nueva intefaz funciona.
        Label label = new Label("¡Hola! JavaFX 21 funcionando.");

        // Creamos un contenedor (StackPane) para organizar los elementos
        StackPane root = new StackPane(label);

        // Creamos la "Escena" (el contenido dentro de la ventana) con tamaño 400x300
        Scene scene = new Scene(root, 400, 300);

        // Configuramos el marco (Stage)
        stage.setTitle("Online Store - Producto 5");
        stage.setScene(scene);
        
        stage.show();
    }

    public static void main(String[] args) {
        // 3. launch() se encarga de arrancar todo el motor gráfico
        launch(args);
    }
}