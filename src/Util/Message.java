package Util;

/**
 * Doppelkopf-Projekt
 * Benutzer: Mats
 * Datum: 18.11.13
 * Klasse: MessageType
 */
public class Message
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

    public Message(String incomingMessage, String _ip, int _port)
    {
        int separator = incomingMessage.indexOf("|");
        ip = _ip;
        port = _port;
        if(separator != -1)
        {
            try{
                int typeInt = Integer.parseInt(incomingMessage.substring(0,separator));
                type = MessageType.values()[typeInt];
                message = incomingMessage.substring(separator+1);
            }
            catch(NumberFormatException e)
            {
                DokoHelper.log(LogType.UTIL, "Ungültiger Nachrichtentyp spezifiziert");
            }
        }else{
            DokoHelper.log(LogType.UTIL, "Fehler beim Initialisieren einer Nachricht: Kein Typ spezifiziert");
        }
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
}
