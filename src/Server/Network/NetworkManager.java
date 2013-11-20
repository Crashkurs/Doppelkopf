package Server.Network;

import Server.ServerUtil.Client;
import Server.ServerUtil.ServerHelper;
import Server.ServerUtil.ServerSocketAccepter;
import Util.Message;
import Util.MessageType;
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
        sendMessage(new Message(MessageType.GENERAL, message));
    }

    public void sendMessage(Message message)
    {
        sendMessage(message, 0);
    }

    public void sendMessage(Message message, int index)
    {
        try{
            clients.get(index).sendMessage(message.getFullMessage());
        }
        catch(NullPointerException e)
        {
            System.out.println("Socket mit Index " + index + " ist nicht vorhanden");
        }
    }

    public void sendMessageToAll(String message)
    {
        System.out.println(message);
        sendMessageToAll(new Message(MessageType.GENERAL, message));
    }

    public void sendMessageToAll(Message message)
    {
        for(Client client : clients)
        {
            client.sendMessage(message.getFullMessage());
            System.out.println("Sende Nachricht [Typ: " + message.getType().name() + "]: " + message.getMessage());
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        Message tmp = new Message(message, ip, port);
        ServerHelper.getController().receiveMessage(tmp);
    }

    public void clientClosed(Client client)
    {
        if(clients.contains(client))
        {
            System.out.println("Entferne geschlossenen Client");
            clients.remove(client);
        }else{
           System.out.println("Client wurde geschlossen, ist aber nicht in der Clientliste");
        }
    }
}
