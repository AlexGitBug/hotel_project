package servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UserInfoService;

@WebServlet("/userinfo")
public class UserInfoServlet extends HttpServlet {

    private final UserInfoService userInfoService = UserInfoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        int id = Integer.parseInt(req.getParameter("1"));

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        int amount = Integer.parseInt(req.getParameter("amount"));
        String password = req.getParameter("password");
        int roleId = Integer.parseInt(req.getParameter("roleId"));

        userInfoService.delete(id);
        userInfoService.findById(id);
        userInfoService.findAll();
        userInfoService.save(firstName, lastName, email, amount, password, roleId);
        userInfoService.update(id, firstName, lastName, email, amount, password, roleId);
    }
}

