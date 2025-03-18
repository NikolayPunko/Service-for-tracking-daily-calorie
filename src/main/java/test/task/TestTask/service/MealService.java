package test.task.TestTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import test.task.TestTask.DTO.DishDTO;
import test.task.TestTask.DTO.MealDTO;
import test.task.TestTask.DTO.MealResponseDTO;
import test.task.TestTask.model.DailyReport;
import test.task.TestTask.model.Dish;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;
import test.task.TestTask.repository.DishRepository;
import test.task.TestTask.repository.MealRepository;
import test.task.TestTask.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    @Autowired
    public MealService(MealRepository mealRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional
    public void addMeal(Meal meal) {
        mealRepository.save(meal);
    }

    public Meal convertMealDTOToMeal(MealDTO mealDTO){
        Meal meal = new Meal();
        meal.setDate(mealDTO.getDate());
        meal.setDishes(new ArrayList<>());

        meal.setUser(userRepository.findById(mealDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        for (int i = 0; i < mealDTO.getDishes().size(); i++) {
            Dish dish = dishRepository.findById(mealDTO.getDishes().get(i)).orElseThrow(() -> new RuntimeException("Dishes not found"));
            meal.getDishes().add(dish);
        }
        return meal;
    }

    public List<Meal> getMealsByUserAndDate(User user, LocalDate date) {
        return mealRepository.findByUserAndDate(user, date);
    }

    public DailyReport getDailyReport(User user, LocalDate date) {
        List<Meal> meals = mealRepository.findByUserAndDate(user, date);

        List<MealResponseDTO> mealResponseDTOList = convertMealListToMealResponseDTOList(meals);

        Integer totalCalories = meals.stream().mapToInt(Meal::totalCalories).sum();

        boolean withinLimit = totalCalories <= user.getDailyCalories();

        return new DailyReport(date, totalCalories, withinLimit, mealResponseDTOList);
    }


    public boolean checkIfWithinCalories(User user, LocalDate date) {
        List<Meal> meals = mealRepository.findByUserAndDate(user, date);
        Integer totalCalories = meals.stream().mapToInt(Meal::totalCalories).sum();
        return totalCalories <= user.getDailyCalories();
    }

    public List<MealResponseDTO> convertMealListToMealResponseDTOList(List<Meal> meals){
        List<MealResponseDTO> mealResponseDTOList = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            MealResponseDTO mealDTO = new MealResponseDTO();
            mealDTO.setUser(meals.get(i).getUser());
            mealDTO.setDate(meals.get(i).getDate());
            mealDTO.setDishes(new ArrayList<>());

            for (int j = 0; j < meals.get(i).getDishes().size(); j++) {
                DishDTO dishDTO = new DishDTO();
                dishDTO.setId(meals.get(i).getDishes().get(j).getId());
                dishDTO.setName(meals.get(i).getDishes().get(j).getName());
                dishDTO.setProtein(meals.get(i).getDishes().get(j).getProtein());
                dishDTO.setFat(meals.get(i).getDishes().get(j).getFat());
                dishDTO.setCaloriesPerServing(meals.get(i).getDishes().get(j).getCaloriesPerServing());
                dishDTO.setCarbs(meals.get(i).getDishes().get(j).getCarbs());

                mealDTO.getDishes().add(dishDTO);
                mealResponseDTOList.add(mealDTO);
            }
        }
        return mealResponseDTOList;
    }
}
