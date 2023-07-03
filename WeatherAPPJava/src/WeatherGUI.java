import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WeatherGUI extends JFrame {
    private JTextField cityField;
    private JButton searchButton;
    private JTextArea resultArea;

    public WeatherGUI() {
        setTitle("Weather Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField(20);
        cityField.addKeyListener(new EnterKeyListener()); // Register EnterKeyListener
        inputPanel.add(cityLabel);
        inputPanel.add(cityField);

        searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        contentPane.add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    public void setResultText(String text) {
        resultArea.setText(text);
    }

    public String getCity() {
        return cityField.getText();
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    private class EnterKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                // Simulate button click when Enter is pressed
                searchButton.doClick();
            }
        }
    }
}
