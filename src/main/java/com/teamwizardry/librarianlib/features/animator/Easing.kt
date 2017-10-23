package com.teamwizardry.librarianlib.features.animator









/**
 * An easing function. Used to make more natural transitions.
 */
abstract class Easing {

    /**
     * @param progress A progress value from 0-1
     * @return A float value for interpolation. This value **is not** guaranteed to be between 0 and 1. If your
     *           interpolation algorithm requires a 0-1 value you **must** clamp this to that range.
     */
    abstract operator fun invoke(progress: Float): Float

    /**
     * Source: http://greweb.me/2012/02/bezier-curve-based-easing-functions-from-concept-to-implementation/
     *
     * To create your own, visit http://cubic-bezier.com/
     */
    class BezierEasing(val mX1: Float, val mY1: Float, val mX2: Float, val mY2: Float) : Easing() {
        constructor(mX1: Number, mY1: Number, mX2: Number, mY2: Number) : this(mX1.toFloat(), mY1.toFloat(), mX2.toFloat(), mY2.toFloat())

        override fun invoke(progress: Float): Float {
            if (mX1 == mY1 && mX2 == mY2) return progress // linear
            return CalcBezier(GetTForX(progress), mY1, mY2)
        }

        private fun A(aA1: Float, aA2: Float): Float { return 1f - 3f * aA2 + 3f * aA1 }
        private fun B(aA1: Float, aA2: Float): Float { return 3f * aA2 - 6f * aA1 }
        private fun C(aA1: Float): Float      { return 3f * aA1 }
        // Returns x(t) given t, x1, and x2, or y(t) given t, y1, and y2.
        private fun CalcBezier(aT: Float, aA1: Float, aA2: Float): Float {
            return ((A(aA1, aA2)*aT + B(aA1, aA2))*aT + C(aA1))*aT
        }
        // Returns dx/dt given t, x1, and x2, or dy/dt given t, y1, and y2.
        private fun GetSlope(aT: Float, aA1: Float, aA2: Float): Float {
            return 3f * A(aA1, aA2)*aT*aT + 2f * B(aA1, aA2) * aT + C(aA1)
        }
        private fun GetTForX(aX: Float): Float {
            // Newton raphson iteration
            var aGuessT = aX
            for (i in 0..3) {
                val currentSlope = GetSlope(aGuessT, mX1, mX2)
                if (currentSlope == 0f) return aGuessT
                val currentX = CalcBezier(aGuessT, mX1, mX2) - aX
                aGuessT -= currentX / currentSlope
            }
            return aGuessT
        }
    }

    abstract class PenningEasing : Easing() {
        // variables used in the penning formulas
        val b = 0f
        val c = 1f
        val d = 1f
    }

