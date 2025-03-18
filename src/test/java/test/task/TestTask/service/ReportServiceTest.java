package test.task.TestTask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.task.TestTask.DTO.MealResponseDTO;
import test.task.TestTask.model.DailyReport;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;
import test.task.TestTask.repository.MealRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private MealService mealService;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHistoryReport() {
        User user = new User();
        user.setDailyCalories(2000.0);

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 3, 3);

        Meal meal1 = mock(Meal.class);
        when(meal1.getDate()).thenReturn(LocalDate.of(2024, 3, 1));
        when(meal1.totalCalories()).thenReturn(500);

        Meal meal2 = mock(Meal.class);
        when(meal2.getDate()).thenReturn(LocalDate.of(2024, 3, 1));
        when(meal2.totalCalories()).thenReturn(700);

        Meal meal3 = mock(Meal.class);
        when(meal3.getDate()).thenReturn(LocalDate.of(2024, 3, 2));
        when(meal3.totalCalories()).thenReturn(1500);

        List<Meal> meals = Arrays.asList(meal1, meal2, meal3);

        when(mealRepository.findByUserAndDateBetween(user, startDate, endDate)).thenReturn(meals);

        // Возвращаем пустой список MealResponseDTO просто для упрощения
        when(mealService.convertMealListToMealResponseDTOList(anyList())).thenReturn(Collections.emptyList());

        List<DailyReport> reports = reportService.getHistoryReport(user, startDate, endDate);

        assertNotNull(reports);
        assertEquals(2, reports.size());

        DailyReport reportDay1 = reports.stream().filter(r -> r.getDate().equals(LocalDate.of(2024, 3, 1))).findFirst().orElse(null);
        assertNotNull(reportDay1);
        assertEquals(1200, reportDay1.getTotalCalories());
        assertTrue(reportDay1.isWithinDailyLimit());

        DailyReport reportDay2 = reports.stream().filter(r -> r.getDate().equals(LocalDate.of(2024, 3, 2))).findFirst().orElse(null);
        assertNotNull(reportDay2);
        assertEquals(1500, reportDay2.getTotalCalories());
        assertTrue(reportDay2.isWithinDailyLimit());

        verify(mealRepository, times(1)).findByUserAndDateBetween(user, startDate, endDate);
        verify(mealService, atLeastOnce()).convertMealListToMealResponseDTOList(anyList());
    }
}
