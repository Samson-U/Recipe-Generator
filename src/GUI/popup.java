package GUI;

import DatabaseConnection.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class popup {

    public popup(final JFrame parentFrame, final String mealName,
                 final String recipeTextContent, final String sourceIngredients) {

        final JDialog dialog = new JDialog(parentFrame, "Recipe Details", true);
        dialog.setSize(650, 500);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(new Color(25, 25, 25));
        dialog.setLocationRelativeTo(parentFrame);


        JLabel title = new JLabel(mealName);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBounds(30, 15, 400, 25);
        dialog.add(title);

        JButton backBtn = new JButton(" Back");
        backBtn.setBounds(510, 15, 90, 28);
        backBtn.setBackground(new Color(70, 70, 70));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dialog.add(backBtn);

        
        JTextArea recipeText = new JTextArea();
        recipeText.setEditable(false);
        recipeText.setBackground(new Color(35, 35, 35));
        recipeText.setForeground(Color.WHITE);
        recipeText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        recipeText.setLineWrap(true);
        recipeText.setWrapStyleWord(true);

        if (recipeTextContent == null || recipeTextContent.trim().equals("")) {
            recipeText.setText(" No recipe data available.\n\nPlease try again or check your network connection.");
        } else {
            recipeText.setText(recipeTextContent);
        }

        JScrollPane scroll = new JScrollPane(recipeText);
        scroll.setBounds(30, 60, 580, 340);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        dialog.add(scroll);


        final JButton saveBtn = new JButton(" Save Recipe");
        saveBtn.setBounds(250, 420, 150, 35);
        saveBtn.setBackground(new Color(120, 80, 200));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dialog.add(saveBtn);

        if (sourceIngredients == null || sourceIngredients.trim().isEmpty()) {
            saveBtn.setEnabled(false);
        }

        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseManager.insertRecipe(
                        mealName,
                        (sourceIngredients == null ? "" : sourceIngredients),
                        (recipeTextContent == null ? "" : recipeTextContent)
                );
                JOptionPane.showMessageDialog(dialog, "Recipe saved successfully!");
                saveBtn.setEnabled(false);
            }
        });

        dialog.setVisible(true);
    }

    
    public popup(final JFrame parentFrame, final String mealName, final String recipeTextContent) {
        this(parentFrame, mealName, recipeTextContent, null);
    }
}
