package Server.Controller;

import Server.GUI.Lobby.LobbyGui;
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
        switch(message.getType())
        {
            case GENERAL:
            {
                break;
            }

            case CLOSE:
            {
                Client client = ServerHelper.getNetworkManager().getClient(message.getIp(), message.getPort());
                ((LobbyGui)ServerHelper.getGuiManager().getGui()).removeClientFromLobby(client.getName());
                ServerHelper.getNetworkManager().clientClosed(message.getIp(), message.getPort());
                break;
            }

            case SENDNAME:
            {
                if(ServerHelper.getNetworkManager().getClient(message.getMessage()) == null)
                {
                    ServerHelper.getNetworkManager().getClient(message.getIp(), message.getPort()).setName(message.getMessage());
                    ((LobbyGui)ServerHelper.getGuiManager().getGui()).addClientToLobby(message.getMessage());
                }else{
                    Client client = ServerHelper.getNetworkManager().getClient(message.getIp(), message.getPort());
                    ServerHelper.getNetworkManager().sendMessage(new Message(MessageType.INVALIDNAME), client.getId());
                    ServerHelper.getNetworkManager().clientClosed(client);
                }
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
