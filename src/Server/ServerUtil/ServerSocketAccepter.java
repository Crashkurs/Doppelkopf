package Server.ServerUtil;

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
        }
        catch (IOException e)
        {
            ServerHelper.log(LogType.SERVERUTIL, "ServerSocket: Port " + port + " scheint belegt zu sein");
        }
    }

    public void run()
    {
        if(socket == null)
        {
            ServerHelper.log(LogType.SERVERUTIL, "ServerSocket wurde nicht erstellt");
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
                ServerHelper.log(LogType.SERVERUTIL, "Fehler bei der Verbindungsherstellung zu einem Clienten");
            }
        }
    }

    public void close()
    {
        try{
            socket.close();
        }
        catch(IOException e)
        {
            ServerHelper.log(LogType.SERVERUTIL, "Fehler beim Schließen des Serversockets");
        }
    }
}
