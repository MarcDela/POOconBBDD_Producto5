package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import InnerJoinConElCafe.modelo.Pedido;

public class PedidosController extends BaseController {

    // 1. Conexión con la lógica de negocio
    private Controlador controladorLogica = new Controlador();

    // 2. Elementos de la interfaz (IDs del FXML)
    @FXML private TextField txtNifCliente;
    @FXML private TextField txtCodigoArticulo;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNumPedido;
    @FXML private TextArea txtAreaResultados;
    @FXML private RadioButton rbTodos;
    @FXML private RadioButton rbEnCurso;
    @FXML private RadioButton rbProcesados;

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
        // 1. Determinar el char del estado según el RadioButton seleccionado
        char estado = '1'; // Por defecto todos
        if (rbEnCurso.isSelected()) { estado = '2';} 
        else if (rbProcesados.isSelected()) { estado = '3'; }

        // 2. Obtener el NIF en caso de que se haya introducido
        String nifFiltro = txtNifCliente.getText();
        if (nifFiltro == null || nifFiltro.trim().isEmpty()) {
            nifFiltro = null;
        }

        // 3. Llamamos a la lógica de negocio
        var res = controladorLogica.obtenerPedidosFiltrados(estado, nifFiltro);

        // 4. Mostrar resultados
        if (res.esExitoso()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== FILTRO: ").append(estado == '1' ? "TODOS" : (estado == '2' ? "EN CURSO" : "PROCESADOS")).append(" ===\n");
            if (nifFiltro != null) sb.append("CLIENTE: ").append(nifFiltro).append("\n");
            sb.append("------------------------------------------\n");

            for (Pedido p : res.getDato().getArrayList()) {
                sb.append(p.toString()).append("\n");
                sb.append("ESTADO: ").append(p.puedeCancelarse() ? "EN CURSO" : "PROCESADO").append("\n");
                sb.append("TOTAL: ").append(String.format("%.2f", p.calcularPrecio())).append(" €\n");
                sb.append("------------------------------------------\n");
            }
            txtAreaResultados.setText(sb.toString());
        } else {
            txtAreaResultados.setText("INFO: " + res.getMensaje());
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

    @FXML void volverMenuPrincipal(ActionEvent event) { cambiarVentana("/VentanaMain.fxml", event); }
    @FXML void abrirMenuClientes(ActionEvent event) { cambiarVentana("/VentanaClientes.fxml", event); }
    @FXML void abrirMenuArticulos(ActionEvent event) { cambiarVentana("/VentanaArticulos.fxml", event); }

}