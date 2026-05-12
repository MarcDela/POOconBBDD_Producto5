package InnerJoinConElCafe.controlador;

import InnerJoinConElCafe.modelo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import java.io.IOException;

public class clientesControlador {

    // Componentes del FXML
    @FXML private TextField txtEmail, txtNombre, txtDomicilio, txtNIF, txtcuotaAnual, txtdescuentoEnvios;
    @FXML private ChoiceBox<String> cbTipoCliente;
    @FXML private TableView<Cliente> tablaClientes; 

    @FXML
    public void initialize() {
        if (cbTipoCliente != null) {
            cbTipoCliente.getItems().addAll("Estándar", "Premium");
            cbTipoCliente.setValue("Estándar");
        }
    }

    @FXML
    private void addCliente(ActionEvent event) {
        String email = txtEmail.getText();
        String nombre = txtNombre.getText();
        String tipo = cbTipoCliente.getValue();
        System.out.println("Cliente guardado");
    }

    @FXML
    private void mostrarClientes(ActionEvent event) {System.out.println("Listado clientes");}

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/inicio.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}