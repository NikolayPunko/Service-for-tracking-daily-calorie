package test.task.TestTask.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.task.TestTask.model.User;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealResponseDTO {

    private User user;

    private LocalDate date;

    private List<DishDTO> dishes;

}
