package be.plomberie.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_KEY = "cc3efbcd78c9fdad954dea957b62465d";

    public String getWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                     "&appid=" + API_KEY + "&units=metric&lang=fr";
        return restTemplate.getForObject(url, String.class);
    }
}
