package Graphics3D;

import javax.media.opengl.awt.GLJPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private GLJPanel panel;
    private Renderer renderer;

    public InputHandler(GLJPanel panel, Renderer renderer) {
        this.panel = panel;
        this.renderer = renderer;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            renderer.addAngle(-Math.PI / 8);
            panel.display();
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            renderer.addAngle(Math.PI / 8);
            panel.display();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
