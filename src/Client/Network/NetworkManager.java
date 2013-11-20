package Client.Network;

import Client.ClientUtil.ClientHelper;
import Util.LogType;
import Util.Message;
import Util.MessageType;
import Util.SocketReader;
import Client.Network.ThreadSocket;

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
        if(sockets.size() > 0)
        {
            for(ThreadSocket socket : sockets)
            {
                socket.close();
                sockets.remove(socket);
            }
        }
        ThreadSocket socket = new ThreadSocket();
        socket.connect(ip, port);
        sockets.add(socket);
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
            if(sockets.get(index).isConnected())
            {
                sockets.get(index).sendMessage(message.getFullMessage());
            }else{
                sockets.remove(index);
            }
        }
        catch(NullPointerException e)
        {
            ClientHelper.log(LogType.ERROR, "Socket mit Index " + index + " ist nicht vorhanden");
        }
    }

    public void sendMessageToAll(String message)
    {
        sendMessageToAll(new Message(MessageType.GENERAL, message));
    }

    public void sendMessageToAll(Message message)
    {
        for(ThreadSocket socket : sockets)
        {
            if(!socket.isConnected())
                sockets.remove(socket);
        }
        for(ThreadSocket socket : sockets)
        {
            socket.sendMessage(message.getFullMessage());
            ClientHelper.log(LogType.NETWORK, "Sende Nachricht [Typ: " + message.getType().name() + "]: " + message.getMessage());
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        ClientHelper.log(LogType.NETWORK, "Empfange Nachricht von " + ip + ":" + port);
        ClientHelper.getController().receiveMessage(new Message(message, ip, port));
    }
}
