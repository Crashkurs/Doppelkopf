package Util;

import java.io.Serializable;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 18.11.13
 * Klasse: MessageType
 */
public class Message implements Serializable
{
    private MessageType type;
    private String message;

    private String ip = "0.0.0.0";
    private int port = -1;

    public Message(MessageType _type, String _message)
    {
        type = _type;
        message = _message;
    }

    public String getMessage()
    {
        return message;
    }

    public String getFullMessage()
    {
        return type.ordinal() + "|" + message;
    }

    public MessageType getType()
    {
        return type;
    }

    public String getIp()
    {
        return ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setIp(String _ip)
    {
        ip = _ip;
    }

    public void setPort(int _port)
    {
        port = _port;
    }
}
