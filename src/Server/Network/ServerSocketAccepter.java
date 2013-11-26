package Server.Network;

import Server.ServerUtil.ServerHelper;
import Util.LogType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketAccepter extends Thread
{
    private ServerSocket socket;

    public ServerSocketAccepter(int port)
    {
        super();
        try
        {
            socket = new ServerSocket(port);
            ServerHelper.log(LogType.NETWORK, "ServerSocket gestartet auf Port " + port);
        }
        catch (IOException e)
        {
            ServerHelper.log(LogType.ERROR, "ServerSocket: Port " + port + " scheint belegt zu sein");
        }
    }

    public void run()
    {
        if(socket == null)
        {
            ServerHelper.log(LogType.ERROR, "ServerSocket wurde nicht erstellt");
            return;
        }
        while(!socket.isClosed())
        {
            try
            {
                Socket s = socket.accept();
                ServerHelper.getNetworkManager().createSocket(s);
            }
            catch (IOException e)
            {
                ServerHelper.log(LogType.ERROR, "Fehler bei der Verbindungsherstellung zu einem Clienten");
            }
        }
    }

    public void close()
    {
        if(socket != null)
        {
            try{
                socket.close();
                ServerHelper.log(LogType.NETWORK, "ServerSocket geschlossen");
            }
            catch(IOException e)
            {
                ServerHelper.log(LogType.ERROR, "Fehler beim Schließen des Serversockets");
            }
        }
    }

    public boolean isConnected()
    {
        if(socket != null)
        {
            return !socket.isClosed();
        }
        return false;
    }
}
