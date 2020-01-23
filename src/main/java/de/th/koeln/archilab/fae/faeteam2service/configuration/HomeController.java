package de.th.koeln.archilab.fae.faeteam2service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${gateway-path:}")
    private String gatewayPath;

    @GetMapping(value = "/")
    public String index() {
        log.info("Redirected to swagger-ui.html");
        return "redirect:" + gatewayPath + "/swagger-ui.html";
    }
}
