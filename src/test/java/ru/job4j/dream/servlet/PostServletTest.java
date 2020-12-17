package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPostInDoPostThenStoreItAndRedirectInPost() throws ServletException, IOException {
        Post added = new Post(0, "Java Job", "В компанию требуется Java Junior Developer", new Timestamp(System.currentTimeMillis()));
        Store store = MemStore.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        when(req.getParameter("id")).thenReturn(String.valueOf(added.getId()));
        when(req.getParameter("name")).thenReturn(added.getName());
        when(req.getParameter("description")).thenReturn(added.getDescription());

        new PostServlet().doPost(req, resp);

        Post result = store.findPostById(1);
        assertThat(result.getId(), is(result.getId()));
        assertThat(result.getName(), is(result.getName()));
        assertThat(result.getDescription(), is(result.getDescription()));
        Mockito.verify(resp).sendRedirect(req.getContextPath() + "/post.do");
    }

    @Test
    public void whenGetAllPostsOnPostsJsp() throws ServletException, IOException {
        Store store = MemStore.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(req.getRequestDispatcher("/post/posts.jsp")).thenReturn(dispatcher);

        new PostServlet().doGet(req, resp);

        Mockito.verify(req).getRequestDispatcher("/post/posts.jsp");
        Mockito.verify(dispatcher).forward(req, resp);
    }
}