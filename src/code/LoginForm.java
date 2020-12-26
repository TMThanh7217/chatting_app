import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class LoginForm extends JFrame implements ActionListener{
    public static int loginWindowWidth = 450;
    public static int loginWindowHeight = 250;
    public static int labelWidth = 70;
    public static int labelHeight = 30;
    public static int fieldWidth = loginWindowWidth + 800;
    public static int fieldHeight = 30;
    public static String user_data_path = "data/user_data.txt";

    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("<html><span style='font-size:10px;color:red'>Login</span></html>");
    JButton resetButton = new JButton("Reset");
    JButton registerButton = new JButton("<html><span style='font-size:10px;color:blue'>Register</span></html>");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel title = new JLabel("<html><span style='font-size:20px;color:red'>Login form</span></html>");
    JPanel labelPanel = new JPanel();
    JPanel fieldPanel = new JPanel();
    JPanel contentPanel = new JPanel();
    JPanel misc = new JPanel();
    JPanel btnPanel = new JPanel();
    JPanel showPwdPanel = new JPanel();
    JPanel primaryPanel = new JPanel();

    public void setupLayout() {
        title.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.setLayout(new BorderLayout());
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        misc.setLayout(new BoxLayout(misc, BoxLayout.Y_AXIS));
        btnPanel.setLayout(new FlowLayout());
        userLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        userLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        passwordLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        passwordLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        userTextField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        userTextField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        showPwdPanel.setLayout(new FlowLayout());
        primaryPanel.setLayout(new FlowLayout());
        this.setContentPane(contentPanel);
    }

    public void addComponents() {
        contentPanel.add(title, BorderLayout.NORTH);
        labelPanel.setBorder(new EmptyBorder(5, 6, 2, 0));
        labelPanel.add(userLabel);
        labelPanel.add(passwordLabel);
        fieldPanel.setBorder(new EmptyBorder(5, 0, 2, 6));
        fieldPanel.add(userTextField);
        fieldPanel.add(passwordField);

        showPwdPanel.add(showPassword);
        btnPanel.add(registerButton);
        btnPanel.add(resetButton);
        primaryPanel.add(loginButton);
        misc.add(showPwdPanel);
        misc.add(btnPanel);
        misc.add(primaryPanel);

        contentPanel.add(labelPanel, BorderLayout.WEST);
        contentPanel.add(fieldPanel, BorderLayout.CENTER);
        contentPanel.add(misc, BorderLayout.SOUTH);
    }

    public void addActionEvent() {
        //adding Action listener to components
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Login button
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            boolean userExist = false;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            File file_in = new File(user_data_path);
            ArrayList<String> user_data = new ArrayList();
            if (file_in.exists()) {
                try {
                    Scanner sc = new Scanner(file_in);
                    while (sc.hasNextLine()) {
                        String tmp = sc.nextLine();
                        user_data.add(tmp);
                    }
                } catch (FileNotFoundException err) {
                    System.out.println(err.getMessage());
                    err.printStackTrace();
                }
            } else {
                System.out.println("The file does not exist.");
            }

            for (int i = 0; i < user_data.size() - 2; i += 3) {
                //System.out.println(user_data.get(i) + "\n" + user_data.get(i + 1) + "\n-----------------------------");
                if (userText.equals(user_data.get(i + 1)) && pwdText.equals(user_data.get(i + 2))) {
                    userExist = true;
                    break;
                }
            }
            if (userExist) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                this.dispose();
                ChatUI myChatUI = new ChatUI(userTextField.getText());
                Thread myThread = new Thread(myChatUI);
                myThread.start();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        }
        //Reset button
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        //showPassword
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if (e.getSource() == registerButton) {
            JOptionPane.showMessageDialog(this, "Switch to register window");
            this.dispose();
            RegisterForm rg = new RegisterForm();
        }
    }

    LoginForm() {
        setupLayout();
        addComponents();
        addActionEvent();
        this.setTitle("Login form");
        this.setVisible(true);
        this.setSize(loginWindowWidth, loginWindowHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
