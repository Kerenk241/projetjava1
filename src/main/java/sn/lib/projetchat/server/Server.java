package sn.lib.projetchat.server;

import sn.lib.projetchat.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    //Déclaration des variables pour la communication avec les clients.
    private ServerSocket serverSocket;
    private Socket socket;

    //Déclaration d'une variable statique
    private static Server server;

    //Déclaration d'une liste clients pour stocker les instances des clients connectés au serveur.
    private List<Client> clients = new ArrayList<>();


    //initialiser le serveur
    private Server() throws IOException {
        serverSocket = new ServerSocket(7000);
    }

//retourne l'instance unique du serveur
    public static Server getInstance() throws IOException {
        return server!=null? server:(server=new Server());
    }


    //Tant que le serveur n'est pas fermé, il accepte les connexions entrantes
    public void makeSocket(){
        while (!serverSocket.isClosed()){
            try{
                socket = serverSocket.accept();
                Client client = new Client(socket,clients);
                clients.add(client);
                System.out.println("client accepté "+socket.toString());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}


