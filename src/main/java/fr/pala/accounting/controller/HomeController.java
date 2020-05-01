package fr.pala.accounting.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class HomeController implements ErrorController {

    private static final String PATH = "/error";

    private final RestTemplate restTemplate;

    public HomeController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/")
    public String writeHello() {
        return "Welcome home";
    }

    @RequestMapping(value = PATH)
    public String error() {
        return "Error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @RequestMapping(path="/testroute")
    public String addUser(@RequestParam String image) {
        String url = "https://api.ocr.space/parse/imageurl?apikey=568d69d02288957&url=" + image;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {
        });


        Map<String, Object> final_response = response.getBody();
        ArrayList<Map<String, Object>> ParsedResults = (ArrayList) final_response.get("ParsedResults");

        Map<String, Object> parsedResult = ParsedResults.get(0);

        return (String) parsedResult.get("ParsedText");
    }
}
