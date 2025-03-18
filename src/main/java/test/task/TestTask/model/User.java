package test.task.TestTask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    private String email;

    private Integer age;
    private Double weight;
    private Double height;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    private Double dailyCalories;

    @PrePersist
    public void calculateDailyCalories() {
        this.dailyCalories = calculateCalories(this);
    }

    private Double calculateCalories(User user) {
        if (user.getGoal() == Goal.LOSE_WEIGHT) {
            return (10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161) * 0.8;
        } else if (user.getGoal() == Goal.MAINTAIN) {
            return (10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161);
        } else {
            return (10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161) * 1.2;
        }
    }

}


