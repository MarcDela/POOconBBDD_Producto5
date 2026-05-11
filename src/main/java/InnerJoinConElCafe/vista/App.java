package InnerJoinConElCafe.vista;

import InnerJoinConElCafe.controlador.Controlador;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Button btnActivo = null;
    private Controlador controlador = new Controlador();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        // Menú lateral
        VBox menuLateral = new VBox(5);
        menuLateral.getStyleClass().add("menu-lateral");
        menuLateral.setPrefWidth(200);

        Label titulo = new Label("ONLINE STORE");
        titulo.getStyleClass().add("titulo-app");

        Button btnArticulos = crearBotonMenu("Artículos");
        Button btnClientes = crearBotonMenu("Clientes");
        Button btnPedidos = crearBotonMenu("Pedidos");

        menuLateral.getChildren().addAll(titulo, btnArticulos, btnClientes, btnPedidos);
        root.setLeft(menuLateral);

        // Panel central vacío de momento
        StackPane contenido = new StackPane();
        contenido.setStyle("-fx-background-color: #2b2d30;");
        root.setCenter(contenido);

        // Acciones de los botones
        btnArticulos.setOnAction(e -> activarBoton(btnArticulos, contenido, "articulos"));
        btnClientes.setOnAction(e -> activarBoton(btnClientes, contenido, "clientes"));
        btnPedidos.setOnAction(e -> activarBoton(btnPedidos, contenido, "pedidos"));

        Scene scene = new Scene(root, 1400, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("OnlineStore");
        stage.setScene(scene);
        stage.show();
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.getStyleClass().add("btn-menu");
        return btn;
    }

    private void activarBoton(Button btn, StackPane contenido, String seccion) {
        if (btnActivo != null) {
            btnActivo.getStyleClass().remove("btn-menu-activo");
        }
        btn.getStyleClass().add("btn-menu-activo");
        btnActivo = btn;

        contenido.getChildren().clear();

        switch (seccion) {
            case "articulos" -> contenido.getChildren().add(new VistaArticulos(controlador));
            case "clientes" -> contenido.getChildren().add(new VistaClientes(controlador));
            case "pedidos" -> contenido.getChildren().add(new VistaPedidos(controlador));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}