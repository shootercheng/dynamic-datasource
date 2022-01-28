package com.scd.model.po;

/**
 * @author James
 */
public class EdgeIdeaSetter {
    private String sPoint;
    private String tPoint;

    public String getsPoint() {
        return sPoint;
    }

    public void setsPoint(String sPoint) {
        this.sPoint = sPoint;
    }

    public String gettPoint() {
        return tPoint;
    }

    public void settPoint(String tPoint) {
        this.tPoint = tPoint;
    }

    @Override
    public String toString() {
        return "EdgeIdeaSetter{" +
                "sPoint='" + sPoint + '\'' +
                ", tPoint='" + tPoint + '\'' +
                '}';
    }
}
