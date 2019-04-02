package com.flowpowered.math;

public class AllocationTracker {
    public static volatile long instancesComplexd = 0;
    public static volatile long instancesComplexf = 0;

    public static volatile long instancesQuaterniond = 0;
    public static volatile long instancesQuaternionf = 0;

    public static volatile long instancesMatrix2d = 0;
    public static volatile long instancesMatrix2f = 0;

    public static volatile long instancesMatrix3d = 0;
    public static volatile long instancesMatrix3f = 0;

    public static volatile long instancesMatrix4d = 0;
    public static volatile long instancesMatrix4f = 0;

    public static volatile long instancesMatrixNd = 0;
    public static volatile long instancesMatrixNf = 0;
    public static volatile long componentsMatrixNd = 0;
    public static volatile long componentsMatrixNf = 0;

    public static volatile long instancesVec2d = 0;
    public static volatile long instancesVec2f = 0;
    public static volatile long instancesVec2i = 0;
    public static volatile long instancesVec2l = 0;

    public static volatile long instancesVec3d = 0;
    public static volatile long instancesMCVec3d = 0;
    public static volatile long instancesVec3f = 0;
    public static volatile long instancesVec3i = 0;
    public static volatile long instancesVec3l = 0;

    public static volatile long instancesVec4d = 0;
    public static volatile long instancesVec4f = 0;
    public static volatile long instancesVec4i = 0;
    public static volatile long instancesVec4l = 0;

    public static volatile long instancesVecNd = 0;
    public static volatile long instancesVecNf = 0;
    public static volatile long instancesVecNi = 0;
    public static volatile long instancesVecNl = 0;
    public static volatile long componentsVecNd = 0;
    public static volatile long componentsVecNf = 0;
    public static volatile long componentsVecNi = 0;
    public static volatile long componentsVecNl = 0;

    public static volatile long instancesRect2d = 0;
    public static volatile long instancesRect2f = 0;
    public static volatile long instancesRect2i = 0;
    public static volatile long instancesRect2l = 0;

    public static volatile long instancesBox3d = 0;
    public static volatile long instancesBox3f = 0;
    public static volatile long instancesBox3i = 0;
    public static volatile long instancesBox3l = 0;

    public static volatile long cacheHitsVec2d = 0;
    public static volatile long cacheHitsVec2f = 0;
    public static volatile long cacheHitsVec2i = 0;
    public static volatile long cacheHitsVec2l = 0;

    public static volatile long cacheHitsVec3d = 0;
    public static volatile long cacheHitsMCVec3d = 0;
    public static volatile long cacheHitsVec3f = 0;
    public static volatile long cacheHitsVec3i = 0;
    public static volatile long cacheHitsVec3l = 0;

    public static volatile long cacheHitsVec4d = 0;
    public static volatile long cacheHitsVec4f = 0;
    public static volatile long cacheHitsVec4i = 0;
    public static volatile long cacheHitsVec4l = 0;

    // size approximations based upon: https://ordepdev.me/posts/size-of-an-object-in-java
    private static final int HEAD_SIZE = 16;
    private static final int ARRAY_HEAD_SIZE = HEAD_SIZE + 24 + 4 + 4;
    private static final int BOOLEAN_SIZE = 1;
    private static final int BYTE_SIZE = 1;
    private static final int CHAR_SIZE = 2;
    private static final int SHORT_SIZE = 2;
    private static final int INT_SIZE = 4;
    private static final int LONG_SIZE = 8;
    private static final int FLOAT_SIZE = 4;
    private static final int DOUBLE_SIZE = 8;
    private static int pad(int size) {
        return ((size + 7) / 8) * 8; // ceil(size/8) * 8
    }

    public static int sizeComplexd = pad(HEAD_SIZE + DOUBLE_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeComplexf = pad(HEAD_SIZE + FLOAT_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeQuaterniond = pad(HEAD_SIZE + DOUBLE_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeQuaternionf = pad(HEAD_SIZE + FLOAT_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeMatrix2d = pad(HEAD_SIZE + DOUBLE_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeMatrix2f = pad(HEAD_SIZE + FLOAT_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeMatrix3d = pad(HEAD_SIZE + DOUBLE_SIZE * 9 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeMatrix3f = pad(HEAD_SIZE + FLOAT_SIZE * 9 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeMatrix4d = pad(HEAD_SIZE + DOUBLE_SIZE * 16 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeMatrix4f = pad(HEAD_SIZE + FLOAT_SIZE * 16 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeInstanceMatrixNd = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeInstanceMatrixNf = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeComponentMatrixNd = DOUBLE_SIZE;
    public static int sizeComponentMatrixNf = FLOAT_SIZE;

    public static int sizeVec2d = pad(HEAD_SIZE + DOUBLE_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec2f = pad(HEAD_SIZE + FLOAT_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec2i = pad(HEAD_SIZE + INT_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec2l = pad(HEAD_SIZE + LONG_SIZE * 2 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeVector3d = pad(HEAD_SIZE + DOUBLE_SIZE * 3 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec3f = pad(HEAD_SIZE + FLOAT_SIZE * 3 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec3i = pad(HEAD_SIZE + INT_SIZE * 3 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec3l = pad(HEAD_SIZE + LONG_SIZE * 3 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeVec4d = pad(HEAD_SIZE + DOUBLE_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec4f = pad(HEAD_SIZE + FLOAT_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec4i = pad(HEAD_SIZE + INT_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);
    public static int sizeVec4l = pad(HEAD_SIZE + LONG_SIZE * 4 + BOOLEAN_SIZE + INT_SIZE);

    public static int sizeInstanceVecNd = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeInstanceVecNf = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeInstanceVecNi = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeInstanceVecNl = pad(HEAD_SIZE + ARRAY_HEAD_SIZE);
    public static int sizeComponentVecNd = DOUBLE_SIZE;
    public static int sizeComponentVecNf = FLOAT_SIZE;
    public static int sizeComponentVecNi = INT_SIZE;
    public static int sizeComponentVecNl = LONG_SIZE;
}
