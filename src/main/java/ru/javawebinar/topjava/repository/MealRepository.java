package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealRepository implements MealRepo {
    private static AtomicInteger id = new AtomicInteger();
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealRepository() {
          Arrays.asList(
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 30, 07, 0), "Завтрак", 500),
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(getId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
                .forEach(x-> meals.put(x.getId(), x));
    }

    public static int getId() {
        return id.incrementAndGet();
    }

    @Override
    public Meal add(Meal meal) {
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        if(getById(id)!=null){
            meals.remove(id);
        }
    }

    @Override
    public Meal edit(Meal meal) {
        return add(meal);
    }

    @Override
    public Meal getById(int id) {
        return meals.keySet().contains(id)? meals.get(id) : null;
    }

    @Override
    public Map<Integer, Meal> getAll() {
        return meals;
    }

}
