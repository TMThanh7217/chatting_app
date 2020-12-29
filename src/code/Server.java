import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Server implements Runnable {
    public static int serverUIWidth = 400;
    public static int serverUIHeight = 150;
    public static int topMargin = serverUIWidth / 25;

    public static String host = "localhost";
    public static int port = 5000;
    public static Vector client = new Vector(); // hold all client
    Socket socket;

    static JFrame serverFrame = new JFrame();
    static JPanel contentPanel = new JPanel();

    static JTextField ipTf = new JTextField(host);
    static JTextField portTf = new JTextField();
    static JPanel ipPanel = new JPanel();
    static JPanel portPanel = new JPanel();

    static JPanel infoPanel = new JPanel();

    static JButton connectBtn = new JButton("Connect");
    static JPanel btnPanel = new JPanel();

    // Transfer file stuff
    private static final long serialVersionUID = 1L;

    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    private int piecesOfFile;
    private int lastByteLength;
    private byte[] dataBytes;
    private String status;
    public static void setupLayout() {
        ipTf.setEditable(false);
        //ipTf.setEnabled(false);
        ipPanel.setLayout(new BorderLayout());
        ipPanel.setPreferredSize(new Dimension(serverUIWidth / 3, serverUIHeight / 2 - serverUIHeight / 5));
        portPanel.setLayout(new BorderLayout());
        portPanel.setPreferredSize(new Dimension(serverUIWidth / 3, serverUIHeight / 2 - serverUIHeight / 5));

        infoPanel.setLayout(new FlowLayout());
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setPreferredSize(new Dimension(serverUIWidth / 2 - serverUIWidth / 4, serverUIWidth / 2 - serverUIWidth / 4));
        
        contentPanel.setLayout(new FlowLayout());  // content panel store every other panel
        serverFrame.setContentPane(contentPanel);
    }

    public static void addComponents() {
        ipPanel.add(ipTf, BorderLayout.CENTER);
        ipPanel.setBorder(new TitledBorder(new EmptyBorder(topMargin, 0, 0, serverUIWidth / 98), "IP"));
        portPanel.add(portTf, BorderLayout.CENTER);
        portPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, serverUIWidth / 98), "Port"));

        infoPanel.add(ipPanel);
        infoPanel.add(portPanel);
        btnPanel.add(connectBtn);

        contentPanel.add(infoPanel);
        contentPanel.add(btnPanel);
        contentPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Server"));

        //add event part

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isInt(portTf.getText())) {
                    int myport = parseInt(portTf.getText());
                    port = myport;
                    serverFrame.dispose();
                    if (port > 2000) {
                        JOptionPane.showMessageDialog(serverFrame, "Connect to port " + port + " successfully");
                        ServerSocket ss = null;
                        try {
                            ss = new ServerSocket(port);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //System.out.println(port);
                        while (true) {
                            Socket socket = null;
                            try {
                                socket = ss.accept();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Server server = new Server(socket);
                            Thread thread = new Thread(server);
                            thread.start();
                        }
                    } else {
                        JOptionPane.showMessageDialog(serverFrame, "Try another port that is greater than 500");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(serverFrame, "Invalid port");
                }
            }
        });
    }

    public static void serverGUI() {
        setupLayout();
        addComponents();
        serverFrame.setTitle("Server");
        serverFrame.setVisible(true);
        serverFrame.setSize(serverUIWidth, serverUIHeight);
        serverFrame.setLocationRelativeTo(null); // set window open at the middle of the screen
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    Server(Socket socket) {
        try {
            this.socket = socket;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader bfReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bfWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            client.add(bfWriter);

            while(true) {
                String data = bfReader.readLine().trim();
                //System.out.println("Received " + data);

                // write to all client
                for(int i = 0; i < client.size(); i++) {
                    try {
                        BufferedWriter bw = (BufferedWriter)client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //misc function
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        serverGUI();
    }
}