    @Suppress("unused")
    companion object {
        @JvmField val linear = object : Easing() {
            override fun invoke(progress: Float): Float {
                return progress
            }
        }

        /** http://easings.net/#easeInSine */
        @JvmField val easeInSine = BezierEasing(0.47, 0, 0.745, 0.715)
        /** http://easings.net/#easeOutSine */
        @JvmField val easeOutSine = BezierEasing(0.39, 0.575, 0.565, 1)
        /** http://easings.net/#easeInOutSine */
        @JvmField val easeInOutSine = BezierEasing(0.445, 0.05, 0.55, 0.95)
        /** http://easings.net/#easeInQuad */
        @JvmField val easeInQuad = BezierEasing(0.55, 0.085, 0.68, 0.53)
        /** http://easings.net/#easeOutQuad */
        @JvmField val easeOutQuad = BezierEasing(0.25, 0.46, 0.45, 0.94)
        /** http://easings.net/#easeInOutQuad */
        @JvmField val easeInOutQuad = BezierEasing(0.455, 0.03, 0.515, 0.955)
        /** http://easings.net/#easeInCubic */
        @JvmField val easeInCubic = BezierEasing(0.55, 0.055, 0.675, 0.19)
        /** http://easings.net/#easeOutCubic */
        @JvmField val easeOutCubic = BezierEasing(0.215, 0.61, 0.355, 1)
        /** http://easings.net/#easeInOutCubic */
        @JvmField val easeInOutCubic = BezierEasing(0.645, 0.045, 0.355, 1)
        /** http://easings.net/#easeInQuart */
        @JvmField val easeInQuart = BezierEasing(0.895, 0.03, 0.685, 0.22)
        /** http://easings.net/#easeOutQuart */
        @JvmField val easeOutQuart = BezierEasing(0.165, 0.84, 0.44, 1)
        /** http://easings.net/#easeInOutQuart */
        @JvmField val easeInOutQuart = BezierEasing(0.77, 0, 0.175, 1)
        /** http://easings.net/#easeInQuint */
        @JvmField val easeInQuint = BezierEasing(0.755, 0.05, 0.855, 0.06)
        /** http://easings.net/#easeOutQuint */
        @JvmField val easeOutQuint = BezierEasing(0.23, 1, 0.32, 1)
        /** http://easings.net/#easeInOutQuint */
        @JvmField val easeInOutQuint = BezierEasing(0.86, 0, 0.07, 1)
        /** http://easings.net/#easeInExpo */
        @JvmField val easeInExpo = BezierEasing(0.95, 0.05, 0.795, 0.035)
        /** http://easings.net/#easeOutExpo */
        @JvmField val easeOutExpo = BezierEasing(0.19, 1, 0.22, 1)
        /** http://easings.net/#easeInOutExpo */
        @JvmField val easeInOutExpo = BezierEasing(1, 0, 0, 1)
        /** http://easings.net/#easeInCirc */
        @JvmField val easeInCirc = BezierEasing(0.6, 0.04, 0.98, 0.335)
        /** http://easings.net/#easeOutCirc */
        @JvmField val easeOutCirc = BezierEasing(0.075, 0.82, 0.165, 1)
        /** http://easings.net/#easeInOutCirc */
        @JvmField val easeInOutCirc = BezierEasing(0.785, 0.135, 0.15, 0.86)
        /** http://easings.net/#easeInBack */
        @JvmField val easeInBack = BezierEasing(0.6, -0.28, 0.735, 0.045)
        /** http://easings.net/#easeOutBack */
        @JvmField val easeOutBack = BezierEasing(0.175, 0.885, 0.32, 1.275)
        /** http://easings.net/#easeInOutBack */
        @JvmField val easeInOutBack = BezierEasing(0.68, -0.55, 0.265, 1.55)
        /** http://easings.net/#easeInElastic */
        @JvmField val easeInElastic = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                var t = progress / d
                if (t == 0f) return b
                if (t == 1f) return b + c
                val p = d * .3f
                val a = c
                val s = p / 4
                t -= 1
                return -(a * Math.pow(2.0, (10 * t).toDouble()).toFloat() * Math.sin((t * d - s) * (2 * Math.PI.toFloat()) / p.toDouble()).toFloat()) + b
            }
        }
        /** http://easings.net/#easeOutElastic */
        @JvmField val easeOutElastic = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                val t = progress / d
                if (t == 0f) return b
                if (t == 1f) return b + c
                val p = d * .3f
                val a = c
                val s = p / 4
                return a * Math.pow(2.0, (-10 * t).toDouble()).toFloat() * Math.sin((t * d - s) * (2 * Math.PI.toFloat()) / p.toDouble()).toFloat() + c + b
            }
        }
        /** http://easings.net/#easeInOutElastic */
        @JvmField val easeInOutElastic = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                var t = progress / (d/2)
                if (t == 0f) return b
                if (t == 2f) return b + c
                val p = d * (.3f * 1.5f)
                val a = c
                val s = p / 4
                t -= 1
                if (t < 1) return -.5f * (a * Math.pow(2.0, (10 * t).toDouble()).toFloat() * Math.sin((t * d - s) * (2 * Math.PI.toFloat()) / p.toDouble()).toFloat()) + b
                t -= 1
                return a * Math.pow(2.0, (-10 * t).toDouble()).toFloat() * Math.sin((t * d - s) * (2 * Math.PI.toFloat()) / p.toDouble()).toFloat() * .5f + c + b
            }
        }
        /** http://easings.net/#easeInBounce */
        @JvmField val easeInBounce = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                var t = progress / d
                return c - Easing.easeOutBounce(d-t) + b
            }
        }
        /** http://easings.net/#easeOutBounce */
        @JvmField val easeOutBounce = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                var t = progress / d
                if (t < (1/2.75f)) {
                    return c*(7.5625f*t*t) + b
                } else if (t < (2/2.75f)) {
                    t-=(1.5f/2.75f)
                    return c*(7.5625f*t*t + .75f) + b
                } else if (t < (2.5/2.75)) {
                    t-=(2.25f/2.75f)
                    return c*(7.5625f*t*t + .9375f) + b
                } else {
                    t-=(2.625f/2.75f)
                    return c*(7.5625f*t*t + .984375f) + b
                }
            }
        }
        /** http://easings.net/#easeInOutBounce */
        @JvmField val easeInOutBounce = object : PenningEasing() {
            override fun invoke(progress: Float): Float {
                var t = progress
                if (t < d/2) return Easing.easeInBounce(t*2) * .5f + b
                else return Easing.easeOutBounce(t*2-d) * .5f + c*.5f + b
            }
        }
    }

}
