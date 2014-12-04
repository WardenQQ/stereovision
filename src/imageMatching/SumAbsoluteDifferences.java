package imageMatching;

import image.IntensityMap;
import util.Point2D;

public class SumAbsoluteDifferences extends MatchingAlgorithmTemplate {
    @Override
    public double computeScore(Point2D origin, int disparity, IntensityMap left, IntensityMap right, int windowSize) {
        double score = 0.0;

        for (int y = 0; y < windowSize; y++) {
            for (int x = 0; x < windowSize; x++) {
                double I1 = left.getIntensity(origin.x + x, origin.y + y);
                double I2 = right.getIntensity(origin.x + x + disparity, origin.y + y);
                score += Math.abs(I1 - I2);
            }
        }

        score /= (windowSize * windowSize);

        return score;
    }

    @Override
    public boolean isBetterScore(double newScore, double bestScore) {
        return newScore <= bestScore;
    }
}
