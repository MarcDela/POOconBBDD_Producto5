package InnerJoinConElCafe.vista;

import InnerJoinConElCafe.controlador.Controlador;
import InnerJoinConElCafe.modelo.Articulo;
import InnerJoinConElCafe.modelo.Lista;
import InnerJoinConElCafe.modelo.Resultado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VistaArticulos extends VBox {

    private Controlador controlador;
    private TableView<Articulo> tabla;

    public VistaArticulos(Controlador controlador) {
        this.controlador = controlador;
        setSpacing(20);
        setPadding(new Insets(30));

        Label titulo = new Label("Gestión de Artículos");
        titulo.getStyleClass().add("titulo-seccion");

        TitledPane tpAnadir = new TitledPane("Añadir Artículo", crearFormulario());
        tpAnadir.setCollapsible(false);

        tabla = crearTabla();

        getChildren().addAll(titulo, tpAnadir, tabla);
        cargarArticulos();
    }

    private HBox crearFormulario() {
        HBox form = new HBox(10);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(10));

        TextField txtDesc = new TextField();
        txtDesc.setPromptText("Descripción");
        txtDesc.getStyleClass().add("input-campo");

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtPrecio.getStyleClass().add("input-campo");

        txtPrecio.focusedProperty().addListener((obs, teniafoco, tieneFoco) -> {
            if (!tieneFoco) {
                try {
                    Double.parseDouble(txtPrecio.getText());
                    txtPrecio.setStyle("-fx-border-color: transparent;");
                } catch (NumberFormatException e) {
                    txtPrecio.setStyle("-fx-border-color: #f25489;");
                }
            }
        });

        TextField txtEnvio = new TextField();
        txtEnvio.setPromptText("Gastos envío");
        txtEnvio.getStyleClass().add("input-campo");

        TextField txtTiempo = new TextField();
        txtTiempo.setPromptText("Tiempo prep. (min)");
        txtTiempo.getStyleClass().add("input-campo");

        Button btnAnadir = new Button("Añadir Artículo");
        btnAnadir.getStyleClass().add("btn-accion");

        txtTiempo.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                btnAnadir.fire();
            }
        });

        Label lblMensaje = new Label();
        lblMensaje.getStyleClass().add("mensaje");

        btnAnadir.setOnAction(e -> {
            try {
                String desc = txtDesc.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                double envio = Double.parseDouble(txtEnvio.getText());
                int tiempo = Integer.parseInt(txtTiempo.getText());

                Resultado<String> res = controlador.añadirArticulo(desc, precio, envio, tiempo);
                lblMensaje.setText(res.getMensaje());
                if (res.esExitoso()) {
                    txtDesc.clear();
                    txtPrecio.clear();
                    txtEnvio.clear();
                    txtTiempo.clear();
                    cargarArticulos();
                }
            } catch (NumberFormatException ex) {
                lblMensaje.setText("Error: introduce valores numéricos válidos.");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("🔍 Buscar artículo...");
        txtBuscar.getStyleClass().add("input-campo");
        txtBuscar.setPrefWidth(200);

        txtBuscar.textProperty().addListener((observable, anterior, nuevo) -> {
            tabla.getItems().clear();
            Resultado<Lista<Articulo>> res = controlador.obtenerArticulos();
            if (res.esExitoso()) {
                for (Articulo a : res.getDato().getArrayList()) {
                    if (a.getDescripcion().toLowerCase().contains(nuevo.toLowerCase())) {
                        tabla.getItems().add(a);
                    }
                }
            }
        });

        form.getChildren().addAll(txtDesc, txtPrecio, txtEnvio, txtTiempo, btnAnadir, lblMensaje, spacer, txtBuscar);
        return form;
    }

    private TableView<Articulo> crearTabla() {
        TableView<Articulo> tabla = new TableView<>();

        TableColumn<Articulo, Integer> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getCodigo()).asObject());

        TableColumn<Articulo, String> colDesc = new TableColumn<>("Descripción");
        colDesc.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDescripcion()));

        TableColumn<Articulo, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrecioVenta()).asObject());

        TableColumn<Articulo, Double> colEnvio = new TableColumn<>("Gastos envío");
        colEnvio.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getGastosEnvio()).asObject());

        TableColumn<Articulo, Integer> colTiempo = new TableColumn<>("Tiempo prep.");
        colTiempo.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getTiempoPreparacion()).asObject());

        tabla.getColumns().addAll(colCodigo, colDesc, colPrecio, colEnvio, colTiempo);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        return tabla;
    }

    private void cargarArticulos() {
        tabla.getItems().clear();
        Resultado<Lista<Articulo>> res = controlador.obtenerArticulos();
        if (res.esExitoso()) {
            tabla.getItems().addAll(res.getDato().getArrayList());
        }
    }
}