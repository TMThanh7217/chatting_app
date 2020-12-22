import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {
    //Container container=getContentPane();
    JLabel userLabel=new JLabel("USERNAME");
    JLabel passwordLabel=new JLabel("PASSWORD");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("LOGIN");
    JButton resetButton=new JButton("RESET");
    JCheckBox showPassword=new JCheckBox("Show Password");
    JPanel textPanel = new JPanel();
    JPanel contentPanel = new JPanel();
    JPanel misc = new JPanel();
    JLabel title = new JLabel("a");

    test() {
        setLayoutManager();
        //setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();//calling addActionEvent() method

    }

    public void setLayoutManager() {
        //container.setLayout(null);
        this.setLayout(new BorderLayout());
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        misc.setLayout(new FlowLayout());
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50,100,100,30);
        passwordLabel.setBounds(50,140,100,30);
        userTextField.setBounds(150,100,150,30);
        passwordField.setBounds(150,140,150,30);
        showPassword.setBounds(150,170,150,30);
        loginButton.setBounds(50,220,100,30);
        resetButton.setBounds(200,220,100,30);
    }

    public void addComponentsToContainer()
    {
        /*container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);*/
        this.add(title, BorderLayout.NORTH);
        textPanel.add(userLabel);
        textPanel.add(passwordLabel);
        this.add(textPanel, BorderLayout.WEST);


        contentPanel.add(userTextField);
        contentPanel.add(passwordField);
        this.add(contentPanel, BorderLayout.EAST);

        misc.add(showPassword);
        misc.add(loginButton);
        misc.add(resetButton);
        this.add(misc, BorderLayout.SOUTH);
    }

    public void addActionEvent() {
        //adding Action listener to components
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
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
        //Coding Part of RESET button
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    public static void main(String[] a){
        test frame=new test();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(300,200,370,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
    }
}



