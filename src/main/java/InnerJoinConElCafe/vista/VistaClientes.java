package InnerJoinConElCafe.vista;

import InnerJoinConElCafe.controlador.Controlador;
import InnerJoinConElCafe.modelo.Cliente;
import InnerJoinConElCafe.modelo.Lista;
import InnerJoinConElCafe.modelo.Resultado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VistaClientes extends VBox {

    private Controlador controlador;
    private TableView<Cliente> tabla;
    private ComboBox<String> filtroTipo;

    public VistaClientes(Controlador controlador) {
        this.controlador = controlador;
        setSpacing(20);
        setPadding(new Insets(30));

        Label titulo = new Label("Gestión de Clientes");
        titulo.getStyleClass().add("titulo-seccion");

        HBox formulario = crearFormulario();
        HBox filtros = crearFiltros();
        tabla = crearTabla();

        getChildren().addAll(titulo, formulario, filtros, tabla);
        cargarClientes(1);
    }

    private HBox crearFormulario() {
        HBox form = new HBox(10);
        form.setAlignment(Pos.CENTER_LEFT);

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("input-campo");

        TextField txtDomicilio = new TextField();
        txtDomicilio.setPromptText("Domicilio");
        txtDomicilio.getStyleClass().add("input-campo");

        TextField txtNif = new TextField();
        txtNif.setPromptText("NIF");
        txtNif.getStyleClass().add("input-campo");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("input-campo");

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Estándar", "Premium");
        cmbTipo.setValue("Estándar");
        cmbTipo.getStyleClass().add("input-campo");

        Button btnAnadir = new Button("Añadir");
        btnAnadir.getStyleClass().add("btn-accion");

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        btnAnadir.setOnAction(e -> {
            int tipo = cmbTipo.getValue().equals("Premium") ? 2 : 1;
            Resultado<String> res = controlador.añadirCliente(
                    txtNombre.getText(), txtDomicilio.getText(),
                    txtNif.getText(), txtEmail.getText(), tipo
            );
            lblMensaje.setText(res.getMensaje());
            if (res.esExitoso()) {
                txtNombre.clear();
                txtDomicilio.clear();
                txtNif.clear();
                txtEmail.clear();
                cargarClientes(1);
            }
        });

        form.getChildren().addAll(txtNombre, txtDomicilio, txtNif, txtEmail, cmbTipo, btnAnadir, lblMensaje);
        return form;
    }

    private HBox crearFiltros() {
        HBox filtros = new HBox(10);
        filtros.setAlignment(Pos.CENTER_LEFT);

        Label lblFiltro = new Label("Mostrar:");
        lblFiltro.getStyleClass().add("mensaje");

        filtroTipo = new ComboBox<>();
        filtroTipo.getItems().addAll("Todos", "Estándar", "Premium");
        filtroTipo.setValue("Todos");

        filtroTipo.setOnAction(e -> {
            int opcion = switch (filtroTipo.getValue()) {
                case "Estándar" -> 2;
                case "Premium" -> 3;
                default -> 1;
            };
            cargarClientes(opcion);
        });

        filtros.getChildren().addAll(lblFiltro, filtroTipo);
        return filtros;
    }

    private TableView<Cliente> crearTabla() {
        TableView<Cliente> tabla = new TableView<>();

        TableColumn<Cliente, String> colNif = new TableColumn<>("NIF");
        colNif.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNif()));

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNombre()));

        TableColumn<Cliente, String> colDomicilio = new TableColumn<>("Domicilio");
        colDomicilio.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDomicilio()));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEmail()));

        TableColumn<Cliente, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue() instanceof InnerJoinConElCafe.modelo.ClientePremium ? "Premium" : "Estándar"
        ));

        tabla.getColumns().addAll(colNif, colNombre, colDomicilio, colEmail, colTipo);
        return tabla;
    }

    private void cargarClientes(int opcion) {
        tabla.getItems().clear();
        Resultado<Lista<Cliente>> res = controlador.obtenerClientes(opcion);
        if (res.esExitoso()) {
            tabla.getItems().addAll(res.getDato().getArrayList());
        }
    }
}