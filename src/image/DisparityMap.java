package image;

import imageMatching.MatchingAlgorithmTemplate;
import util.Point2D;

import java.awt.image.BufferedImage;

public class DisparityMap implements GrayScale {
    private int[] disparityMap;
    private final int width;
    private final int height;
    private int maximalDisparity;

    public DisparityMap(IntensityMap leftImageIntensity, IntensityMap rightImageIntensity, int windowSize, int numberDisparity, double threshold, MatchingAlgorithmTemplate matchingAlgorithm) {
        this.width = leftImageIntensity.getWidth();
        this.height = leftImageIntensity.getHeight();

        disparityMap = new int[width * height];

        for (int y = 0; y< height; y++) {
            for (int x = 0; x < width; x++) {
                setDisparity(
                        x,
                        y,
                        matchingAlgorithm.findBestDisparity(new Point2D(x, y), leftImageIntensity, rightImageIntensity, windowSize, numberDisparity, threshold)
                );
            }
        }
    }

    private int getDisparity(int x, int y) {
        return disparityMap[x + y * width];
    }

    private void setDisparity(int x, int y, int value) {
        disparityMap[x + y * width] = value;
        maximalDisparity = value > maximalDisparity ? value : maximalDisparity;
    }

    @Override
    public BufferedImage createGrayScaleImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayLevel = (255 * getDisparity(x, y)) / maximalDisparity;
                int rgb = 0xff000000 | grayLevel << 16 | grayLevel << 8 | grayLevel;

                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }
}
