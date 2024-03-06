package sn.lib.projetchat.controller;

import javafx.application.Platform;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Dialog;
import sn.lib.projetchat.Emoji;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private ImageView audio;
    @FXML
    private Button btn1;
    @FXML
    private ImageView emoji;

    @FXML
    private ImageView send;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane pane;



    @FXML
    private Button btn2;

    @FXML
    private TextField champ;

    @FXML
    private ScrollPane chat;

    @FXML
    private ImageView logo;

    @FXML
    private VBox vBox;

    //Autres variables
    private String clientName;


    private Socket socket;
    private DataInputStream dataE;
    private DataOutputStream dataS;

    public HelloController() {}


    @FXML
    void send(ActionEvent event) {
        sendMsg(champ.getText());

    }

    @FXML
    void txtMsg(ActionEvent event) {
        send(event);
    }


    //initialisation du contrôleur
    public void initialize(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket = new Socket("localhost", 7000);
                    dataE = new DataInputStream(socket.getInputStream());
                    dataS = new DataOutputStream(socket.getOutputStream());
                    System.out.println("Client connecté");
                    ServerController.recevoirMessage(clientName+" connecté.");

                    while (socket.isConnected()){
                        String receivingMsg = dataE.readUTF();
                        receiveMessage(receivingMsg, HelloController.this.vBox);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        this.vBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                chat.setVvalue((Double) newValue);
            }
        });
    }

    //notifier le serveur lorsque le client se déconnecte du chat.
    public void shutdown() {
        ServerController.recevoirMessage(clientName + " déconnecté.");
    }



    //envoyer un message saisi par l'utilisateur
    private void sendMsg(String msgToSend) {
        if (!msgToSend.isEmpty()){

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 0, 10));

                Text text = new Text(msgToSend);

                //decoration des messages
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(1, 1, 1));

                hBox.getChildren().add(textFlow);

                HBox hBoxTime = new HBox();
                hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                hBoxTime.setPadding(new Insets(0, 5, 5, 10));
                String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                Text time = new Text(stringTime);
                time.setStyle("-fx-font-size: 8");

                hBoxTime.getChildren().add(time);

                vBox.getChildren().add(hBox);
                vBox.getChildren().add(hBoxTime);

                try {
                    dataS.writeUTF(clientName + "-" + msgToSend);
                    dataS.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                champ.clear();
            }
        }



//afficher les messages reçus du serveur dans l'interface serveur
    public static void receiveMessage(String msg, VBox vBox) throws IOException {
        if (msg.matches(".*\\.(png|jpe?g|gif)$")){
            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            Text textName = new Text(msg.split("[-]")[0]);
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Image image = new Image(msg.split("[-]")[1]);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,5,5,10));
            hBox.getChildren().add(imageView);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                }
            });
        }
        else {
            String name = msg.split("-")[0];
            String msgFromServer = msg.split("-")[1];

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5,5,5,10));

            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            Text textName = new Text(name);
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Text text = new Text(msgFromServer);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0,0,0));

            hBox.getChildren().add(textFlow);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBoxName);
                    vBox.getChildren().add(hBox);
                }
            });
        }
    }



    // Méthode pour définir le nom du client connecté sur le serveur
    public void setClientName(String nom) {
        clientName = nom;
    }


    @FXML
    void emoji(ActionEvent actionEvent) {

            // Ouvrir la liste d'emojis lorsque l'utilisateur clique sur le bouton
            Emoji emojiPicker = new Emoji();
            ListView<String> emojiListView = emojiPicker.getEmojiListView();

            // Créer une boîte de dialogue pour afficher la liste d'emojis
            Dialog<ListView<String>> dialog = new Dialog<>();
            dialog.setTitle("Choisir un emoji");
            dialog.getDialogPane().setContent(emojiListView);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Récupérer l'emoji sélectionné lorsque l'utilisateur clique sur OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return emojiListView; // Retourner l'élément ListView<String> sélectionné
            }
            return null;
        });

// Afficher la boîte de dialogue
        Optional<ListView<String>> result = dialog.showAndWait();
        result.ifPresent(selectedEmojiList -> {
            // Récupérer l'emoji sélectionné dans la liste
            String selectedEmoji = selectedEmojiList.getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                // Ajouter l'emoji sélectionné au champ de texte
                String currentText = champ.getText();
                champ.setText(currentText + selectedEmoji);
            }
        });

    }




}
