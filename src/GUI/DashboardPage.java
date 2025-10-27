package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import AICodes.AIRefiner;
import AICodes.GetRecipe;
import AICodes.RecipeFetcher;

public class DashboardPage extends JFrame {

    private JTextArea ingField;

    public DashboardPage() {

        setTitle("Recipe Generator Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // ---------------- Header ----------------
        JPanel header = new JPanel();
        header.setBounds(0, 0, 900, 60);
        header.setBackground(new Color(40, 40, 40));
        header.setLayout(null);

        JLabel title = new JLabel(" Recipe Generator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(20, 15, 300, 30);
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

        // ---------------- Main Panel ----------------
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

        JButton generateBtn = new JButton("Generate Recipe");
        generateBtn.setBounds(100, 210, 200, 35);
        generateBtn.setBackground(new Color(120, 80, 200));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generateBtn.setFocusPainted(false);
        generateBtn.setBorderPainted(false);
        mainPanel.add(generateBtn);

        add(mainPanel);
        setVisible(true);

        // ---------------- Generate Recipe Action ----------------
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

    // --------- Recipe Generation Logic ------------
    private void generateRecipe(final String ingredients) {
        System.out.println("[DEBUG] Starting recipe generation for: " + ingredients);

        Thread worker = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("[DEBUG] Fetching suggestion...");
                    final String mealName = GetRecipe.fetchSuggestion(ingredients);
                    System.out.println("[DEBUG] Suggested Meal Name: " + mealName);

                    System.out.println("[DEBUG] Fetching raw recipe...");
                    String rawRecipe = RecipeFetcher.getRecipe(mealName);

                    System.out.println("[DEBUG] Refining recipe...");
                    final String refinedRecipe = AIRefiner.refineRecipe(rawRecipe);

                    System.out.println("[DEBUG] Recipe generation complete!");

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new popup(DashboardPage.this, mealName, refinedRecipe);
                        }
                    });

                } catch (Exception ex) {
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
