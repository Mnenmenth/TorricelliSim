package com.mnenmenth.torricellisim.drawable

import java.awt.geom.{Ellipse2D, Line2D}

import com.mnenmenth.torricellisim.drawable.CoordSys.SingleAxis
import com.mnenmenth.torricellisim.math.Point

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

/**
  * Drawable generic
  */
trait Drawable {
  def draw(g: Graphics2D): Unit = {}
}

/**
  * Line generic
  */
trait Line extends Drawable {
  private var _begin: Point[Double] = Point(0.0, 0.0)
  def begin = _begin
  def begin_=(p: Point[Double])={
    _begin = p
    val beginConv = CoordSys.c2p(begin)
    val endConv = CoordSys.c2p(end)
    line.setLine(beginConv.x, beginConv.y, endConv.x, endConv.y)
  }

  private var _end: Point[Double] = Point(0.0, 0.0)
  def end = _end
  def end_=(p: Point[Double])={
    _end = p
    val beginConv = CoordSys.c2p(begin)
    val endConv = CoordSys.c2p(end)
    line.setLine(beginConv.x, beginConv.y, endConv.x, endConv.y)
  }

  val line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

  def m = (begin.y - end.y) / (begin.x - end.x)
  def b = begin.y - (m*begin.x)

  /**
    * Calculates point of intersection between two lines
    * @param line Line to intersect with
    * @return Point of intersection
    */
  def intersection(line: Line): Point[Double] = {
    val x = (line.b - b) / (m - line.m)
    val y = m * x + b
    Point(x, y)
  }

  /**
    * Extend the line along a specific axis
    * @param axis The axis in which length is added
    *             @example add 1 to x
    * @param length Length to add to axis
    */
  def extendAlong(axis: SingleAxis.Value, length: Double = 283): Unit = {
    val m = this.m
    val b = this.b
    var end1 = Point(0.0,0.0)
    if(axis == SingleAxis.X) {
      val sign = (end.x - begin.x)/math.abs(end.x - begin.x)
      end1 = Point(end.x + sign * length, m * end.x + b)
    } else if (axis == SingleAxis.Y){
      val sign = (end.y - begin.y)/math.abs(end.y - begin.y)
      end1 = Point(end.y + sign * length, (end.y-b)/m)
    }
    end = end1
  }

  /**
    * So... If you don't remember this is what this does:
    *
    * by definition l=sqrt(dx^2 + dy^2)
    * and dy = m dx
    * So dx = +- sqrt(l^2/(m^2 +1))
    *
    * @param l0 the length to be added; l != 0, if it does, the universe will implode.
    */
  def extend(l0: Double = 283): Unit = {
    val l     = if (begin.x < end.x) l0 else -l0
    val begin1 = if (begin.x < end.x) begin else end
    val end1   = if (end.x >= begin.x) end else begin
    val dx = math.sqrt((l*l)/(m*m+1))
    val dy = m*dx
    val p = Point(dx,dy)
    begin = if(l>0) begin1   else begin1 - p
    end   = if(l>0) p + end1 else end1
  }

  /**
    * Does line intersect with given line
    * @param _line Line to test intersection with
    * @return Boolean intersection
    */
  def intersects(_line: Line2D): Boolean = line.intersectsLine(_line)

  override def draw(g: Graphics2D): Unit = {
    g.draw(line)
  }
}

/**
  * Circle generic
  */
trait Circle extends Drawable {
  private var _pos: Point[Double] = Point(0.0, 0.0)
  def pos = _pos
  def pos_=(p: Point[Double])={
    _pos = p
    circle.x = CoordSys.c2p(pos.x, SingleAxis.X)-(diameter/2)
    circle.y = CoordSys.c2p(pos.y, SingleAxis.Y)-(diameter/2)
  }
  private var _diameter: Int = 0
  def diameter = _diameter
  def diameter_=(d: Int)={
    _diameter = d
    circle.height = d
    circle.width = d
    circle.x = CoordSys.c2p(pos.x, SingleAxis.X)-(diameter/2)
    circle.y = CoordSys.c2p(pos.y, SingleAxis.Y)-(diameter/2)
  }

  val circle = new Ellipse2D.Double(CoordSys.c2p(pos.x, SingleAxis.X)-(diameter/2), CoordSys.c2p(pos.y, SingleAxis.Y)-(diameter/2), diameter, diameter)
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}