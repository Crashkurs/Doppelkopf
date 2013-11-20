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
        ServerHelper.getNetworkManager().sendMessageToAll(new Message(MessageType.SENDNAME , "Nachricht empfangen"));
    }
}
