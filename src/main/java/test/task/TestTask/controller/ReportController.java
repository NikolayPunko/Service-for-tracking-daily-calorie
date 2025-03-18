package test.task.TestTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.task.TestTask.model.DailyReport;
import test.task.TestTask.model.User;
import test.task.TestTask.service.MealService;
import test.task.TestTask.service.ReportService;
import test.task.TestTask.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final MealService mealService;
    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public ReportController(MealService mealService, ReportService reportService, UserService userService) {
        this.mealService = mealService;
        this.reportService = reportService;
        this.userService = userService;
    }

    // Отчет за день с суммой всех калорий и приемов пищи
    @GetMapping("/daily/{email}")
    public ResponseEntity<DailyReport> getDailyReport(@PathVariable String email, @RequestParam String date) {

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        LocalDate parsedDate = LocalDate.parse(date);

        DailyReport report = mealService.getDailyReport(user, parsedDate);

        return ResponseEntity.ok(report);
    }

    // Проверка, уложился ли пользователь в свою дневную норму калорий
    @GetMapping("/check-calories/{email}")
    public ResponseEntity<Boolean> checkIfWithinCalories(@PathVariable String email, @RequestParam String date) {

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        LocalDate parsedDate = LocalDate.parse(date);

        boolean withinLimit = mealService.checkIfWithinCalories(user, parsedDate);

        return ResponseEntity.ok(withinLimit);
    }

    // История питания по дням
    @GetMapping("/history/{email}")
    public ResponseEntity<List<DailyReport>> getHistoryReport(@PathVariable String email,
                                                              @RequestParam String startDate,
                                                              @RequestParam String endDate) {
        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);

        List<DailyReport> historyReport = reportService.getHistoryReport(user, parsedStartDate, parsedEndDate);

        return ResponseEntity.ok(historyReport);
    }
}
