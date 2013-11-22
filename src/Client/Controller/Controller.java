package Client.Controller;

import Client.ClientUtil.ClientHelper;
import Util.Karte;
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
        ClientHelper.log(LogType.CONTROLLER, "Empfange Nachricht [Typ: " + message.getType().name() + "]" + ": " + message.getMessage());
    }

    public void legeKarte(Karte karte)
    {
        ClientHelper.getNetworkManager().sendMessage(new Message(MessageType.PLAYCARD,karte.toString()));
    }
}
