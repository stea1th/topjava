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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealService service;
    private static String LIST_MEAL = "/meals.jsp";
    private static String EDIT_MEAL = "/editMeal.jsp";

    public MealServlet() {
        super();
        service = new MealServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        switch(action){
            case "delete" :
                service.delete(Integer.parseInt(req.getParameter("mealId")));
                forward = LIST_MEAL;
                break;
            case "edit" :
                int id = Integer.parseInt(req.getParameter("mealId"));
                Meal meal = service.getMealById(id);
                service.delete(id);
                forward = EDIT_MEAL;
                req.getRequestDispatcher(forward).forward(req, resp);
                req.getParameter("calories");
                System.out.println(req.getParameter("calories"));

                //meal.setCalories(Integer.parseInt(req.getParameter("calories")));
                System.out.println(req.getParameter("calories"));
                //service.addMeal(meal);
                //forward = LIST_MEAL;

        }
        List<MealWithExceed> mealWithExceeds = MealsUtil.getUnfilteredWithExceeded(service.getAll(), 2000);
        req.setAttribute("list", mealWithExceeds);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<MealWithExceed> mealWithExceeds = MealsUtil.getUnfilteredWithExceeded(service.getAll(), 2000);
        req.setAttribute("list", mealWithExceeds);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);










    }
}
