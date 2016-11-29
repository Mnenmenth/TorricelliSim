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
  def toAwtPoint: java.awt.Point = new java.awt.Point(numT.toInt(x), numT.toInt(y))
  def toDimension: java.awt.Dimension = new java.awt.Dimension(numT.toInt(x), numT.toInt(y))
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

  def distance(hVel: Double, height: Double, g: Double = G): Double = hVel * math.sqrt((2*height)/g)
  def distance(hVel: Double, time: Double): Double = hVel * time

  def time(height: Double, g: Double = G): Double = math.sqrt((2*height)/g)
  def vVel(time: Double, g: Double = G): Double = -time * g

  def impactAngle(vVel: Double, hVel: Double) = math.atan(vVel/hVel)

}
