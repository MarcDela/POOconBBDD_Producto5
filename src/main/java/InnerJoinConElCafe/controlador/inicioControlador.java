package InnerJoinConElCafe.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Node;
import java.io.IOException;

public class inicioControlador {

//Metodos para app (JavaFX)
@FXML
private void abrirMenuPedidos(ActionEvent event){
   try {
        Parent root = FXMLLoader.load(getClass().getResource("/pedidos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    } catch (IOException e) {e.printStackTrace();}
}

@FXML
private void abrirMenuClientes(ActionEvent event){
   try {
        Parent root = FXMLLoader.load(getClass().getResource("/clientes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    } catch (IOException e) {e.printStackTrace();}
}

@FXML
private void abrirMenuArticulos(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/articulos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    } catch (IOException e) {e.printStackTrace();}
}
}
