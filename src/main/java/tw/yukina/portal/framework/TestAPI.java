package tw.yukina.portal.framework;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestAPI {

    @GetMapping("/arduino/dht")
    public String helloIndex(){
        return "26";
    }
}
