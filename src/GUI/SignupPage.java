package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import DatabaseConnection.UserDAO;

public class SignupPage extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passField;

    public SignupPage() {
        setTitle("Sign Up Form");
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

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(90, 80, 200, 25);
        nameLabel.setForeground(Color.WHITE);
        rightPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(90, 105, 250, 30);
        rightPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(90, 150, 200, 25);
        emailLabel.setForeground(Color.WHITE);
        rightPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(90, 175, 250, 30);
        rightPanel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(90, 220, 200, 25);
        passLabel.setForeground(Color.WHITE);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(90, 245, 250, 30);
        rightPanel.add(passField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(90, 295, 250, 35);
        signupButton.setBackground(new Color(120, 80, 200));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(signupButton);

        JButton loginLink = new JButton("Already have an account? Login");
        loginLink.setBounds(90, 340, 250, 25);
        loginLink.setForeground(Color.LIGHT_GRAY);
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLink.setContentAreaFilled(false);
        loginLink.setBorderPainted(false);
        rightPanel.add(loginLink);

        add(rightPanel);

        setLocationRelativeTo(null);
        setVisible(true);

        // ---------- ACTIONS ----------
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }

                UserDAO userDAO = new UserDAO();
                boolean success = userDAO.registerUser(name, email, password);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                    dispose();
                    new LoginPage(); // go to login after signup
                } else {
                    JOptionPane.showMessageDialog(null, "Signup failed! Email might already exist or DB error.");
                }
            }
        });

        loginLink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage();
            }
        });
    }

    public static void main(String[] args) {
        new SignupPage();
    }
}
