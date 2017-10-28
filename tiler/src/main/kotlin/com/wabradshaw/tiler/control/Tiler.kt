package com.wabradshaw.tiler.control

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.imageio.ImageIO

/**
 * The master class behind the Tiler.
 *
 * The Tiler is designed to take the strategy for a canvas and use it to present a list of content. This class is purely
 * an orchestrator, handling the calls to different parts of each strategy.
 */
class Tiler {

    fun tile(height: Int, width: Int, targets:List<String>, strategy:TileStrategy): ByteArrayOutputStream {

        return strategy.canvasPainter.paint(targets)

   }
}