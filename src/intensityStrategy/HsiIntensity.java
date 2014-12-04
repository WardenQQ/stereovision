package intensityStrategy;

public class HsiIntensity implements IntensityStrategy {
    @Override
    public double compute(int rgb) {
        int red = (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue = rgb & 0x000000ff;

        return (double) (red + green + blue) / (3.0 * 255.0);
    }
}
