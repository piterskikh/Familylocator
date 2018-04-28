package com.exubit.familylocator.utils.s2;

public class Metric {


        private final double deriv;
        private final int dim;
        public static final double M_SQRT2 = Math.sqrt(2);

        /**
         * Defines a cell metric of the given dimension (1 == length, 2 == area).
         */
        public Metric(int dim, double deriv) {
            this.deriv = deriv;
            this.dim = dim;
        }


        private static final int EXPONENT_SHIFT = 52;
        // Mask to extract the exponent from a double.
        private static final long EXPONENT_MASK = 0x7ff0000000000000L;

        /**
         * If v is non-zero, return an integer {@code exp} such that
         * {@code (0.5 <= |v|*2^(-exp) < 1)}. If v is zero, return 0.
         *
         * <p>Note that this arguably a bad definition of exponent because it makes
         * {@code exp(9) == 4}. In decimal this would be like saying that the
         * exponent of 1234 is 4, when in scientific 'exponent' notation 1234 is
         * {@code 1.234 x 10^3}.
         *
         * TODO(dbeaumont): Replace this with "DoubleUtils.getExponent(v) - 1" ?
         */

        static int exp(double v) {
            if (v == 0) {
                return 0;
            }
            long bits = Double.doubleToLongBits(v);
            return (int) ((EXPONENT_MASK & bits) >> EXPONENT_SHIFT) - 1022;
        }
        /**
         * The "deriv" value of a metric is a derivative, and must be multiplied by
         * a length or area in (s,t)-space to get a useful value.
         */
        public double deriv() {
            return deriv;
        }

        /** Return the value of a metric for cells at the given level. */
        public double getValue(int level) {
            return StrictMath.scalb(deriv, dim * (1 - level));
        }

        /**
         * Return the level at which the metric has approximately the given value.
         * For example, S2::kAvgEdge.GetClosestLevel(0.1) returns the level at which
         * the average cell edge length is approximately 0.1. The return value is
         * always a valid level.
         */
        public int getClosestLevel(double value) {
            return getMinLevel(M_SQRT2 * value);
        }

        /**
         * Return the minimum level such that the metric is at most the given value,
         * or S2CellId::kMaxLevel if there is no such level. For example,
         * S2::kMaxDiag.GetMinLevel(0.1) returns the minimum level such that all
         * cell diagonal lengths are 0.1 or smaller. The return value is always a
         * valid level.
         */
        public int getMinLevel(double value) {
            if (value <= 0) {
                return S2CellId.MAX_LEVEL;
            }

            // This code is equivalent to computing a floating-point "level"
            // value and rounding up.
            int exponent = exp(value / ((1 << dim) * deriv));
            int level = Math.max(0,
                    Math.min(S2CellId.MAX_LEVEL, -((exponent - 1) >> (dim - 1))));
            // assert (level == S2CellId.MAX_LEVEL || getValue(level) <= value);
            // assert (level == 0 || getValue(level - 1) > value);
            return level;
        }

        /**
         * Return the maximum level such that the metric is at least the given
         * value, or zero if there is no such level. For example,
         * S2.kMinWidth.GetMaxLevel(0.1) returns the maximum level such that all
         * cells have a minimum width of 0.1 or larger. The return value is always a
         * valid level.
         */
        public int getMaxLevel(double value) {
            if (value <= 0) {
                return S2CellId.MAX_LEVEL;
            }

            // This code is equivalent to computing a floating-point "level"
            // value and rounding down.
            int exponent = exp((1 << dim) * deriv / value);
            int level = Math.max(0,
                    Math.min(S2CellId.MAX_LEVEL, ((exponent - 1) >> (dim - 1))));
            // assert (level == 0 || getValue(level) >= value);
            // assert (level == S2CellId.MAX_LEVEL || getValue(level + 1) < value);
            return level;
        }

    }

