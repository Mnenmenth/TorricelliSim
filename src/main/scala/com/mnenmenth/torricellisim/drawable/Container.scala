package com.mnenmenth.torricellisim.drawable

import java.awt.geom.{Arc2D, Line2D, Path2D, Rectangle2D}
import java.awt.{BasicStroke, Color, Dimension, Polygon}

import com.mnenmenth.torricellisim.core.TorricelliSim
import com.mnenmenth.torricellisim.math.{Point, ProjectileMotion, TorricellisLaw}

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class Container(private var _size: Dimension,
                private var _liquidHeight: Double = 0,
                private var _liquidVel: Double = 0) extends Drawable {
  private object WallSide extends Enumeration { val Left, Right, Bottom = Value }
  private class Wall(side: WallSide.Value) extends Line {
    import WallSide._
    if (side == Left) {
      begin = Point(-size.width, 0)
      end = Point(-size.width, size.height)
    } else if (side == Right) {
      begin = Point(0, 0)
      end = Point(0, size.height)
    } else if (side == Bottom) {
      begin = Point(-size.width, 0)
      end = Point(0, 0)
    }
  }
  private class WaterStream(height: Double) extends Drawable {
    private val arc = new Path2D.Double
    var distance: Double = 0
    var isDrawn: Boolean = true
    def updateArc(): Unit =  {
      if (liquidHeight-height > 0) {
        val liquidVel = TorricellisLaw.velocity(liquidHeight - height)
        val time = ProjectileMotion.time(height)
        distance = ProjectileMotion.distance(liquidVel, time)
        val mid = ProjectileMotion.distance(liquidVel, time / 2)
        val start = CoordSys.c2p(Point[Double](0, height))
        val control = CoordSys.c2p(Point(mid, height))
        val end = CoordSys.c2p(Point(distance, 0))
        arc.reset()
        arc.moveTo(start.x, start.y)
        arc.curveTo(start.x, start.y, control.x, control.y, end.x, end.y)
        isDrawn = true
      } else {
        arc.reset()
        arc.moveTo(0, 0)
        arc.curveTo(0, 0, 0, 0, 0, 0)
        isDrawn = false
      }
    }

    override def draw(g: Graphics2D): Unit = {
      val g2 = g.create().asInstanceOf[Graphics2D]
      g2.setStroke(new BasicStroke(2))
      g2.setColor(Color.CYAN)
      g2.draw(arc)
      g2.dispose()
    }

  }

  private class Liquid(private var _height: Double = liquidHeight) extends Drawable {
    //private val box = new Rectangle2D.Double(0, 0, 0, 0)
    private val box = new Polygon()

    def height = _height
    def height_=(h: Double)={
      _height = h
      val bottomLeft = CoordSys.c2p(Point(-size.width, 0))
      val topLeft = CoordSys.c2p(Point(-size.width, height))
      val bottomRight = CoordSys.c2p(Point(0, 0))
      val topRight = CoordSys.c2p(Point(0, height))
      box.reset()
      box.addPoint(bottomLeft.x, bottomLeft.y)
      box.addPoint(topLeft.x, topLeft.y)
      box.addPoint(topRight.x, topRight.y)
      box.addPoint(bottomRight.x, bottomRight.y)
    }
    height = height

    override def draw(g: Graphics2D): Unit = {
      val g2 = g.create().asInstanceOf[Graphics2D]
      g2.setColor(Color.CYAN)
      g2.fill(box)
      g2.dispose()
    }
  }

  private class Envelope extends DottedLine {
    override val stroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, Array(20.0f), 0.0f)

    def updateEnvelope(liquidHeight: Double, maxStreamLength: Double): Unit = {
      val length = math.sqrt(math.pow(liquidHeight, 2) + math.pow(maxStreamLength, 2))
      begin = Point(0, liquidHeight)
      val endX = begin.x + length * math.cos(math.toRadians(45))
      val endY = begin.y - length * math.sin(math.toRadians(45))
      end = Point(endX, endY)
    }
  }

  def size: Dimension = _size
  def size_=(s: Dimension)= {
    _size = s
    leftWall = new Wall(WallSide.Left)
    rightWall = new Wall(WallSide.Right)
    bottomWall = new Wall(WallSide.Bottom)
  }
  size = size

  def liquidHeight: Double = _liquidHeight
  def liquidHeight_=(h: Double)={
    _liquidHeight = h
    liquid.height = h
    streams.foreach(_.updateArc())
    var maxStreamLength: Double = 0
    streams.foreach(s => if (s.distance > maxStreamLength && s.isDrawn) maxStreamLength = s.distance)
    envelope.updateEnvelope(liquidHeight, maxStreamLength)
  }

  private var leftWall = new Wall(WallSide.Left)
  private var rightWall = new Wall(WallSide.Right)
  private var bottomWall = new Wall(WallSide.Bottom)

  private val liquid = new Liquid
  private var streams = List[WaterStream]()
  for (h <- 5 to size.height by 5) streams = streams :+ new WaterStream(h)

  private val envelope = new Envelope

  liquidHeight = liquidHeight
  override def draw(g: Graphics2D): Unit = {
    liquid.draw(g)
    val g2 = g.create().asInstanceOf[Graphics2D]
    g2.setStroke(new BasicStroke(2))
    leftWall.draw(g2)
    rightWall.draw(g2)
    bottomWall.draw(g2)
    g2.dispose()
    streams.foreach(_.draw(g))
    envelope.draw(g)
  }

}
