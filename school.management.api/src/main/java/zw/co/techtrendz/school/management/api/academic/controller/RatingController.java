package zw.co.techtrendz.school.management.api.academic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.academic.entity.Rating;
import zw.co.techtrendz.school.management.api.academic.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/rating")
@Tag(name = "Ratings", description = "Management of student activity ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(
            summary = "Save rating",
            description = "Creates or updates a student activity rating record"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('TEACHER')")
    public Rating saveRating(@RequestBody @Valid Rating rating) {
        return this.ratingService.save(rating);
    }

    @Operation(
            summary = "Save multiple ratings",
            description = "Creates or updates multiple student activity rating records"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Rating> saveAllRatings(@RequestBody @Valid List<Rating> ratings) {
        return this.ratingService.saveAll(ratings);
    }

    @Operation(
            summary = "Get rating by ID",
            description = "Retrieves a specific rating by its ID"
    )
    @GetMapping("/get/one")
    public Rating getRating(@Parameter(description = "Rating ID") @RequestParam Long id) {
        return this.ratingService.getById(id);
    }

    @Operation(
            summary = "Get all ratings",
            description = "Retrieves all rating records"
    )
    @GetMapping("/get/all")
    public List<Rating> getAllRatings() {
        return this.ratingService.getAll();
    }

    @Operation(
            summary = "Get ratings by activity",
            description = "Retrieves all ratings for a specific activity"
    )
    @GetMapping("/get/all/by/activity")
    public List<Rating> getAllRatings(@Parameter(description = "Activity ID") @RequestParam Long activityId) {
        return this.ratingService.getAllByActivityId(activityId);
    }
}
