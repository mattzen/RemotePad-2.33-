// 
// Decompiled by Procyon v0.5.36
// 

package mouseserv;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.MouseInfo;
import java.awt.AWTException;
import java.awt.Component;
import java.net.UnknownHostException;
import java.net.InetAddress;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.ServerSocket;
import java.awt.event.ActionListener;

public class ListeningServer implements ActionListener
{
    ServerSocket providerSocket;
    Socket connection;
    static DatagramSocket socket;
    static DatagramPacket in;
    byte[] buffer;
    String message;
    Robot r;
    int x;
    int y;
    int deltax;
    int deltay;
    PointerInfo a;
    Point b;
    boolean forx;
    static int port;
    JButton button;
    JTextField txt;
    JFrame frame;
    boolean running;
    InetAddress addr;
    static boolean drag;
    
    static {
        ListeningServer.port = 51315;
        ListeningServer.drag = false;
    }
    
    public ListeningServer() {
        this.connection = null;
        this.x = 0;
        this.y = 0;
        this.deltax = 0;
        this.deltay = 0;
        this.forx = true;
        this.running = true;
        try {
            this.addr = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.frame = new JFrame("Server running on ip: " + this.addr.getHostAddress().toString() + " and port: " + ListeningServer.port);
        (this.button = new JButton("Disconnect")).addActionListener(this);
        this.frame.add(this.button);
        this.frame.setSize(600, 100);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(3);
    }
    
    void run(final int p) {
        try {
            ListeningServer.socket = new DatagramSocket(p);
            System.out.println("Waiting for connection");
            do {
                this.buffer = new byte[16];
                ListeningServer.in = new DatagramPacket(this.buffer, this.buffer.length);
                ListeningServer.socket.receive(ListeningServer.in);
                this.buffer = ListeningServer.in.getData();
                this.message = new String(ListeningServer.in.getData());
                this.message = this.message.substring(0, ListeningServer.in.getLength());
                System.out.println("client>" + this.message);
                try {
                    this.r = new Robot();
                }
                catch (AWTException e1) {
                    e1.printStackTrace();
                }
                if (this.message.equals("rc")) {
                    this.r.mousePress(4);
                    this.r.mouseRelease(4);
                }
                else if (this.message.equals("slc")) {
                    this.r.mousePress(16);
                }
                else if (this.message.equals("lc")) {
                    this.r.mousePress(16);
                    this.r.mouseRelease(16);
                }
                else if (this.message.contains("dr:")) {
                    char[] ar;
                    int i;
                    for (ar = this.message.toCharArray(), i = 3; ar[i] != ','; ++i) {}
                    this.deltax = Integer.parseInt(this.message.substring(3, i));
                    this.deltay = Integer.parseInt(this.message.substring(i + 1, ListeningServer.in.getLength()));
                    System.out.println("x: " + this.x + " , y: " + this.y);
                    this.a = MouseInfo.getPointerInfo();
                    this.b = this.a.getLocation();
                    this.x = (int)this.b.getX();
                    this.y = (int)this.b.getY();
                    this.x += this.deltax;
                    this.y += this.deltay;
                    this.r.mouseMove(this.x, this.y);
                }
                else if (this.message.contains("sc")) {
                    final int scroll = Integer.parseInt(this.message.subSequence(3, this.message.length()).toString());
                    this.r.mouseWheel(scroll / 3);
                }
                else if (this.message.equals("drC")) {
                    this.r.mouseRelease(16);
                }
                else if (this.message.equals("bc")) {
                    this.r.keyPress(8);
                    this.r.keyRelease(8);
                }
                else if (this.message.equals("fc")) {
                    this.r.keyPress(16);
                    this.r.keyPress(8);
                    this.r.keyRelease(16);
                    this.r.keyRelease(8);
                }
                else {
                    char[] ar;
                    int i;
                    for (ar = this.message.toCharArray(), i = 0; ar[i] != ','; ++i) {}
                    this.deltax = Integer.parseInt(this.message.substring(0, i));
                    this.deltay = Integer.parseInt(this.message.substring(i + 1, ListeningServer.in.getLength()));
                    System.out.println("x: " + this.x + " , y: " + this.y);
                    this.a = MouseInfo.getPointerInfo();
                    this.b = this.a.getLocation();
                    this.x = (int)this.b.getX();
                    this.y = (int)this.b.getY();
                    this.x += this.deltax;
                    this.y += this.deltay;
                    this.r.mouseMove(this.x, this.y);
                }
            } while (this.running);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        ListeningServer.socket.close();
    }
    
    public static void main(final String[] args) {
        if (args.length > 0) {
            ListeningServer.port = Integer.parseInt(args[0]);
            final ListeningServer server = new ListeningServer();
            while (true) {
                server.run(Integer.parseInt(args[0]));
            }
        }
        else {
            final ListeningServer server = new ListeningServer();
            while (true) {
                server.run(ListeningServer.port);
            }
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        this.running = false;
        System.exit(0);
    }
}