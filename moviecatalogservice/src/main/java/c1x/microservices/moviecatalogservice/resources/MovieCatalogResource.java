package c1x.microservices.moviecatalogservice.resources;

import c1x.microservices.moviecatalogservice.models.CatalogItem;
import c1x.microservices.moviecatalogservice.models.Movie;
import c1x.microservices.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    //Alternative way of Effective LoadBalancing
//    @Autowired
//    private DiscoveryClient discoveryClient;

    //Web Client(Alternative for RestTemplate
//    @Autowired
//    private WebClient.Builder webClientbuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalogItem(@PathVariable("userId") String userId) {

//        RestTemplate  restTemplate = new RestTemplate();
//        WebClient.Builder builder = WebClient.builder();

        //get all rated movie IDs

//        List<Rating> ratings = Arrays.asList
//                (new Rating("1234", 4),
//                        new Rating("67", 5));

        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingdata/users/" + userId, UserRating.class);

        return userRating.getUserRatings().stream().map(rating -> {

            //For each movie ID, call movie info service and get details
            //unmarshalling happens
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

            //put them all together
            return new CatalogItem(movie.getName(), "trending", rating.getRating());
        })
                .collect(Collectors.toList());
    }
}


//            replacing by web client
          /* Movie movie = webClientbuilder.build()
                    .get()      //http get request
                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/