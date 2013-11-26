package Server.ServerUtil;

import Server.Network.ThreadSocket;
import Util.Karte;
import Util.LogType;
import Util.Message;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 18.11.13
 * Klasse: Client
 */
public class Client
{
    private String name;
    private String ip;
    private int port;
    private int id;

    private ThreadSocket socket;

    private ArrayList<Karte> hand;

    public Client()
    {
        hand = new ArrayList<Karte>();
        ServerHelper.log(LogType.SERVERUTIL, "Client erstellt");
    }

    public Client(String _name)
    {
        this();
        name = _name;
    }

    public Client(String _name, ThreadSocket _socket)
    {
        this(_name);
        socket = _socket;
        ip = socket.getIp();
        port = socket.getPort();
        socket.setClient(this);
    }

    public Client(String _name, Socket _socket)
    {
        this(_name);
        socket = new ThreadSocket(_socket);
        socket.setClient(this);
    }

    public Client(String _name, String _ip, int _port)
    {
        this(_name);
        socket = new ThreadSocket();
        socket.connect(_ip, _port);
        ip = _ip;
        port = _port;
        socket.setClient(this);
    }

    public void setName(String _name)
    {
        ServerHelper.log(LogType.SERVERUTIL, "Client " + ip + ":" + port + " hat jetzt den Namen " + _name);
        name = _name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int _id)
    {
        id = _id;
    }

    public String getName()
    {
        return name;
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
        return socket.isConnected();
    }

    public void addKarte(Karte karte)
    {
        hand.add(karte);
    }

    public void legeKarte(Karte karte)
    {
        if(hand.contains(karte))
        {
            hand.remove(karte);
        }else{
            ServerHelper.log(LogType.ERROR, "Client " + name + "(" + ip + ":" + port + ") hat eine nicht vorhandene Karte " + karte.toString() + " gelegt.");
        }
    }

    public void sendMessage(Message message)
    {
        if(socket.isConnected())
        {
            socket.sendMessage(message);
        }else{
            ServerHelper.log(LogType.ERROR, "Konnte Nachricht nicht senden, da Socket geschlossen ist");
            ServerHelper.getNetworkManager().clientClosed(this);
        }
    }

    public void closeClient()
    {
        ServerHelper.getNetworkManager().clientClosed(this);
    }

    public void close()
    {
        socket.close();
    }

}
