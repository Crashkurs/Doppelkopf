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

    private String ip;
    private int port;

    public Message(MessageType _type, String _message)
    {
        type = _type;
        message = _message;
    }

    public Message(String incomingMessage, String ip, int port)
    {
        int separator = incomingMessage.indexOf("|");
        if(separator != -1)
        {
            try{
                int typeInt = Integer.parseInt(incomingMessage.substring(0,separator));
                type = MessageType.values()[typeInt];
                message = incomingMessage.substring(separator+1);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Ungültiger Nachrichtentyp spezifiziert");
            }
        }else{
            System.out.println("Fehler beim Initialisieren einer Nachricht: Kein Typ spezifiziert");
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
}
