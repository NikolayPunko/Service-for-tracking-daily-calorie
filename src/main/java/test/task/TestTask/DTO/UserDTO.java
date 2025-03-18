package test.task.TestTask.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.task.TestTask.model.Goal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String name;

    @Email
    private String email;

    @Min(1)
    @Max(150)
    private Integer age;

    @Min(20)
    @Max(300)
    private Double weight;

    @Min(50)
    @Max(250)
    private Double height;

    @Enumerated(EnumType.STRING)
    private Goal goal;

}
