package zw.co.techtrendz.school.management.api.unit.academic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import zw.co.techtrendz.school.management.api.academic.entity.Rating;
import zw.co.techtrendz.school.management.api.academic.querydsl.RatingQueryService;
import zw.co.techtrendz.school.management.api.academic.repository.RatingRepository;
import zw.co.techtrendz.school.management.api.academic.service.impl.RatingServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTests {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private RatingQueryService ratingQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRating() {
        Rating rating = new Rating();
        rating.setRating(85.5f);

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating savedRating = ratingService.save(rating);

        assertNotNull(savedRating);
        assertEquals(85.5f, savedRating.getRating());
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testSaveAllRatings() {
        Rating rating1 = new Rating();
        rating1.setRating(85.5f);
        Rating rating2 = new Rating();
        rating2.setRating(92.0f);

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        when(ratingRepository.saveAll(ratings)).thenReturn(ratings);

        List<Rating> savedRatings = ratingService.saveAll(ratings);

        assertNotNull(savedRatings);
        assertEquals(2, savedRatings.size());
        verify(ratingRepository, times(1)).saveAll(ratings);
    }

    @Test
    void testGetRatingById() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        when(ratingQueryService.getById(1L)).thenReturn(rating);

        Rating foundRating = ratingService.getById(1L);

        assertNotNull(foundRating);
        assertEquals(1L, foundRating.getId());
        assertEquals(85.5f, foundRating.getRating());
        verify(ratingQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetAllRatings() {
        Rating rating1 = new Rating();
        rating1.setId(1L);
        rating1.setRating(85.5f);
        Rating rating2 = new Rating();
        rating2.setId(2L);
        rating2.setRating(92.0f);

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        when(ratingQueryService.getAll()).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAll();

        assertNotNull(foundRatings);
        assertEquals(2, foundRatings.size());
        verify(ratingQueryService, times(1)).getAll();
    }

    @Test
    void testGetAllByActivityId() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);

        when(ratingQueryService.getAllByActivityId(1L)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByActivityId(1L);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByActivityId(1L);
    }

    @Test
    void testGetAllByActivityIds() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);
        List<Long> activityIds = Arrays.asList(1L, 2L);

        when(ratingQueryService.getAllByActivityIds(activityIds)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByActivityIds(activityIds);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByActivityIds(activityIds);
    }

    @Test
    void testGetAllByStudentIdByActivityIdByYear() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);

        when(ratingQueryService.getAllByStudentIdByActivityIdByYear(1L, 2L, 2023)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByStudentIdByActivityIdByYear(1L, 2L, 2023);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByStudentIdByActivityIdByYear(1L, 2L, 2023);
    }

    @Test
    void testGetAllByStudentIdByYear() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);

        when(ratingQueryService.getAllByStudentIdByYear(1L, 2023)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByStudentIdByYear(1L, 2023);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByStudentIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByActivityIdByYear() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);

        when(ratingQueryService.getAllByActivityIdByYear(1L, 2023)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByActivityIdByYear(1L, 2023);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByActivityIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByTeacherIdByYear() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(85.5f);

        List<Rating> ratings = Arrays.asList(rating);

        when(ratingQueryService.getAllByTeacherIdByYear(1L, 2023)).thenReturn(ratings);

        List<Rating> foundRatings = ratingService.getAllByTeacherIdByYear(1L, 2023);

        assertNotNull(foundRatings);
        assertEquals(1, foundRatings.size());
        verify(ratingQueryService, times(1)).getAllByTeacherIdByYear(1L, 2023);
    }
}