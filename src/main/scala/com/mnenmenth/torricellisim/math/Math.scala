package com.mnenmenth.torricellisim.math

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
object Math {



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
  def maxHeight(Vo: Double, θ: Double, g: Double = G): Double = ((Vo*Vo) * math.pow(math.sin(θ), 2) / (2 * g)

  /**
    *
    * @param Vo Initial Velocity
    * @param θ Initial angle
    * @param g Acceleration due to gravity
    * @return Horizontal range
    */
  def horizontalRange(Vo: Double, θ: Double, g: Double = G): Double = ((Vo*Vo) * (2 * math.sin(θ))) / g
}
