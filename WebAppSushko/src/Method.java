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
import java.io.UnsupportedEncodingException;
import java.util.*;

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
        Method.setAttributeAdminPage(req);
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

    public static void setLanguage(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession s = req.getSession();
        Locale locale;
        System.out.println("ЯЗЫК >>> "+req.getParameter("changeLanguage"));

        if(req.getParameter("changeLanguage").equals("Русский")){
            locale  = new Locale(Constant.LANGUAGE_RU);
        }else{
            locale  = new Locale(Constant.LANGUAGE_EN);
        }

        s.setAttribute("locale", locale);
//        System.out.println("ЯЗЫК Установили >>> "+resourceBundle.toString());
    }

    public static void openStartPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        System.out.println(rb.getString(Constant.RESOURCE_KEY_STARTFORM_LOG_IN));

        String LogIn = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_LOG_IN).getBytes("ISO-8859-1"), "UTF-8");
        String username = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_LOGIN).getBytes("ISO-8859-1"), "UTF-8");
        String password = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_PASSWORD).getBytes("ISO-8859-1"), "UTF-8");
        String infoString = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_INFO).getBytes("ISO-8859-1"), "UTF-8");
        String enter = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_ENTER).getBytes("ISO-8859-1"), "UTF-8");
        String forgot = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_FORGOT).getBytes("ISO-8859-1"), "UTF-8");
        String changeLanguage = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_LANG).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("LogIn",LogIn);
        req.setAttribute("username",username);
        req.setAttribute("password",password);
        req.setAttribute("infoString",infoString);
        req.setAttribute("enter",enter);
        req.setAttribute("forgot",forgot);
        req.setAttribute("changeLanguage",changeLanguage);

        RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_START_PAGE);
        rd.forward(req, resp);
    }

    public static void setAttributeAdminPage(HttpServletRequest req) throws UnsupportedEncodingException {

        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String administrator = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADMINISTRATOR).getBytes("ISO-8859-1"), "UTF-8");
        String newAdmin = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_ADMIN).getBytes("ISO-8859-1"), "UTF-8");
        String allTariff = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ALL_T).getBytes("ISO-8859-1"), "UTF-8");
        String descript = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");
        String changeT = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_CHNGE_T).getBytes("ISO-8859-1"), "UTF-8");
        String delTariff = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DELETE_T).getBytes("ISO-8859-1"), "UTF-8");
        String newTariff = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_T).getBytes("ISO-8859-1"), "UTF-8");
        String tableService = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TABLE_SERVICE).getBytes("ISO-8859-1"), "UTF-8");
        String id = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ID).getBytes("ISO-8859-1"), "UTF-8");
        String changeS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_CHANGE_S).getBytes("ISO-8859-1"), "UTF-8");
        String openTforS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_OPEN_T_FOR_S).getBytes("ISO-8859-1"), "UTF-8");
        String deleteS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DELETE_S).getBytes("ISO-8859-1"), "UTF-8");
        String newS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_S).getBytes("ISO-8859-1"), "UTF-8");
        String sorted = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_SORTED).getBytes("ISO-8859-1"), "UTF-8");
        String tableU = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TABLE_U).getBytes("ISO-8859-1"), "UTF-8");
        String fio = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_FIO).getBytes("ISO-8859-1"), "UTF-8");
        String balance = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_BALANCE).getBytes("ISO-8859-1"), "UTF-8");
        String tell = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TELL).getBytes("ISO-8859-1"), "UTF-8");
        String editU = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_EDIT_U).getBytes("ISO-8859-1"), "UTF-8");
        String newU = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_U).getBytes("ISO-8859-1"), "UTF-8");
        String exit = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_EXIT).getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String price = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_PRICE).getBytes("ISO-8859-1"), "UTF-8");
        String login = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_LOGIN).getBytes("ISO-8859-1"), "UTF-8");
        String adress = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADRESS).getBytes("ISO-8859-1"), "UTF-8");
        String block = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_BLOCK).getBytes("ISO-8859-1"), "UTF-8");
        String username = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_USERNAME).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("administrator",administrator);
        req.setAttribute("newAdmin",newAdmin);
        req.setAttribute("allTariff",allTariff);
        req.setAttribute("descript",descript);
        req.setAttribute("changeT",changeT);
        req.setAttribute("delTariff",delTariff);
        req.setAttribute("newTariff",newTariff);
        req.setAttribute("tableService",tableService);
        req.setAttribute("id",id);
        req.setAttribute("name",name);
        req.setAttribute("username",username);
        req.setAttribute("price",price);
        req.setAttribute("changeS",changeS);
        req.setAttribute("openTforS",openTforS);
        req.setAttribute("deleteS",deleteS);
        req.setAttribute("newS",newS);
        req.setAttribute("sorted",sorted);
        req.setAttribute("tableU",tableU);
        req.setAttribute("login",login);
        req.setAttribute("fio",fio);
        req.setAttribute("balance",balance);
        req.setAttribute("tell",tell);
        req.setAttribute("adress",adress);
        req.setAttribute("block",block);
        req.setAttribute("editU",editU);
        req.setAttribute("newU",newU);
        req.setAttribute("exit",exit);
    }

    public static void setAttributeUserPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String user = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_USER).getBytes("ISO-8859-1"), "UTF-8");
        String downlMyTariff = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_DOWNL_MY_T).getBytes("ISO-8859-1"), "UTF-8");
        String downlAllTariff = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_DOWNL_ALL).getBytes("ISO-8859-1"), "UTF-8");
        String status = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_STATUS).getBytes("ISO-8859-1"), "UTF-8");
        String allMoneyToCosts = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_ALL_MONEY_TO_COSTS).getBytes("ISO-8859-1"), "UTF-8");
        String pay = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_PAY).getBytes("ISO-8859-1"), "UTF-8");
        String myTariff = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_MY_TARIFF).getBytes("ISO-8859-1"), "UTF-8");
        String myService = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_MY_SERVICE).getBytes("ISO-8859-1"), "UTF-8");
        String allService = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_ALL_SERVICE).getBytes("ISO-8859-1"), "UTF-8");
        String openTariff = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_OPEN_TARIFF).getBytes("ISO-8859-1"), "UTF-8");
        String balance = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_BALANCE).getBytes("ISO-8859-1"), "UTF-8");
        String login = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_LOGIN).getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String tell = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TELL).getBytes("ISO-8859-1"), "UTF-8");
        String adress = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADRESS).getBytes("ISO-8859-1"), "UTF-8");
        String id = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ID).getBytes("ISO-8859-1"), "UTF-8");
        String username = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_USERNAME).getBytes("ISO-8859-1"), "UTF-8");
        String price = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_PRICE).getBytes("ISO-8859-1"), "UTF-8");
        String descript = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");
        String delTariff = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DELETE_T).getBytes("ISO-8859-1"), "UTF-8");
        String sorted = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_SORTED).getBytes("ISO-8859-1"), "UTF-8");
        String exit = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_EXIT).getBytes("ISO-8859-1"), "UTF-8");

        req.setAttribute("user",user);
        req.setAttribute("downlMyTariff",downlMyTariff);
        req.setAttribute("downlAllTariff",downlAllTariff);
        req.setAttribute("status",status);
        req.setAttribute("allMoneyToCosts",allMoneyToCosts);
        req.setAttribute("pay",pay);
        req.setAttribute("myTariff",myTariff);
        req.setAttribute("myService",myService);
        req.setAttribute("allService",allService);
        req.setAttribute("openTariff",openTariff);
        req.setAttribute("balance",balance);
        req.setAttribute("login",login);
        req.setAttribute("adress",adress);
        req.setAttribute("username",username);
        req.setAttribute("name",name);
        req.setAttribute("id",id);
        req.setAttribute("tell",tell);
        req.setAttribute("price",price);
        req.setAttribute("descript",descript);
        req.setAttribute("delTariff",delTariff);
        req.setAttribute("sorted",sorted);
        req.setAttribute("exit",exit);



    }

    public static void setAttributeTariffPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);


        String tariff = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_TARIFF).getBytes("ISO-8859-1"), "UTF-8");
        String addInMyT = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_TARIFF_ADD_IN_MY_TARIFF).getBytes("ISO-8859-1"), "UTF-8");
        String back = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");
        String id = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ID).getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String price = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_PRICE).getBytes("ISO-8859-1"), "UTF-8");
        String descript = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");

        req.setAttribute("tariff",tariff);
        req.setAttribute("id",id);
        req.setAttribute("name",name);
        req.setAttribute("price",price);
        req.setAttribute("descript",descript);
        req.setAttribute("addInMyT",addInMyT);
        req.setAttribute("back",back);
    }

    public static void setAttributeNewAdminPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String newAdmin = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_ADMIN).getBytes("ISO-8859-1"), "UTF-8");
        String username = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_USERNAME).getBytes("ISO-8859-1"), "UTF-8");
        String password = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_PASSWORD).getBytes("ISO-8859-1"), "UTF-8");
        String fio = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_FIO).getBytes("ISO-8859-1"), "UTF-8");
        String tell = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TELL).getBytes("ISO-8859-1"), "UTF-8");
        String adress = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADRESS).getBytes("ISO-8859-1"), "UTF-8");
        String addAdmin = new String(rb.getString(Constant.RESOURCE_KEY_NEW_ADMIN_PAGE).getBytes("ISO-8859-1"), "UTF-8");
        String back = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("newAdmin",newAdmin);
        req.setAttribute("login",username);
        req.setAttribute("password",password);
        req.setAttribute("fio",fio);
        req.setAttribute("tell",tell);
        req.setAttribute("adress",adress);
        req.setAttribute("addAdmin",addAdmin);
        req.setAttribute("back",back);
    }

    public static void setAttributeNewUserPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String newUser = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_USER).getBytes("ISO-8859-1"), "UTF-8");
        String username = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_USERNAME).getBytes("ISO-8859-1"), "UTF-8");
        String password = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_PASSWORD).getBytes("ISO-8859-1"), "UTF-8");
        String fio = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_FIO).getBytes("ISO-8859-1"), "UTF-8");
        String balance = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_BALANCE).getBytes("ISO-8859-1"), "UTF-8");
        String tell = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TELL).getBytes("ISO-8859-1"), "UTF-8");
        String adress = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADRESS).getBytes("ISO-8859-1"), "UTF-8");
        String addUser = new String(rb.getString(Constant.RESOURCE_KEY_NEW_USER_PAGE).getBytes("ISO-8859-1"), "UTF-8");
        String back = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");
        String save = new String(rb.getString(Constant.RESOURCE_KEY_SAVE).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("newUser",newUser);
        req.setAttribute("login",username);
        req.setAttribute("password",password);
        req.setAttribute("fio",fio);
        req.setAttribute("balance",balance);
        req.setAttribute("tell",tell);
        req.setAttribute("adress",adress);
        req.setAttribute("addUser",addUser);
        req.setAttribute("back",back);
        req.setAttribute("save",save);
    }

    public static void setAttributeEditTAriffPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String changeS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_CHANGE_S).getBytes("ISO-8859-1"), "UTF-8");
        String neweS = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_SERVICE).getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String save = new String(rb.getString(Constant.RESOURCE_KEY_SAVE).getBytes("ISO-8859-1"), "UTF-8");
        String descript = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");
        String back = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("changeS",changeS);
        req.setAttribute("neweS",neweS);
        req.setAttribute("name",name);
        req.setAttribute("descript",descript);
        req.setAttribute("save",save);
        req.setAttribute("back",back);

    }

                //ничего не трогать работает
    public static void setAttributeEditServicePage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String changeS = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_CHANGE_S).getBytes("ISO-8859-1"), "UTF-8");
        String newS = new String(rb.getString(Constant.RESOURCE_KEY_USER_PAGE_TARIFF).getBytes("ISO-8859-1"), "UTF-8");
        String name = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NAME).getBytes("ISO-8859-1"), "UTF-8");
        String priceT = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_PRICE).getBytes("ISO-8859-1"), "UTF-8");
        String save = new String(rb.getString(Constant.RESOURCE_KEY_SAVE).getBytes("ISO-8859-1"), "UTF-8");
        String descript = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_DESCRIPTION).getBytes("ISO-8859-1"), "UTF-8");
        String back = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("changeS",changeS);
        req.setAttribute("newS",newS);
        req.setAttribute("name",name);
        req.setAttribute("priceT",priceT);
        req.setAttribute("descript",descript);
        req.setAttribute("save",save);
        req.setAttribute("back",back);

    }

    public static void setAttributeEditUserPage(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        HttpSession s = req.getSession();
        Locale locale = (Locale) s.getAttribute("locale");
        ResourceBundle rb = ResourceBundle.getBundle("MyBundle", locale);

        String newUser2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_NEW_USER).getBytes("ISO-8859-1"), "UTF-8");
        String username2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_USERNAME).getBytes("ISO-8859-1"), "UTF-8");
        String password2 = new String(rb.getString(Constant.RESOURCE_KEY_STARTFORM_PASSWORD).getBytes("ISO-8859-1"), "UTF-8");
        String fio2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_FIO).getBytes("ISO-8859-1"), "UTF-8");
        String balance2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_BALANCE).getBytes("ISO-8859-1"), "UTF-8");
        String tell2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_TELL).getBytes("ISO-8859-1"), "UTF-8");
        String adress2 = new String(rb.getString(Constant.RESOURCE_KEY_ADMIN_PAGE_ADRESS).getBytes("ISO-8859-1"), "UTF-8");
        String addUser2 = new String(rb.getString(Constant.RESOURCE_KEY_NEW_USER_PAGE).getBytes("ISO-8859-1"), "UTF-8");
        String back2 = new String(rb.getString(Constant.RESOURCE_KEY_USER_BACK).getBytes("ISO-8859-1"), "UTF-8");
        String save2 = new String(rb.getString(Constant.RESOURCE_KEY_SAVE).getBytes("ISO-8859-1"), "UTF-8");


        req.setAttribute("newUser2",newUser2);
        req.setAttribute("login2",username2);
        req.setAttribute("password2",password2);
        req.setAttribute("fio2",fio2);
        req.setAttribute("balance2",balance2);
        req.setAttribute("tell2",tell2);
        req.setAttribute("adress2",adress2);
        req.setAttribute("addUser2",addUser2);
        req.setAttribute("back2",back2);
        req.setAttribute("save2",save2);
    }




}
