import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class MenuView {
    private JFrame frame;
    private JPanel menuPanel;
    private GameBoard gamePanel;
    private BufferedImage menuBackgroundImage;
    private JTextField nameField;
    private String playerName; 
    private JButton playButton;
    public MenuView() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(menuBackgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        menuPanel.setLayout(new GridBagLayout());

        try {
            menuBackgroundImage = ImageIO.read(new File("src\\Images\\game.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        createUI();
    }

    private void createUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        addNameField(gbc);
        addLevelComboBox(gbc);
        addPlayButton(gbc);
        addInfoButton(gbc);
        addHistoryButton(gbc);

        gamePanel = new GameBoard();
        gamePanel.setBackground(Color.BLUE);
        gamePanel.setVisible(true);

        frame.add(menuPanel);
        frame.add(gamePanel);
        frame.setLayout(new CardLayout());
    }

    private void addNameField(GridBagConstraints gbc) {
        nameField = new JTextField(15);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(nameField, gbc);
    }

    private void addLevelComboBox(GridBagConstraints gbc) {
        String[] niveles = {"Fácil", "Medio", "Difícil"};
        JComboBox<String> nivelComboBox = new JComboBox<>(niveles);
        nivelComboBox.setPreferredSize(new Dimension(200, 50));
        nivelComboBox.setBackground(new Color(39, 39, 44));
        nivelComboBox.setForeground(Color.WHITE);
        nivelComboBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        nivelComboBox.setOpaque(false);

        nivelComboBox.addActionListener(e -> {
            String selectedLevel = (String) nivelComboBox.TOOL_TIP_TEXT_KEY;
            int speed = 0;
            switch (selectedLevel) {
                case "Fácil":
                    speed = 300;
                    break;
                case "Medio":
                    speed = 200;
                    break;
                case "Difícil":
                    speed = 100;
                    break;
            }
            gamePanel.setSnakeSpeed(speed);
        });

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(nivelComboBox, gbc);
    }

    private void addPlayButton(GridBagConstraints gbc) {
        playButton = new JButton("Jugar"); 
        playButton.setPreferredSize(new Dimension(200, 100));
        playButton.setBackground(new Color(39, 39, 44));
        playButton.setForeground(Color.WHITE);
        playButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        playButton.addActionListener(e -> {
            playerName = nameField.getText();
            if (!playerName.isEmpty()) {
                menuPanel.setVisible(false);
                gamePanel.setVisible(true);
                savePlayerName(playerName);
                Movement movement = new Movement(gamePanel.getSnakeBody(), gamePanel);
                movement.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un nombre.",
                        "Advertencia - Nombre no ingresado", JOptionPane.WARNING_MESSAGE);
            }
        });

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(playButton, gbc);
    }

    private void addInfoButton(GridBagConstraints gbc) {
        JButton infoButton = new JButton("Info");
        infoButton.setPreferredSize(new Dimension(100, 30));
        infoButton.setBackground(new Color(39, 39, 44));
        infoButton.setForeground(Color.WHITE);
        infoButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        infoButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        BufferedImage backgroundImage = ImageIO.read(new File("src\\Images\\Logo.jpg"));
                        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            dialog.setTitle("Información del Estudiante");
            dialog.setSize(350, 350);
            dialog.setLocationRelativeTo(frame);
            dialog.setModal(true);

            panel.setLayout(new BorderLayout());
            JLabel infoLabel = new JLabel("<html><pre>Nombre: William Cely<br>Código: 202012319<br>Facultad: Ingenieria<br>Escuela: Ingenieria de Sistemas y Computación<br>Año: 2023<br>Curso: Programación III</pre></html>");
            infoLabel.setForeground(Color.WHITE);
            panel.add(infoLabel, BorderLayout.CENTER);

            dialog.add(panel);
            dialog.setVisible(true);
        });

        gbc.gridy++;
        menuPanel.add(infoButton, gbc);
    }

    private void addHistoryButton(GridBagConstraints gbc) {
        JButton historyButton = new JButton("Historial");
        historyButton.setPreferredSize(new Dimension(100, 30));
        historyButton.setBackground(new Color(39, 39, 44));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        historyButton.addActionListener(e -> {
           
        });

        gbc.gridy++;
        menuPanel.add(historyButton, gbc);
    }

    private void savePlayerName(String playerName) {
        String fileName = "src\\resources\\Historial.ser";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public void showMenuView() {
    }

    public Object getPlayButton() {
        return playButton;
    }

    public static String getNivelSeleccionado() {
        return null;
    }

    public void setGameLogic(SnakeModel model) {
    }
}
