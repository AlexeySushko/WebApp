
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;


@WebServlet("/start")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logic.doGetMyServlet(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {




        if(req.getParameter("changeLang")!=null){

            Method.setLanguage(req, resp);
            Method.openStartPage(req, resp);

        }if(req.getParameter("user").length()>3&req.getParameter("user").length()<9&
                req.getParameter("pass").length()>3&req.getParameter("pass").length()<9){
            HttpSession s = req.getSession();
            if(s.getAttribute("locale")==null){
                Locale locale  = new Locale(Constant.LANGUAGE_RU);
                s.setAttribute("locale", locale);
            }
            Logic.doPostMyServlet(req,resp);
        }else{
            req.setAttribute("messageError", "Логин и пароль должен быть длинной 4-8 символов");
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
            rd.forward(req, resp);
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("<<< Service MyServlet >>> ");
        super.service(req, resp);
    }
}
