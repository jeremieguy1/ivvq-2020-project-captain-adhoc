package captainadhoc.captainadhocbackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class IndexController {

 @GetMapping("/test")
 public ArrayList<String> testAxios() {
     ArrayList<String> als = new ArrayList<>();
     als.add("6");
    return als;
 }
}
