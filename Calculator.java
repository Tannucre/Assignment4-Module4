import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0;
    private double num2 = 0;
    private char operator = '\0';
    private StringBuilder expression = new StringBuilder();

    public Calculator() {
        setTitle("Calculator");
        setSize(320, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Deep blue background for the entire calculator body
        Color bgBlue = new Color(0, 115, 160);
        getContentPane().setBackground(bgBlue);
        setLayout(new BorderLayout(10, 10));

        // Screen display matching the file view
        display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 20));
        display.setHorizontalAlignment(JTextField.LEFT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setForeground(Color.BLACK);
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        display.setPreferredSize(new Dimension(300, 48));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        topPanel.setOpaque(false);
        topPanel.add(display, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Main center area container
        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        centerPanel.setOpaque(false);

        // 4x4 Grid layout:
        // +, 1, 2, 3
        // -, 4, 5, 6
        // *, 7, 8, 9
        // /, . (below 7), 0 (below 8), C (below 9)
        JPanel gridPanel = new JPanel(new GridLayout(4, 4, 8, 8));
        gridPanel.setOpaque(false);

        String[] gridButtons = {
                "+", "1", "2", "3",
                "-", "4", "5", "6",
                "*", "7", "8", "9",
                "/", ".", "0", "C"
        };

        for (String label : gridButtons) {
            JButton btn = createStyledButton(label);
            gridPanel.add(btn);
        }

        centerPanel.add(gridPanel, BorderLayout.CENTER);

        // Full-width '=' button below the grid line
        JButton btnEqual = createStyledButton("=");
        btnEqual.setPreferredSize(new Dimension(0, 42));
        btnEqual.setFont(new Font("SansSerif", Font.BOLD, 22));
        centerPanel.add(btnEqual, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Helper method to create white buttons with black text and 3D border
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        // Clear 'C' button
        if (cmd.equals("C")) {
            expression.setLength(0);
            num1 = 0;
            num2 = 0;
            operator = '\0';
            display.setText("");
            return;
        }

        // Decimal point '.' button
        if (cmd.equals(".")) {
            String current = expression.toString();
            if (operator == '\0') {
                if (!current.contains(".")) {
                    if (current.isEmpty()) expression.append("0");
                    expression.append(".");
                }
            } else {
                int opIndex = current.lastIndexOf(operator);
                String secondPart = current.substring(opIndex + 1);
                if (!secondPart.contains(".")) {
                    if (secondPart.isEmpty()) expression.append("0");
                    expression.append(".");
                }
            }
            display.setText(expression.toString());
            return;
        }

        // Operators (+, -, *, /)
        if (cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
            if (expression.length() > 0 && operator == '\0') {
                num1 = Double.parseDouble(expression.toString());
                operator = cmd.charAt(0);
                expression.append(operator);
                display.setText(expression.toString());
            }
            return;
        }

        // Equals '=' button
        if (cmd.equals("=")) {
            if (operator != '\0' && expression.length() > 0) {
                String fullExpr = expression.toString();
                int opIndex = fullExpr.lastIndexOf(operator);

                if (opIndex != -1 && opIndex < fullExpr.length() - 1) {
                    num2 = Double.parseDouble(fullExpr.substring(opIndex + 1));
                    double result = 0;

                    switch (operator) {
                        case '+': result = num1 + num2; break;
                        case '-': result = num1 - num2; break;
                        case '*': result = num1 * num2; break;
                        case '/':
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                    }

                    // Formats full calculation on screen (e.g. 52*4=208.0)
                    display.setText(fullExpr + "=" + result);
                    expression.setLength(0);
                    expression.append(result);
                    operator = '\0';
                }
            }
            return;
        }

        // Digits (0 to 9)
        expression.append(cmd);
        display.setText(expression.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}