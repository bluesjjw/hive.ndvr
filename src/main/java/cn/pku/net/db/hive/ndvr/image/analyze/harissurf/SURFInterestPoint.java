package cn.pku.net.db.hive.ndvr.image.analyze.harissurf;

import java.util.Arrays;

import cn.pku.net.db.hive.ndvr.image.analyze.search.cluster.Clusterable;

public class SURFInterestPoint implements Cloneable, InterestPoint, Clusterable {
    private static final long serialVersionUID = 1L;

    private float             mX, mY;

    private float             mScale;

    private float             mOrientation;

    private int               mLaplacian;

    private float[]           mDescriptor;

    private float             mDx, mDy;

    private int               mClusterIndex;

    public SURFInterestPoint(float x, float y, float scale, int laplacian) {
        mX = x;
        mY = y;
        mScale = scale;
        mLaplacian = laplacian;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getScale() {
        return mScale;
    }

    public float getOrientation() {
        return mOrientation;
    }

    public void setOrientation(float orientation) {
        mOrientation = orientation;
    }

    public int getLaplacian() {
        return mLaplacian;
    }

    public float[] getDescriptor() {
        return mDescriptor;
    }

    /**
     * To take care of the InterestPoint Interface
     */
    @Override
    public float[] getLocation() {
        return mDescriptor;
    }

    public void setDescriptor(float[] descriptor) {
        mDescriptor = descriptor;
    }

    public float getDx() {
        return mDx;
    }

    public void setDx(float dx) {
        mDx = dx;
    }

    public float getDy() {
        return mDy;
    }

    public void setDy(float dy) {
        mDy = dy;
    }

    public int getClusterIndex() {
        return mClusterIndex;
    }

    public void setClusterIndex(int clusterIndex) {
        mClusterIndex = clusterIndex;
    }

    @Override
    public double getDistance(InterestPoint point) {
        float sum = 0;
        float[] location = point.getLocation();
        if (location == null || mDescriptor == null)
            return Float.MAX_VALUE;

        for (int i = 0; i < mDescriptor.length; i++) {
            double diff = mDescriptor[i] - location[i];
            sum += diff * diff;
        }
        return sum;
        //return (double)Math.sqrt(sum);
    }

    public Float getCoord(int dimension) {
        return mDescriptor[dimension];
    }

    public int getDimensions() {
        return mDescriptor.length;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isEquivalentTo(SURFInterestPoint point) {
        boolean isEquivalent = true;

        isEquivalent &= mX == point.getX();
        isEquivalent &= mY == point.getY();

        isEquivalent &= mDx == point.getDx();
        isEquivalent &= mDy == point.getDy();

        isEquivalent &= mOrientation == point.getOrientation();

        isEquivalent &= mScale == point.getScale();

        isEquivalent &= mLaplacian == point.getLaplacian();

        isEquivalent &= Arrays.equals(mDescriptor, point.getDescriptor());

        return isEquivalent;
    }

    public String getDescriptorString() {
        StringBuilder sb = new StringBuilder();
        for (float descriptor : mDescriptor) {
            sb.append(descriptor);
            sb.append(",");
        }
        return sb.toString();
    }
}
