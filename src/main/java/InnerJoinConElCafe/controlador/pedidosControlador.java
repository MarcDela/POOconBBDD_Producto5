package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import InnerJoinConElCafe.modelo.Pedido;
import InnerJoinConElCafe.modelo.Resultado;

public class pedidosControlador {
private Controlador controlador = new Controlador();
    @FXML private ComboBox<String> cbClientes; 
    @FXML private ComboBox<String> cbArticulos;
    @FXML private TextField txtCantidad;
    @FXML private TableView<Pedido> tablaPedidos;
    

    @FXML
    public void initialize() {
        System.out.println("Cargando listas de clientes y artículos para el formulario de pedidos...");
    }

    @FXML
    private void addPedido(ActionEvent event) {
        String cliente = cbClientes.getValue();
        String articulo = cbArticulos.getValue();
        System.out.println("Pedido añadido");
    }

    @FXML
    private void mostrarPedidos(ActionEvent event) {
        System.out.println("Listado pedidos");
    }

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/inicio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

   @FXML
    private void cancelarPedido(ActionEvent event) {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Ahora 'controlador' ya funcionará porque lo declaramos arriba
            Resultado<String> resultado = controlador.cancelarPedido(seleccionado.getNumeroPedido());
        }
    }
}