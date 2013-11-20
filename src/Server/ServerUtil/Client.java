package Server.ServerUtil;

import Util.Karte;
import Util.LogType;

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

    private ThreadSocket socket;

    private ArrayList<Karte> hand;

    public Client()
    {
        hand = new ArrayList<Karte>();
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
    }

    public Client(String _name, Socket _socket)
    {
        this(_name);
        socket = new ThreadSocket(_socket);
    }

    public Client(String _name, String _ip, int _port)
    {
        this(_name);
        socket = new ThreadSocket();
        socket.connect(_ip, _port);
        ip = _ip;
        port = _port;
    }

    public void setName(String _name)
    {
        name = _name;
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
            ServerHelper.log(LogType.SERVERUTIL, "Fehler: Client " + name + "(" + ip + ":" + port + ") hat eine nicht vorhandene Karte (" + karte.getFarbe() + "|" + karte.getTyp() + ") gelegt.");
        }
    }

    public void sendMessage(String message)
    {
        if(socket.isConnected())
        {
            socket.sendMessage(message);
        }else{
            ServerHelper.log(LogType.SERVERUTIL, "Konnte Nachricht nicht senden, da Socket geschlossen ist");
            ServerHelper.getNetworkManager().clientClosed(this);
        }
    }

}
