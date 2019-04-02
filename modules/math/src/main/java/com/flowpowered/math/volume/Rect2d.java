package com.flowpowered.math.volume;

import com.flowpowered.math.AllocationTracker;
import com.flowpowered.math.GenericMath;
import com.flowpowered.math.HashFunctions;
import com.flowpowered.math.vector.Vec2d;
import com.flowpowered.math.vector.Vec4d;
import org.jetbrains.annotations.NotNull;

public class Rect2d {
    private static final long serialVersionUID = 1;

    public static final Rect2d ZERO = new Rect2d(0, 0, 0, 0);
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private transient volatile boolean hashed = false;
    private transient volatile int hashCode = 0;

    public Rect2d() {
        this(0, 0, 0, 0);
    }

    public Rect2d(@NotNull Vec2d pos, @NotNull Vec2d size) {
        this(pos.getX(), pos.getY(), size.getX(), size.getY());
    }

    public Rect2d(@NotNull Rect2d rect) {
        this(rect.x, rect.y, rect.width, rect.height);
    }

    public Rect2d(float x, float y, float width, float height) {
        this((double) x, (double) y, (double) width, (double) height);
    }

    public Rect2d(double x, double y, double width, double height) {
        if(width < 0) {
            double tmp = x;
            x = x + width;
            width = tmp;
        }
        if(height < 0) {
            double tmp = y;
            y = y + height;
            height = tmp;
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        AllocationTracker.instancesRect2d++;
    }

    public double getX() {
        return x;
    }
    /** Operator function for Kotlin */
    public double component1() {
        return getX();
    }

    public double getY() {
        return y;
    }
    /** Operator function for Kotlin */
    public double component2() {
        return getY();
    }

    public double getWidth() {
        return width;
    }
    /** Operator function for Kotlin */
    public double component3() {
        return getWidth();
    }

    public double getHeight() {
        return height;
    }
    /** Operator function for Kotlin */
    public double component4() {
        return getHeight();
    }

    @NotNull
    public Vec2d getPos() {
        return Vec2d.createPooled(x, y);
    }

    @NotNull
    public Vec2d getSize() {
        return Vec2d.createPooled(width, height);
    }

    public boolean contains(@NotNull Vec2d v) {
        return this.getX() <= v.getX() && v.getX() <= this.getX() + this.getWidth() &&
                this.getY() <= v.getY() && v.getY() <= this.getY() + this.getHeight();
    }

    @NotNull
    public Rect2d offset(@NotNull Vec2d v) {
        return offset(v.getX(), v.getY());
    }

    @NotNull
    public Rect2d offset(float x, float y) {
        return offset((double) x, (double) y);
    }

    @NotNull
    public Rect2d offset(double x, double y) {
        return new Rect2d(this.x + x, this.y + y, this.width, this.height);
    }

    @NotNull
    public double[] toArray() {
        return new double[]{x, y, width, height};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rect2d)) {
            return false;
        }
        final Rect2d Rect2 = (Rect2d) o;
        if (Double.compare(Rect2.x, x) != 0) {
            return false;
        }
        if (Double.compare(Rect2.y, y) != 0) {
            return false;
        }
        if (Double.compare(Rect2.width, width) != 0) {
            return false;
        }
        if (Double.compare(Rect2.height, height) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            final int result = (x != +0.0f ? HashFunctions.hash(x) : 0);
            hashCode = 31 * result + (y != +0.0f ? HashFunctions.hash(y) : 0);
            hashCode = 31 * result + (width != +0.0f ? HashFunctions.hash(width) : 0);
            hashCode = 31 * result + (height != +0.0f ? HashFunctions.hash(height) : 0);
            hashed = true;
        }
        return hashCode;
    }

    @NotNull
    @Override
    public Rect2d clone() {
        return new Rect2d(this);
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + width + ", " + height + ")";
    }
}
