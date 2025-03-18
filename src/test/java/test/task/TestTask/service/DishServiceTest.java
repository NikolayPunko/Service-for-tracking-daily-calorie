package test.task.TestTask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import test.task.TestTask.model.Dish;
import test.task.TestTask.repository.DishRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDish_ShouldSaveDish() {
        Dish dish = new Dish();
        dish.setName("Chicken Breast");
        dish.setCaloriesPerServing(200);

        when(dishRepository.save(dish)).thenReturn(dish);

        Dish savedDish = dishService.createDish(dish);

        assertNotNull(savedDish);
        assertEquals("Chicken Breast", savedDish.getName());
        assertEquals(200, savedDish.getCaloriesPerServing());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void getAllDishes_ShouldReturnList() {
        Dish dish1 = new Dish();
        dish1.setName("Rice");
        Dish dish2 = new Dish();
        dish2.setName("Salad");

        when(dishRepository.findAll()).thenReturn(Arrays.asList(dish1, dish2));

        List<Dish> dishes = dishService.getAllDishes();

        assertEquals(2, dishes.size());
        assertEquals("Rice", dishes.get(0).getName());
        assertEquals("Salad", dishes.get(1).getName());
        verify(dishRepository, times(1)).findAll();
    }
}
