import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener{
    public static int loginWindowWidth = 450;
    public static int loginWindowHeight = 250;
    public static int labelWidth = 70;
    public static int labelHeight = 30;
    public static int fieldWidth = loginWindowWidth + 800;
    public static int fieldHeight = 30;

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
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            if (userText.equalsIgnoreCase("a") && pwdText.equalsIgnoreCase("a")) {
                JOptionPane.showMessageDialog(this, "Login Successful");
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
