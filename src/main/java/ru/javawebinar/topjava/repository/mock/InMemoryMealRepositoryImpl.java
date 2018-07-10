package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);

    }

    /*public static void main(String[] args) {
        MealRepository meal = new InMemoryMealRepositoryImpl();
        meal.getAllByUserId(1).forEach(System.out::println);

    }*/

    @Override
    public Meal save(Meal meal) {
        if(meal.getUserId()== AuthorizedUser.id()) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            repository.put(meal.getId(), meal);
            return meal;
        }
        else return meal;
    }

    @Override
    public void delete(int id) {
        Meal meal = repository.get(id);
        if(meal.getUserId()== AuthorizedUser.id())
                repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        Meal meal = repository.get(id);
        if(meal.getUserId()== AuthorizedUser.id())
            return meal;
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .filter(x-> x.getUserId() == AuthorizedUser.id())
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}

