package com.mnenmenth.torricellisim.drawable

import java.awt.geom.{Arc2D, Rectangle2D}
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

  private class WaterStream(private var _height: Double = size.height) extends Drawable {
    private val arc = new Arc2D.Double(Arc2D.OPEN)

    def height = _height
    def height_=(h: Double) = {
      _height = h
      val Vi = liquidVel
      val time = ProjectileMotion.timeTaken(Vi, 0)
      val hDis = ProjectileMotion.horizontalDistance(Vi, time)
      val maxH = ProjectileMotion.maxHeight(Vi, time)
      val impactAngle = ProjectileMotion.impactAngle(0, Vi)
      val pos = CoordSys.c2p(Point(0, height))
      val rect =
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
    _liquidVel = TorricellisLaw.velocity(h)
    liquid.height = h
  }
  liquidHeight = liquidHeight
  def liquidVel: Double = _liquidVel
  def liquidVel_=(v: Double)={
    _liquidVel = v
    _liquidHeight = TorricellisLaw.liquidHeight(v)
  }

  private var leftWall = new Wall(WallSide.Left)
  private var rightWall = new Wall(WallSide.Right)
  private var bottomWall = new Wall(WallSide.Bottom)

  private val liquid = new Liquid

  override def draw(g: Graphics2D): Unit = {
    liquid.draw(g)
    val g2 = g.create().asInstanceOf[Graphics2D]
    g2.setStroke(new BasicStroke(2))
    leftWall.draw(g2)
    rightWall.draw(g2)
    bottomWall.draw(g2)
    g2.dispose()
  }

}
