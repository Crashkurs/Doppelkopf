package Util;

public interface SocketReader
{
    public void sendMessage(String message);
    public void receiveMessage(String message, String ip, int port);
}
