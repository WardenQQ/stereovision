package imageMatching;


import image.IntensityMap;
import util.Point2D;

public class NormalizedCrossCorrelation extends MatchingAlgorithmTemplate {
    @Override
    public double computeScore(Point2D origin, int disparity, IntensityMap left, IntensityMap right, int windowSize) {
        return 0.0;
    }

    @Override
    public boolean isBetterScore(double newScore, double bestScore) {
        return false;
    }
}
