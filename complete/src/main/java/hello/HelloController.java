package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HelloController {
    
    @RequestMapping("/asset")
    public String index() {
        return "Greetings from Spring Boot!";
    }



    /*



    {
  "referenceno":"621273af-9017-4d38-b1bf-a07549aaec0a",
  "asset":{
    "keys":[
      {"email":"jane@server.org"}
    ],
    "attributes":[
      {"firstname":"Jane"},
      {"lastname":"Doe"}
    ]
  }
}


     */

    @RequestMapping(value = "/asset", method = RequestMethod.POST)
    public String createTask(@RequestBody  String request) {




        return request;

    }


}
