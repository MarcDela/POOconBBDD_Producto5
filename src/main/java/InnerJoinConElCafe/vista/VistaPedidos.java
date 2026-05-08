package InnerJoinConElCafe.vista;

import InnerJoinConElCafe.controlador.Controlador;
import InnerJoinConElCafe.modelo.Lista;
import InnerJoinConElCafe.modelo.Pedido;
import InnerJoinConElCafe.modelo.Resultado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VistaPedidos extends VBox {

    private Controlador controlador;
    private TableView<Pedido> tabla;

    public VistaPedidos(Controlador controlador) {
        this.controlador = controlador;
        setSpacing(20);
        setPadding(new Insets(30));

        Label titulo = new Label("Gestión de Pedidos");
        titulo.getStyleClass().add("titulo-seccion");

        HBox formulario = crearFormulario();
        HBox filtros = crearFiltros();
        tabla = crearTabla();

        getChildren().addAll(titulo, formulario, filtros, tabla);
        cargarPedidos('1', null);
    }

    private HBox crearFormulario() {
        HBox form = new HBox(10);
        form.setAlignment(Pos.CENTER_LEFT);

        TextField txtNif = new TextField();
        txtNif.setPromptText("NIF cliente");
        txtNif.getStyleClass().add("input-campo");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código artículo");
        txtCodigo.getStyleClass().add("input-campo");

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtCantidad.getStyleClass().add("input-campo");

        Button btnAnadir = new Button("Añadir");
        btnAnadir.getStyleClass().add("btn-accion");

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        btnAnadir.setOnAction(e -> {
            try {
                String nif = txtNif.getText();
                int codigo = Integer.parseInt(txtCodigo.getText());
                int cantidad = Integer.parseInt(txtCantidad.getText());

                Resultado<String> res = controlador.añadirPedido(nif, codigo, cantidad);
                lblMensaje.setText(res.getMensaje());
                if (res.esExitoso()) {
                    txtNif.clear();
                    txtCodigo.clear();
                    txtCantidad.clear();
                    cargarPedidos('1', null);
                }
            } catch (NumberFormatException ex) {
                lblMensaje.setText("Error: introduce valores numéricos válidos.");
            }
        });

        form.getChildren().addAll(txtNif, txtCodigo, txtCantidad, btnAnadir, lblMensaje);
        return form;
    }

    private HBox crearFiltros() {
        HBox filtros = new HBox(10);
        filtros.setAlignment(Pos.CENTER_LEFT);

        Label lblEstado = new Label("Estado:");
        lblEstado.getStyleClass().add("mensaje");

        ComboBox<String> cmbEstado = new ComboBox<>();
        cmbEstado.getItems().addAll("Todos", "Pendientes", "Enviados");
        cmbEstado.setValue("Todos");

        Label lblCliente = new Label("NIF cliente:");
        lblCliente.getStyleClass().add("mensaje");

        TextField txtNifFiltro = new TextField();
        txtNifFiltro.setPromptText("Filtrar por NIF");
        txtNifFiltro.getStyleClass().add("input-campo");
        txtNifFiltro.setPrefWidth(120);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.getStyleClass().add("btn-accion");

        // Cancelar pedido
        Label lblCancelar = new Label("Nº pedido:");
        lblCancelar.getStyleClass().add("mensaje");

        TextField txtNumPedido = new TextField();
        txtNumPedido.setPromptText("Número");
        txtNumPedido.getStyleClass().add("input-campo");
        txtNumPedido.setPrefWidth(80);

        Button btnCancelar = new Button("Cancelar pedido");
        btnCancelar.getStyleClass().add("btn-cancelar");

        Label lblMensajeCancelar = new Label();
        lblMensajeCancelar.getStyleClass().add("mensaje");

        btnFiltrar.setOnAction(e -> {
            char estado = switch (cmbEstado.getValue()) {
                case "Pendientes" -> '2';
                case "Enviados" -> '3';
                default -> '1';
            };
            String nif = txtNifFiltro.getText().isEmpty() ? null : txtNifFiltro.getText();
            cargarPedidos(estado, nif);
        });

        btnCancelar.setOnAction(e -> {
            try {
                int numPedido = Integer.parseInt(txtNumPedido.getText());
                Resultado<String> res = controlador.cancelarPedido(numPedido);
                lblMensajeCancelar.setText(res.getMensaje());
                cargarPedidos('1', null);
            } catch (NumberFormatException ex) {
                lblMensajeCancelar.setText("Error: introduce un número válido.");
            }
        });

        filtros.getChildren().addAll(lblEstado, cmbEstado, lblCliente, txtNifFiltro, btnFiltrar,
                lblCancelar, txtNumPedido, btnCancelar, lblMensajeCancelar);
        return filtros;
    }

    private TableView<Pedido> crearTabla() {
        TableView<Pedido> tabla = new TableView<>();

        TableColumn<Pedido, Integer> colNum = new TableColumn<>("Nº Pedido");
        colNum.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNumeroPedido()).asObject());

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCliente().getNombre()));

        TableColumn<Pedido, String> colArticulo = new TableColumn<>("Artículo");
        colArticulo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getArticulo().getDescripcion()));

        TableColumn<Pedido, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getCantidad()).asObject());

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFechaHora().toString()));

        TableColumn<Pedido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
                d.getValue().puedeCancelarse() ? "Pendiente" : "Enviado"
        ));

        TableColumn<Pedido, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().calcularPrecio()).asObject());

        tabla.getColumns().addAll(colNum, colCliente, colArticulo, colCantidad, colFecha, colEstado, colTotal);
        return tabla;
    }

    private void cargarPedidos(char estado, String nif) {
        tabla.getItems().clear();
        Resultado<Lista<Pedido>> res = controlador.obtenerPedidosFiltrados(estado, nif);
        if (res.esExitoso()) {
            tabla.getItems().addAll(res.getDato().getArrayList());
        }
    }
}