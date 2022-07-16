import java.applet.Applet;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Snake extends Applet implements KeyListener {

  String[] sk;
  int X[], Y[], N;
  String direction;
  String preDirection;
  String lost;
  boolean flag;
  int height, width, px, py, prex, prey, count;
  Random rand;
  Button playAgain;

  public void init() {
    playAgain = new Button("PLAY AGAIN");
    defaultSnake();
    setLayout(null);
    addKeyListener(this);
    setFocusable(true);
    requestFocusInWindow();
    resize(600, 600);
    rand = new Random();
    Dimension dimension = this.getSize();
    height = dimension.height;
    width = dimension.width;
    Game gm = new Game();
    playAgain.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent ev1) {
          defaultSnake();
          repaint();
        }
      }
    );
    playAgain.setBounds(230, 10, 100, 40);
    add(playAgain);
    playAgain.setVisible(false);
    gm.start();
  }

  public void defaultSnake() {
    direction = "right";
    preDirection = "right";
    lost = "";
    N = 5;
    count = 0;
    sk = new String[100];
    X = new int[100];
    Y = new int[100];
    X[0] = 100;
    X[1] = 90;
    X[2] = 80;
    X[3] = 70;
    X[4] = 60;
    Y[0] = 100;
    Y[1] = 100;
    Y[2] = 100;
    Y[3] = 100;
    Y[4] = 100;
    flag = false;
    playAgain.setVisible(false);
  }

  public void destroy() {
    super.destroy();
    System.out.println("destroy");
  }

  public void keyPressed(KeyEvent e) {
    int k = e.getKeyCode();
    if (k == 37) {
      direction = "left";
    }
    if (k == 38) {
      direction = "up";
    }
    if (k == 39) {
      direction = "right";
    }
    if (k == 40) {
      direction = "down";
    }
  }

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}

  public int round(int n) {
    int a = (n / 10) * 10;

    int b = a + 10;

    return (n - a > b - n) ? b : a;
  }

  public void paint(Graphics g) {
    setFocusable(true);
    requestFocusInWindow();
    Font f1 = new Font("Impact", Font.BOLD, 30);
    g.setFont(f1);
    if (lost.equals("") && count < 100) {
      for (int i = 0; i < N; i++) {
        sk[i] = ".";
      }
      for (int i = 0; i < N; i++) {
        g.drawString(sk[i], X[i], Y[i]);
      }
      px = (int) Math.floor(Math.random() * (440 - 30 + 1) + 10);
      py = (int) Math.floor(Math.random() * (440 - 80 + 1) + 70);
      if (flag == false) {
        g.drawString(".", px, py);
        prex = round(px);
        prey = round(py);
      } else {
        g.drawString(".", prex, prey);
      }
      g.drawString("SCORE: " + count, 230, 50);
      g.drawString("___________________________________", 0, 60);
    } else {
      if (!lost.equals("")) {
        g.drawString(lost, 230, 300);
        playAgain.setVisible(true);
      } else {
        g.drawString("You Win", 230, 300);
        playAgain.setVisible(true);
      }
    }
  }

  public void left() {
    for (int i = N - 1; i > 0; i--) {
      X[i] = X[i - 1];
      Y[i] = Y[i - 1];
    }
    X[0] = X[0] - 10;
    Y[0] = Y[0];
  }

  public void up() {
    for (int i = N - 1; i > 0; i--) {
      X[i] = X[i - 1];
      Y[i] = Y[i - 1];
    }
    Y[0] = Y[0] - 10;
    X[0] = X[0];
  }

  public void right() {
    for (int i = N - 1; i > 0; i--) {
      X[i] = X[i - 1];
      Y[i] = Y[i - 1];
    }
    X[0] = X[0] + 10;
    Y[0] = Y[0];
  }

  public void down() {
    for (int i = N - 1; i > 0; i--) {
      X[i] = X[i - 1];
      Y[i] = Y[i - 1];
    }
    Y[0] = Y[0] + 10;
    X[0] = X[0];
  }

  public class Game extends Thread {

    public void run() {
      for (;;) {
        try {
          sleep(100);
          if (direction.equals("left") && !preDirection.equals("right")) {
            left();
            preDirection = "left";
          } else if (direction.equals("up") && !preDirection.equals("down")) {
            up();
            preDirection = "up";
          } else if (
            direction.equals("right") && !preDirection.equals("left")
          ) {
            right();
            preDirection = "right";
          } else if (direction.equals("down") && !preDirection.equals("up")) {
            down();
            preDirection = "down";
          } else {
            direction = preDirection;
          }
          if (X[0] > width) {
            X[0] = 20;
          }
          if (X[0] <= 0) {
            X[0] = width;
          }
          if (Y[0] >= height + 100) {
            Y[0] = 70;
          }
          if (Y[0] <= 65) {
            Y[0] = height;
          }
          for (int i = 1; i < N; i++) {
            if (X[0] == X[i] && Y[0] == Y[i]) {
              lost = "You Lost";
            }
          }

          flag = true;
          if (X[0] == prex && Y[0] == prey) {
            N++;
            count++;
            flag = false;
          }
          repaint();
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
  }
}
