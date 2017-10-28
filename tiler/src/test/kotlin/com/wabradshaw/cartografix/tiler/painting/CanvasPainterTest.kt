package com.wabradshaw.cartografix.tiler.painting

import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.io.IOException
import java.io.FileOutputStream
import javax.imageio.ImageIO
import java.awt.image.BufferedImage

/**
 * A set of tests for the CanvasPainter
 */
class CanvasPainterTest {

    private val path = "out/test/"
    private val type = ".png"
    private val abz_L_blue = "https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME&center=AB24%203LR&zoom=13&scale=2&size=600x640&maptype=roadmap&style=feature:all%7Ccolor:0xffffff&style=feature:water%7Ccolor:0x2a435d&style=feature:poi%7Ccolor:0x2a435d&style=feature:road%7Ccolor:0xffffff&style=feature:transit%7Ccolor:0xffffff&style=feature:landscape.man_made%7Ccolor:0x3d7ab6&style=feature:poi.school%7Ccolor:0x3d7ab6&style=feature:landscape.natural%7Ccolor:0x37618a&style=feature:poi.park%7Ccolor:0x37618a&style=feature:poi.sports_complex%7Ccolor:0x37618a&style=feature:poi.business%7Ccolor:0x37618a&style=feature:poi.attraction%7Ccolor:0xffffff&style=feature:poi.place_of_worship%7Ccolor:0xffffff&style=feature:poi.government%7Ccolor:0xffffff&style=feature:poi.medical%7Ccolor:0xffffff&style=feature:all%7Celement:labels%7Cvisibility:off"

    /**
     * Tests that a single image can be displayed
     */
    @Test
    fun testPaintExists() {
        val painter: CanvasPainter = CanvasPainter(1200, 80)

        val content = listOf(abz_L_blue)

        val result = painter.paint(content).toByteArray()
        assertTrue(result != null)

        val name="exists"
        save(result, name)
        checkSize(1200,1200,name)
    }

    /**
     * Tests that the requested scale is used for a single image
     */
    @Test
    fun testPaintDifferentSize() {
        val painter: CanvasPainter = CanvasPainter(120, 80)

        val content = listOf(abz_L_blue)

        val result = painter.paint(content).toByteArray()
        assertTrue(result != null)

        val name = "120x120"
        save(result, name)
        checkSize(120,120,name)
    }

    /**
     * Saves the output to a file so it can be viewed and tested.
     */
    private fun save(output:ByteArray, name:String){
        try {
            FileOutputStream("$path$name$type").use { fos -> fos.write(output) }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

    /**
     * Checks that an image file fits the given dimensions.
     */
    private fun checkSize(width:Int, height:Int, name:String){
        try {
            val image = ImageIO.read(File("$path$name$type"))
            assertEquals(width, image.width)
            assertEquals(height, image.height)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}