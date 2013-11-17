package Server.Network;

import Server.ServerUtil.ServerHelper;
import Server.ServerUtil.ServerSocketAccepter;
import Util.SocketReader;
import Util.ThreadSocket;
import java.net.Socket;
import java.util.LinkedList;

public class NetworkManager implements NetworkManagerInterface, SocketReader
{
    private LinkedList<ThreadSocket> sockets;
    private ServerSocketAccepter accepter;

    public NetworkManager()
    {
        sockets = new LinkedList<ThreadSocket>();
    }

    public void connect(int port)
    {
        accepter = new ServerSocketAccepter(port);
        accepter.start();
    }

    public void createSocket(Socket s)
    {
        sockets.add(new ThreadSocket(s));
    }

    public void sendMessage(String message)
    {
        sendMessage(message, 0);
    }

    public void sendMessage(String message, int index)
    {
        try{
            sockets.get(index).sendMessage(message);
        }
        catch(NullPointerException e)
        {
            System.out.println("Socket mit Index " + index + " ist nicht vorhanden");
        }
    }

    public void sendMessageToAll(String message)
    {
        if(sockets.size() == 0)
            System.out.println("Keine Sockets vorhanden für sendMessageToAll");
        for(ThreadSocket socket : sockets)
        {
            socket.sendMessage(message);
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        ServerHelper.getController().receiveMessage(message);
    }
}
