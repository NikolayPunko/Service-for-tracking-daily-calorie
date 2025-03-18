package test.task.TestTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.task.TestTask.DTO.MealResponseDTO;
import test.task.TestTask.model.DailyReport;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;
import test.task.TestTask.repository.MealRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {


    private final MealRepository mealRepository;
    private final MealService mealService;

    @Autowired
    public ReportService(MealRepository mealRepository, MealService mealService) {
        this.mealRepository = mealRepository;
        this.mealService = mealService;
    }

    public List<DailyReport> getHistoryReport(User user, LocalDate startDate, LocalDate endDate) {
        // Извлекаем все приемы пищи за указанный период
        List<Meal> meals = mealRepository.findByUserAndDateBetween(user, startDate, endDate);

        // Группируем приемы пищи по дням
        Map<LocalDate, List<Meal>> mealsGroupedByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate));

        // Подготовим отчет
        List<DailyReport> reports = new ArrayList<>();
        for (Map.Entry<LocalDate, List<Meal>> entry : mealsGroupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Meal> dailyMeals = entry.getValue();
            List<MealResponseDTO> mealResponseDTOList = mealService.convertMealListToMealResponseDTOList(dailyMeals);
            Integer totalCalories = dailyMeals.stream().mapToInt(Meal::totalCalories).sum();
            boolean withinLimit = totalCalories <= user.getDailyCalories();

            reports.add(new DailyReport(date, totalCalories, withinLimit, mealResponseDTOList));
        }

        return reports;
    }
}
