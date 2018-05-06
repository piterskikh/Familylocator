package com.exubit.familylocator.core.utils.s2;


import android.support.annotation.NonNull;

import com.google.android.gms.common.internal.Preconditions;

import org.jetbrains.annotations.Contract;

public class S2CellId implements Comparable<S2CellId> {


    private static final int FACE_BITS = 3;
    private static final int NUM_FACES = 6;
    private static final long MAX_UNSIGNED = -1L;
    public static final int MAX_LEVEL = 30;
    private static final int POS_BITS = 2 * MAX_LEVEL + 1;
    private static final int MAX_SIZE = 1 << MAX_LEVEL;
    private static final int LOOKUP_BITS = 4;
    private static final int SWAP_MASK = 0x01;
    private static final int INVERT_MASK = 0x02;
    private static final int[] LOOKUP_POS = new int[1 << (2 * LOOKUP_BITS + 2)];
    private static final int[] LOOKUP_IJ = new int[1 << (2 * LOOKUP_BITS + 2)];
    private static final long WRAP_OFFSET = (long) (NUM_FACES) << POS_BITS;
    private static final int[] POS_TO_ORIENTATION =
            {SWAP_MASK, 0, 0, INVERT_MASK + SWAP_MASK};

    private static final int[][] POS_TO_IJ = {
            {0, 1, 3, 2},
            {0, 2, 3, 1},
            {3, 2, 0, 1},
            {3, 1, 0, 2},
    };

    static {
        initLookupCell(0, 0, 0, 0, 0, 0);
        initLookupCell(0, 0, 0, SWAP_MASK, 0, SWAP_MASK);
        initLookupCell(0, 0, 0, INVERT_MASK, 0, INVERT_MASK);
        initLookupCell(0, 0, 0, SWAP_MASK | INVERT_MASK, 0, SWAP_MASK | INVERT_MASK);
    }

    private final long id;


    public static S2CellId fromLatLng(S2LatLng ll) {
        return fromPoint(ll.toPoint());
    }


    public S2CellId(long id) {
        this.id = id;
    }

    public long id() {
        return id;
    }


    public S2LatLng toLatLng() {
        return new S2LatLng(toPointRaw());
    }


    private static S2CellId fromPoint(S2Point p) {
        int face = S2Projections.xyzToFace(p);
        R2Vector uv = S2Projections.validFaceXyzToUv(face, p);
        int i = stToIJ(S2Projections.uvToST(uv.x()));
        int j = stToIJ(S2Projections.uvToST(uv.y()));
        return fromFaceIJ(face, i, j);
    }


    private static S2CellId fromFaceIJ(int face, int i, int j) {

        long n[] = {0, face << (POS_BITS - 33)};


        int bits = (face & SWAP_MASK);


        for (int k = 7; k >= 0; --k) {
            bits = getBits(n, i, j, k, bits);
        }

        S2CellId s = new S2CellId((((n[1] << 32) + n[0]) << 1) + 1);
        return s;
    }

    private static int stToIJ(double s) {
        final int m = MAX_SIZE / 2;
        return (int) Math
                .max(0, Math.min(2 * m - 1, Math.round(m * s + (m - 0.5))));
    }


    private static int getBits(long[] n, int i, int j, int k, int bits) {
        final int mask = (1 << LOOKUP_BITS) - 1;
        bits += (((i >> (k * LOOKUP_BITS)) & mask) << (LOOKUP_BITS + 2));
        bits += (((j >> (k * LOOKUP_BITS)) & mask) << 2);
        bits = LOOKUP_POS[bits];
        n[k >> 2] |= ((((long) bits) >> 2) << ((k & 3) * 2 * LOOKUP_BITS));
        bits &= (SWAP_MASK | INVERT_MASK);
        return bits;
    }


    @NonNull
    private S2Point toPointRaw() {
        MutableInteger i = new MutableInteger(0);
        MutableInteger j = new MutableInteger(0);
        int face = toFaceIJOrientation(i, j, null);
        int delta = isLeaf() ? 1 : (((i.intValue() ^ (((int) id) >>> 2)) & 1) != 0)
                ? 2 : 0;
        int si = (i.intValue() << 1) + delta - MAX_SIZE;
        int ti = (j.intValue() << 1) + delta - MAX_SIZE;
        return faceSiTiToXYZ(face, si, ti);
    }


