package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository {
    private static List<Meal> meals;
    private static AtomicInteger id = new AtomicInteger();

    static{
        meals = new CopyOnWriteArrayList(Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 07, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ));
    }

    public MealRepository() {

    }

    public static int getId() {
        return id.incrementAndGet();
    }

    public static List<Meal> getMeals() {
        return meals;
    }
}
