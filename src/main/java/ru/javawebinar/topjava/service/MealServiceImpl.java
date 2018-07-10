package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {


    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);

    }

    @Override
    public void update(Meal meal) {
        repository.save(meal);

    }

    @Override
    public Meal get(int id) throws NotFoundException {
        Meal meal = repository.get(id);
        if(meal == null || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("not found");
        else
            return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(meals, caloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(meals, caloriesPerDay, startTime, endTime);
    }


}