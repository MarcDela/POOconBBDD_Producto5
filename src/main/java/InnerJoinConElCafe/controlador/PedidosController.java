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
import InnerJoinConElCafe.modelo.Pedido;
// import InnerJoinConElCafe.modelo.Lista;

public class PedidosController {

    // 1. Conexión con la lógica de negocio
    private Controlador controladorLogica = new Controlador();

    // 2. Elementos de la interfaz (IDs del FXML)
    @FXML private TextField txtNifCliente;
    @FXML private TextField txtCodigoArticulo;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNumPedido;
    @FXML private TextArea txtAreaResultados;

    // --- MÉTODOS DE ACCIÓN ---

    @FXML
    void anadirPedido(ActionEvent event) {
        try {
            String nif = txtNifCliente.getText();
            int codigo = Integer.parseInt(txtCodigoArticulo.getText());
            int cant = Integer.parseInt(txtCantidad.getText());

            // Llamamos al método de lógica de negocio y recogemos el mensaje
            var res = controladorLogica.añadirPedido(nif, codigo, cant);
            txtAreaResultados.setText(res.getMensaje());

            // Limpieza de campos si el pedido se ha guardado correctamente
            if (res.esExitoso()) {
                txtNifCliente.clear();
                txtCodigoArticulo.clear();
                txtCantidad.clear();
                txtNumPedido.clear();
            }

        } catch (NumberFormatException e) {
            txtAreaResultados.setText("ERROR: Código y cantidad deben ser números.");
        }
    }

    @FXML
    void mostrarPedidos(ActionEvent event) {
        // 1. Obtenemos el NIF del campo para filtrar los resultados
        String nifFiltro = txtNifCliente.getText();
    
        // Si el campo está vacío o solo tiene espacios, pasamos null para que no filtre
        if (nifFiltro == null || nifFiltro.trim().isEmpty()) {
            nifFiltro = null;
        }

        // 2. Llamamos al controlador de lógica
        // Usamos '1' para traer TODOS (En curso y procesados) filtrados por ese NIF
        var res = controladorLogica.obtenerPedidosFiltrados('1', nifFiltro);
    
        if (res.esExitoso()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== RESULTADOS PARA NIF: ").append(nifFiltro == null ? "TODOS" : nifFiltro).append(" ===\n\n");
        
            for (Pedido p : res.getDato().getArrayList()) {
                sb.append(p.toString()).append("\n");
                // Añadimos el estado y el total como haciamos en GestionOS
                sb.append("ESTADO: ").append(p.puedeCancelarse() ? "EN CURSO" : "PROCESADO").append("\n");
                sb.append("TOTAL: ").append(String.format("%.2f", p.calcularPrecio())).append(" €\n");
                sb.append("------------------------------------------\n");
            }

            txtAreaResultados.setText(sb.toString());
            txtNifCliente.clear();
            txtCodigoArticulo.clear();
            txtCantidad.clear();
            txtNumPedido.clear();
            
        } else {
            txtAreaResultados.setText("MENSAJE: " + res.getMensaje());
        }
    }

    @FXML
    void eliminarPedido(ActionEvent event) {
        try {
            int num = Integer.parseInt(txtNumPedido.getText());
            var res = controladorLogica.cancelarPedido(num);

            txtAreaResultados.setText(res.getMensaje());
            txtNifCliente.clear();
            txtCodigoArticulo.clear();
            txtCantidad.clear();
            txtNumPedido.clear();

        } catch (NumberFormatException e) {
            txtAreaResultados.setText("ERROR: Introduce un número de pedido válido.");
        }
    }

    // --- MENÚ DE NAVEGACIÓN ---

    @FXML
    void volverMenuPrincipal(ActionEvent event) {
        cambiarVentana("/VentanaMain.fxml", event);
    }

    @FXML
    void abrirMenuClientes(ActionEvent event) {
        txtAreaResultados.setText("Navegando a Clientes... (Falta FXML)");
    }

    @FXML
    void abrirMenuArticulos(ActionEvent event) {
        txtAreaResultados.setText("Navegando a Artículos... (Falta FXML)");
    }

    private void cambiarVentana(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}