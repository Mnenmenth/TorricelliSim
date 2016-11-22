package com.mnenmenth.torricellisim.ui

import java.awt.Dimension

import com.mnenmenth.torricellisim.core.TorricelliSim
import com.mnenmenth.torricellisim.drawable.{Container, CoordSys}
import com.mnenmenth.torricellisim.math.Point

import scala.swing.event.{MouseClicked, MouseDragged}
import scala.swing.{Graphics2D, Panel}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class RenderPane extends Panel {

  val graph = new CoordSys(TorricelliSim.windowSize, 100, 100, 5)//1, shiftX = -9, shiftY = -9)
  val container = new Container(new Dimension(50,50), _liquidHeight = 43)

  listenTo(mouse.moves, mouse.clicks)
  reactions += {
    case MouseDragged(_, point, _) =>
      val p = CoordSys.p2c(Point(point.x, point.y))
      if (p.y <= container.size.height && p.y >= 0) container.liquidHeight = p.y
    case MouseClicked(_, point, _, _, _) =>
      val p = CoordSys.p2c(Point(point.x, point.y))
      println(p.y)
      if (p.y <= container.size.height && p.y >= 0) container.liquidHeight = p.y
  }

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    container.draw(g)
    graph.draw(g)
    repaint()
  }

}
