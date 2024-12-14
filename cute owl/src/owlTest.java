import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class OwlTest implements GLEventListener, MouseMotionListener {
    private int pupilOffsetXLeft = 0;
    private int pupilOffsetYLeft = 0;
    private int pupilOffsetXRight = 0;
    private int pupilOffsetYRight = 0;
    private final int range = 20; // Max range of pupil movement

    private GLCanvas glCanvas;

    public void setGLCanvas(GLCanvas glCanvas) {
        this.glCanvas = glCanvas;
        this.glCanvas.addMouseMotionListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glColor3f(0.8f, 0.035f, 0.8f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-100, 100, -100.0, 100.0, -1.0, 1.0);
        gl.glViewport(0, 0, drawable.getWidth(), drawable.getHeight());
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        drawBody(gl);

        drawBeak(gl);

        drawEye(gl, -40, 70, 50, 1, 1, 1); // Left eye
        drawEye(gl, 40, 70, 50, 1, 1, 1); // Right eye


        drawEye(gl, -40 + pupilOffsetXLeft, 70 + pupilOffsetYLeft, 10, 0, 0, 0);
        drawEye(gl, 40 + pupilOffsetXRight, 70 + pupilOffsetYRight, 10, 0, 0, 0);
    }

    private void drawEye(GL gl, double centerX, double centerY, double radius, float r, float g, float b) {
        double x, y;
        final double oneDegree = Math.PI / 180;

        gl.glColor3f(r, g, b);
        gl.glBegin(GL.GL_POLYGON);
        for (double angle = 0; angle <= 2 * Math.PI; angle += oneDegree) {
            x = centerX + radius * Math.cos(angle);
            y = centerY + radius * Math.sin(angle);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void drawBeak(GL gl) {
        gl.glColor3f(1, 1, 0);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2d(-20, 40);
        gl.glVertex2d(0, 0);
        gl.glVertex2d(20, 40);
        gl.glEnd();
    }

    private void drawBody(GL gl) {
        gl.glColor3f(0.612f, 0.11f, 0.71f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2d(-100, -100);
        gl.glVertex2d(100, -100);
        gl.glVertex2d(100, 100);
        gl.glVertex2d(-100, 100);
        gl.glEnd();
    }
    private double[] Pupil (double mouseX, double mouseY, double centerX, double centerY) {
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > range) {
            dx = (dx / distance) * range;
            dy = (dy / distance) * range;
        }

        return new double[] { dx, dy };
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        double positionX = e.getX();
        double positionY = e.getY();

        // Convert mouse coordinates to OpenGL coordinates
        double X = (positionX / glCanvas.getWidth()) * 200 - 100;
        double Y = 100 - (positionY / glCanvas.getHeight()) * 200;

        // Update pupil positions for both eyes
        double[] leftPupil = Pupil(X, Y, -40, 70);
        pupilOffsetXLeft = (int) leftPupil[0];
        pupilOffsetYLeft = (int) leftPupil[1];

        double[] rightPupil = Pupil(X, Y, 40, 70);
        pupilOffsetXRight = (int) rightPupil[0];
        pupilOffsetYRight = (int) rightPupil[1];

        glCanvas.repaint();
    }
}