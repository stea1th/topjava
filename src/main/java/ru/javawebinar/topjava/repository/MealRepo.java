package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealRepo {
    Meal add(Meal meal);
    void delete(int id);
    Meal edit(Meal meal);
    Meal getById(int id);
    Map<Integer, Meal> getAll();
}
