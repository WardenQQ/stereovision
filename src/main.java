import image.DepthMap;
import image.DisparityMap;
import image.IntensityMap;

import imageMatching.NormalizedCrossCorrelation;
import imageMatching.SumAbsoluteDifferences;

import imageMatching.SumSquaredDifferences;
import intensityStrategy.HslIntensity;
import intensityStrategy.HsiIntensity;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class main {
    public static void main(String[] args) {
        try {
            //BufferedImage leftImage = ImageIO.read(new File("./image/imL.png"));
            //BufferedImage rightImage = ImageIO.read(new File("./image/imR.png"));
            BufferedImage leftImage = ImageIO.read(new File("./vaseL.png"));
            BufferedImage rightImage = ImageIO.read(new File("./vaseR.png"));

            IntensityMap leftIntensity = new IntensityMap(leftImage, new HsiIntensity());
            IntensityMap rightIntensity = new IntensityMap(rightImage, new HsiIntensity());

            DisparityMap disparityMap = new DisparityMap(
                    leftIntensity,
                    rightIntensity,
                    9,
                    100,
                    NormalizedCrossCorrelation.WORST_SCORE,
                    new NormalizedCrossCorrelation()
            );

            DepthMap depth = new DepthMap(disparityMap, 100, 7.9, 7.114);
            System.out.println(disparityMap.getDisparity(162, 148));
            System.out.println(depth.getDepth(162, 148));

            ImageIO.write(disparityMap.createGrayScaleImage(), "png", new File("./disparityMap.png"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
