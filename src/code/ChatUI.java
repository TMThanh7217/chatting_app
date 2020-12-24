import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class ChatUI extends JFrame{
    public static int chatUIWidth = 800;
    public static int chatUIHeight = 450;
    public static int topMargin = chatUIWidth / 25;

    JPanel contentPanel = new JPanel();

    JTextArea chatScreen = new JTextArea();
    JScrollPane chatScreenSB = new JScrollPane(chatScreen);
    JPanel chatScreenPanel = new JPanel();

    JTextArea inputArea = new JTextArea();
    JScrollPane inputAreaSB = new JScrollPane(inputArea);
    JButton sendButton = new JButton("Send");
    JPanel inputAreaPanel = new JPanel();

    JTextField ipTf = new JTextField();
    JTextField portTf = new JTextField();
    JPanel ipPanel = new JPanel();
    JPanel portPanel = new JPanel();

    JButton connectBtn = new JButton("Connect");

    JPanel infoPanel = new JPanel();
    JPanel rightPanelBtn = new JPanel();

    JPanel centerPanel = new JPanel();
    JPanel rightPanel = new JPanel();


    // Set up all necessary layout
    // Center panel store chat screen, input area and send button
    // Right panel store IP, Port information and connect button
    // Put every component inside a border layout then add them as center is easy to manage
    public void setupLayout() {
        chatScreen.setLineWrap(true); // Prevent word from overflowing to the left or right
        inputArea.setLineWrap(true); // Prevent word from overflowing to the left or right

        chatScreenPanel.setLayout(new BorderLayout());
        inputAreaPanel.setLayout(new BorderLayout());

        centerPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ipPanel.setLayout(new BorderLayout());
        portPanel.setLayout(new BorderLayout());

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        rightPanelBtn.setLayout(new BoxLayout(rightPanelBtn, BoxLayout.Y_AXIS));

        contentPanel.setLayout(new BorderLayout());  // content panel store every other panel
        this.setContentPane(contentPanel);
    }

    // Add components to each panel
    public void addComponents() {
        chatScreenPanel.add(chatScreenSB, BorderLayout.CENTER);
        chatScreenSB.setBorder(new EtchedBorder());
        chatScreenPanel.setBorder(new TitledBorder(new EmptyBorder(topMargin, chatUIWidth / 98, 0, chatUIWidth / 98), "Username"));

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

        rightPanelBtn.add(connectBtn);
        rightPanelBtn.setBorder(new EmptyBorder(0, 0, chatUIHeight / 2, chatUIWidth / 82));

        rightPanel.add(infoPanel);
        rightPanel.add(rightPanelBtn);
        rightPanel.setPreferredSize(new Dimension(chatUIWidth / 8, chatUIHeight));

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
    }

    ChatUI() {
        setupLayout();
        addComponents();
        this.setTitle("Chat UI");
        this.setVisible(true);
        this.setSize(chatUIWidth, chatUIHeight);
        this.setLocationRelativeTo(null); // set window open at the middle of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
