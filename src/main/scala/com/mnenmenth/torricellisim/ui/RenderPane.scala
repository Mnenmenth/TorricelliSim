package com.mnenmenth.torricellisim.ui

import com.mnenmenth.torricellisim.core.TorricelliSim
import com.mnenmenth.torricellisim.drawable.CoordSys

import scala.swing.{Graphics2D, Panel}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class RenderPane extends Panel {

  val graph = new CoordSys(TorricelliSim.windowSize, 100, 100, 2)

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    graph.draw(g)
    repaint()
  }

}
