package servlet;

import entity.Enum.ConditionEnum;;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OrderService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));


//        var userInfoId = Integer.parseInt(req.getParameter("userId"));
//        var roomId = Integer.parseInt(req.getParameter("roomId"));
        var beginTime = LocalDateTime.parse(req.getParameter("beginTime"));
        var endTime = LocalDateTime.parse(req.getParameter("endTime"));
        var condition = ConditionEnum.valueOf(req.getParameter("condition"));
        var message = req.getParameter("message");

  //      userInfoId, roomId,

                orderService.delete(id);
        orderService.findById(id);
        orderService.findAll();
        orderService.save(beginTime, endTime, condition, message);
        orderService.update(id,beginTime, endTime, condition, message);
    }

}
