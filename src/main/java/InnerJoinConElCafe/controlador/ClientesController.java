package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientesController extends BaseController {

    private Controlador controladorLogica = new Controlador();

    @FXML private TextField txtNif, txtNombre, txtEmail, txtDomicilio;
    @FXML private RadioButton rbAltaEstandar, rbAltaPremium, rbMostrarTodos, rbMostrarEstandar, rbMostrarPremium;
    @FXML private TextArea txtAreaResultados;

    @FXML
    void anadirCliente(ActionEvent event) {
        try {
            String nif = txtNif.getText();
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String domicilio = txtDomicilio.getText();

            // Validamos que no haya campos vacíos
            if (nif.isEmpty() || nombre.isEmpty()) {
                txtAreaResultados.setText("ERROR: El NIF y el Nombre son obligatorios.");
                return;
            }

            // Determinamos el tipo según el RadioButton (1: Estándar, 2: Premium)
            int tipo = rbAltaPremium.isSelected() ? 2 : 1;

            // Llamada a la lógica de negocio
            var res = controladorLogica.añadirCliente(nombre, domicilio, nif, email, tipo);

            txtAreaResultados.setText(res.getMensaje());

            if (res.esExitoso()) {
                limpiarCampos();
            }
        } catch (Exception e) {
            txtAreaResultados.setText("Error al registrar: " + e.getMessage());
        }
    }

    @FXML
    void mostrarClientes(ActionEvent event) {
        // Traducimos RadioButtons a la opción numérica de tu lógica
        int opcion = 1;
        if (rbMostrarEstandar.isSelected()) opcion = 2;
        if (rbMostrarPremium.isSelected()) opcion = 3;

        var res = controladorLogica.obtenerClientes(opcion);

        if (res.esExitoso()) {
            StringBuilder sb = new StringBuilder();
            String titulo = (opcion == 1) ? "TODOS" : (opcion == 2 ? "ESTÁNDAR" : "PREMIUM");
            sb.append("=== LISTA DE CLIENTES (").append(titulo).append(") ===\n\n");

            for (var c : res.getDato().getArrayList()) {
                sb.append(c.toString()).append("\n");
                sb.append("------------------------------------------\n");
            }
            txtAreaResultados.setText(sb.toString());
        } else {
            txtAreaResultados.setText("AVISO: " + res.getMensaje());
        }
    }

    //Método para limpiar los campos una vez se hace una entrada
    private void limpiarCampos() {
        txtNif.clear();
        txtNombre.clear();
        txtEmail.clear();
        txtDomicilio.clear();
        rbAltaEstandar.setSelected(true); 
    }

    @FXML void abrirMenuPedidos(ActionEvent event) { cambiarVentana("/VentanaPedidos.fxml", event); }
    @FXML void abrirMenuArticulos(ActionEvent event) { cambiarVentana("/VentanaArticulos.fxml", event); }
    @FXML void volverMenuPrincipal(ActionEvent event) { cambiarVentana("/VentanaMain.fxml", event); }
}