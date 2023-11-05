import javax.swing.*;

public class Score extends Thread {
    private int score;
    private SnakeModel snake;
    private boolean running;
    private JLabel scoreLabel;

    public Score(SnakeModel snake) {
        this.snake = snake;
        this.score = 0;
        this.running = true;
    }

    public void setScoreLabel(JLabel label) {
        this.scoreLabel = label;
    }

    public void stopRunning() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            if (snake.didEatFood()) {
                int foodValue = snake.getFoodValue();
                updateScore(foodValue);
            }
        }
    }

    public void updateScore(int value) {
        score += value;

        if (scoreLabel != null) {
            SwingUtilities.invokeLater(() -> scoreLabel.setText("Score: " + score));
        }
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int value) {
        updateScore(value);
    }
}
