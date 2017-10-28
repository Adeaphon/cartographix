package com.wabradshaw.tiler.control

import com.wabradshaw.tiler.painting.CanvasPainter

/**
 * A TileStrategy bundles the classes necessary to manage a tiling strategy.
 *
 * @param canvasPainter - The class that will actually convert the content into an image.
 */
data class TileStrategy(val canvasPainter: CanvasPainter)