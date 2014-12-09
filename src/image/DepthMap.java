package image;

public class DepthMap {
    private double[] depth;
    private int width;
    private int height;

    public DepthMap(DisparityMap disparity, double baseLine, double focalLength, double ccdWidth) {
        width = disparity.getWidth();
        height = disparity.getHeight();
        depth = new double[width * height];

        double ratio = baseLine * focalLength * (width / ccdWidth);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double depth = 0.0;

                if (disparity.getDisparity(x, y) != 0) {
                    depth = ratio / disparity.getDisparity(x, y);
                }

                setDepth(x, y , depth);
            }
        }
    }

    public double getDepth(int x, int y) {
        return depth[x + y * width];
    }

    private void setDepth(int x, int y, double value) {
        depth[x + y * width] = value;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
