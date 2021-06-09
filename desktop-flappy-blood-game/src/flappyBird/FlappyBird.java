package flappyBird;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author eduar
 *
 */
// https://www.youtube.com/watch?v=I1qTZaUcFX0&t=1623s

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public int ticks, yMotion, score;
    public boolean gameOver, started;
    public Random rand;

    public static void main(String args[]) {
        flappyBird = new FlappyBird();
    }

    public FlappyBird() {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new Renderer();
        rand = new Random();

        // make the window in which the app is run
        jframe.add(renderer);
        jframe.setTitle("Flappy Bird Copy");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH / 2, HEIGHT / 2 - 10, 20, 20); // x/y position in screen, x/y dimensions
        columns = new ArrayList<Rectangle>(); // declares the array of columns

        addColumn(true);
        for (int i = 0; i < 199; i++) {
            addColumn(true);
        }
        timer.start();
    }

    public void addColumn(boolean start) {
        int space = 300; // vertical space in between one column
        int width = 100;
        int height = 150 + rand.nextInt(300); // starting size of the bottom column

        // 'start' if it is the starting column, else, add the other columns in a
        // different location
        if (start) {
            // add top and bottom column
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height, width, height)); // top
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space)); // bottom
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    @Override // pressed control + space to get this override (then choose actionPerformed
              // method)
    public void actionPerformed(ActionEvent e) {
        int columnSpeed = 15;
        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) { // size of the array of columns
                Rectangle column = columns.get(i);
                column.x -= columnSpeed; // moves the columns to te left
            }

            if (ticks % 2 == 0 && yMotion < 15) { // pulls the bird (AKA: gravity force)
                yMotion += 3;
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                if (column.y + column.width < 0) {
                    columns.remove(column); // i think it removes the column that passes

                    if (column.y == 0) {
                        addColumn(false); // i think theres a prolem here bc when I have to add a lot of columns at the
                                          // beggining, otherwise the game would run out of columns faster
                    }
                }
            }

            bird.y -= yMotion; // direction of the gravity

            for (Rectangle column : columns) { // collision detection part1

                if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10
                        && bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
                    score++;
                }

                if (column.intersects(bird)) { // game over if column intersects with bird
                    gameOver = true;

                    if (bird.x <= column.x) { // if bird is before the column
                        bird.x = column.x - bird.width;
                    } else {
                        if (column.y != 0) { // if it's after column
                            bird.y = column.y - bird.height;
                            yMotion = 0;
                        } else if (bird.y < column.height) { // if it's at column
                            bird.y = column.height;
                            yMotion = 0;
                        }
                    }
                }
            }

            if (bird.y > HEIGHT - 130 || bird.y < -50) { // if bird goes too low or too high
                gameOver = true;
            }

            if (bird.y + yMotion >= HEIGHT - 120) { // stops bird from falling throught the ground
                bird.y = HEIGHT - 140;
            }
        }
        renderer.repaint();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.RED.darker().darker().darker().darker().darker()); // backround
        g.fillRect(0, 0, WIDTH, WIDTH);

        g.setColor(Color.red.darker().darker().darker().darker().darker().darker()); // ground
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.red.darker().darker()); // grass
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        g.setColor(Color.cyan); // bird
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        g.setColor(Color.MAGENTA); // Score
        g.setFont(new Font("Aeral", 1, 100));

        if (!started) {
            g.drawString("Click to Start!", 75, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over", 100, HEIGHT / 2 - 50);
        }
        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }

    public void paintColumn(Graphics g, Rectangle column) { // paints the columns
        g.setColor(Color.red.darker().darker().darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void jump() {
        // there shouled be a sepparate method for jump, and another for gameover/start
        if (gameOver) {
            bird = new Rectangle(WIDTH / 2, HEIGHT / 2 - 10, 20, 20);
            columns.clear(); // clear all the columns
            // yMotion = 0;
            score = 0;

            addColumn(true);
            for (int i = 0; i < 199; i++) {
                addColumn(true);
            }

            gameOver = false;
        }
        if (!started) {
            started = true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion = 5; // it goes up right after the key is pressed very proud of this btw
            yMotion -= 25; // how high to jump
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent me) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
