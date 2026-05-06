package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    // Instanciamos el controlador de lógica de negocio para tenerlo listo
    // private Controlador logicController = new Controlador();

    @FXML
    private Button btnMenuPedidos;

    @FXML
    private Button btnMenuClientes;

    @FXML
    private Button btnMenuArticulos;

    @FXML
    void abrirMenuPedidos(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaPedidos.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) btnMenuPedidos.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } catch (Exception e) {
        System.err.println("Error al cambiar a la ventana de pedidos: " + e.getMessage());
        e.printStackTrace();
    }
    }

    @FXML
    void abrirMenuClientes(ActionEvent event) {
        System.out.println("Cambiando a Clientes...");
    }

    @FXML
    void abrirMenuArticulos(ActionEvent event) {
        System.out.println("Cambiando a Articulos...");
    }
}