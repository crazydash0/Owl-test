import java.awt.BorderLayout;
import com.sun.opengl.util.*;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Owl extends JFrame {
    private GLCanvas glCanvas;
    static FPSAnimator animator = null;
    private OwlTest listener = new OwlTest();

    public static void main(String[] args) {
        new Owl();
        animator.start();
    }

    public Owl() {
        super("Simple JOGL Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glCanvas = new GLCanvas();
        glCanvas.addGLEventListener(listener);
        animator = new FPSAnimator(glCanvas, 30);
        listener.setGLCanvas(glCanvas);

        getContentPane().add(glCanvas, BorderLayout.CENTER);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}