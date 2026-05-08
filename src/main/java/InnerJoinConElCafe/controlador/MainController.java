package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController extends BaseController {

    @FXML private Button btnMenuPedidos;
    @FXML private Button btnMenuClientes;
    @FXML private Button btnMenuArticulos;

    @FXML void abrirMenuPedidos(ActionEvent event) { cambiarVentana("/VentanaPedidos.fxml", event); }
    @FXML void abrirMenuClientes(ActionEvent event) { cambiarVentana("/VentanaClientes.fxml", event); }
    @FXML void abrirMenuArticulos(ActionEvent event) { cambiarVentana("/VentanaArticulos.fxml", event); }
}