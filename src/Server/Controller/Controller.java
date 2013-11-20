package Server.Controller;

import Server.ServerUtil.ServerHelper;
import Util.LogType;
import Util.Message;
import Util.MessageType;

public class Controller implements ControllerInterface
{

    public Controller()
    {

    }

    public void receiveMessage(Message message)
    {
        ServerHelper.log(LogType.CONTROLLER, "Empfange Nachricht [Typ: " + message.getType().name() + "]" + ": " + message.getMessage());
        ServerHelper.getNetworkManager().sendMessageToAll(new Message(MessageType.GENERAL , "Nachricht empfangen"));

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
        }
    }
}
