
import javax.swing.*;
import java.awt.*;

public class StringAnimation extends JFrame {

    private volatile boolean running = true;
    private final AnimationPanel panel = new AnimationPanel();

    public StringAnimation() {
        setTitle("String Animation");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(panel);
        setVisible(true);

        Thread animationThread = new Thread(() -> {
            while (running) {
                panel.moveText();

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        animationThread.start();
    }

    class AnimationPanel extends JPanel {
        private volatile int x = 0;

        public void moveText() {
            x += 5;

            if (x > getWidth()) {
                x = -150;
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Welcome to Java Programming!", x, 100);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StringAnimation::new);
    }
}
