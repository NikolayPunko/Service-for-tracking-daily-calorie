package test.task.TestTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.task.TestTask.DTO.MealDTO;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;
import test.task.TestTask.service.MealService;
import test.task.TestTask.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;
    private final UserService userService;


    @Autowired
    public MealController(MealService mealService, UserService userService) {
        this.mealService = mealService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addMeal(@RequestBody MealDTO mealDTO) {
        try {
            Meal meal = mealService.convertMealDTOToMeal(mealDTO);
            mealService.addMeal(meal);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Meal created!");
    }

    @GetMapping("/daily/{email}")
    public ResponseEntity<List<Meal>> getDailyMeals(@PathVariable String email, @RequestParam String date) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        LocalDate parsedDate = LocalDate.parse(date);
        List<Meal> meals = mealService.getMealsByUserAndDate(user, parsedDate);
        return ResponseEntity.ok(meals);
    }
}
