package hello;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mapdb.DB;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;

@RestController
public class HelloController {


    @Resource
    public void setMapdb(DB mapdb) {
        this.mapdb = mapdb;
    }


    private DB mapdb;

    @RequestMapping("/asset")
    public String index() {


        return "Greetings from Spring Boot!";
    }


    @RequestMapping("/asset/{id}")
    public String indexe(@PathVariable String id) {


        String referenceno = UUID.randomUUID().toString();


        ConcurrentNavigableMap<String, String> keyMap = mapdb.getTreeMap("key");
        String assetid = keyMap.get(id);
        if (assetid != null) {
            ConcurrentNavigableMap<String, String> assetMap = mapdb.getTreeMap("asset");
            JsonObject response = new JsonObject(assetMap.get(assetid));
            response.put("referenceno", referenceno);
            return response.encode();
        }


        return "asset not found";

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
    public String createTask(@RequestBody String body) {

        JsonObject response = new JsonObject();


        JsonObject request = new JsonObject(body);


        JsonObject asset = request.getJsonObject("asset");
        response.put("referenceno", request.getString("referenceno"));


        String id = persistAsset(asset);
        asset.put("id", id);
        response.put("asset", asset);
        return response.encode();

    }


    private String persistAsset(JsonObject asset) {
        ConcurrentNavigableMap<String, String> assetRepository = mapdb.getTreeMap("asset");
        ConcurrentNavigableMap<String, String> keyRepository = mapdb.getTreeMap("key");
        String id = null;
        JsonArray keys = asset.getJsonArray("keys");
        if (!exits(keys)) {
            id = "simulator::" + UUID.randomUUID().toString();
            asset.put("id", id);
            assetRepository.put(id, asset.encode());

            //Save a relation between asset and key so can lookup asset with key.
            for (Object entry : keys) {
                if (entry instanceof JsonObject) {
                    JsonObject key = (JsonObject) entry;
                    keyRepository.put(key.encode(), id);
                }
            }
        }


        return id;
    }





    private boolean exits(JsonArray keys) {
        ConcurrentNavigableMap<String, String> keyMap = mapdb.getTreeMap("key");
        for (Object entry : keys) {
            if (entry instanceof JsonObject) {
                JsonObject key = (JsonObject) entry;
                String mapKey = key.encode();

                String test = keyMap.get(mapKey);
                if (test != null) {
                    return true;
                }
            }
        }
        return false;
    }


    private static int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }




}
