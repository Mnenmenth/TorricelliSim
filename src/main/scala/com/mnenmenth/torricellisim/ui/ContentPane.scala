package com.mnenmenth.torricellisim.ui

import com.mnenmenth.torricellisim.core.TorricelliSim

import scala.swing.BorderPanel
import scala.swing.event.{Key, KeyPressed}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class ContentPane extends BorderPanel {

  val renderPane = new RenderPane
  layout(renderPane) = BorderPanel.Position.Center

  listenTo(keys)
  reactions += {
    case KeyPressed(_, Key.Escape, _, _) =>
      TorricelliSim.quit()
  }

  focusable = true
  requestFocus()
}
