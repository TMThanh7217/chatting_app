import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatUI extends JFrame{
    public static int chatUIWidth = 500;
    public static int chatUIHeight = 500;
    public static int chatScreendWidth = 500;
    public static int chatScreenHeight = 450;
    public static int inputAreaWidth = 400;
    public static int inputAreaHeight = 40;

    JTextField chatScreen = new JTextField();
    JButton sendButton = new JButton("Send");
    JTextField inputArea = new JTextField();
    JPanel contentPanel = new JPanel();
    JPanel misc = new JPanel();

    public void setupLayout() {
        contentPanel.setLayout(new BorderLayout());
        misc.setLayout(new FlowLayout());
        chatScreen.setPreferredSize(new Dimension(chatScreendWidth, chatScreenHeight));
        chatScreen.setMaximumSize(new Dimension(chatScreendWidth, chatScreenHeight));
        inputArea.setPreferredSize(new Dimension(inputAreaWidth, inputAreaHeight));
        inputArea.setMaximumSize(new Dimension(inputAreaWidth, inputAreaHeight));
    }

    public void addComponents() {
        contentPanel.add(chatScreen, BorderLayout.CENTER);
        misc.add(inputArea);
        misc.add(sendButton);
        contentPanel.add(misc, BorderLayout.SOUTH);
        this.setContentPane(contentPanel);
    }

    ChatUI() {
        setupLayout();
        addComponents();
        this.setTitle("Chat UI");
        this.setVisible(true);
        this.setSize(chatUIWidth, chatUIHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ChatUI tmp = new ChatUI();
    }
}
