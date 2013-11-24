package Server.Controller;

import Server.ServerUtil.ServerHelper;
import Util.*;
import Server.ServerUtil.Client;

public class Controller implements ControllerInterface
{

    public Controller()
    {

    }

    public void receiveMessage(Message message)
    {
        ServerHelper.log(LogType.CONTROLLER, "Empfange Nachricht [Typ: " + message.getType().name() + "]" + ": " + message.getMessage());
        ServerHelper.getNetworkManager().sendMessageToAll(new Message(MessageType.GENERAL , "Nachricht empfangen"));
        //Test für das Hinzufügen von Parametern bei einer Nachricht
        try
        {
            MessageType mt = (MessageType) message.getParam("testParam");
            ServerHelper.log(LogType.CONTROLLER, "Parameter testParam hat den Wert " + mt.name());
        } catch (ParamNotDefinedException e)
        {
            ServerHelper.log(LogType.ERROR, "Parameter testParam ist nicht gesetzt");
        }
        switch(message.getType())
        {
            case GENERAL:
            {
                break;
            }

            case CLOSE:
            {
                ServerHelper.getNetworkManager().clientClosed(message.getIp(), message.getPort());
                break;
            }

            case SENDNAME:
            {
                ServerHelper.getNetworkManager().getClient(message.getIp(), message.getPort()).setName(message.getMessage());
                break;
            }

            case JOINGAME:
            {
                break;
            }

            case JOINLOBBY:
            {
                break;
            }

            case LEAVEGAME:
            {
                break;
            }

            case LEAVELOBBY:
            {
                break;
            }

            case PLAYCARD:
            {
                Karte karte = new Karte(message.getMessage());
                Client client = ServerHelper.getNetworkManager().getClient(message.getIp(), message.getPort());
                client.legeKarte(karte);
                break;
            }
        }
    }
}
