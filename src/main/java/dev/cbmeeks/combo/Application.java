package dev.cbmeeks.combo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cbmeeks.combo.hotel.Hotel;
import dev.cbmeeks.combo.hotel.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Here we are simply importing sample data (hotels) from the JSON file and
     * storing in our demo H2 database.
     */
    @Autowired
    private HotelRepository hotelRepository;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Hotel hotels = mapper.readValue(
                    new File("src/main/resources/static/data/foursquare_hotel_sample.json"),
                    Hotel.class
            );

            hotelRepository.saveAll(Arrays.asList(hotels.getResults()));
        };
    }

}
