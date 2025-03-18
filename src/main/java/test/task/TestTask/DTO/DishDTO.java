package test.task.TestTask.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {

    private Long id;
    private String name;
    private Integer caloriesPerServing;
    private Double protein;
    private Double fat;
    private Double carbs;

}
