package com.wabradshaw.tiler.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TileService {

    @GetMapping("/tile")
    fun tile(@RequestParam(value = "height") width: Int,
                 @RequestParam(value = "width") height: Int): String{

        return "Attempted to produce a $height x $width report"
    }

}