package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealService service;
    private Meal meal;

    public MealServlet() {
        super();
        service = new MealServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch(action){
            case "delete" :
                service.delete(Integer.parseInt(req.getParameter("mealId")));
                break;
            case "edit" :
                log.debug("redirect to editMeal");
                String mealId = req.getParameter("mealId");
                if(mealId != null) {
                    log.debug("update meal");
                    int id = Integer.parseInt(mealId);
                    meal = service.getMealById(id);
                }else{
                    log.debug("insert meal");
                    meal = new Meal();
                    meal.setDateTime(LocalDateTime.now());
                }
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
                break;
            case "update" :
                meal.setDescription(req.getParameter("description").equals("")? meal.getDescription() : req.getParameter("description"));
                meal.setCalories(req.getParameter("calories").equals("")? meal.getCalories() : Integer.parseInt(req.getParameter("calories")));
                service.editMeal(meal);
        }
        log.debug(action+" meal");
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealWithExceed> mealWithExceeds = MealsUtil.getUnfilteredWithExceeded(service.getAll(), 2000);
        req.setAttribute("list", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);










    }
}
