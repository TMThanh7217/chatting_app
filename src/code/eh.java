import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class eh extends JFrame implements Runnable {
    public static int chatUIWidth = 800;
    public static int chatUIHeight = 450;
    public static int topMargin = chatUIWidth / 25;
    public static int port = 5000;
    public static String username = "";

    public static void setUsername(String username) {
        eh.username = username;
    }

    JPanel contentPanel = new JPanel();

    JTextArea chatScreen = new JTextArea();
    JScrollPane chatScreenSB = new JScrollPane(chatScreen);
    JPanel chatScreenPanel = new JPanel();

    JTextArea inputArea = new JTextArea();
    JScrollPane inputAreaSB = new JScrollPane(inputArea);
    JButton sendButton = new JButton("Send");
    JPanel inputAreaPanel = new JPanel();

    JTextField ipTf = new JTextField("Local host");
    JTextField portTf = new JTextField();
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
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String str = username + "\n"+ inputArea.getText();
                System.out.print(str);
                try{
                    bfWriter.write(str);
                    bfWriter.write("\r\n");
                    bfWriter.flush();
                } catch(Exception e){
                    e.printStackTrace();
                }
                inputArea.setText("");
            }
        });
    }

    eh() {
        setupLayout();
        addComponents();
        this.setTitle("Chat UI");
        this.setVisible(true);
        this.setSize(chatUIWidth, chatUIHeight);
        this.setLocationRelativeTo(null); // set window open at the middle of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Open socket here
        try {
            Socket socketClient = new Socket("localhost", port);
            bfWriter = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            bfReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    eh(String myUsername) {
        eh.setUsername(myUsername);  // call this before adding any component
        setupLayout();
        addComponents();
        this.setTitle("Chat UI");
        this.setVisible(true);
        this.setSize(chatUIWidth, chatUIHeight);
        this.setLocationRelativeTo(null); // set window open at the middle of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Open socket here
        try {
            Socket socketClient = new Socket("localhost", port);
            bfWriter = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            bfReader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String msg = "";
            while((msg = bfReader.readLine()) != null){
                chatScreen.append(msg + "\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        eh a = new eh("aaaaa");
        Thread t1 = new Thread(a);
        t1.start();
    }
}
