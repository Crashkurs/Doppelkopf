package Server.ServerUtil;

import Util.LogType;
import Util.SocketReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ThreadSocket implements SocketReader
{
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private NetworkThread networkThread;

    private String ip;
    private int port;

    public ThreadSocket()
    {
        socket = new Socket();
    }

    public ThreadSocket(Socket s)
    {
        socket = s;
        if(socket.isConnected())
        {
            try{
                ip = socket.getInetAddress().getHostAddress();
                port = socket.getPort();
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                networkThread = new NetworkThread(this,socket);
                networkThread.start();
            }
            catch(IOException e)
            {
                ServerHelper.log(LogType.SERVERUTIL, "Fehler beim Speichern der Clientattribute");
            }
        }
    }

    public void connect(String ip, int port)
    {
        try
        {
            this.ip = ip;
            this.port = port;
            socket.connect(new InetSocketAddress(ip, port));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            networkThread = new NetworkThread(this,socket);
            networkThread.start();
        }
        catch(IOException e)
        {
            ServerHelper.log(LogType.SERVERUTIL, "Verbindung zu "+ip+":"+port+" ist fehlgeschlagen");
        }
    }

    public void sendMessage(String message)
    {
        try{
            writer.println(message);
            writer.flush();
        }
        catch(NullPointerException e)
        {
            ServerHelper.log(LogType.SERVERUTIL, "Kein Outputstream für " + ip + ":" + port + " vorhanden");
        }
    }

    public void receiveMessage(String message, String ip, int port)
    {
        ServerHelper.getNetworkManager().receiveMessage(message, ip, port);
    }

    public void close()
    {
        networkThread.close();
        System.gc();
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isConnected()
    {
        return !socket.isClosed();
    }

    private class NetworkThread extends Thread
    {
        private ThreadSocket threadSocket;
        private Socket socket;

        public NetworkThread(Object _threadSocket, Socket _socket)
        {
            if(SocketReader.class.isInstance(_threadSocket))
            {
                threadSocket = (ThreadSocket)_threadSocket;
                socket = _socket;
            }else{
                ServerHelper.log(LogType.SERVERUTIL, "Threadsocket implementiert das SocketReader-Interface nicht");
            }
        }

        public void run()
        {
            while(threadSocket != null && socket.isConnected())
            {
                try{
                    String message = reader.readLine();
                    threadSocket.receiveMessage(message, socket.getInetAddress().getHostAddress(), socket.getPort());
                }
                catch(IOException e)
                {
                    ServerHelper.log(LogType.SERVERUTIL, "Fehler beim Empfangen einer Nachricht");
                    close();
                }
            }
            ServerHelper.log(LogType.SERVERUTIL, "Networkthread closed");
            close();
        }

        public void close()
        {
            try{
                socket.close();
                threadSocket = null;
            }
            catch(IOException e)
            {
                ServerHelper.log(LogType.SERVERUTIL, "Socket konnte nicht geschlossen werden");
            }
        }
    }
}
