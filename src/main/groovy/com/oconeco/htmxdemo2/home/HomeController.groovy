package com.oconeco.htmxdemo2.home

import groovy.util.logging.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Slf4j
@Controller
@RequestMapping(['', "/", '/index'])
class HomeController {

    @GetMapping
    String list() {
        // todo -- redirect to /concepts
//        "redirect:/concepts"
        return 'index'

    }
}
