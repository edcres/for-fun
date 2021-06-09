package flappyBird;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author eduar
 */

public class Renderer extends JPanel{       // extends from the imported JPanel
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        FlappyBird.flappyBird.repaint(g);
    }
}
