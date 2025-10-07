package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.Rating;
import zw.co.techtrendz.school.management.api.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public Rating saveRating(@RequestBody @Valid Rating rating) {
        return this.ratingService.save(rating);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rating> saveAllRatings(@RequestBody @Valid List<Rating> ratings) {
        return this.ratingService.saveAll(ratings);
    }

    @GetMapping("/get/one")
    public Rating getRating(@RequestParam Long id) {
        return this.ratingService.getById(id);
    }

    @GetMapping("/get/all")
    public List<Rating> getAllRatings() {
        return this.ratingService.getAll();
    }

    @GetMapping("/get/all/by/activity")
    public List<Rating> getAllRatings(@RequestParam Long activityId) {
        return this.ratingService.getAllByActivityId(activityId);
    }
}
