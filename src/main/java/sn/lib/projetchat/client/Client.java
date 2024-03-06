package sn.lib.projetchat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket socket;
    private List<Client> clients;
    private DataInputStream dataE;
    private DataOutputStream dataS;
    private String msg = "";

    public Client(Socket socket, List<Client> clients) {

        //Initialisation des variables membres dans le constructeur
        try {
            this.socket = socket;
            this.clients = clients;
            this.dataE = new DataInputStream(socket.getInputStream());
            this.dataS = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }


        //gère la réception et la diffusion des messages.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        msg = dataE.readUTF();
                        for (Client c : clients) {
                            if (c.socket.getPort() != socket.getPort()) {
                                c.dataS.writeUTF(msg);
                                c.dataS.flush();
                            }
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
