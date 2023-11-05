import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class GameBoard extends JPanel {
    private Image gameBackgroundImage;
    private SnakeModel snake;
    private Timer timer;
    private boolean changeDirection;
    private Obstacles obstacles;
    
    public boolean isChangeDirection() {
        return changeDirection;
    }
    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }

    private JLabel scoreLabel;
    private Score score;
    private Food food;
    private Movement movement;
    private boolean isGameRunning;

    private int boardWidth = 25; 
    private int boardHeight = 25; 

    public GameBoard() {
        try {
            gameBackgroundImage = ImageIO.read(new File("src\\Images\\cesped.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        snake = new SnakeModel();
        snake.start();

        int delay = 400;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveSnake();
                checkFood();
                repaint();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (!isGameRunning) {
                    startGame();
                }

                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    snake.changeDirection(0);
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    snake.changeDirection(1);
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    snake.changeDirection(2);
                } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    snake.changeDirection(3);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.black);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.NORTH);

        score = new Score(snake);
        score.setScoreLabel(scoreLabel);
        score.start();

        food = new Food(boardWidth, boardHeight, snake); 
        food.start();

        isGameRunning = false;
        requestFocus();

        obstacles = new Obstacles(boardWidth, boardHeight, snake);
        obstacles.start(); 
    }
    
    
    public void startGame() {
        if (!isGameRunning) {
            snake.changeDirection(1); 
            movement = new Movement(snake, this);
            movement.start();
            isGameRunning = true;
            timer.start(); 
        }
    }

    public void setSnakeSpeed(int speed) {
        snake.setSpeed(speed);
    }

    private void moveSnake() {
        snake.moveSnake();
        checkCollisionWithObstacle(); 
        repaint();
    }
    
    private void checkCollisionWithObstacle() {
        int snakeHeadX = snake.getHeadX();
        int snakeHeadY = snake.getHeadY();
        int obstacleX = obstacles.getObstacleX();
        int obstacleY = obstacles.getObstacleY();
    
        if (snakeHeadX == obstacleX && snakeHeadY == obstacleY) {
            snake.setGameOver(true); 
        }
    }

    private void checkFood() {
        if (snake.didEatFood()) {
            score.incrementScore(snake.getFoodValue());
            food.setFoodAvailable(false);
            snake.grow(); 
        }
        if (!food.isFoodAvailable) {
            food.spawnFood(); 
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gameBackgroundImage, 0, 0, getWidth(), getHeight(), this);

        int cellSize = 30;
        int offsetY = 60;

        int[][] snakeBody = snake.getSnakeBody();
        g.setColor(Color.RED);

        for (int i = 0; i < snake.getSnakeLength(); i++) {
            int x = snakeBody[i][0] * cellSize;
            int y = snakeBody[i][1] * cellSize + offsetY;
            g.fillRect(x, y, cellSize, cellSize);
        }

        if (food.isFoodAvailable) {
            g.setColor(Color.BLUE);
            int x = food.foodX * cellSize;
            int y = food.foodY * cellSize + offsetY;
            g.fillOval(x, y, cellSize, cellSize);
        }

        g.setColor(Color.ORANGE); 

        if (obstacles.isObstacleAvailable()) {
            int obsX = obstacles.getObstacleX() * cellSize;
            int obsY = obstacles.getObstacleY() * cellSize + offsetY;
            g.fillRect(obsX, obsY, cellSize, cellSize);
        }
    }

    public int getLevel() {
        return 0;
    }

    public SnakeModel getSnakeBody() {
        return snake;
    }
}