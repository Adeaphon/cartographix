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

    @GetMapping(value = "/tile")
    fun tile(@RequestParam(value = "width") width: Int,
                 @RequestParam(value = "height") height: Int,
             @RequestParam(value = "target") target: List<String>): ByteArray {

        //TODO: Read in image list (+ size, hue

        var image: Image? = null
        val url = URL("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME&center=AB24%203LR&zoom=13&scale=2&size=600x640&maptype=roadmap&style=feature:all|color:0xffffff&style=feature:water|color:0x2a435d&style=feature:poi|color:0x2a435d&style=feature:road|color:0xffffff&style=feature:transit|color:0xffffff&style=feature:landscape.man_made|color:0x3d7ab6&style=feature:poi.school|color:0x3d7ab6&style=feature:landscape.natural|color:0x37618a&style=feature:poi.park|color:0x37618a&style=feature:poi.sports_complex|color:0x37618a&style=feature:poi.business|color:0x37618a&style=feature:poi.attraction|color:0xffffff&style=feature:poi.place_of_worship|color:0xffffff&style=feature:poi.government|color:0xffffff&style=feature:poi.medical|color:0xffffff&style=feature:all|element:labels|visibility:off")
        image = ImageIO.read(url)

        var image2: Image? = null
        val url2 = URL("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME&center=AB24%203LR&zoom=5&scale=2&size=600x640&maptype=roadmap&style=feature:all|color:0xffffff&style=feature:water|color:0x2a435d&style=feature:poi|color:0x2a435d&style=feature:road|color:0xffffff&style=feature:transit|color:0xffffff&style=feature:landscape.man_made|color:0x3d7ab6&style=feature:poi.school|color:0x3d7ab6&style=feature:landscape.natural|color:0x37618a&style=feature:poi.park|color:0x37618a&style=feature:poi.sports_complex|color:0x37618a&style=feature:poi.business|color:0x37618a&style=feature:poi.attraction|color:0xffffff&style=feature:poi.place_of_worship|color:0xffffff&style=feature:poi.government|color:0xffffff&style=feature:poi.medical|color:0xffffff&style=feature:all|element:labels|visibility:off")
        image2 = ImageIO.read(url2)

        var image3: Image? = null
        val url3 = URL("https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME&center=AB24%203LR&zoom=16&scale=2&size=600x640&maptype=roadmap&style=feature:all|color:0xffffff&style=feature:water|color:0x2a435d&style=feature:poi|color:0x2a435d&style=feature:road|color:0xffffff&style=feature:transit|color:0xffffff&style=feature:landscape.man_made|color:0x3d7ab6&style=feature:poi.school|color:0x3d7ab6&style=feature:landscape.natural|color:0x37618a&style=feature:poi.park|color:0x37618a&style=feature:poi.sports_complex|color:0x37618a&style=feature:poi.business|color:0x37618a&style=feature:poi.attraction|color:0xffffff&style=feature:poi.place_of_worship|color:0xffffff&style=feature:poi.government|color:0xffffff&style=feature:poi.medical|color:0xffffff&style=feature:all|element:labels|visibility:off")
        image3 = ImageIO.read(url3)

        var target: BufferedImage = BufferedImage(1200, 3600, BufferedImage.TYPE_INT_RGB)
        var g = target.graphics
        g.drawImage(image, 0, 0, 1200, 1280,null)
        g.drawImage(image2, 0, 1200, 1200, 1280, null)
        g.drawImage(image3, 0, 2400, 1200, 1280, null)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(target, "png", byteArrayOutputStream)

        return Base64.getEncoder().encode(byteArrayOutputStream.toByteArray())
    //    return target.joinToString("</br>")
    }

    fun displayImage(image: Image) {
        val label = JLabel(ImageIcon(image))

        val panel = JPanel()
        panel.add(label)

        val scrollPane = JScrollPane(panel)
        JOptionPane.showMessageDialog(null, scrollPane)
    }

}
