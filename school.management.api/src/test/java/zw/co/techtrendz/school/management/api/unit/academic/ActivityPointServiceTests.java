package zw.co.techtrendz.school.management.api.unit.academic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.academic.entity.ActivityPoint;
import zw.co.techtrendz.school.management.api.academic.entity.ExtraCurricularYear;
import zw.co.techtrendz.school.management.api.academic.entity.Rating;
import zw.co.techtrendz.school.management.api.academic.querydsl.ActivityPointQueryService;
import zw.co.techtrendz.school.management.api.academic.querydsl.ExtraCurricularYearQueryService;
import zw.co.techtrendz.school.management.api.academic.repository.ActivityPointRepository;
import zw.co.techtrendz.school.management.api.academic.service.RatingService;
import zw.co.techtrendz.school.management.api.academic.service.impl.ActivityPointServiceImpl;
import zw.co.techtrendz.school.management.api.user.entity.Student;
import zw.co.techtrendz.school.management.api.user.querydsl.StudentQueryService;
import zw.co.techtrendz.school.management.api.user.querydsl.TeacherQueryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityPointServiceTests {

    @InjectMocks
    private ActivityPointServiceImpl activityPointService;

    @Mock
    private ActivityPointRepository activityPointRepository;

    @Mock
    private ActivityPointQueryService activityPointQueryService;

    @Mock
    private RatingService ratingService;

    @Mock
    private ExtraCurricularYearQueryService extraCurricularYearQueryService;

    @Mock
    private TeacherQueryService teacherQueryService;

    @Mock
    private StudentQueryService studentQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveActivityPoint() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setRemarks("Good performance");

        when(activityPointRepository.save(activityPoint)).thenReturn(activityPoint);

        ActivityPoint savedActivityPoint = activityPointService.save(activityPoint);

        assertNotNull(savedActivityPoint);
        assertEquals("Good performance", savedActivityPoint.getRemarks());
        verify(activityPointRepository, times(1)).save(activityPoint);
    }

    @Test
    void testSaveAllActivityPoints() {
        ActivityPoint activityPoint1 = new ActivityPoint();
        activityPoint1.setRemarks("Good performance");
        ActivityPoint activityPoint2 = new ActivityPoint();
        activityPoint2.setRemarks("Excellent performance");

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint1, activityPoint2);

        when(activityPointRepository.saveAll(activityPoints)).thenReturn(activityPoints);

        List<ActivityPoint> savedActivityPoints = activityPointService.saveAll(activityPoints);

        assertNotNull(savedActivityPoints);
        assertEquals(2, savedActivityPoints.size());
        verify(activityPointRepository, times(1)).saveAll(activityPoints);
    }

    @Test
    void testGetActivityPointById() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);
        activityPoint.setRemarks("Good performance");

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getById(1L)).thenReturn(activityPoint);
        when(ratingService.getAllByActivityId(1L)).thenReturn(Arrays.asList(rating));

        ActivityPoint foundActivityPoint = activityPointService.getById(1L);

        assertNotNull(foundActivityPoint);
        assertEquals(1L, foundActivityPoint.getId());
        assertEquals("Good performance", foundActivityPoint.getRemarks());
        assertNotNull(foundActivityPoint.getRatings());
        verify(activityPointQueryService, times(1)).getById(1L);
        verify(ratingService, times(1)).getAllByActivityId(1L);
    }

    @Test
    void testGetActivityPointByIdNotFound() {
        when(activityPointQueryService.getById(1L)).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            activityPointService.getById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Activity point with id '1' was not found"));
        verify(activityPointQueryService, times(1)).getById(1L);
    }

    @Test
    void testGetAllActivityPoints() {
        ActivityPoint activityPoint1 = new ActivityPoint();
        activityPoint1.setId(1L);
        ActivityPoint activityPoint2 = new ActivityPoint();
        activityPoint2.setId(2L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint1, activityPoint2);

        Rating rating1 = new Rating();
        rating1.setActivityPoint(activityPoint1);
        Rating rating2 = new Rating();
        rating2.setActivityPoint(activityPoint2);
        when(activityPointQueryService.getAll()).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(rating1, rating2));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAll();

        assertNotNull(foundActivityPoints);
        assertEquals(2, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAll();
        verify(ratingService, times(1)).getAllByActivityIds(Arrays.asList(1L, 2L));
    }

    @Test
    void testGetAllByActivityId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByActivityId(1L)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByActivityId(1L);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByActivityId(1L);
        verify(ratingService, times(1)).getAllByActivityIds(Arrays.asList(1L));
    }

    @Test
    void testGetAllByActivityIds() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);
        List<Long> activityIds = Arrays.asList(1L, 2L);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByActivityIds(activityIds)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByActivityIds(activityIds);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByActivityIds(activityIds);
        verify(ratingService, times(1)).getAllByActivityIds(Arrays.asList(1L));
    }

    @Test
    void testGetAllByStudentId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByStudentId(1L)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByStudentId(1L);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByStudentId(1L);
        verify(ratingService, times(1)).getAllByActivityIds(Arrays.asList(1L));
    }

    @Test
    void testGetByStudentIdByActivityId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getByStudentIdByActivityId(1L, 2L)).thenReturn(activityPoint);
        when(ratingService.getAllByActivityId(2L)).thenReturn(Arrays.asList(rating));

        ActivityPoint foundActivityPoint = activityPointService.getByStudentIdByActivityId(1L, 2L);

        assertNotNull(foundActivityPoint);
        assertEquals(1L, foundActivityPoint.getId());
        verify(activityPointQueryService, times(1)).getByStudentIdByActivityId(1L, 2L);
        verify(ratingService, times(1)).getAllByActivityId(2L);
    }

    @Test
    void testGetAllByTeacherId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByTeacherId(1L)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByTeacherId(1L);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByTeacherId(1L);
    }

    @Test
    void testGetAllByClassroomId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByClassroomId(1L)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByClassroomId(1L);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByClassroomId(1L);
    }

    @Test
    void testGetAllByFacilitatorId() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByFacilitatorId(1L)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByFacilitatorId(1L);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByFacilitatorId(1L);
    }

    @Test
    void testGetAllByStudentIdByActivityIdByYear() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByStudentIdByActivityIdByYear(1L, 2L, 2023)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByStudentIdByActivityIdByYear(1L, 2L, 2023);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByStudentIdByActivityIdByYear(1L, 2L, 2023);
    }

    @Test
    void testGetAllByStudentIdByYear() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByStudentIdByYear(1L, 2023)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByStudentIdByYear(1L, 2023);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByStudentIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByActivityIdByYear() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByActivityIdByYear(1L, 2023)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByActivityIdByYear(1L, 2023);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByActivityIdByYear(1L, 2023);
    }

    @Test
    void testGetAllByTeacherIdByYear() {
        ActivityPoint activityPoint = new ActivityPoint();
        activityPoint.setId(1L);

        List<ActivityPoint> activityPoints = Arrays.asList(activityPoint);

        Rating rating = new Rating();
        rating.setActivityPoint(activityPoint);
        when(activityPointQueryService.getAllByTeacherIdByYear(1L, 2023)).thenReturn(activityPoints);
        when(ratingService.getAllByActivityIds(Arrays.asList(1L))).thenReturn(Arrays.asList(rating));

        List<ActivityPoint> foundActivityPoints = activityPointService.getAllByTeacherIdByYear(1L, 2023);

        assertNotNull(foundActivityPoints);
        assertEquals(1, foundActivityPoints.size());
        verify(activityPointQueryService, times(1)).getAllByTeacherIdByYear(1L, 2023);
    }
}