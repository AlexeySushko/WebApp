import db.DBManager;
import entity.Service;
import entity.Tariff;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Method {


    public static Integer calcSumTariffs(List<Service> tariffList) {
        Integer sum = 0;
        for (Service service : tariffList) {
            sum += service.getPrice();
        }
        return sum;
    }

    public static ArrayList<Tariff> otherTariffs(ArrayList<Tariff> allTariff, ArrayList<Tariff> myTariff) {

        ArrayList<Tariff> otherTariff = new ArrayList<>();

        for (Tariff t : allTariff) {
            if (!searchElementTariff(t.getId(), myTariff)) {
                otherTariff.add(t);
            }
        }
        return otherTariff;
    }

    private static boolean searchElementTariff(int id, ArrayList<Tariff> myTariff) {
        for (Tariff tariff : myTariff) {
            if (tariff.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static Tariff createTariff(HttpServletRequest req) {
        Tariff tariff = new Tariff();
        tariff.setNameTariff(req.getParameter("nameTariff"));
//        tariff.setPrice(Integer.parseInt(req.getParameter("price")));
        tariff.setComment(req.getParameter("comment"));
        return tariff;
    }

    public static Service createService(HttpServletRequest req) {
        Service service = new Service();
        service.setNameService(req.getParameter("nameService"));
        service.setPrice(Integer.parseInt(req.getParameter("price")));
        service.setComment(req.getParameter("comment"));
        return service;
    }

    public static User createUser(HttpServletRequest req) {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPass(Utilities.encryptText(req.getParameter("pass")));
        user.setFio(req.getParameter("fio"));
        user.setBalance(Integer.parseInt(req.getParameter("balance")));
        user.setTell(req.getParameter("tell"));
        user.setAdress(req.getParameter("adress"));
        if (req.getParameter("block").equals("Blocked")) {
            user.setBlock(true);
        } else {
            user.setBlock(false);
        }

        return user;
    }

    public static HttpServletRequest sortTariff(HttpServletRequest req) {
        DBManager db = DBManager.getInstance();
        ArrayList<Tariff> tariffs = db.findAlltariff();//получим все тарифы
        List<User> users = db.findAllUsers();//получим всех пользователей

        if (req.getParameter("sort").equals("Name A-Z")) {
            Collections.sort(tariffs, new Comparator<Tariff>() {
                @Override
                public int compare(Tariff tariff2, Tariff tariff1) {

                    return tariff2.getNameTariff().compareTo(tariff1.getNameTariff());
                }
            });

        }
        if (req.getParameter("sort").equals("Name Z-A")) {
            Collections.sort(tariffs, new Comparator<Tariff>() {
                @Override
                public int compare(Tariff tariff2, Tariff tariff1) {

                    return tariff1.getNameTariff().compareTo(tariff2.getNameTariff());
                }
            });

        }
        if (req.getParameter("sort").equals("Price 9-1")) {
            Collections.sort(tariffs, new Comparator<Tariff>() {
                @Override
                public int compare(Tariff tariff2, Tariff tariff1) {

                    return tariff1.getPrice() - (tariff2.getPrice());
                }
            });

        }
        if (req.getParameter("sort").equals("Price 1-9")) {

            Collections.sort(tariffs, new Comparator<Tariff>() {
                @Override
                public int compare(Tariff tariff1, Tariff tariff2) {

                    return tariff1.getPrice() - (tariff2.getPrice());
                }
            });

        }

        ArrayList<Service> allService = db.findAllService();//получим все сервисы
        req.setAttribute("allService", allService);
        req.setAttribute("arrTariff", tariffs);
        req.setAttribute("arrUser", users);

        return req;
    }

    public static HttpServletRequest addTariffForUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager db = DBManager.getInstance();
        Tariff tariff = db.findOneTariff(req.getParameter("selectTafiff"));
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("currentUser");
        if (db.addDeleteTarifForUser(user, tariff, true)) {
            req = createReqForUser(req);
        } else {

            //При повторной отправке запроса будет ошибка и мы перейдем на нашу же страницу Пользователя
            req = createReqForUser(req);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);
        }

        return req;
    }

    public static HttpServletRequest deleteTariffForUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager db = DBManager.getInstance();
        Tariff tariff = db.findOneTariff(req.getParameter("deleteTafiff"));
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("currentUser");
        if (db.addDeleteTarifForUser(user, tariff, false)) {
            req = createReqForUser(req);
        } else {

            //При повторной отправке запроса будет ошибка и мы перейдем на нашу же страницу Пользователя
            req = createReqForUser(req);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);
        }

        return req;
    }

    public static HttpServletRequest createReqForUser(HttpServletRequest req) {
        HttpSession s = req.getSession();
        User user = (User) s.getAttribute("currentUser");

        DBManager db = DBManager.getInstance();
        ArrayList<Tariff> arrTariff = db.findTariffForOneUser(user.getLogin());
        ArrayList<Tariff> allTariff = db.findAlltariff();
        ArrayList<Tariff> tariffs = db.findAlltariff();//получим все тарифы

        ArrayList<Tariff> otherTariffs = Method.otherTariffs(tariffs, arrTariff);
        ArrayList<Service> arrServiceForUser = db.searchServiceForUser(user);
        Integer allSum = Method.calcSumTariffs(arrServiceForUser);

        String status = "";
        if (user.isBlock()) {
            status = "BLOCKED";
        } else {
            status = "UNBLOCKED";
        }
        //сначала установим значения все на будущей форме
        req.setAttribute("nameUser", user.getFio());
        req.setAttribute("statusUser", status);
        req.setAttribute("balanceUser", user.getBalance());
        req.setAttribute("loginUser", user.getLogin());
        req.setAttribute("tellUser", user.getTell());
        req.setAttribute("adressUser", user.getAdress());
        req.setAttribute("arrTariff", arrTariff);
        req.setAttribute("allMoney", allSum);
        req.setAttribute("otherTariffs", allTariff);
        req.setAttribute("arrServiceForUser", arrServiceForUser);
        s.setAttribute("allSum", allSum);
        s.setAttribute("balanceUser", user.getBalance());

        return req;
    }


    public static Tariff getOneTarif(String name) {
        DBManager db = DBManager.getInstance();
        Tariff tariff = db.searchTariffName(name, true);
        return tariff;

    }

    public static void openAdminPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager db = DBManager.getInstance();
        HttpSession s = req.getSession();
        ArrayList<Tariff> tariffs = db.findAlltariff();//получим все тарифы
        ArrayList<Service> allService = db.findAllService();//получим все сервисы

        List<User> users = db.findAllUsers();//получим всех пользователей
        req.setAttribute("arrUser", users);
        req.setAttribute("arrTariff", tariffs);
        req.setAttribute("allService", allService);
        User user = (User) s.getAttribute("currentUser");
        req.setAttribute("nameAdmin", user.getFio());
        RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ADMIN_PAGE);
        rd.forward(req, resp);
    }

    public static boolean deleteTariff(String nameTariff) {
        DBManager db = DBManager.getInstance();
        ArrayList<Service> list = db.allServiceForTarif(nameTariff);
        boolean res = false;

        if (db.deleteServiceTariff(nameTariff, false)) {
            res = true;

            for (Service service : list) {
                if (db.deleteServiceTariff(service.getNameService(), true)) {
                    res = true;
                }
            }
            return res;
        }
        return res;
    }

    public static HttpServletRequest sortService(HttpServletRequest req, ArrayList<Service> list) {
        DBManager db = DBManager.getInstance();
        ArrayList<Service> service = list;

        if (req.getParameter("sort").equals("Name A-Z")) {
            Collections.sort(service, new Comparator<Service>() {
                @Override
                public int compare(Service service2, Service service1) {

                    return service2.getNameService().compareTo(service1.getNameService());
                }
            });

        }
        if (req.getParameter("sort").equals("Name Z-A")) {
            Collections.sort(service, new Comparator<Service>() {
                @Override
                public int compare(Service service2, Service service1) {

                    return service1.getNameService().compareTo(service2.getNameService());
                }
            });

        }
        if (req.getParameter("sort").equals("Price 9-1")) {
            Collections.sort(service, new Comparator<Service>() {
                @Override
                public int compare(Service service2, Service service1) {

                    return service1.getPrice() - (service2.getPrice());
                }
            });

        }
        if (req.getParameter("sort").equals("Price 1-9")) {

            Collections.sort(service, new Comparator<Service>() {
                @Override
                public int compare(Service service1, Service service2) {

                    return service1.getPrice() - (service2.getPrice());
                }
            });

        }

        ArrayList<Service> allService = db.findAllService();//получим все сервисы
        req.setAttribute("arrServiceForUser", service);

        return req;
    }

    public static void writeFile(String nameFile, String text) {

        try (FileWriter writer = new FileWriter("C:/Users/Olga/IdeaProjects/SummaryTask4/"+nameFile+".txt", false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public static String createFileForUser(User user, ArrayList<Service> serviceList){
        StringBuffer sb = new StringBuffer();
        StringBuffer services = new StringBuffer("");
        int i =1;
        for (Service serv:serviceList) {
             services.append(i).append(". ").append(serv.getNameService()).append("\n\tСтоимость:").append(serv.getPrice()).append("\n" +
                     "\tОписание: ").append(serv.getComment()).append("\n\n\t");
            i++;
        }
        i=1;

        String status = user.isBlock()?"BLOCKED":"UNBLOCKED";
        sb.append("Договор №").append(user.getId()).append(" на предоставление услуг\n" +
                "Пользователю ").append(user.getFio()).append("\nЛогин - ").append(user.getLogin()).append("\nТекущий " +
                "статус - ").append(status).append("\nБалланс = ").append(user.getBalance()).append("\nТелефон " +
                "пользователя: ").append(user.getTell()).append("\nАдресс пользователя - ").append(user.getAdress()).append("\n" +
                "\n\n").append("\nПодключенные услуги:\n\t").append(services);

        return sb.toString();
    }

    public static String createPriceForUser(User user){

        return "";
    }
}
