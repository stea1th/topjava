package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    void addMeal(Meal meal);
    void delete(int id);
    void editMeal(Meal meal);
    List<Meal> getAll();
    public Meal getMealById(int id);
}
