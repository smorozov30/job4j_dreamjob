package ru.job4j.dream.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.dream.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GreetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        Answer answer = new Answer(name);
        answer.updateName("Nice to meet you, " + answer.getName());
        String jsonResp = gson.toJson(answer);
        resp.getWriter().write(jsonResp);
    }
}