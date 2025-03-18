package test.task.TestTask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.task.TestTask.DTO.MealDTO;
import test.task.TestTask.model.Dish;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;
import test.task.TestTask.repository.DishRepository;
import test.task.TestTask.repository.MealRepository;
import test.task.TestTask.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private MealService mealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMeal() {
        Meal meal = new Meal();
        mealService.addMeal(meal);
        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void testConvertMealDTOToMeal() {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setUserId(1L);
        mealDTO.setDate(LocalDate.now());
        mealDTO.setDishes(List.of(1L, 2L));

        User user = new User();
        user.setId(1L);

        Dish dish1 = new Dish();
        dish1.setId(1L);
        Dish dish2 = new Dish();
        dish2.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(2L)).thenReturn(Optional.of(dish2));

        Meal meal = mealService.convertMealDTOToMeal(mealDTO);

        assertEquals(user, meal.getUser());
        assertEquals(2, meal.getDishes().size());
        assertTrue(meal.getDishes().contains(dish1));
        assertTrue(meal.getDishes().contains(dish2));
    }

    @Test
    void testGetMealsByUserAndDate() {
        User user = new User();
        LocalDate date = LocalDate.now();
        List<Meal> meals = List.of(new Meal(), new Meal());

        when(mealRepository.findByUserAndDate(user, date)).thenReturn(meals);

        List<Meal> result = mealService.getMealsByUserAndDate(user, date);

        assertEquals(2, result.size());
    }

    @Test
    void testCheckIfWithinCalories() {
        User user = new User();
        user.setDailyCalories(2000.0);

        Meal meal1 = mock(Meal.class);
        Meal meal2 = mock(Meal.class);

        when(meal1.totalCalories()).thenReturn(800);
        when(meal2.totalCalories()).thenReturn(1000);

        when(mealRepository.findByUserAndDate(any(), any()))
                .thenReturn(List.of(meal1, meal2));

        boolean result = mealService.checkIfWithinCalories(user, LocalDate.now());
        assertTrue(result);

        // Проверяем случай превышения
        when(meal2.totalCalories()).thenReturn(1500);
        boolean exceeded = mealService.checkIfWithinCalories(user, LocalDate.now());
        assertFalse(exceeded);
    }
}
