package test.task.TestTask.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.TestTask.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
}