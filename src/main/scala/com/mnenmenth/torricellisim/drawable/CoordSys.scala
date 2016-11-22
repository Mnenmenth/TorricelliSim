package com.mnenmenth.torricellisim.drawable

import java.awt.{Color, Font, Graphics2D}

import com.mnenmenth.torricellisim.core.TorricelliSim
import com.mnenmenth.torricellisim.drawable.CoordSys.{Axis, SingleAxis}
import com.mnenmenth.torricellisim.math.Point

import scala.swing.{Dimension, Graphics2D}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
object CoordSys {

  var width, height, shiftX, shiftY : Int = 0

  object SingleAxis extends Enumeration {
    val X, Y = Value
  }

  class Hatch(pos: Int, axis: SingleAxis.Value) extends Line {
    import SingleAxis.{X, Y}
    if (axis == X) {
      begin = Point(pos, width/70)
      end = Point(pos, -width/70)
    } else if (axis == Y) {
      begin = Point(height/70, pos)
      end = Point(-height/70, pos)
    }

    override def draw(g: Graphics2D): Unit = {
      g.draw(line)
      val point = c2p(end)
      val g2 = g.create().asInstanceOf[Graphics2D]
      g2.setFont(new Font("serif", Font.PLAIN, 10))
      g2.drawString(s" $pos", point.x, point.y)
      g2.dispose()
    }
  }
  /**
    * Drawable axis for graph
    */
  class Axis(axis: SingleAxis.Value, incrementBy: Int) extends Line {
    import SingleAxis.{X,Y}
    if(axis == X) {
      begin = Point(-width, 0)
      end = Point(width, 0)
    } else if (axis == Y) {
      begin = Point(0, -height)
      end = Point(0, height)
    }
    var hatches = List[Hatch]()
    for (p <- 0 to (if(axis == X) width+shiftX/2 else if (axis == Y) height+shiftY/2 else 0) by incrementBy) {
      hatches = hatches :+ new Hatch(p, axis)
      hatches = hatches :+ new Hatch(-p, axis)
    }
    hatches = hatches.dropRight(2)
    override def draw(g: Graphics2D) = {
      g.draw(line)
      hatches.foreach(_.draw(g))
    }
  }

  //Coordinate to pixel
  /**
    * Convert coordinate (-100 to 100) to pixel on screen
    * @param coord Coordinate to convert
    * @param axis Axis coordinate lies on (X or Y)
    * @return Pixel
    */
  def c2p(coord: Double, axis: SingleAxis.Value): Int = {
    if(axis == SingleAxis.X) {
      (((width/2)+shiftX+coord)*TorricelliSim.windowSize.width/width).toInt
    } else if(axis == SingleAxis.Y) {
      (((height/2)+shiftY-coord)*TorricelliSim.windowSize.height/height).toInt
    } else {
      0
    }
  }

  /**
    * Coordinate Point to Pixel Point
    * @param coord Coordinate to convert
    * @return Pixel Point
    */
  def c2p(coord: Point[Double]): Point[Int] = {
    val x = ((width/2)+shiftX+coord.x)*TorricelliSim.windowSize.width/width
    val y = ((height/2)+shiftY-coord.y)*TorricelliSim.windowSize.height/height
    Point(x.toInt, y.toInt)
  }

  /**
    * Pixel to Coordinate
    * @param coord Pixel to convert
    * @param axis Axis pixel lies on
    * @return Coordinate
    */
  def p2c(coord: Double, axis: SingleAxis.Value): Double = {
    if(axis == SingleAxis.X) {
      (coord/TorricelliSim.windowSize.width)*width-((width/2)+shiftX)
    } else if(axis == SingleAxis.Y) {
      -1*((coord/TorricelliSim.windowSize.height)*height-((height/2)+shiftY))
    } else {
      0.0
    }
  }

  /**
    * Pixel Point to Coordinate Point
    * @param pixel Pixel point to convert to coordinate point
    * @return Coordinate point
    */
  def p2c(pixel: Point[Double]): Point[Double] = {
    val x = (pixel.x/TorricelliSim.windowSize.width)*width-((width/2)+shiftX)
    val y = -1*((pixel.y/TorricelliSim.windowSize.height)*height-((height/2)+shiftY))
    Point[Double](x, y)
  }

}

/**
  * Drawable cartesian coordinate system
  * @param windowSize Size of host window
  */
//max only works at 100 right now so don't use anything else
class CoordSys(windowSize: Dimension, maxX: Int, maxY: Int, incrementBy: Int, shiftX: Int = 0, shiftY: Int = 0) extends Drawable {
  CoordSys.width = maxX*2
  CoordSys.height = maxY*2
  CoordSys.shiftX = shiftX
  CoordSys.shiftY = -shiftY

  val yAxis = new Axis(SingleAxis.Y, incrementBy)
  val xAxis = new Axis(SingleAxis.X, incrementBy)
  

  override def draw(g: Graphics2D): Unit = {
    val g2 = g.create().asInstanceOf[Graphics2D]
    g2.setColor(Color.DARK_GRAY)
    yAxis.draw(g2)
    xAxis.draw(g2)
    g2.dispose()
  }

}