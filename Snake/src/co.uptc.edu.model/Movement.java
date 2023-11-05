import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Movement extends Thread {
    private SnakeModel snake;
    private GameBoard gameBoard;
    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
    private Properties properties;
    private boolean isRunning;

    public Movement(SnakeModel snake, GameBoard gameBoard) {
        this.snake = snake;
        this.gameBoard = gameBoard;
        this.properties = loadProperties();
        this.isRunning = true; 
    }
    private Properties loadProperties() {
        Properties prop = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\resources\\propsnake.txt"))) {
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
    @Override
    public void run() {
        while (isRunning) {
            try {
                snake.moveSnake();
                Thread.sleep(calculateSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopMovement() {
        isRunning = false;
    }
    private long calculateSpeed() {
        int snakeSize = snake.getSnakeLength();
        long speed = 100;
    
        String nivelSeleccionado = "facil";
    
        if (properties.containsKey(nivelSeleccionado)) {
            speed = Long.parseLong(properties.getProperty(nivelSeleccionado)) - (snakeSize * 10);
        }
    
        return speed;
    }
}
