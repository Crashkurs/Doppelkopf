package Server.Network;

import Server.ServerUtil.Client;
import Server.ServerUtil.ServerHelper;
import Server.ServerUtil.ServerSocketAccepter;
import Util.SocketReader;
import Server.ServerUtil.ThreadSocket;
import java.net.Socket;
import java.util.LinkedList;

public class NetworkManager implements NetworkManagerInterface, SocketReader
{
    private LinkedList<Client> clients;
    private ServerSocketAccepter accepter;

    public NetworkManager()
    {
        clients = new LinkedList<Client>();
    }

    public void connect(int port)
    {
        accepter = new ServerSocketAccepter(port);
        accepter.start();
    }

    public void createSocket(Socket s)
    {
        ThreadSocket tmp = new ThreadSocket(s);
        clients.add(new Client("",tmp));
    }

    public void sendMessage(String message)
    {
        sendMessage(message, 0);
    }

    public void sendMessage(String message, int index)
    {
        try{
            clients.get(index).sendMessage(message);
        }
        catch(NullPointerException e)
        {
            System.out.println("Socket mit Index " + index + " ist nicht vorhanden");
        }
    }

    public void sendMessageToAll(String message)
    {
        if(clients.size() == 0)
            System.out.println("Keine Sockets vorhanden für sendMessageToAll");
        for(Client client : clients)
        {
            client.sendMessage(message);
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        ServerHelper.getController().receiveMessage(message);
    }

    public void clientClosed(Client client)
    {
        if(clients.contains(client))
        {
            clients.remove(client);
        }else{
           System.out.println("Client wurde geschlossen, ist aber nicht in der Clientliste");
        }
    }
}
