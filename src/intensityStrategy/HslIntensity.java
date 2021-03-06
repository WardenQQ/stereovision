package intensityStrategy;

public class HslIntensity implements IntensityStrategy {
    @Override
    public double compute(int rgb) {
        int red = (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue = rgb & 0x000000ff;

        double max = red > green ? red : green;
        max = blue > max ? blue : max;

        double min = red < green ? red : green;
        min = blue < min ? blue : min;

        return (max - min) / (2.0 * 255.0);
    }
}
