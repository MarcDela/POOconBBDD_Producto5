package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    @FXML private Button btnMenuPedidos;
    @FXML private Button btnMenuClientes;
    @FXML private Button btnMenuArticulos;

    @FXML
    void abrirMenuPedidos(ActionEvent event) {
        try {
            // Cargamos la ventana seleccionada
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaClientes.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnMenuPedidos.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cambiar a la ventana de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void abrirMenuArticulos(ActionEvent event) {
        try {
            // Cargamos la ventana seleccionada
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaArticulos.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnMenuPedidos.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cambiar a la ventana de articulos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}