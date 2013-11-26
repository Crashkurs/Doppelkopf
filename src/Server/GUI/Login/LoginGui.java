package Server.GUI.Login;

import Server.GUI.GuiManager;
import Util.GuiCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginGui extends JPanel
{
    private JButton serverStart, serverStop;
    private JTextField port;

    public LoginGui()
    {
        super();
        setLayout(new GridLayout());

        port = new JTextField("3000");
        port.setMinimumSize(new Dimension(100, 30));
        add(port);

        serverStart = new JButton("Starte Server");
        serverStart.setActionCommand(GuiCommand.ServerStart.name());
        serverStart.setVisible(true);
        add(serverStart);

        serverStop = new JButton("Stoppe Server");
        serverStop.setActionCommand(GuiCommand.ServerStop.name());
        serverStop.setVisible(false);
        add(serverStop);
    }

    public void setActionListener(ActionListener al)
    {
        serverStart.addActionListener(al);
        serverStop.addActionListener(al);
    }

    public void switchStartStop()
    {
        boolean startVisible = serverStart.isVisible();
        serverStart.setVisible(!startVisible);
        serverStop.setVisible(startVisible);
    }

    public int getPort()
    {
        return Integer.parseInt(port.getText());
    }
}
