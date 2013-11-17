package Client.Network;

import Client.ClientUtil.ClientHelper;
import Util.SocketReader;
import Util.ThreadSocket;

import java.util.LinkedList;

public class NetworkManager implements NetworkManagerInterface, SocketReader
{
    private LinkedList<ThreadSocket> sockets;

    public NetworkManager()
    {
        sockets = new LinkedList<ThreadSocket>();
    }

    public void connect(String ip, int port)
    {
        ThreadSocket socket = new ThreadSocket();
        socket.connect(ip, port);
        sockets.add(socket);
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
        ClientHelper.getController().receiveMessage(message);
    }
}
