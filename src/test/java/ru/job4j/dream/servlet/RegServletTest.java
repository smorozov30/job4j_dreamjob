package ru.job4j.dream.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class RegServletTest {

    @Test
    public void whenRegUserInDoPostThenStoreItAndRedirectInLoginJsp() throws ServletException, IOException {
        User addedUser = new User("Sergey Morozov", "root@local", "root");
        Store store = MemStore.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        when(req.getParameter("name")).thenReturn(addedUser.getName());
        when(req.getParameter("email")).thenReturn(addedUser.getEmail());
        when(req.getParameter("password")).thenReturn(addedUser.getPassword());
        when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        new RegServlet().doPost(req, resp);

        User result = store.checkEmail(new User("root@local", "root"));
        assertThat(result.getName(), is(addedUser.getName()));
        assertThat(result.getEmail(), is(addedUser.getEmail()));
        assertThat(result.getPassword(), is(addedUser.getPassword()));
        Mockito.verify(req).getRequestDispatcher("login.jsp");
    }

    @Test
    public void whenGetRegJsp() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        PowerMockito.mockStatic(PsqlStore.class);
        when(req.getRequestDispatcher("reg.jsp")).thenReturn(dispatcher);

        new RegServlet().doGet(req, resp);

        Mockito.verify(req).getRequestDispatcher("reg.jsp");
        Mockito.verify(dispatcher).forward(req, resp);
    }
}
