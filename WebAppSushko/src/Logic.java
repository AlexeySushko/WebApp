import db.DBManager;
import entity.Service;
import entity.Tariff;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logic {

//    public static boolean autorization(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter("user");
//        String pass = req.getParameter("pass");
//        System.out.println("P User ---> " + login);
//        System.out.println("P pass ---> " + pass);
//
//        req.setAttribute("key","VALUE");
//
//        if(login.equals("admin")&pass.equals("admin")){
////            req.getRequestDispatcher("enterAdmin.html").forward(req, resp);
//            return true;
//        }else{
//
////            req.setAttribute("val","qqqqqqqqqqqqqqqqqqqq");
////            req.getRequestDispatcher("/WEB-INF/noEnter.jsp").forward(req, resp);
//            return false;
//        }
//    }

    public static void doGetUserServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO GET USER >> ");
        HttpSession s = req.getSession();
        DBManager db = DBManager.getInstance();
        User user = (User) s.getAttribute("currentUser");

        if (req.getParameter("exit") != null) {
            s.removeAttribute("currentUser");

            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_START_PAGE);
            rd.forward(req, resp);
        }if (req.getParameter("pay") != null) {
            //!!!ПЕРЕСЧИТАТЬ ДАННЫЕ
            System.out.println("ПЕРЕСЧИТАТЬ ДАННЫЕ");
        }if (req.getParameter("useTarif") != null) {
            req = Method.addTariffForUser(req, resp);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);
            System.out.println("CLICK USE");
        }if (req.getParameter("deleteTarif") != null) {
            req = Method.deleteTariffForUser(req, resp);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);
            System.out.println("CLICK USE");
        }if (req.getParameter("openTariff") != null) {

            ArrayList<Service> allServiceForTariff = db.allServiceForTarif(req.getParameter("selectTafiff"));
            req.setAttribute("allServiceForTariff", allServiceForTariff);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE_SERVICE_FOR_ONE_TARIFF);
            rd.forward(req, resp);
        }if (req.getParameter("sorted") != null) {

            req = Method.createReqForUser(req);
            ArrayList<Service> arrServiceForUser = db.searchServiceForUser(user);
            req = Method.sortService(req, arrServiceForUser);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);



        }if(req.getParameter("deleteService")!=null){

            System.out.println("DELETE SERVICE>> "+ req.getParameter("deleteMyService"));
//            User user = (User) s.getAttribute("currentUser");
            Tariff tariff = db.searchTariffName(req.getParameter("deleteMyService"), false);
            Service service = db.findOneServiceName(req.getParameter("deleteMyService"));
            if(db.addServiceForUser(user.getId(), tariff.getId(), service.getId(), false)){

                System.out.println("SERVICE ADED>> ");
                req = Method.createReqForUser(req);
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
                rd.forward(req, resp);

            }else{

                System.out.println("NOT DELETE service>> ");
            }
        }
        if (req.getParameter("downloadTAriff") != null) {
            ArrayList<Service> arrServiceForUser = db.searchServiceForUser(user);
            Method.writeFile(user.getLogin(), Method.createFileForUser(user,arrServiceForUser));
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
            rd.forward(req, resp);

            System.out.println("Файл записан >>> ");
        } else {

            //Что ы при обновленнии не падали данные
//            req = Method.createReqForUser(req);
//            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
//            rd.forward(req, resp);
            System.out.println("CLICK USE");
        }

    }

    public static void doPostUserServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO POST USER >> ");
        HttpSession s = req.getSession();
        DBManager db = DBManager.getInstance();
        User user = (User) s.getAttribute("currentUser");


        if(req.getParameter("useService")!=null){

            System.out.println("USE SERVICE>> "+ req.getParameter("selectService"));
            Tariff tariff = db.searchTariffName(req.getParameter("selectService"), false);
            Service service = db.findOneServiceName(req.getParameter("selectService"));
            if(db.addServiceForUser(user.getId(), tariff.getId(), service.getId(), true)){

                System.out.println("SERVICE ADED>> ");
                req = Method.createReqForUser(req);
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
                rd.forward(req, resp);

            }else{

                req.setAttribute("messageError", "you already use this service");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
                System.out.println("you already use this service>> ");
            }
        }if(req.getParameter("pay")!=null){
            System.out.println("PAYYYYY>> ");
            int balance = (int) s.getAttribute("balanceUser");
            int sum = (int) s.getAttribute("allSum");
            int finalBalance = balance-sum;
            if(finalBalance<0){
                user.setBalance(finalBalance);
                user.setBlock(true);
            }else{
                user.setBalance(finalBalance);
            }
            if (balance>=0){
                if(db.updateUser(user)){
                    if(balance<0){
                        System.out.println("Ваш баланс отрицательный. Вы звблокированы.\nПожалуйста пополните ваш счет\n" +
                                "текуущий баланс="+finalBalance);
                    }

                    resp.sendRedirect(Path.PAGE_PAY_OK_PAGE);
                    System.out.println("Оплата произведена успешно");
                }else{
                    System.out.println("Оплата не произведена");
                }

            }else{
                resp.sendRedirect(Path.PAGE_PAY_OK_PAGE);
                System.out.println("Вы не можете произвести оплату при отрицательном балансе");
            }


        }
    }

    public static void doGetMyServlet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("DO GET 1 >> ");
    }

    public static void doPostMyServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO POST 1  >> ");

        String login = req.getParameter("user");
        String pass = Utilities.encryptText(req.getParameter("pass"));

        //Найти пользователя в БД и вернуть его
        //Если найдет то перенаправить на страницу Юзеи или Админ
        //Иначе выкинуть на страницу ошибки

        DBManager dbManager = new DBManager();
        User user = dbManager.autorization(login,pass);

        if(user.getId()!=0&user.getLogin()!=null){

            System.out.println("Который заходит >> "+user.toString());

            ArrayList<Tariff> tariffs = dbManager.findAlltariff();//получим все тарифы
            ArrayList<Service> allService = dbManager.findAllService();//получим все сервисы

            HttpSession s = req.getSession();
            s.setAttribute("currentUser",user);
            //Зайем под администратором
            if(user.isAdministrator()){

                //сначала установим значения все на будущей форме
                dbManager = DBManager.getInstance();
                List<User> users = dbManager.findAllUsers();//gполучим всех пользователей

                //!!ПОЛУЧИТЬ ТАРИФЫ ДЛЯ ПОЛЬЗОВАТЕЛЕЙ И ДОБАВИТЬ В ПОСЛЕДНЮЮ КОЛОНКУ


                String str = users.toString();//--
                System.out.println("Users >> "+str);//--

                req.setAttribute("arrUser", users);
                req.setAttribute("arrTariff", tariffs);
                req.setAttribute("allService", allService);
                req.setAttribute("nameAdmin", user.getFio());
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ADMIN_PAGE);
                rd.forward(req, resp);

                //Зайдем под пользователем
            }else if(!user.isAdministrator()){

                //ПОЛУЧИТЬ ДЛЯ ПОЛЬЗОВАТЕЛЯ ЕГО ТАРИФ В AreayListe  И ВЫВЕСТИ ОТДЕЛЬНОЙ ТАБЛИЧКОЙ
                //получим тарифы для данного пользователя
                req = Method.createReqForUser(req);
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_USER_PAGE);
                rd.forward(req, resp);
            }
        }
        else{
            req.getRequestDispatcher(Path.PAGE_NO_ENTER_PAGE).forward(req, resp);
        }
    }

    public static void doGetAdminSrvlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO GET ADMIN  >> ");
        System.out.println("SORTED  >> " + req.getParameter("sorted"));
        System.out.println("Открыть форму для изменения тарифа ---> " + req.getParameter("resChangeTariff"));
        System.out.println("Кнопка изменения тарифа ---> " + req.getParameter("changeTariff"));
        System.out.println("Открыть форму для изменения ПОЛЬЗОВАТЕЛЯ ---> " + req.getParameter("numberUserChange"));
        System.out.println("Кнопка изменения ПОЛЬЗОВАТЕЛЯ ---> " + req.getParameter("changeuser"));

        DBManager db = new DBManager();
        HttpSession s = req.getSession();

        if (req.getParameter("newUser") != null) {
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_NEW_USER_PAGE);
            rd.forward(req, resp);
        }
        if (req.getParameter("changeuser") != null) {
            db = new DBManager();

            s.setAttribute("idForUpdateUser", req.getParameter("numberUserChange"));
            User user = db.findOneUser(Integer.parseInt(req.getParameter("numberUserChange")));
            //Заполним форму для редактирования
            req.setAttribute("login", user.getLogin());
            req.setAttribute("pass", user.getPass());
            req.setAttribute("fio", user.getFio());
            req.setAttribute("balance", user.getBalance());
            req.setAttribute("tell", user.getTell());
            req.setAttribute("adress", user.getAdress());
//            req.setAttribute("block", (user.isBlock()) ? "Blocked" : "Unblocked");
            System.out.println("откуда заполняли пользователя>> " + user.toString());
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_CHANGE_USER_PAGE);
            rd.forward(req, resp);

        }
        if (req.getParameter("newTariff") != null) {
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_NEW_TARIFF_PAGE);
            rd.forward(req, resp);
        }
        if (req.getParameter("newService") != null) {

            req.setAttribute("arrTariff", db.findAlltariff());

            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_NEW_SERVICE_PAGE);
            rd.forward(req, resp);
        }
        if (req.getParameter("newAdmin") != null) {
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_NEW_ADMIN_PAGE);
            rd.forward(req, resp);
        }
        if (req.getParameter("changeTariff") != null) {
            db = new DBManager();
            Tariff tariff = db.findOneTariff(req.getParameter("resChangeTariff"));

            if (tariff.getId() > 0) {
                //Вставим в параметры формы

                s.setAttribute("idForUpdate", tariff.getId());
                req.setAttribute("nameTariff", tariff.getNameTariff());
                req.setAttribute("price", tariff.getPrice());
                req.setAttribute("comment", tariff.getComment());
                System.out.println("ТАРИФ Который искали >> " + tariff.toString());
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_CHANGE_TARIFF_PAGE);
                rd.forward(req, resp);

            } else {
                System.out.println("Не нашли тариф>> ");
            }

        }
        if (req.getParameter("changeService") != null) {
            db = new DBManager();
            String nameService = req.getParameter("resChangeService");
            Service service = db.findOneServiceName(nameService);

            if (service.getId() > 0) {
                //Вставим в параметры формы
                System.out.println("ID Service>> "+service.getId());
                s.setAttribute("idForUpdateService", service.getId());
                req.setAttribute("nameService", service.getNameService());
                req.setAttribute("price", service.getPrice());
                req.setAttribute("comment", service.getComment());
                System.out.println("ТАРИФ Который искали >> " + service.toString());
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_CHANGE_SERVICE_PAGE);
                rd.forward(req, resp);

            } else {
                System.out.println("Не нашли тариф>> ");
            }

        }
        if (req.getParameter("sorted") != null) {
            System.out.println("Попал перед сортировкой");
            req = Method.sortTariff(req);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ADMIN_PAGE);
            rd.forward(req, resp);
        }
        if (req.getParameter("openTariff") != null) {

            ArrayList<Service> allServiceForTariff = db.allServiceForTarif(req.getParameter("resChangeTariff"));
            req.setAttribute("allServiceForTariff", allServiceForTariff);
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ADMIN_PAGE_SERVICE_FOR_ONE_TARIFF);
            rd.forward(req, resp);
        }if(req.getParameter("deleteService")!=null){
            if(db.deleteServiceTariff(req.getParameter("resChangeService"),true)) {
                Method.openAdminPage(req, resp);
            }else{
                System.out.println("Service dont delete");
            }
        }if (req.getParameter("deleteTariff") != null) {

            if(Method.deleteTariff(req.getParameter("resChangeTariff")))
                Method.openAdminPage(req, resp);
        }if (req.getParameter("exit") != null) {
            s.removeAttribute("currentUser");
            RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_START_PAGE);
            rd.forward(req, resp);
        }

    }


    public static void doPostAdminSrvlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO POST ADMIN  >> ");
        DBManager db = new DBManager();
        HttpSession s = req.getSession();


        if (req.getParameter("addTariff") != null) {
            Tariff tariff = Method.createTariff(req);
            if (db.insertTariff(tariff)) {
                System.out.println("Добавлен НОВЫЙ ТАРИФ >> " + tariff.toString());
//                ArrayList<Tariff> tariffs = db.findAlltariff();//получим все тарифы
//                ArrayList<Service> allService = db.findAllService();//получим все сервисы
//
//                List<User> users = db.findAllUsers();//получим всех пользователей
//                req.setAttribute("arrUser", users);
//                req.setAttribute("arrTariff", tariffs);
//                req.setAttribute("allService", allService);
//                req.setAttribute("nameAdmin", s.getAttribute("currentUser"));
//                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ADMIN_PAGE);
//                rd.forward(req, resp);
                Method.openAdminPage(req,resp);
            } else {
                System.out.println("ТАРИФ НЕ добавлен >> ");
                req.setAttribute("messageError", "Tariff not added");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }
        }
        if (req.getParameter("addService") != null) {
            Service service = Method.createService(req);
            Tariff tariff = Method.getOneTarif(req.getParameter("nameTariffForInsertService"));
            User user = (User) s.getAttribute("currentUser");
            if (db.insertService(service, tariff, user.getId()) & tariff.getId() > 0) {
                System.out.println("Добавлен НОВЫЙ СЕРВИС >> " + service.toString());
                Method.openAdminPage(req,resp);


            } else {
                System.out.println("ТАРИФ НЕ добавлен >> ");
                req.setAttribute("messageError", "Tariff not added");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }
        }
        if (req.getParameter("editTariff") != null) {
            Tariff tariff = Method.createTariff(req);

            tariff.setId((Integer) s.getAttribute("idForUpdate"));
//            s.removeAttribute("idForUpdate");
            if (db.updateTariff(tariff)) {
                System.out.println("ТАРИФ изменен ");
                Method.openAdminPage(req,resp);

            } else {
                req.setAttribute("messageError", "Tariff not changed");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }

        }if (req.getParameter("editService") != null) {
            Service service = Method.createService(req);

            service.setId((Integer) s.getAttribute("idForUpdateService"));
//            s.removeAttribute("idForUpdate");
            if (db.updateService(service)) {
                System.out.println("SERVICE изменен ");
                Method.openAdminPage(req,resp);

            } else {
                req.setAttribute("messageError", "Tariff not changed");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }
        }
        if (req.getParameter("save") != null) {

            User user = Method.createUser(req);
            user.setId(Integer.parseInt((String) s.getAttribute("idForUpdateUser")));
            System.out.println("Не нашли тариф>> " + Integer.parseInt((String) s.getAttribute("idForUpdateUser")));
            if (db.updateUser(user)) {
                Method.openAdminPage(req,resp);


            } else {
                req.setAttribute("messageError", "User not changed");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }
        }
    }

    public static void doPostNewUserServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("NEW USER DO POST");
        DBManager db = new DBManager();
        HttpSession s = req.getSession();

        if(req.getParameter("addUser")!=null){
            //ДОБАВИМ ПОЛЬЗОВАТЕЛЯ И ВЕРНЕМ РЕЗУЛЬТАТ

            User user = new User();

            user.setLogin(req.getParameter("login"));
            user.setPass(Utilities.encryptText(req.getParameter("pass")));
            user.setFio(req.getParameter("fio"));
            user.setBalance(Integer.parseInt(req.getParameter("balance")));
            user.setTell(req.getParameter("tell"));
            user.setAdress(req.getParameter("adress"));
            user.setAdministrator(false);
            user.setBlock(false);


            if(db.insertUser(user)){
                System.out.println("Добавлен НОВЫЙ ПОЛЬЗОВАТЕЛЬ >> "+user.toString());
                Method.openAdminPage(req,resp);

            }else{
                System.out.println("Пользователь НЕ добавлен >> ");
                req.setAttribute("messageError", "New user not added");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }

        }if(req.getParameter("addAdmin")!=null){
            //ДОБАВИМ ПОЛЬЗОВАТЕЛЯ И ВЕРНЕМ РЕЗУЛЬТАТ

            User user = new User();

            user.setLogin(req.getParameter("login"));
            user.setPass(Utilities.encryptText(req.getParameter("pass")));
            user.setFio(req.getParameter("fio"));
            user.setBalance(0);
            user.setTell(req.getParameter("tell"));
            user.setAdress(req.getParameter("adress"));
            user.setAdministrator(true);
            user.setBlock(false);


            if(db.insertUser(user)){
                System.out.println("Добавлен НОВЫЙ АДМИНИСТРАТОР >> "+user.toString());

                Method.openAdminPage(req, resp);
            }else{
                System.out.println("Администратор НЕ добавлен >> ");
                req.setAttribute("messageError", "New Administrator not added");
                RequestDispatcher rd = req.getRequestDispatcher(Path.PAGE_ERROR_PAGE);
                rd.forward(req, resp);
            }

        }if(req.getParameter("cancel")!=null){
            System.out.println("Нажата кнопка отмены >> ");
        }else{
//            System.out.println("ОШИБКА ПОЛЬЗ ИЛИ АДМИН НЕ ДОБАВЛЕНЫ >> ");
        }
    }
}
