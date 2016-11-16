package com.mnenmenth.torricellisim.math

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

/**
  * Point
  * @param x X Coordinate
  * @param y Y Coordinate
  * @param numT Implicit numeric type
  * @tparam T Implicit numeric type
  */
case class Point[T](var x: T, var y: T)(implicit numT:Numeric[T]){
  def +(p:Point[T]): Point[T] = Point(numT.plus(x, p.x), numT.plus(y, p.y))
  def -(p:Point[T]): Point[T]= Point(numT.minus(x, p.x), numT.minus(y, p.y))
  def *(p: Point[T]): Point[T] = Point(numT.times(x, p.x), numT.times(y, p.y))
  def toInt: Point[Int] = Point(numT.toInt(x), numT.toInt(y))
  def toDouble: Point[Double] = Point(numT.toDouble(x), numT.toDouble(y))
  def toFloat: Point[Float] = Point(numT.toFloat(x), numT.toFloat(y))
  def toLong: Point[Float] = Point(numT.toLong(x), numT.toLong(y))
  override def toString = s"($x,$y)"
}

object TorricellisLaw {

  val G = 9.8

  /**
    *
    * @param h Height of fluid
    * @param g Acceleration due to gravity
    * @return Velocity of fluid
    */
  def velocity(h: Double, g: Double = G): Double =  math.sqrt(2*g*h)

  /**
    *
    * @param v Velocity of fluid
    * @param h Height of fluid
    * @return Acceleration due to gravity
    */
  def gravity(v: Double, h: Double): Double = (v*v)/(2*h)

  /**
    *
    * @param v Velocity of fluid
    * @param g Acceleration due to gravity
    * @return Height of fluid
    */
  def liquidHeight(v: Double, g: Double = G): Double = (v*v)/(2*g)

}

object ProjectileMotion {

  val G = 9.8

  /**
    *
    * @param Vx Velocity along x
    * @param t Time taken
    * @return Horizontal distance
    */
  def horizontalDistance(Vx: Double, t: Double): Double = Vx * t

  /**
    *
    * @param Vyo Initial velocity along y
    * @param t Time taken
    * @param g Acceleration due to gravity
    * @return Vertical distance
    */
  def verticalDistance(Vyo: Double, t: Double, g: Double = G): Double = (Vyo * t) - (.5 * g * (t*t))

  /**
    *
    * @param Vyo Initial velocity along y
    * @param t Time taken
    * @param g Acceleration due to gravity
    * @return Vertical velocity
    */
  def verticalVelocity(Vyo: Double, t: Double, g: Double = G): Double = Vyo - (g * t)

  /**
    *
    * @param Vo Initial Velocity
    * @param θ Initial angle
    * @param g Acceleration due to gravity
    * @return Time Taken
    */
  def timeTaken(Vo: Double, θ: Double, g: Double = G): Double = (2 * Vo * math.sin(θ)) / g

  /**
    *
    * @param Vo Initial Velocity
    * @param θ Initial angle
    * @param g Acceleration due to gravity
    * @return Maximum height during flight
    */
  def maxHeight(Vo: Double, θ: Double, g: Double = G): Double = ((Vo*Vo) * math.sqrt(math.sin(θ))) / (2 * g)

  /**
    *
    * @param Vo Initial Velocity
    * @param θ Initial angle
    * @param g Acceleration due to gravity
    * @return Horizontal range
    */
  def horizontalRange(Vo: Double, θ: Double, g: Double = G): Double = ((Vo*Vo) * (2 * math.sin(θ))) / g
}