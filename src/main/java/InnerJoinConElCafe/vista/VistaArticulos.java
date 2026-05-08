package InnerJoinConElCafe.vista;

import InnerJoinConElCafe.controlador.Controlador;
import InnerJoinConElCafe.modelo.Articulo;
import InnerJoinConElCafe.modelo.Lista;
import InnerJoinConElCafe.modelo.Resultado;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VistaArticulos extends VBox {

    private Controlador controlador;
    private TableView<Articulo> tabla;

    public VistaArticulos(Controlador controlador) {
        this.controlador = controlador;
        setSpacing(20);
        setPadding(new Insets(30));
        getStyleClass().add("contenido");

        Label titulo = new Label("Gestión de Artículos");
        titulo.getStyleClass().add("titulo-seccion");

        HBox formulario = crearFormulario();
        tabla = crearTabla();

        getChildren().addAll(titulo, formulario, tabla);
        cargarArticulos();
    }

    private HBox crearFormulario() {
        HBox form = new HBox(10);
        form.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        TextField txtDesc = new TextField();
        txtDesc.setPromptText("Descripción");
        txtDesc.getStyleClass().add("input-campo");

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtPrecio.getStyleClass().add("input-campo");

        TextField txtEnvio = new TextField();
        txtEnvio.setPromptText("Gastos envío");
        txtEnvio.getStyleClass().add("input-campo");

        TextField txtTiempo = new TextField();
        txtTiempo.setPromptText("Tiempo prep. (min)");
        txtTiempo.getStyleClass().add("input-campo");

        Button btnAnadir = new Button("Añadir");
        btnAnadir.getStyleClass().add("btn-accion");

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

        form.getChildren().addAll(txtDesc, txtPrecio, txtEnvio, txtTiempo, btnAnadir, lblMensaje);
        return form;
    }

    private TableView<Articulo> crearTabla() {
        TableView<Articulo> tabla = new TableView<>();
        tabla.getStyleClass().add("tabla");

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