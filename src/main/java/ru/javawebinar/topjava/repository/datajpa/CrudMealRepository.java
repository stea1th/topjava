package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    List<Meal> findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Transactional
    int deleteMealByIdAndUserId(int id, int userId);

    @Transactional
    Meal save(Meal meal);

    @Transactional
    Meal findFirstByIdAndUserId(int id, int userId);
}
