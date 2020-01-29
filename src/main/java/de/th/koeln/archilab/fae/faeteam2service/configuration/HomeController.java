package de.th.koeln.archilab.fae.faeteam2service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Home redirection to swagger api documentation
 */
@Controller
@ApiIgnore
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = "/")
    public String index() {
        log.info("Redirected to index.html");
        return "redirect:/index.html";
    }

    @GetMapping(value = "/openapi")
    public String restApi() {
        log.info("Redirected to swagger-ui.html");
        return "redirect:/swagger-ui.html";
    }

    @GetMapping(value = "/asyncapi")
    public String eventApi() {
        log.info("Redirected to asyncapi");
        return "redirect:/asyncapi/index.html";
    }
}
