package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        mealList.stream().filter(x-> x.getDateTime().toLocalTime().isAfter(startTime) && x.getDateTime().toLocalTime().isBefore(endTime))
                 .forEach(userMeal-> {
             if(mealList.stream()
                     .collect(Collectors.groupingBy((UserMeal meal)-> meal.getDateTime().toLocalDate(), Collectors.summingLong(UserMeal::getCalories)))
                     .entrySet()
                     .stream()
                     .filter(entry-> entry.getValue()>caloriesPerDay)
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toList())
                     .contains(userMeal.getDateTime().toLocalDate())){
                 userMealWithExceeds.add(new UserMealWithExceed(userMeal, true));
             }else{
                 userMealWithExceeds.add(new UserMealWithExceed(userMeal, false));
             }
         });

         return userMealWithExceeds;
    }
}
