package Graphics3D;

import image.DisparityMap;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

/**
 * Created by mmichels on 12/10/14.
 */
public class Renderer implements GLEventListener {
    int[] heightMap;
    int width;
    int height;
    double angle = 0.0;

    public Renderer(DisparityMap disparity) {
        width = disparity.getWidth();
        height = disparity.getHeight();

        heightMap = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setHeightMap(x, y, (disparity.getDisparity(x, y) * 256) / disparity.getMaximalDisparity());
            }
        }
    }

    private int getHeightMap(int x, int y) {
        return heightMap[x + y * width];
    }

    private void setHeightMap(int x, int y, int value) {
        heightMap[x + y * width] = value;
    }

    public void addAngle(double increment) {
        angle += increment;
        if (angle > Math.PI) {
            angle -= Math.PI;
        } else if (angle <= 0) {
            angle += Math.PI;
        }
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClearDepth(1.0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        GLU glu = new GLU();

        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();
        glu.gluLookAt(1.5 * width * Math.cos(angle) + width / 2, 1.5 * height * Math.sin(angle) + height, 256 + 128, this.width / 2, this.height / 2, 0, 0, 0, 1);


        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1, 0, 0);
        gl.glVertex3i(0, 0, 0);

        gl.glColor3f(0, 1, 0);
        gl.glVertex3i(10, 0, 0);

        gl.glColor3f(1, 1, 1);
        gl.glVertex3i(10, 10, 0);

        gl.glColor3f(0, 0, 1);
        gl.glVertex3i(0, 10, 0);

        int step = 1;
        for (int y = 0; y < height - step; y += step) {
            for (int x = 0; x < width - step; x += step) {
                int z = getHeightMap(x, y);
                gl.glColor3f(0, z / 256.0f, 0);
                gl.glVertex3i(x, y, z);

                z = getHeightMap(x, y + step);
                gl.glColor3f(0, z / 256.0f, 0);
                gl.glVertex3i(x, y + step, z);

                z = getHeightMap(x + step, y + step);
                gl.glColor3f(0, z / 256.0f, 0);
                gl.glVertex3i(x + step, y + step, z);

                z = getHeightMap(x + step, y);
                gl.glColor3f(0, z / 256.0f, 0);
                gl.glVertex3i(x + step, y, z);
            }
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        // coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluPerspective(45, (float) width / height, 1, 1000);

        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();

        gl.glViewport( 0, 0, width, height );
    }
}
