module sn.lib.projetchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires emoji.java;


    opens sn.lib.projetchat to javafx.fxml;
    exports sn.lib.projetchat;
    exports sn.lib.projetchat.controller;
    opens sn.lib.projetchat.controller to javafx.fxml;
    exports sn.lib.projetchat.client;
    opens sn.lib.projetchat.client to javafx.fxml;
    exports sn.lib.projetchat.server;
    opens sn.lib.projetchat.server to javafx.fxml;

}