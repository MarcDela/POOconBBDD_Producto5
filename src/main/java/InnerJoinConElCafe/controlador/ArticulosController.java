package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import InnerJoinConElCafe.modelo.Articulo;

public class ArticulosController {

    private Controlador controladorLogica = new Controlador();

    @FXML private TextField txtDescripcion, txtPrecio, txtEnvio, txtTiempo;
    @FXML private TextArea txtAreaResultados;

    @FXML
    void anadirArticulo(ActionEvent event) {
        try {
            String desc = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            double envio = Double.parseDouble(txtEnvio.getText());
            int tiempo = Integer.parseInt(txtTiempo.getText());

            var res = controladorLogica.añadirArticulo(desc, precio, envio, tiempo);
            txtAreaResultados.setText(res.getMensaje());

            if (res.esExitoso()) {
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            txtAreaResultados.setText("ERROR: Precio y Envío deben ser decimales. Tiempo debe ser entero.");
        }
    }

    @FXML
    void mostrarArticulos(ActionEvent event) {
        var res = controladorLogica.obtenerArticulos();

        if (res.esExitoso()) {
            StringBuilder sb = new StringBuilder("=== CATÁLOGO DE ARTÍCULOS ===\n\n");
            for (Articulo a : res.getDato().getArrayList()) {
                sb.append(a.toString()).append("\n");
                sb.append("------------------------------------------\n");
            }
            txtAreaResultados.setText(sb.toString());
        } else {
            txtAreaResultados.setText("AVISO: " + res.getMensaje());
        }
    }

    private void limpiarCampos() {
        txtDescripcion.clear();
        txtPrecio.clear();
        txtEnvio.clear();
        txtTiempo.clear();
        txtDescripcion.requestFocus();
    }

    // --- NAVEGACIÓN ---
    @FXML void abrirMenuPedidos(ActionEvent event) { cambiarVentana("/VentanaPedidos.fxml", event); }
    @FXML void abrirMenuClientes(ActionEvent event) { cambiarVentana("/VentanaClientes.fxml", event); }
    @FXML void volverMenuPrincipal(ActionEvent event) { cambiarVentana("/VentanaMain.fxml", event); }

    private void cambiarVentana(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) { e.printStackTrace(); }
    }
}