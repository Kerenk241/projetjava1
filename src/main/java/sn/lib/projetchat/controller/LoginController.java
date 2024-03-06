package sn.lib.projetchat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button exit;

    @FXML
    private ImageView image;

    @FXML
    private Button login;

    @FXML
    private AnchorPane pane;

    @FXML
    private PasswordField pwd;

    @FXML
    private TextField user;


    //gestion du bouton login
    @FXML
    void login(ActionEvent event) throws IOException {
        if (!user.getText().isEmpty() && !pwd.getText().isEmpty() && user.getText().matches("[A-Za-z0-9]+")){
            Stage primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.fxml"));
            HelloController controller = new HelloController();
            controller.setClientName(user.getText());


            primaryStage.setScene(new Scene(fxmlLoader.load()));
            primaryStage.setTitle(user.getText());
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(windowEvent -> {
                controller.shutdown();

            });
            primaryStage.show();
            user.clear();
            pwd.clear();
        }else{
            new Alert(Alert.AlertType.ERROR, "Veuillez entrer les valeurs appropriées").show();
        }
    }


    @FXML
    void quitter(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
