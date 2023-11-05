import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class HistoryPanel extends JPanel {
    private JTable table;

    public HistoryPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Fecha y Hora", "Nombre del Jugador", "Puntaje"};

        ArrayList<Score> scoreEntries = readScoresFromFile("src\\resources\\Historial.ser");

        Object[][] data = new Object[scoreEntries.size()][3];

        for (int i = 0; i < scoreEntries.size(); i++) {
            Score entry = scoreEntries.get(i);
            data[i][0] = entry.getDateAndTime();
            data[i][1] = entry.getPlayerName();
            data[i][2] = entry.getScore();
        }

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private ArrayList<Score> readScoresFromFile(String filename) {
        ArrayList<Score> scoreEntries = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            scoreEntries = (ArrayList<ScoreEntry>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scoreEntries;
    }
}
