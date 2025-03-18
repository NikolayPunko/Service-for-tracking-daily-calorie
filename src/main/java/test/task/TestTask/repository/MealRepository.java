package test.task.TestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.TestTask.model.Meal;
import test.task.TestTask.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserAndDate(User user, LocalDate date);

    List<Meal> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
