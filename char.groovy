// this is the server class
// creates the first text box to communicate.
// when typing in it, the text doesn't send to the other box. vice versa

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class chat extends JFrame
{   
    private JTextArea chatter1 = new JTextArea();
    private JTextArea chatter2 = new JTextArea();
    private PrintWriter output;

    private ServerSocket serverSocket;

    public static void main(String[] args)
    {
        new chat();
    }

    public chat()
    {
        setLayout(new GridLayout(3, 2));

        JScrollPane firstBox = new JScrollPane(chatter1);
        JScrollPane secondBox = new JScrollPane(chatter2);

        firstBox.setBorder(new TitledBorder("Superman"));
        secondBox.setBorder(new TitledBorder("Batman"));

        add(firstBox, BorderLayout.CENTER);
        add(secondBox, BorderLayout.CENTER);

        chatter1.setWrapStyleWord(true);
        chatter1.setLineWrap(true);
        chatter2.setWrapStyleWord(true);
        chatter2.setLineWrap(true);

        setTitle("Batman V Superman");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try
        {
            serverSocket = new ServerSocket(8000);
            Socket connectToSide = serverSocket.accept();
            output = new PrintWriter(connectToSide.getOutputStream());
            new SendThread(connectToSide).start();
            new ReceiveThread(connectToSide).start();   
        }
        catch(IOException ex)
        {
            System.err.println(ex);
        }

        chatter1.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent a)
            {
                if(a.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    output.print(chatter1.getText());
                }
            }
        }
        );
    }

    class SendThread extends Thread
    {
        SendThread(Socket socket)
        {

        }
    }

    class ReceiveThread extends Thread
    {
        ReceiveThread(Socket socket)
        {

        }
    }

}

// this is the client box 
// this class is for the second box that creates a box to talk to the main.


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class chat2 extends JFrame
{
    private JTextArea chatter2 = new JTextArea ();
    private JTextArea chatter1 = new JTextArea ();
    private PrintWriter output;

    public static void main(String[] args)
    {
        new chat2();    
    }

    public chat2()
    {
        setLayout(new GridLayout(2, 2));

        JScrollPane firstBox = new JScrollPane(chatter1);
        JScrollPane secondBox = new JScrollPane(chatter2);

        firstBox.setBorder(new TitledBorder("Batman"));
        secondBox.setBorder(new TitledBorder("Superman"));

        add(firstBox, BorderLayout.CENTER);
        add(secondBox, BorderLayout.CENTER);

        chatter2.setWrapStyleWord(true);
        chatter2.setLineWrap(true);
        chatter1.setWrapStyleWord(true);
        chatter1.setLineWrap(true);

        setTitle("Superman V Batman");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try
        {
            Socket connectToServer = new Socket ("localhost", 8000);
            output = new PrintWriter(connectToServer.getOutputStream());
            new SendThread(connectToServer).start();
            new ReceiveThread(connectToServer).start();
        }
        catch(IOException ex)
        {
            System.err.println(ex);
        }

        chatter1.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    output.print(chatter1.getText());
                }
            }
        }
        );
    }


    class SendThread extends Thread
    {
        SendThread(Socket socket)
        {

        }
    }

    class ReceiveThread extends Thread
    {
        ReceiveThread(Socket socket)
        {

        }
    }
}