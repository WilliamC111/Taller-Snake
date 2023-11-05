import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Food extends Thread {
    boolean isFoodAvailable;
    private int boardWidth;
    private int boardHeight;
    int foodX;
    int foodY;
    private SnakeModel snake;
    private int intervaloTiempoComida;

    public Food(int boardWidth, int boardHeight, SnakeModel snake) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.snake = snake;

        isFoodAvailable = false;
        foodX = 0;
        foodY = 0;
        loadProperties();
    }

    private void loadProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\resources\\propsnake.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String property = parts[0].trim();
                    String value = parts[1].trim();

                    if (property.equals("intervaloTiempoComida")) {
                        intervaloTiempoComida = Integer.parseInt(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            if (!isFoodAvailable) {
                spawnFood();
            } else {
                try {
                    Thread.sleep(intervaloTiempoComida); 
                    setFoodAvailable(false); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   void spawnFood() {
        Random random = new Random();
        if (boardWidth > 0 && boardHeight > 0) {
            foodX = random.nextInt(boardWidth);
            foodY = random.nextInt(boardHeight);
            isFoodAvailable = true;
        } else {
            System.out.println("Las dimensiones del tablero no son válidas.");
        }
    }

    public void setFoodAvailable(boolean available) {
        isFoodAvailable = available;
    }

    public boolean isFoodAvailable() {
        return isFoodAvailable;
    }

    public int getFoodX() {
        return 0;
    }
}
