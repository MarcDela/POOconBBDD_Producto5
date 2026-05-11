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

        // Fila con los dos grupos en la misma línea
        HBox filaGrupos = new HBox(20);
        filaGrupos.setAlignment(Pos.TOP_LEFT);
        filaGrupos.getChildren().addAll(crearGrupoAnadir(), crearGrupoCancelar());

        HBox filtros = crearFiltros();
        tabla = crearTabla();

        getChildren().addAll(titulo, filaGrupos, filtros, tabla);
        cargarPedidos('1', null);
    }

    private VBox crearGrupoAnadir() {
        VBox grupo = new VBox(10);
        grupo.setStyle(
                "-fx-border-color: #444444;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: #313335;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 15;"
        );

        Label lblTitulo = new Label("Añadir Pedido");
        lblTitulo.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

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

        Button btnAnadir = new Button("Añadir Pedido");
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

        form.getChildren().addAll(txtNif, txtCodigo, txtCantidad, btnAnadir);
        grupo.getChildren().addAll(lblTitulo, form, lblMensaje);
        return grupo;
    }

    private VBox crearGrupoCancelar() {
        VBox grupo = new VBox(10);
        grupo.setStyle(
                "-fx-border-color: #444444;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: #313335;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 15;"
        );

        Label lblTitulo = new Label("Cancelar Pedido");
        lblTitulo.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        HBox form = new HBox(10);
        form.setAlignment(Pos.CENTER_LEFT);

        Label lblNum = new Label("Nº pedido:");
        lblNum.getStyleClass().add("mensaje");

        TextField txtNumPedido = new TextField();
        txtNumPedido.setPromptText("Número");
        txtNumPedido.getStyleClass().add("input-campo");
        txtNumPedido.setPrefWidth(80);

        Button btnCancelar = new Button("Cancelar pedido");
        btnCancelar.getStyleClass().add("btn-cancelar");

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        btnCancelar.setOnAction(e -> {
            try {
                int numPedido = Integer.parseInt(txtNumPedido.getText());
                Resultado<String> res = controlador.cancelarPedido(numPedido);
                lblMensaje.setText(res.getMensaje());
                cargarPedidos('1', null);
            } catch (NumberFormatException ex) {
                lblMensaje.setText("Error: introduce un número válido.");
            }
        });

        form.getChildren().addAll(lblNum, txtNumPedido, btnCancelar);
        grupo.getChildren().addAll(lblTitulo, form, lblMensaje);
        return grupo;
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

        btnFiltrar.setOnAction(e -> {
            char estado = switch (cmbEstado.getValue()) {
                case "Pendientes" -> '2';
                case "Enviados" -> '3';
                default -> '1';
            };
            String nif = txtNifFiltro.getText().isEmpty() ? null : txtNifFiltro.getText();
            cargarPedidos(estado, nif);
        });

        filtros.getChildren().addAll(lblEstado, cmbEstado, lblCliente, txtNifFiltro, btnFiltrar);
        return filtros;
    }

    private TableView<Pedido> crearTabla() {
        TableView<Pedido> tabla = new TableView<>();

        TableColumn<Pedido, Integer> colNum = new TableColumn<>("Nº Pedido");
        colNum.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getNumeroPedido()).asObject());

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCliente().getNombre()));

        TableColumn<Pedido, String> colNif = new TableColumn<>("NIF");
        colNif.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getCliente().getNif()));

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

        tabla.getColumns().addAll(colNum, colCliente, colNif, colArticulo, colCantidad, colFecha, colEstado, colTotal);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
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