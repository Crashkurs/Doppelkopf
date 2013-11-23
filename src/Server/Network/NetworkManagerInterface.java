package Server.Network;

import Util.Message;

public interface NetworkManagerInterface
{
    public void connect(int port);
    public void sendMessage(Message message, int index);
    public void sendMessageToAll(String message);
    public void sendMessageToAll(Message message);
    public void receiveMessage(Message message);
}
