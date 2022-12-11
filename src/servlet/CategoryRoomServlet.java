package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryRoomService;

import java.io.IOException;

@WebServlet("/categoryroom")
public class CategoryRoomServlet extends HttpServlet {

    private final CategoryRoomService categoryRoomService = CategoryRoomService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String kind = req.getParameter("kind");

        categoryRoomService.delete(id);
        categoryRoomService.findAll();
        categoryRoomService.findById(id);
        categoryRoomService.saveCategoryRoom(kind);
        categoryRoomService.updateRoom(id, kind);

    }

}
