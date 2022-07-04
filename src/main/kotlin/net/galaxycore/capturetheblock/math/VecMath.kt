package net.galaxycore.capturetheblock.math

import javax.vecmath.Vector3d

fun Vector3d.times(other: Vector3d): Vector3d {
    return Vector3d(x * other.x, y * other.y, z * other.z)
}

fun Vector3d.times(other: Double): Vector3d {
    return Vector3d(x * other, y * other, z * other)
}

operator fun Vector3d.plus(other: Vector3d): Vector3d {
    this.add(other)
    return this
}