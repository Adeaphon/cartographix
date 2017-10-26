package com.wabradshaw.tiler.service

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sun.misc.IOUtils
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JPanel
import javax.swing.ImageIcon
import javax.swing.JLabel





@RestController
class TileService {

    val scale = 1200
    val padding = 80

    @GetMapping(value = "/tile")
    fun tile(@RequestParam(value = "width") width: Int,
                 @RequestParam(value = "height") height: Int,
             @RequestParam(value = "target") targets: List<String>): ByteArray {

        //TODO: Read in image list (+ size, hue
        val output: BufferedImage = BufferedImage(scale, scale * targets.size, BufferedImage.TYPE_INT_RGB)

        val g = output.graphics

        var i = 0;
        targets.forEach{map ->
            val image: Image? = ImageIO.read(URL(map))
            g.drawImage(image, 0, i * scale, scale, scale + padding, null)
            i++
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(output, "png", byteArrayOutputStream)
        return Base64.getEncoder().encode(byteArrayOutputStream.toByteArray())
    }

    fun displayImage(image: Image) {
        val label = JLabel(ImageIcon(image))

        val panel = JPanel()
        panel.add(label)

        val scrollPane = JScrollPane(panel)
        JOptionPane.showMessageDialog(null, scrollPane)
    }

}
