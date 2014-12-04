package image;

import intensityStrategy.IntensityStrategy;
import java.awt.image.BufferedImage;

public class IntensityMap implements GrayScale {
    private double[] intensity;
    private int width;
    private int height;

    public IntensityMap(BufferedImage image, IntensityStrategy strategy) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        intensity = new double[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setIntensity(x, y, strategy.compute(image.getRGB(x, y)));
            }
        }
    }

    public double getIntensity(int x, int y) {
        return intensity[x + width * y];
    }

    private void setIntensity(int x, int y, double value) {
        intensity[x + width * y] = value;
    }

    @Override
    public BufferedImage createGrayScaleImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double intensity = getIntensity(x, y);

                int grayLevel = (int)(255 * intensity);
                int rgb = 0xff000000 | grayLevel << 16 | grayLevel << 8 | grayLevel;

                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
