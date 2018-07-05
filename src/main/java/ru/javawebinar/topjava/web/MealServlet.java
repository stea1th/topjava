package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private InMemoryMealRepository repo;
    private Meal meal;


    public void init() {
        repo = new InMemoryMealRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        switch(action){
            case "delete" :
                log.debug(action+" meal");
                repo.delete(Integer.parseInt(req.getParameter("mealId")));
                break;
            case "edit" :
                log.debug("redirect to editMeal");
                String mealId = req.getParameter("mealId");
                if(mealId != null) {
                    log.debug("update meal");
                    int id = Integer.parseInt(mealId);
                    meal = repo.getById(id);
                }else{
                    log.debug("insert meal");
                    meal = new Meal(InMemoryMealRepositoryImpl.getId());
                    meal.setDateTime(LocalDateTime.now());
                }
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/editmeal.jsp").forward(req, resp);
                break;
            case "update" :
                String date = req.getParameter("date");
                if(!date.equals("")){
                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    meal.setDateTime(LocalDateTime.parse(date));
                }
                meal.setDescription(req.getParameter("description").equals("") ? meal.getDescription() : req.getParameter("description"));
                meal.setCalories(req.getParameter("calories").equals("") ? meal.getCalories() : Integer.parseInt(req.getParameter("calories")));
                repo.edit(meal);

        }
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doForward(req, resp);
    }

    private void doForward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        log.debug("redirect to meals");
        List<MealWithExceed> mealWithExceeds = MealsUtil.getUnfilteredWithExceeded(new ArrayList<>(repo.getAll().values()), 2000);
        req.setAttribute("list", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
