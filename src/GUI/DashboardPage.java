package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import AICodes.AIRefiner;
import AICodes.GetRecipe;
import DatabaseConnection.DatabaseManager;

public class DashboardPage extends JFrame {

    private JTextArea ingField;
    private JButton generateBtn;
    private JLabel statusLabel;

    public DashboardPage() {

        setTitle("AI Recipe Generator");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);


        JPanel header = new JPanel();
        header.setBounds(0, 0, 900, 60);
        header.setBackground(new Color(40, 40, 40));
        header.setLayout(null);

        JLabel title = new JLabel(" AI Recipe Generator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(20, 15, 400, 30);
        header.add(title);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(770, 15, 100, 30);
        logoutBtn.setBackground(new Color(180, 70, 70));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                JOptionPane.showMessageDialog(null, "Logged out successfully!");
                new LoginPage();
            }
        });

        header.add(logoutBtn);
        add(header);


        
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 60, 900, 440);
        mainPanel.setBackground(new Color(25, 25, 25));
        mainPanel.setLayout(null);

        JLabel ingLabel = new JLabel("Enter Ingredients:");
        ingLabel.setBounds(100, 80, 200, 25);
        ingLabel.setForeground(Color.WHITE);
        ingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        mainPanel.add(ingLabel);

        ingField = new JTextArea();
        ingField.setBounds(100, 110, 400, 80);
        ingField.setBackground(new Color(40, 40, 40));
        ingField.setForeground(Color.WHITE);
        ingField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ingField.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        mainPanel.add(ingField);

        generateBtn = new JButton("Generate Recipe");
        generateBtn.setBounds(100, 210, 200, 35);
        generateBtn.setBackground(new Color(120, 80, 200));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateBtn.setFocusPainted(false);
        generateBtn.setBorderPainted(false);
        mainPanel.add(generateBtn);

        statusLabel = new JLabel("");
        statusLabel.setBounds(100, 260, 600, 25);
        statusLabel.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(statusLabel);

        add(mainPanel);
        setVisible(true);

        
        
        generateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingredients = ingField.getText().trim();
                if (ingredients.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter some ingredients first!");
                } else {
                    generateRecipe(ingredients);
                }
            }
        });
    }

    private void generateRecipe(final String ingredients) {
       
        Thread worker = new Thread(new Runnable() {
            public void run() {
                try {

                    final String mealName = GetRecipe.fetchSuggestion(ingredients);

                    boolean exists = DatabaseManager.existsInDatabase(mealName);
                    if (exists) {
                        System.out.println("[DEBUG] Meal exists in DB, fetching saved recipe.");
                        final String saved = DatabaseManager.getRecipeByName(mealName);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new popup(DashboardPage.this, mealName,
                                    saved == null ? "No recipe saved." : saved);
                            }
                        });
                    } else {

                        System.out.println("[DEBUG] Meal not in DB, generating recipe from AI...");
                        final String fullRecipe = GetRecipe.fetchFullRecipe(ingredients);
                        final String refined = AIRefiner.refineRecipe(fullRecipe);

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new popup(DashboardPage.this, mealName, refined, ingredients);
                            }
                        });
                    }
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            JOptionPane.showMessageDialog(null, "Failed to generate recipe!");
                        }
                    });
                }
            }
        });

        worker.start();
    }
    

    public static void main(String[] args) {
        new DashboardPage();
    }
}
