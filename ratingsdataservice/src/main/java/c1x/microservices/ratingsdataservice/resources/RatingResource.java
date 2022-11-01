package c1x.microservices.ratingsdataservice.resources;

import c1x.microservices.ratingsdataservice.models.Rating;
import c1x.microservices.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId")String movieId){
        return new Rating(movieId,4);
    }

    //Getting Object instead of list
    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId")String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",5)

        );
        UserRating userRating = new UserRating();
        userRating.setUserRatings(ratings);
        return userRating;
    }
}

