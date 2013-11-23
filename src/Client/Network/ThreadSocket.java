package Client.Network;

import Client.ClientUtil.ClientHelper;
import Util.LogType;
import Util.Message;
import Util.SocketReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ThreadSocket implements SocketReader
{
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
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
                writer = new ObjectOutputStream(socket.getOutputStream());
                reader = new ObjectInputStream(socket.getInputStream());
                networkThread = new NetworkThread(this,socket);
                networkThread.start();
            }
            catch(IOException e)
            {
                ClientHelper.log(LogType.ERROR, "Fehler beim Speichern der Clientattribute");
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
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
            networkThread = new NetworkThread(this,socket);
            networkThread.start();
        }
        catch(IOException e)
        {
            ClientHelper.log(LogType.ERROR, "Verbindung zu "+ip+":"+port+" ist fehlgeschlagen");
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
            ClientHelper.log(LogType.ERROR, "Kein Outputstream für " + ip + ":" + port + " vorhanden");
        }
        catch(IOException e)
        {
            ClientHelper.log(LogType.ERROR, "Fehler beim Senden einer Nachricht");
        }
    }

    public void receiveMessage(Message message)
    {
        ClientHelper.getNetworkManager().receiveMessage(message);
    }

    public boolean isConnected()
    {
        return !socket.isClosed();
    }

    public void close()
    {
        networkThread.close();
        System.gc();
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
                ClientHelper.log(LogType.ERROR, "Threadsocket implementiert das SocketReader-Interface nicht");
            }
        }

        public void run()
        {
            while(threadSocket != null && !socket.isClosed())
            {
                try{
                    Message message = (Message) reader.readObject();
                    message.setIp(socket.getInetAddress().getHostAddress());
                    message.setPort(socket.getPort());
                    threadSocket.receiveMessage(message);
                }
                catch(IOException e)
                {
                    ClientHelper.log(LogType.ERROR, "Fehler beim Empfangen einer Nachricht (IOException)");
                    close();
                }
                catch(ClassNotFoundException e)
                {
                    ClientHelper.log(LogType.ERROR, "Empfangene Nachricht hat nicht den Typ Message");
                }
            }
            ClientHelper.log(LogType.ERROR, "Verbindung zu " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " unterbrochen");
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
                ClientHelper.log(LogType.ERROR, "Socket konnte nicht geschlossen werden");
            }
        }
    }
}