    private int toFaceIJOrientation(MutableInteger pi, MutableInteger pj,
                                   MutableInteger orientation) {

        int face = this.face();
        int bits = (face & SWAP_MASK);


        for (int k = 7; k >= 0; --k) {
            bits = getBits1(pi, pj, k, bits);
        }

        if (orientation != null) {


            if ((lowestOnBit() & 0x1111111111111110L) != 0) {
                bits ^= SWAP_MASK;
            }
            orientation.setValue(bits);
        }
        return face;
    }


    @Contract(pure = true)
    private int face() {
        return (int) (id >>> POS_BITS);
    }


    private int getBits1(MutableInteger i, MutableInteger j, int k, int bits) {
        final int nbits = (k == 7) ? (MAX_LEVEL - 7 * LOOKUP_BITS) : LOOKUP_BITS;

        bits += (((int) (id >>> (k * 2 * LOOKUP_BITS + 1)) &
                ((1 << (2 * nbits)) - 1))) << 2;

        bits = LOOKUP_IJ[bits];
        i.setValue(i.intValue()
                + ((bits >> (LOOKUP_BITS + 2)) << (k * LOOKUP_BITS)));

        j.setValue(j.intValue()
                + ((((bits >> 2) & ((1 << LOOKUP_BITS) - 1))) << (k * LOOKUP_BITS)));
        bits &= (SWAP_MASK | INVERT_MASK);
        return bits;
    }


    @Contract(pure = true)
    private long lowestOnBit() {
        return id & -id;
    }

    @Contract(pure = true)
    private boolean isLeaf() {
        return ((int) id & 1) != 0;
    }

    @NonNull
    private static S2Point faceSiTiToXYZ(int face, int si, int ti) {
        final double kScale = 1.0 / MAX_SIZE;
        double u = S2Projections.stToUV(kScale * si);
        double v = S2Projections.stToUV(kScale * ti);
        return S2Projections.faceUvToXyz(face, u, v);
    }

    private static void initLookupCell(int level, int i, int j,
                                       int origOrientation, int pos, int orientation) {
        if (level == LOOKUP_BITS) {
            int ij = (i << LOOKUP_BITS) + j;
            LOOKUP_POS[(ij << 2) + origOrientation] = (pos << 2) + orientation;
            LOOKUP_IJ[(pos << 2) + origOrientation] = (ij << 2) + orientation;
        } else {
            level++;
            i <<= 1;
            j <<= 1;
            pos <<= 2;
            // Initialize each sub-cell recursively.
            for (int subPos = 0; subPos < 4; subPos++) {
                int ij = posToIJ(orientation, subPos);
                int orientationMask = posToOrientation(subPos);
                initLookupCell(level, i + (ij >>> 1), j + (ij & 1), origOrientation,
                        pos + subPos, orientation ^ orientationMask);
            }
        }
    }

    @Override
    public int compareTo(S2CellId that) {
        return unsignedLongLessThan(this.id, that.id) ? -1 :
                unsignedLongGreaterThan(this.id, that.id) ? 1 : 0;
    }

    @Contract(pure = true)
    private static boolean unsignedLongLessThan(long x1, long x2) {
        return (x1 + Long.MIN_VALUE) < (x2 + Long.MIN_VALUE);
    }

    @Contract(pure = true)
    private static boolean unsignedLongGreaterThan(long x1, long x2) {
        return (x1 + Long.MIN_VALUE) > (x2 + Long.MIN_VALUE);
    }

    private static int posToIJ(int orientation, int position) {
        Preconditions.checkArgument(0 <= orientation && orientation < 4);
        Preconditions.checkArgument(0 <= position && position < 4);
        return POS_TO_IJ[orientation][position];
    }

    @Contract(pure = true)
    private static int posToOrientation(int position) {
        Preconditions.checkArgument(0 <= position && position < 4);
        return POS_TO_ORIENTATION[position];
    }

}
