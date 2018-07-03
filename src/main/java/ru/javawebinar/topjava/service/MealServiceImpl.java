package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public class MealServiceImpl implements MealService {


    @Override
    public void addMeal(Meal meal) {
        MealRepository.getMeals().add(meal);
    }

    @Override
    public void delete(int id) {
        MealRepository.getMeals().remove(getMealById(id));
    }

    @Override
    public void editMeal(Meal meal) {

    }

    @Override
    public List<Meal> getAll() {
        return MealRepository.getMeals();
    }

    public Meal getMealById(int id){
        return  MealRepository.getMeals()
                .stream()
                .filter(x-> x.getId()==id)
                .findFirst()
                .get();
    }
}
