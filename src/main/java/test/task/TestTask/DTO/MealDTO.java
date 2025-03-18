package test.task.TestTask.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.task.TestTask.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {

    private Long userId;

    private LocalDate date;

    private List<Long> dishes;

}
