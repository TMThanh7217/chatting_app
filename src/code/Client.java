import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements Runnable, Serializable {
    public static int chatUIWidth = 800;
    public static int chatUIHeight = 450;
    public static int topMargin = chatUIWidth / 25;
    public static String host = "localhost";
    public static int port = 6000;
    public static String username = "";
    public static int fileSize = 10000;
    public static Socket socketClient;

    public static void setUsername(String username) {
        Client.username = username;
    }

    JPanel contentPanel = new JPanel();

    JTextArea chatScreen = new JTextArea();
    JScrollPane chatScreenSB = new JScrollPane(chatScreen);
    JPanel chatScreenPanel = new JPanel();

    JTextArea inputArea = new JTextArea();
    JScrollPane inputAreaSB = new JScrollPane(inputArea);
    JButton sendButton = new JButton("Send");
    JPanel inputAreaPanel = new JPanel();

    JTextField ipTf = new JTextField(host);
    JTextField portTf = new JTextField(Integer.toString(port));
    JPanel ipPanel = new JPanel();
    JPanel portPanel = new JPanel();

    JButton connectBtn = new JButton("Connect");
    JButton sendFileBtn = new JButton("Send file");

    JPanel infoPanel = new JPanel();
    JPanel connectBtnPanel = new JPanel();
    JPanel sendFileBtnPanel = new JPanel();

    JPanel centerPanel = new JPanel();
    JPanel rightPanel = new JPanel();

    BufferedWriter bfWriter;
    BufferedReader bfReader;
    OutputStream os;

    // Set up all necessary layout
    // Center panel store chat screen, input area and send button
    // Right panel store IP, Port information and connect button
    // Put every component inside a border layout then add them as center is easy to manage
    public void setupLayout() {
        chatScreen.setLineWrap(true); // Prevent word from overflowing to the left or right
        chatScreen.setEditable(false);
        inputArea.setLineWrap(true); // Prevent word from overflowing to the left or right

        chatScreenPanel.setLayout(new BorderLayout());
        inputAreaPanel.setLayout(new BorderLayout());

        centerPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ipTf.setEditable(false);
        //ipTf.setEnabled(false);

        //portTf.setEditable(false);
        ipPanel.setLayout(new BorderLayout());
        portPanel.setLayout(new BorderLayout());

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        connectBtnPanel.setLayout(new BorderLayout());
        sendFileBtn.setLayout(new BorderLayout());

        contentPanel.setLayout(new BorderLayout());  // content panel store every other panel
        this.setContentPane(contentPanel);
    }

    // Add components to each panel
    public void addComponents() {
        chatScreenPanel.add(chatScreenSB, BorderLayout.CENTER);
        chatScreenSB.setBorder(new EtchedBorder());
        chatScreenPanel.setBorder(new TitledBorder(new EmptyBorder(topMargin, chatUIWidth / 98, 0, chatUIWidth / 98), (String) username));

        inputAreaPanel.add(inputAreaSB, BorderLayout.CENTER);
        inputAreaSB.setBorder(new EtchedBorder());
        inputAreaPanel.add(sendButton, BorderLayout.EAST);
        inputAreaPanel.setBorder(new TitledBorder(new EmptyBorder(topMargin, chatUIWidth / 98, 10, chatUIWidth / 98), "Type your message here"));

        centerPanel.add(chatScreenPanel);
        centerPanel.add(inputAreaPanel, BorderLayout.SOUTH);

        ipPanel.add(ipTf, BorderLayout.CENTER);
        ipPanel.setBorder(new TitledBorder(new EmptyBorder(topMargin, 0, 0, chatUIWidth / 98), "IP"));
        portPanel.add(portTf);
        portPanel.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, chatUIWidth / 98), "Port"));

        infoPanel.add(ipPanel);
        infoPanel.add(portPanel);
        infoPanel.setBorder(new EmptyBorder(0, 0, chatUIHeight / 8, 0));

        connectBtnPanel.add(connectBtn, BorderLayout.CENTER);
        connectBtnPanel.setBorder(new EmptyBorder(0, 0, 0, chatUIWidth / 82));
        connectBtnPanel.setPreferredSize(new Dimension(chatUIWidth / 80, chatUIHeight / 8));

        sendFileBtnPanel.add(sendFileBtn, BorderLayout.CENTER);
        sendFileBtnPanel.setBorder(new EmptyBorder(0, 0, chatUIHeight / 2, chatUIWidth / 82));

        rightPanel.add(infoPanel);
        rightPanel.add(connectBtnPanel);
        rightPanel.add(sendFileBtnPanel);
        //rightPanel.setPreferredSize(new Dimension(chatUIWidth / 8, chatUIHeight));

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);

        //add event part
        //start thread when connect button is hit
        Client myClient = this;  //get current client
        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isInt(portTf.getText())) {
                    int myPort = Integer.parseInt(portTf.getText());
                    port = myPort;
                    //Open socket here
                    try {
                        port = myPort;
                        //System.out.println(port);
                        JOptionPane.showMessageDialog(myClient, "Connect to port " + port + " successfully");
                        socketClient = new Socket(host, port);
                        bfWriter = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
                        bfReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                        os = socketClient.getOutputStream();
                        Thread myThread = new Thread(myClient);
                        myThread.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(myClient, "Invalid port");
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String str = "user | " + username + "\n"+ inputArea.getText();
                try {
                    bfWriter.write(str);
                    bfWriter.write("\r\n");
                    bfWriter.flush();
                } catch(Exception e){
                    e.printStackTrace();
                }
                inputArea.setText("");
            }
        });

        sendFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(); //points to user's default directory
                int option = fc.showOpenDialog(null);
                // if user choose other option bfWriter will be null and throw exception so check it first
                if (option == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getAbsolutePath();
                    String str = "user | " + username + "\n" + path + "\r\n";
                    System.out.println("str: " + str);
                    System.out.println("path: " + path);

                    try {
                        File file = new File(path);  // create a file
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        byte[] contents;
                        long fileLength = file.length();

                        String fileClientString = Long.toString(fileLength);
                        System.out.println(fileClientString);
                        long current = 0;

                        byte[] strContent = str.getBytes();
                        os.write(strContent);

                        while(current != fileLength){
                            int size = fileSize;
                            if(fileLength - current >= size)
                                current += size;
                            else{
                                size = (int)(fileLength - current);
                                current = fileLength;
                            }
                            contents = new byte[size];
                            System.out.println("size: " + size);
                            System.out.println("current: " + current);
                            System.out.println("contetns: " + contents);
                            bis.read(contents, 0, size);
                            System.out.println("Pass read");
                            os.write(contents);
                            byte[] newLine = "\n".getBytes();
                            os.write(newLine);
                            System.out.println("Pass write");
                        }
                        bfWriter.flush();
                        os.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    Client(String myUsername) {
        Client.setUsername(myUsername);  // call this before adding any component
        setupLayout();
        addComponents();
        this.setTitle("Client");
        this.setVisible(true);
        this.setSize(chatUIWidth, chatUIHeight);
        this.setLocationRelativeTo(null); // set window open at the middle of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() {
        try {
            String msg = "";
            while ((msg = bfReader.readLine()) != null) {
                chatScreen.append(msg + "\n");
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

    public static void main(String[] args) {
        LoginForm lf = new LoginForm();
    }
}
