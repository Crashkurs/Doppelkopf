package Server.Controller;

public class Controller implements ControllerInterface
{

    public Controller()
    {

    }

    public void receiveMessage(String message)
    {
        System.out.println("Empfange Nachricht: " + message);
    }
}
