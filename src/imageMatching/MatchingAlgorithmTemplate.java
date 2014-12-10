package imageMatching;

import util.Point2D;
import image.IntensityMap;

public abstract class MatchingAlgorithmTemplate {
    public abstract double getWorstScore();

    public abstract double computeScore(Point2D origin, int disparity, IntensityMap left, IntensityMap right, int windowSize);

    public abstract boolean isBetterScore(double newScore, double bestScore);

    public int findBestDisparity(
            Point2D center,
            IntensityMap left,
            IntensityMap right,
            int windowSize,
            int numberDisparity,
            double threshold
    ) {
        int bestDisparity = 0;
        double bestScore = threshold;

        Point2D origin = new Point2D(center.x - windowSize / 2, center.y - windowSize / 2);
        if (origin.x < 0 || origin.x + windowSize >= left.getWidth() ||
            origin.y < 0 || origin.y + windowSize >= left.getHeight()) {
            return 0;
        } else {
            numberDisparity = origin.x < numberDisparity ? origin.x : numberDisparity;

            for (int d = -numberDisparity; d <= 0; d++) {
                double currentScore = computeScore(origin, d, left, right, windowSize);

                if (isBetterScore(currentScore, bestScore)) {
                    bestDisparity = d;
                    bestScore = currentScore;
                }
            }
        }

        return Math.abs(bestDisparity);
    }
}
