package car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class CarController extends TimerTask implements KeyListener {

    public static final int TIME_TO_UPDATE = 30; // ms per update

    private final JFrame gameJFrame;
    private final Container contentPane;

    private boolean up, down, left, right, drift;

    private final Timer gameTimer = new Timer();
    private final Car car;

    private boolean gameRunning = true;

    public static void main(String[] args) {
        new CarController("Pixel Drift", 50, 50, 800, 600);
    }

    public CarController(String title, int x, int y, int width, int height) {
        gameJFrame = new JFrame(title);
        gameJFrame.setSize(width, height);
        gameJFrame.setLocation(x, y);
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = gameJFrame.getContentPane();
        contentPane.setLayout(null);


        // Create a car
        car = new Car(100, 100, "testCar.jpg");
        car.loadImage();

        gameJFrame.addKeyListener(this);
        gameJFrame.setVisible(true);

        // Schedule repeated updates
        gameTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);
    }

    @Override
    public void run() {
        if (!gameRunning) return;

        car.setDrift(drift);
        if (up) car.accelerate(0.2);
        if (down) car.accelerate(-0.2);
        if (left) car.turn(-0.05);
        if (right) car.turn(0.05);

        car.move();
        repaint();
    }

    private void repaint() {
        Graphics g = contentPane.getGraphics();
        if (g != null) {
            g.clearRect(0, 0, contentPane.getWidth(), contentPane.getHeight());
            car.draw(g);
        }
    }

    //  key control
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> drift = true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_SPACE -> drift = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}