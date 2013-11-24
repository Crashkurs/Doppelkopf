package Util;

import java.io.Serializable;
import java.util.HashMap;

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

    private HashMap<String, Object> param;

    public Message(MessageType _type, String _message)
    {
        type = _type;
        message = _message;
        param = new HashMap<String, Object>();
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

    public void addParam(String paramName, Object object)
    {
        if(!param.containsKey(paramName))
        {
            param.put(paramName, object);
        }
    }

    public Object getParam(String paramName) throws ParamNotDefinedException
    {
        if(param.containsKey(paramName))
        {
            return param.get(paramName);
        }else{
            throw new ParamNotDefinedException("Parameter " + paramName + " ist in der Message nicht gesetzt");
        }
    }
}
