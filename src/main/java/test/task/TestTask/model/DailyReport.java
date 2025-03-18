package test.task.TestTask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.task.TestTask.DTO.MealDTO;
import test.task.TestTask.DTO.MealResponseDTO;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyReport {

    private LocalDate date;
    private Integer totalCalories;
    private boolean withinDailyLimit;
    private List<MealResponseDTO> mealsResponseDto;

}
