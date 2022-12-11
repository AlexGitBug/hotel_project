package servlet;

import entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.RoomService;

import java.io.IOException;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));


        var quantityBed = Integer.parseInt(req.getParameter("quantityBed"));
        int categoryRoom = Integer.parseInt(req.getParameter("categoryRoom"));
        int floor = Integer.parseInt(req.getParameter("floor"));
        int dayPrice = Integer.parseInt(req.getParameter("dayPrice"));
        var status = RoomStatusEnum.valueOf(req.getParameter("status"));

        var numberRoom = NumberRoom.valueOf(req.getParameter("numberRoom"));


        roomService.delete(id);
        roomService.findById(id);
        roomService.findAll();
        roomService.save(numberRoom, quantityBed, categoryRoom, floor, dayPrice, status);
        roomService.update(id, numberRoom, quantityBed, categoryRoom, floor, dayPrice, status);
    }

}