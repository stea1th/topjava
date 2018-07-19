package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.junit.Assert.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {


    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal newMeal = service.get(MEAL_ID, USER_ID);
        assertMatch(newMeal, MEAL);

    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(DATE_TIME.toLocalDate(), DATE_TIME.toLocalDate(), USER_ID),
                MEALS.stream()
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(),
                                DATE_TIME.toLocalDate(),
                                DATE_TIME.toLocalDate()))
        .collect(Collectors.toList()));
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(DATE_TIME.minusHours(1), DATE_TIME.plusHours(1), USER_ID),
                MEALS.stream()
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(),
                                DATE_TIME.minusHours(1),
                                DATE_TIME.plusHours(1)))
                        .collect(Collectors.toList()));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEALS);

    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL);
        updated.setDescription("TestDescript");
        updated.setCalories(880);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }



    @Test(expected = NotFoundException.class)
    public void NotFoundDelete(){
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void NotFoundGet(){
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void NotFoundUpdate(){
        service.update(service.get(1, USER_ID), USER_ID);
    }

    @Test
    public void delete() {
        MEALS.remove(MEAL);
        //service.delete(MEAL_ID, USER_ID);
        service.delete(MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEALS);
        MEALS.add(MEAL);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "Полдник", 400);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MEALS.add(0, newMeal);
        assertMatch(service.getAll(USER_ID), MEALS);
        MEALS.remove(newMeal);

    }
}