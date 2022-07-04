package net.galaxycore.capturetheblock.bezier

import net.galaxycore.capturetheblock.math.plus
import net.galaxycore.capturetheblock.math.times
import javax.vecmath.Vector3d
import kotlin.math.pow


/**
 * Calculates the Bézier curve point at t using the Bernstein polynomials
 *
 *
 * Why is it implemented this way?
 * First, take a look at the De Casteljau's Algorithm
 *
 * ```lambda
 * A = lerp(P0, P1, t)
 * B = lerp(P1, P2, t)
 * C = lerp(P2, P3, t)
 * D = lerp(A, B, t)
 * E = lerp(B, C, t)
 * P = lerp(D, E, t)
 * ```
 *
 * Kinda long and complicated... There is a better way to do this with polynomials: Bernstein's Polynomial
 *
 *
 * Bernstein polynomials
 * ```lambda
 * P(t) -> x,y =
 *                P0( -t^3 + 3t^2 - 3t + 1 ) +
 *                P1( 3t^3 - 6t^2 + 3t ) +
 *                P2( -3t^3 + 3t^2 ) +
 *                P3( t^3 )
 * P'(t) - x,y =
 *                P0( -3t^2 + 6t - 3 ) +
 *                P1( 6t^2 - 12t ) +
 *                P2( -6t^2 + 6t ) +
 *                P3( 3t^2 )
 * ```
 *
 * The deriviative of the polynomial is the tangent vector of the curve at t
 * It can be calculated as the vector of the derivative of the polynomial
 *
 * @param p0 The first point of the curve
 * @param p1 The first anchor of the curve
 * @param p2 the second point of the curve
 * @param p3 the second anchor of the curve
 * @param t The t value between 0 and 1
 * @return The point at t
 *
 * @see <a href="http://en.wikipedia.org/wiki/Bernstein_polynomial">Bernstein polynomial</a>
 *
 */
fun bernsteinBezier(p0: Vector3d, p1: Vector3d, p2: Vector3d, p3: Vector3d, t: Double): Vector3d {
    return p0.times(-t.pow(3) + 3 * t.pow(2) - 3 * t + 1) +
            p1.times(3 * t.pow(3) - 6 * t.pow(2) + 3 * t) +
            p2.times(-3 * t.pow(3) + 3 * t.pow(2)) +
            p3.times(t.pow(3))
}

