package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Photo;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DeleteCandidateServlet", urlPatterns = {"/delete.do"})
public class DeleteCandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("/candidate/delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Candidate candidate = PsqlStore.instOf().findCandidateById(id);
        if (candidate != null) {
            if (candidate.getPhotoId() != 0) {
                Photo photo = PsqlStore.instOf().findPhotoById(candidate.getPhotoId());
                File file = new File("D:\\bin\\images" + File.separator + photo.getName());
                file.delete();
            }
            PsqlStore.instOf().delete(candidate);
        }
        resp.sendRedirect(req.getContextPath() + "/candidate.do");
    }
}
