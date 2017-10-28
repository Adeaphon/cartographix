package com.wabradshaw.cartografix.tiler.service

import com.wabradshaw.cartografix.tiler.control.TileStrategy
import com.wabradshaw.cartografix.tiler.control.Tiler
import com.wabradshaw.cartografix.tiler.painting.CanvasPainter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * The restful interface for the Tiler. Accepts requests, determines the strategy for them, and converts them to a
 * base64 for the user.
 */
@RestController
class TileService {

    val tileController = Tiler()

    @GetMapping(value = "/tile")
    fun tile(@RequestParam(value = "width") width: Int,
             @RequestParam(value = "height") height: Int,
             @RequestParam(value = "target") targets: List<String>): ByteArray {

        val strategy: TileStrategy = TileStrategy(CanvasPainter(1200, 80))
        return Base64.getEncoder().encode(tileController.tile(width, height, targets, strategy).toByteArray())
    }

}
