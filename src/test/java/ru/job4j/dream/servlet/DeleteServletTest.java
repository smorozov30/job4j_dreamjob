package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class DeleteServletTest {

    @Test
    public void whenDeleteCandidateInDoPostThenRedirectInCandidate() throws ServletException, IOException {
        Candidate added = new Candidate(0,"Sergey Morozov", 0);
        Store store = MemStore.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        store.save(added);

        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        when(req.getParameter("id")).thenReturn(String.valueOf(added.getId()));

        new DeleteCandidateServlet().doPost(req, resp);

        Candidate result = store.findCandidateById(added.getId());
        assertNull(result);
        Mockito.verify(resp).sendRedirect(req.getContextPath() + "/candidate.do");
    }

    @Test
    public void whenGetRegJsp() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.mockStatic(PsqlStore.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(req.getRequestDispatcher("/candidate/delete.jsp")).thenReturn(dispatcher);

        new DeleteCandidateServlet().doGet(req, resp);

        Mockito.verify(req).getRequestDispatcher("/candidate/delete.jsp");
        Mockito.verify(dispatcher).forward(req, resp);
    }
}