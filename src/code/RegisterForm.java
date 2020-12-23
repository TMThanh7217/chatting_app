import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RegisterForm extends JFrame implements ActionListener{
    public static int registerWindowWidth = 500;
    public static int registerWindowHeight = 280;
    public static int labelWidth = 70;
    public static int labelHeight = 30;
    public static int fieldWidth = registerWindowWidth + 800;
    public static int fieldHeight = 30;
    public static String user_data_path = "data/user_data.txt";

    JLabel nameLabel = new JLabel("Name");
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel confirmPwdLabel = new JLabel("Re-enter password");
    JTextField nameTextField = new JTextField();
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPwdField = new JPasswordField();
    JButton loginButton = new JButton("<html><span style='font-size:10px;color:red'>Login</span></html>");
    JButton resetButton = new JButton("Reset");
    JButton registerButton = new JButton("<html><span style='font-size:10px;color:blue'>Register</span></html>");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel title = new JLabel("<html><span style='font-size:20px;color:blue'>Register form</span></html>");
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
        nameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        nameLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        userLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        userLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        passwordLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        passwordLabel.setMaximumSize(new Dimension(labelWidth, labelHeight));
        confirmPwdLabel.setPreferredSize(new Dimension(labelWidth + 50, labelHeight));
        confirmPwdLabel.setMaximumSize(new Dimension(labelWidth + 50, labelHeight));
        nameTextField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        nameTextField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        userTextField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        userTextField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        passwordField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        confirmPwdField.setPreferredSize(new Dimension(fieldWidth - 100, fieldHeight));
        confirmPwdField.setMaximumSize(new Dimension(fieldWidth - 100, fieldHeight));
        showPwdPanel.setLayout(new FlowLayout());
        primaryPanel.setLayout(new FlowLayout());
        this.setContentPane(contentPanel);
    }

    public void addComponents() {
        contentPanel.add(title, BorderLayout.NORTH);
        labelPanel.setBorder(new EmptyBorder(5, 6, 2, 0));
        labelPanel.add(nameLabel);
        labelPanel.add(userLabel);
        labelPanel.add(passwordLabel);
        labelPanel.add(confirmPwdLabel);

        fieldPanel.setBorder(new EmptyBorder(5, 0, 2, 6));
        fieldPanel.add(nameTextField);
        fieldPanel.add(userTextField);
        fieldPanel.add(passwordField);
        fieldPanel.add(confirmPwdField);

        showPwdPanel.add(showPassword);
        btnPanel.add(loginButton);
        btnPanel.add(resetButton);
        primaryPanel.add(registerButton);
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
            JOptionPane.showMessageDialog(this, "Switch to login form");
            this.dispose();
            LoginForm lf = new LoginForm();
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
            String userText, pwdText, nameText;
            nameText = nameTextField.getText();
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            boolean userExist = checkUserExist(user_data_path, userText, pwdText);

            if (!userExist) {
                try {
                    FileWriter my_writer = new FileWriter(user_data_path, true);
                    System.out.println(nameText + userText + pwdText);
                    my_writer.write("\n" + nameText + "\n");
                    my_writer.write(userText + "\n");
                    my_writer.write(pwdText);
                    my_writer.close();
                } catch (IOException err) {
                    System.out.println(err.getMessage());
                    err.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Register Successful");
            }
            else {
                JOptionPane.showMessageDialog(this, "User already exist");
            }
        }
    }

    public boolean checkUserExist(String path, String userText, String pwdText) {
        boolean userExist = false;
        File file_in = new File(path);
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

       return userExist;
    }

    RegisterForm() {
        setupLayout();
        addComponents();
        addActionEvent();
        this.setTitle("Register form");
        this.setVisible(true);
        this.setSize(registerWindowWidth, registerWindowHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
