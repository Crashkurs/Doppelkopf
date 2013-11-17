package Server.Network;

public interface NetworkManagerInterface
{
    public void connect(int port);
    public void sendMessage(String message, int index);
    public void sendMessageToAll(String message);
    public void receiveMessage(String message, String ip, int port);

}
