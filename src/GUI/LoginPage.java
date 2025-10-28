package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DatabaseConnection.UserDAO;

public class LoginPage extends JFrame {
    private JTextField emailField;
    private JPasswordField passField;

    public LoginPage() {
        setTitle("Login Form");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 380, 450);
        leftPanel.setBackground(new Color(40, 40, 40));
        leftPanel.setLayout(null);

        JLabel title = new JLabel("MealIO", SwingConstants.CENTER);
        title.setBounds(50, 150, 280, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        leftPanel.add(title);

        JLabel tagline = new JLabel("Get popular recipes with one click!!!", SwingConstants.CENTER);
        tagline.setBounds(50, 200, 280, 30);
        tagline.setForeground(Color.LIGHT_GRAY);
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftPanel.add(tagline);

        add(leftPanel);

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(380, 0, 420, 450);
        rightPanel.setBackground(new Color(25, 25, 25));
        rightPanel.setLayout(null);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(90, 120, 200, 25);
        emailLabel.setForeground(Color.WHITE);
        rightPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(90, 145, 250, 30);
        rightPanel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(90, 190, 200, 25);
        passLabel.setForeground(Color.WHITE);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(90, 215, 250, 30);
        rightPanel.add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(90, 265, 250, 35);
        loginButton.setBackground(new Color(120, 80, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(loginButton);

        JButton signupLink = new JButton("Don't have an account? Sign Up");
        signupLink.setBounds(90, 315, 250, 25);
        signupLink.setForeground(Color.LIGHT_GRAY);
        signupLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        signupLink.setContentAreaFilled(false);
        signupLink.setBorderPainted(false);
        rightPanel.add(signupLink);

        add(rightPanel);

        setLocationRelativeTo(null);
        setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }


                UserDAO userDAO = new UserDAO();
                boolean isValid = userDAO.validateUser(email, password);

                if (isValid) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new DashboardPage(); // go to dashboard
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password!");
                }
            }
        });

        signupLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignupPage();
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
