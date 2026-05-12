package InnerJoinConElCafe.controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;

public class articulosControlador {

@FXML
private void mostrarArticulos(ActionEvent event) {
    System.out.println("Cargando lista de artículos...");
    // Aquí irá la lógica de la tabla
}

@FXML
private void addArticulo(ActionEvent event) {
    System.out.println("Guardando nuevo artículo...");
    // Aquí irá la lógica de guardado
}

    // Método para volver a la pantalla principal (inicio)
@FXML
private void volverInicio(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/inicio.fxml"));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
}
}