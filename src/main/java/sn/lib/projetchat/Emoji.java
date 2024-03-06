package sn.lib.projetchat;

import com.vdurmont.emoji.EmojiParser;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Emoji extends VBox {
        private ListView<String> liste;

        public Emoji() {
            // charger les images des emoji
            List<String> emojis = new ArrayList<>();

            String[] emojiList = new String[]{"&#128514;","&#10084;","&#128525;","&#129315;","&#128522;",
                    "&#128591;","&#128149;","&#128557;","&#128293;","&#128536;","&#128077;","&#129392;","&#128526;","&#128518;",
                    "&#128513;","&#128521;","&#129300;","&#128517;","&#128532;","&#128580;","&#128540;","&#9829;","&#9851;","&#128530;",
                    "&#128553;","&#9786;","&#128513;","&#128076;","&#128079;","&#128148;","&#128150;","&#128153;"};
            for (String em:emojiList) {
                emojis.add(EmojiParser.parseToUnicode(em));
            }

            // Creation de la list des emoji
            liste = new ListView<>();
            liste.setItems(FXCollections.observableArrayList(emojis));
            HBox hBox = new HBox(liste);
            hBox.setPadding(new Insets(10));
            getChildren().add(hBox);
        }

        public ListView<String> getEmojiListView() {
            return liste;
        }

}