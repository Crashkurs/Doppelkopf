package Server.GUI.Login;

import Util.GuiCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginGui extends JPanel
{
    private JButton serverStart;
    private JTextField port;

    public LoginGui()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        port = new JTextField("3000");
        port.setPreferredSize(new Dimension(100, 30));
        add(port);

        serverStart = new JButton("Starte Server");
        serverStart.setPreferredSize(new Dimension(150, 30));
        serverStart.setActionCommand(GuiCommand.ServerStart.name());
        add(serverStart);
    }

    public void setActionListener(ActionListener al)
    {
        serverStart.addActionListener(al);
    }

    public int getPort()
    {
        return Integer.parseInt(port.getText());
    }
}
