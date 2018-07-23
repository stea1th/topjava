package ru.javawebinar.topjava.service;

import org.hibernate.LazyInitializationException;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.*;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MealServiceTest.class);

    private static List<String> result = new LinkedList<>();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Rule
    public final TestWatcher watcher = new TestWatcher() {

        private long start;
        private long finish;

        @Override
        protected void starting(Description description) {
            //start = new Date().getTime();     -- что предпочтительнее?
            start = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            //finish = new Date().getTime();
            finish = System.currentTimeMillis();
            String testResult = description.getMethodName()+": "+(finish - start)+"ms";
            result.add("Method name "+testResult);
            log.info(testResult);
        }
    };

    @AfterClass
    public static void printResult(){
        System.out.println("--------------------");
        result.forEach(System.out::println);
        System.out.println("--------------------");
    }

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
        throw new NotFoundException("Not Found");

    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        exception.expect(NoResultException.class);
        service.get(MEAL1_ID, ADMIN_ID);
        throw new NoResultException();
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        exception.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
        throw new NotFoundException("NotFoundException");
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);

    }
}