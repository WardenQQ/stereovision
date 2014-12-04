import image.DisparityMap;
import image.IntensityMap;

import imageMatching.SumAbsoluteDifferences;

import imageMatching.SumSquaredDifferences;
import intensityStrategy.HslIntensity;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class main {
    public static void main(String[] args) {
        try {
            BufferedImage leftImage = ImageIO.read(new File("./image/imL.png"));
            BufferedImage rightImage = ImageIO.read(new File("./image/imR.png"));

            IntensityMap leftIntensity = new IntensityMap(leftImage, new HslIntensity());
            IntensityMap rightIntensity = new IntensityMap(rightImage, new HslIntensity());

            DisparityMap disparityMap = new DisparityMap(
                    leftIntensity,
                    rightIntensity,
                    20,
                    25,
                    1.0,
                    new SumSquaredDifferences()
            );

            ImageIO.write(disparityMap.createGrayScaleImage(), "png", new File("./disparityMap.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
