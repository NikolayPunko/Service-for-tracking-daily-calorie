package test.task.TestTask.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "calories_per_serving")
    private Integer caloriesPerServing;
    private Double protein;
    private Double fat;
    private Double carbs;

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Meal> meals;

}
