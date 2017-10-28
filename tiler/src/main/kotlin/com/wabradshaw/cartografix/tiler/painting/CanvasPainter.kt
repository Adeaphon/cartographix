package com.wabradshaw.cartografix.tiler.painting

import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import javax.imageio.ImageIO

class CanvasPainter(private val scale:Int, private val padding:Int) {

    //TODO: Change input type
    fun paint(content: List<String>):ByteArrayOutputStream{
        val output: BufferedImage = BufferedImage(scale, scale * content.size, BufferedImage.TYPE_INT_RGB)

        //TODO: Change method
        val g = output.graphics

        var i = 0;
        content.forEach{map ->
            val image: Image? = ImageIO.read(URL(map))
            g.drawImage(image, 0, i * scale, scale, scale + padding, null)
            i++
        }

        val result = ByteArrayOutputStream()
        ImageIO.write(output, "png", result)
        return result;
    }
}