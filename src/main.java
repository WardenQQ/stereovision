import Graphics3D.InputHandler;
import Graphics3D.Renderer;
import image.DepthMap;
import image.DisparityMap;
import image.IntensityMap;
import imageMatching.SumSquaredDifferences;
import intensityStrategy.HsiIntensity;

import javax.imageio.ImageIO;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A minimal program that draws with JOGL in a Swing JFrame using the AWT GLJPanel.
 *
 * @author Wade Walker
 */
public class main {

    public static void main( String [] args ) {
        DisparityMap disparityMap;
        try {
            BufferedImage leftImage = ImageIO.read(new File("./image/imL.png"));
            BufferedImage rightImage = ImageIO.read(new File("./image/imR.png"));
            IntensityMap leftIntensity = new IntensityMap(leftImage, new HsiIntensity());
            IntensityMap rightIntensity = new IntensityMap(rightImage, new HsiIntensity());
            disparityMap = new DisparityMap(
                    leftIntensity,
                    rightIntensity,
                    10,
                    150,
                    new SumSquaredDifferences()
            );
            DepthMap depth = new DepthMap(disparityMap, 100, 7.9, 7.114);
            System.out.println(disparityMap.getDisparity(162, 148));
            System.out.println(depth.getDepth(162, 148));

            ImageIO.write(disparityMap.createGrayScaleImage(), "png", new File("./disparityMap.png"));

            // Thomas pour la 3D.

            GLProfile glprofile = GLProfile.getDefault();
            GLCapabilities glcapabilities = new GLCapabilities( glprofile );
            GLJPanel gljpanel = new GLJPanel( glcapabilities );

            Renderer renderer = new Renderer(disparityMap);

            gljpanel.addGLEventListener(renderer);
            gljpanel.addKeyListener(new InputHandler(gljpanel, renderer));

            final JFrame jframe = new JFrame( "Vue 3D de la scene" );
            jframe.addWindowListener( new WindowAdapter() {
                public void windowClosing( WindowEvent windowevent ) {
                    jframe.dispose();
                    System.exit( 0 );
                }
            });

            jframe.getContentPane().add(gljpanel, BorderLayout.CENTER);
            jframe.setSize(640, 480);
            jframe.setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
