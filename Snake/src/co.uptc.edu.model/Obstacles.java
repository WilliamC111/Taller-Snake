import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Obstacles extends Thread {
    private boolean areObstaclesAvailable; // Variable para verificar si hay obstáculos disponibles
    private int obstacleX, obstacleY; // Coordenadas del obstáculo en la pantalla
    private int boardWidth, boardHeight; // Ancho y alto del tablero del juego
    private SnakeModel snake; // Referencia a la serpiente
    private int intervaloBarreras; // Intervalo de tiempo para la aparición de obstáculos (según las propiedades)

    public Obstacles(int boardWidth, int boardHeight, SnakeModel snake) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.snake = snake;
        loadProperties();
        this.areObstaclesAvailable = true; // Inicialmente hay obstáculos disponibles
        spawnObstacle(); // Método para generar el primer obstáculo
    }

    public Obstacles() {
    }

    private void loadProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\resources\\propsnake.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String property = parts[0].trim();
                    String value = parts[1].trim();

                    if (property.equals("intervaloBarreras")) {
                        intervaloBarreras = Integer.parseInt(value);
                        break; 
                    }
                   
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(intervaloBarreras); 
                if (!areObstaclesAvailable) {
                    spawnObstacle(); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void spawnObstacle() {
        Random random = new Random();
        obstacleX = random.nextInt(boardWidth);
        obstacleY = random.nextInt(boardHeight);
        areObstaclesAvailable = true;
    }

    public void checkSnakeCollision() {
        if (snake.getHeadX() == obstacleX && snake.getHeadY() == obstacleY) {
            areObstaclesAvailable = false; 
            snake.setGameOver(true); 
        }
    }
    public int getObstacleX() {
        return obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }

    public boolean isObstacleAvailable() {
        return areObstaclesAvailable;
    }
    
}
