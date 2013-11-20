package Server.Network;

import Server.ServerUtil.Client;
import Server.ServerUtil.ServerHelper;
import Util.LogType;
import Util.Message;
import Util.MessageType;
import Util.SocketReader;

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
        ServerHelper.log(LogType.NETWORK, "Erstelle neuen Clienten für Adresse " + s.getInetAddress().getHostAddress() + ":" + s.getPort());
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
        removeClosedClients();
        try{
            clients.get(index).sendMessage(message.getFullMessage());
            ServerHelper.log(LogType.NETWORK, "Sende Nachricht an Client " + index + " [Typ: " + message.getType().name() + "]: " + message.getMessage());
        }
        catch(NullPointerException e)
        {
            ServerHelper.log(LogType.ERROR, "Socket mit Index " + index + " ist nicht vorhanden");
        }
    }

    public void sendMessageToAll(String message)
    {
        sendMessageToAll(new Message(MessageType.GENERAL, message));
    }

    public void sendMessageToAll(Message message)
    {
        removeClosedClients();
        for(Client client : clients)
        {
            client.sendMessage(message.getFullMessage());
            ServerHelper.log(LogType.NETWORK, "Sende Nachricht an Client " + clients.indexOf(client) + " [Typ: " + message.getType().name() + "]: " + message.getMessage());
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        Message tmp = new Message(message, ip, port);
        ServerHelper.getController().receiveMessage(tmp);
    }

    public void removeClosedClients()
    {
        LinkedList<Client> tmp = clients;
        for(Client client : tmp)
        {
            if(!client.isConnected())
                clients.remove(client);
        }
    }

    public void clientClosed(Client client)
    {
        if(clients.contains(client))
        {
            ServerHelper.log(LogType.NETWORK, "Entferne geschlossenen Client");
            clients.remove(client);
        }else{
            ServerHelper.log(LogType.ERROR, "Client wurde geschlossen, ist aber nicht in der Clientliste");
        }
    }
}
