import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SnakeModel extends Thread {
    private int[][] snakeBody;
    private int snakeLength;
    private int speed;
    private int size;
    private int direction;
    private boolean gameOver;
    private int intervaloTiempoComida;
    private int intervaloBarreras;
    private int tasaAumentoComida;
    private String nivelSeleccionado;
    private int foodValue;
    private Food food;
    public SnakeModel() {
        snakeBody = new int[100][2];
        snakeLength = 1;
        direction = 1;
        gameOver = false;
        loadProperties();
        foodValue = 10; 
    }

    private void loadProperties() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\resources\\propsnake.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String property = parts[0].trim();
                    String value = parts[1].trim();

                    switch (property) {
                        case "speed":
                            speed = Integer.parseInt(value);
                            break;
                        case "size":
                            size = Integer.parseInt(value);
                            break;
                        case "intervaloTiempoComida":
                            intervaloTiempoComida = Integer.parseInt(value);
                            break;
                        case "intervaloBarreras":
                            intervaloBarreras = Integer.parseInt(value);
                            break;
                        case "tasaAumentoComida":
                            tasaAumentoComida = Integer.parseInt(value);
                            break;
                        case "Niveles":
                            nivelSeleccionado = value;
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
        while (!gameOver) {
            moveSnake();
            if (didEatFood()) {
            }
            if (isGameOver()) {
                gameOver = true;
            }
            try {
                sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isGameOver() {
        return checkCollision();
    }

    

    public int getFoodValue() {
        return foodValue;
    }

    void moveSnake() {
        int headX = snakeBody[0][0];
        int headY = snakeBody[0][1];
    
        
        int boardWidth = 40;
        int boardHeight = 40; 
    
        switch (direction) {
            case 0: 
                headY = (headY - 1 + boardHeight) % boardHeight;
                break;
            case 1: 
                headX = (headX + 1) % boardWidth;
                break;
            case 2: 
                headY = (headY + 1) % boardHeight;
                break;
            case 3: 
                headX = (headX - 1 + boardWidth) % boardWidth;
                break;
        }
    
        snakeBody[0][0] = headX;
        snakeBody[0][1] = headY;
    
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeBody[i][0] = snakeBody[i - 1][0];
            snakeBody[i][1] = snakeBody[i - 1][1];
        }
    }
    

    public void changeDirection(int newDirection) {
        if (Math.abs(newDirection - direction) != 2) {
            direction = newDirection;
        }
    }

    public void grow() {
       
        int lastSegment = snakeBody[snakeLength - 1][0];
        int lastSegmentY = snakeBody[snakeLength - 1][1];
    
      
        switch (direction) {
            case 0: 
                snakeBody[snakeLength][0] = lastSegment;
                snakeBody[snakeLength][1] = lastSegmentY + 1;
                break;
            case 1:
                snakeBody[snakeLength][0] = lastSegment - 1;
                snakeBody[snakeLength][1] = lastSegmentY;
                break;
            case 2: 
                snakeBody[snakeLength][0] = lastSegment;
                snakeBody[snakeLength][1] = lastSegmentY - 1;
                break;
            case 3: 
                snakeBody[snakeLength][0] = lastSegment + 1;
                snakeBody[snakeLength][1] = lastSegmentY;
                break;
        }
    
        snakeLength++;
    }

    private boolean checkCollision() {
        int headX = snakeBody[0][0];
        int headY = snakeBody[0][1];

        if (headX < 0 || headY < 0 || headX >= 20 || headY >= 20) {
            return true;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (headX == snakeBody[i][0] && headY == snakeBody[i][1]) {
                return true;
            }
        }

        return false;
    }

   
    private boolean checkFoodCollision() {
        int headX = snakeBody[0][0];
        int headY = snakeBody[0][1];
    
        if (headX == food.getFoodX() && headY == food.getFoodX()) {
            grow();
            return true; 
        }
    
        return false;
    }

    public int getHeadX() {
        return snakeBody[0][0];
    }

    public int getHeadY() {
        return snakeBody[0][1];
    }

    public int[][] getSnakeBody() {
        return snakeBody;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public void setSpeed(int speed2) {
    }

    public void setGameOver(boolean b) {
    }

    public boolean didEatFood() {
        return false;
    }
}
