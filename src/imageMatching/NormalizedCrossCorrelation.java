package imageMatching;


import image.IntensityMap;
import util.Point2D;

public class NormalizedCrossCorrelation extends MatchingAlgorithmTemplate {
    public static final double WORST_SCORE = -1.0;
    private double computeArithmeticMean(Point2D origin, IntensityMap intensity, int windowSize) {
        double sum = 0.0;

        for (int y = 0; y < windowSize; y++) {
            for (int x = 0; x < windowSize; x++) {
                sum += intensity.getIntensity(origin.x + x, origin.y + y);
            }
        }

        return sum / (windowSize * windowSize);
    }

    private double computeStandardDeviation(Point2D origin, IntensityMap intensity, int windowSize, double arithmeticMean) {
        double sum = 0.0;

        for (int y = 0; y < windowSize; y++) {
            for (int x = 0; x < windowSize; x++) {
                double term = intensity.getIntensity(origin.x + x, origin.y + y) - arithmeticMean;
                sum += term * term;
            }
        }

        return Math.sqrt(sum);
    }

    @Override
    public double computeScore(Point2D origin, int disparity, IntensityMap left, IntensityMap right, int windowSize) {
        Point2D rightOrigin = new Point2D(origin.x + disparity, origin.y);

        double leftMean = computeArithmeticMean(origin, left, windowSize);
        double rightMean = computeArithmeticMean(rightOrigin, right, windowSize);

        double leftDeviation = computeStandardDeviation(origin, left, windowSize, leftMean);
        double rightDeviation = computeStandardDeviation(rightOrigin, right, windowSize, rightMean);

        double score = WORST_SCORE;

        for (int y = 0; y < windowSize; y++) {
            for (int x = 0; x < windowSize; x++) {
                score += (left.getIntensity(origin.x + x, origin.y + y) - leftMean) *
                        (right.getIntensity(origin.x + x + disparity, origin.y + y) - rightMean);
            }
        }

        score /= leftDeviation * rightDeviation;

        return score / (windowSize * windowSize);
    }

    @Override
    public boolean isBetterScore(double newScore, double bestScore) {
        return newScore > bestScore;
    }
}
