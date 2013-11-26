package Server.Network;

import Server.ServerUtil.ServerHelper;
import Util.LogType;
import Util.Message;
import Util.SocketReader;
import Server.ServerUtil.Client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ThreadSocket implements SocketReader
{
    private Client client;
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private NetworkThread networkThread;

    private String ip;
    private int port;

    public ThreadSocket()
    {
        socket = new Socket();
        ip = "0.0.0.0";
        port = -1;
    }

    public ThreadSocket(Socket s)
    {
        socket = s;
        if(socket.isConnected())
        {
            try{
                ip = socket.getInetAddress().getHostAddress();
                port = socket.getPort();
                writer = new ObjectOutputStream(socket.getOutputStream());
                reader = new ObjectInputStream(socket.getInputStream());
                networkThread = new NetworkThread(this,socket);
                networkThread.start();
            }
            catch(IOException e)
            {
                ServerHelper.log(LogType.ERROR, "Fehler beim Speichern der Clientattribute");
            }
        }else{
            ServerHelper.log(LogType.ERROR, "Threadsocket konnte wegen einem nicht verbundenen Socket nicht erstellt werden");
        }
    }

    public void setClient(Client _client)
    {
        client = _client;
    }

    public void connect(String ip, int port)
    {
        try
        {
            this.ip = ip;
            this.port = port;
            socket.connect(new InetSocketAddress(ip, port));
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
            networkThread = new NetworkThread(this,socket);
            networkThread.start();
        }
        catch(IOException e)
        {
            ServerHelper.log(LogType.ERROR, "Verbindung zu "+ip+":"+port+" ist fehlgeschlagen");
        }
    }

    public void sendMessage(Message message)
    {
        try{
            writer.writeObject(message);
            writer.flush();
        }
        catch(NullPointerException e)
        {
            ServerHelper.log(LogType.ERROR, "Kein Outputstream für " + ip + ":" + port + " vorhanden");
        }
        catch(IOException e)
        {
            ServerHelper.log(LogType.ERROR, "Fehler beim Senden einer Nachricht");
        }
    }

    public void receiveMessage(Message message)
    {
        ServerHelper.log(LogType.NETWORK, "Empfange Nachricht von " + message.getIp() + ":" + message.getPort());
        ServerHelper.getNetworkManager().receiveMessage(message);
    }

    public void close()
    {
        if(networkThread != null)
            networkThread.close();
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
                ServerHelper.log(LogType.ERROR, "Threadsocket implementiert das SocketReader-Interface nicht");
            }
        }

        public void run()
        {
            while(threadSocket != null && socket.isConnected())
            {
                try{
                    Message message = (Message)reader.readObject();
                    message.setIp(socket.getInetAddress().getHostAddress());
                    message.setPort(socket.getPort());
                    threadSocket.receiveMessage(message);
                }
                catch(IOException e)
                {
                    ServerHelper.log(LogType.ERROR, "Fehler beim Empfangen einer Nachricht (IOException)");
                    if(client != null)
                        client.closeClient();
                    close();
                }
                catch(ClassNotFoundException e)
                {
                    ServerHelper.log(LogType.ERROR, "Empfangene Nachricht hat nicht den Typ Message");
                }
            }
            ServerHelper.log(LogType.NETWORK, "Verbindung zu " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " unterbrochen");
            close();
        }

        public void close()
        {
            try{
                if(socket != null)
                    socket.close();
                threadSocket = null;
            }
            catch(IOException e)
            {
                ServerHelper.log(LogType.ERROR, "Socket konnte nicht geschlossen werden");
            }
        }
    }
}
