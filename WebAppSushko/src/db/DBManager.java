package db;

import entity.Service;
import entity.Tariff;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManager {



    private static final String URL = "jdbc:mysql://localhost:3306/FinalProject" + "?user=root&password=12345";

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    private static final String SQL_FIND_GROUP_BY_NAME = "SELECT * FROM groups WHERE name=?";

    private static final String SQL_FIND_ALL_USER = "SELECT * FROM users WHERE administrator=false";
    private static final String SQL_FIND_ALL_TARIFF = "SELECT * FROM tariff";
    private static final String SQL_FIND_ALL_SERVICE = "SELECT * FROM service";
    private static final String SQL_FIND_ONE_TARIFF_NAME = "SELECT * FROM tariff WHERE name_tariff=?";
    private static final String SQL_FIND_ONE_SERVICE_NAME = "SELECT * FROM service WHERE name_service=?";
    private static final String SQL_FIND_ALL_SERVICE_FOR_TARIFF = "select DISTINCT service.id, service.name_service, service.price, service.comment from service inner join(tariff inner join users_tariff on tariff.id=users_tariff.tariff_id) on service.id=users_tariff.service_id where name_tariff=?";
    private static final String SQL_FIND_ONE_TARIFF_FOR_SERVICE = "select tariff.id, tariff.name_tariff, tariff.price, tariff.comment from tariff inner join(service inner join users_tariff on service.id=users_tariff.service_id) on tariff.id=users_tariff.tariff_id where name_service=?";

    private static final String SQL_AUTORIZATION = "SELECT * FROM users WHERE login=? and pass=?";

    private static final String SQL_FIND_ALL_USER_AZ_LOGIN = "SELECT * FROM users ORDER BY login";
    private static final String SQL_FIND_ALL_USER_ZA_LOGIN = "SELECT * FROM users ORDER BY login DESC";
    private static final String SQL_FIND_ALL_USER_AZ_FIO = "SELECT * FROM users ORDER BY fio";
    private static final String SQL_FIND_ALL_USER_ZA_FIO = "SELECT * FROM users ORDER BY fio DESC";
    private static final String SQL_FIND_ALL_USER_AZ_BALANCE = "SELECT * FROM users ORDER BY balance";
    private static final String SQL_FIND_ALL_USER_ZA_BALANCE = "SELECT * FROM users ORDER BY balance DESC";

    private static final String SQL_FIND_ALL_TARIFF_AZ_NAME_TARIFF = "SELECT * FROM tariff ORDER BY name_tariff";
    private static final String SQL_FIND_ONE_TARIFF = "SELECT * FROM tariff WHERE name_tariff=?";
    private static final String SQL_FIND_ONE_USER = "SELECT * FROM users WHERE id=?";
    private static final String SQL_FIND_ALL_TARIFF_ZA_NAME_TARIFF = "SELECT * FROM tariff ORDER BY name_tariff DESC";
    private static final String SQL_FIND_ALL_TARIFF_AZ_PRICE = "SELECT * FROM tariff ORDER BY price";
    private static final String SQL_FIND_ALL_TARIFF_ZA_PRICE = "SELECT * FROM tariff ORDER BY price DESC";

    private static final String SQL_FIND_TRIFF_FOR_ONE_USER = "select DISTINCT tariff.id, tariff.name_tariff, tariff.price, tariff.comment from tariff inner join(users inner join users_tariff on users.id=users_tariff.user_id) on tariff.id=users_tariff.tariff_id where login=?";
    private static final String SQL_FIND_SERVICE_FOR_ONE_USER = "select service.id, service.name_service, service.price, service.comment from service inner join(users inner join users_tariff on users.id=users_tariff.user_id) on service.id=users_tariff.service_id where login=?";


    private static final String SQL_CREATE_NEW_USER =
            "INSERT INTO users VALUES (DEFAULT, ?,?,?,?,?,?,?,?)";
    private static final String SQL_CREATE_NEW_TARIFF ="INSERT INTO tariff VALUES (DEFAULT,?,?,?)";
    private static final String SQL_CREATE_NEW_SERVICE ="INSERT INTO service VALUES (DEFAULT,?,?,?)";
    private static final String SQL_CREATE_COMMUN_SERVICE_TARIFF ="INSERT INTO tariff_service VALUES (?, ?)";
    private static final String SQL_CREATE_COMMUN_USER_SERVICE_TARIFF ="INSERT INTO users_tariff VALUES (?, ?, ?)";
    private static final String SQL_ADD_TARIFF_FOR_USER =
            "INSERT INTO users_tariff VALUE(?, ?)";



    private static final String SQL_CREATE_NEW_GROUP =
            "INSERT INTO groups VALUES (DEFAULT, ?)";

    private static final String SQL_CREATE_NEW_USERS_GROUPS =
            "INSERT INTO users_groups VALUES (?, ?)";

    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_FIND_ALL_GROUPS = "SELECT * FROM groups";
    private static final String SQL_ONE_GROUPS = "select groups.id, groups.name from groups inner join(users inner join users_groups on users.id=users_groups.user_id) on groups.id=users_groups.group_id where login=?";
    private static final String SQL_FIND_ALL_USERS_GROUPS = "select name from groups where id=(select group_id from users_groups where user_id=(select id from users where login=?))";

    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id=?";
    private static final String SQL_DELETE_GROUP = "DELETE FROM groups WHERE name=?";
    private static final String SQL_DELETE_TARIFF = "DELETE FROM tariff WHERE name_tariff=?";
    private static final String SQL_DELETE_SERVICE = "DELETE FROM service WHERE name_service=?";
    private static final String SQL_DELETE_TARIFF_FOR_USER = "delete from users_tariff where user_id=? and tariff_id=?";
    private static final String SQL_DELETE_COMMUN_USER_SERVICE_TARIFF ="DELETE FROM users_tariff WHERE user_id=? and tariff_id=? and service_id=?";


    private static final String SQL_UPDATE_USER = "UPDATE users SET login=?, pass=?, fio=?, balance=?, tell=?, adress=?, block=? WHERE id=?";
    private static final String SQL_UPDATE_TARIFF = "UPDATE tariff SET name_tariff=?, price=?, comment=? WHERE id=?";
    private static final String SQL_UPDATE_SERVICE = "UPDATE service SET name_service=?, price=?, comment=? WHERE id=?";


    private static DBManager instance;


    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        Properties connInfo = new Properties();



        connInfo.put("useUnicode","true");
        connInfo.put("characterEncoding","utf8");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(URL,connInfo);
        return con;
    }


    public User autorization(String login, String pass) {

        User user = new User();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_AUTORIZATION);

            int k = 1;
            pstmt.setString(k++, login);
            pstmt.setString(k++, pass);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return user;
    }

    public List<User> findUsers(String query) {
        List<User> users = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }
        return users;
    }

    public List<User> findAllUsers() {
        return findUsers(SQL_FIND_ALL_USER);
    }

    public List<User> findAllUsersAZLogin() {
        return findUsers(SQL_FIND_ALL_USER_AZ_LOGIN);
    }

    public List<User> findAllUsersZALogin() {
        return findUsers(SQL_FIND_ALL_USER_ZA_LOGIN);
    }

    public List<User> findAllUsersAZFIO() {
        return findUsers(SQL_FIND_ALL_USER_AZ_FIO);
    }

    public List<User> findAllUsersZAFIO() {
        return findUsers(SQL_FIND_ALL_USER_ZA_FIO);
    }

    public List<User> findAllUsersAZBalance() {
        return findUsers(SQL_FIND_ALL_USER_AZ_BALANCE);
    }

    public List<User> findAllUsersZABalance() {
        return findUsers(SQL_FIND_ALL_USER_ZA_BALANCE);
    }


    public ArrayList<Tariff> findTariff(String query) {
        ArrayList<Tariff> tariff = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                tariff.add(extractTariff(rs));
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }
        return tariff;
    }

    public Tariff findOneTariff(String name){
        Tariff tariff = new Tariff();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ONE_TARIFF);

            int k = 1;
            pstmt.setString(k++, name);


            rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractTariff(rs);
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return tariff;
    }

    public ArrayList<Tariff> findAlltariff() {
        return findTariff(SQL_FIND_ALL_TARIFF);
    }

    public List<Tariff> findAlltariffAZNameTariff() {
        return findTariff(SQL_FIND_ALL_TARIFF_AZ_NAME_TARIFF);
    }

    public List<Tariff> findAlltariffZANameTariff() {
        return findTariff(SQL_FIND_ALL_TARIFF_ZA_NAME_TARIFF);
    }

    public List<Tariff> findAlltariffAZPrice() {
        return findTariff(SQL_FIND_ALL_TARIFF_AZ_PRICE);
    }

    public List<Tariff> findAlltariffZAPrice() {
        return findTariff(SQL_FIND_ALL_TARIFF_ZA_PRICE);
    }


    public ArrayList<Tariff> findTariffForOneUser(String login) {
        ArrayList<Tariff> tariffList = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_TRIFF_FOR_ONE_USER);

            int k = 1;
            pstmt.setString(k++, login);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                tariffList.add(extractTariff(rs));
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return tariffList;
    }


    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPass(rs.getString("pass"));
        user.setFio(rs.getString("fio"));
        user.setBalance(rs.getInt("balance"));
        user.setTell(rs.getString("tell"));
        user.setAdress(rs.getString("adress"));
        user.setAdministrator(rs.getBoolean("administrator"));
        user.setBlock(rs.getBoolean("block"));
        return user;
    }

    private Tariff extractTariff(ResultSet rs) throws SQLException {
        Tariff tariff = new Tariff();
        tariff.setId(rs.getInt("id"));
        tariff.setNameTariff(rs.getString("name_tariff"));
        tariff.setPrice(rs.getInt("price"));
        tariff.setComment(rs.getString("comment"));

        return tariff;
    }

    public boolean insertUser(User user) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_CREATE_NEW_USER,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPass());
            pstmt.setString(k++, user.getFio());
            pstmt.setInt(k++, user.getBalance());
            pstmt.setString(k++, user.getTell());
            pstmt.setString(k++, user.getAdress());
            pstmt.setBoolean(k++, user.isAdministrator());
            pstmt.setBoolean(k++, false);


            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    res = true;
                }
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    public boolean insertTariff(Tariff tariff) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();


            pstmt = con.prepareStatement(SQL_CREATE_NEW_TARIFF,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, tariff.getNameTariff());
            pstmt.setInt(k++, tariff.getPrice());
            pstmt.setString(k++, tariff.getComment());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    tariff.setId(rs.getInt(1));
                    res = true;
                }
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    public boolean insertService(Service service, Tariff tariff, int userID) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
//            con.setAutoCommit(false);

            pstmt = con.prepareStatement(SQL_CREATE_NEW_SERVICE,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, service.getNameService());
            pstmt.setInt(k++, service.getPrice());
            pstmt.setString(k++, service.getComment());


            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    service.setId(rs.getInt(1));
                    if(addServiceVSTariff(rs.getInt(1),tariff.getId())&addUserServiceTariff(userID,tariff.getId(),rs.getInt(1) )){

                        res = true;
//                        con.commit();
                    }else{
//                        con.rollback();
                    }

                }
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    private boolean addServiceVSTariff(int servId, int tariffId) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
//            con.setAutoCommit(false);

            pstmt = con.prepareStatement(SQL_CREATE_COMMUN_SERVICE_TARIFF);

            int k = 1;
            pstmt.setInt(k++, tariffId);
            pstmt.setInt(k++, servId);

            if (pstmt.executeUpdate() > 0) {
//                con.commit();
                   res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    private boolean addUserServiceTariff(int userId, int tariffId,int servId) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
//            con.setAutoCommit(false);

            pstmt = con.prepareStatement(SQL_CREATE_COMMUN_USER_SERVICE_TARIFF);

            int k = 1;
            pstmt.setInt(k++, userId);
            pstmt.setInt(k++, tariffId);
            pstmt.setInt(k++, servId);


            if (pstmt.executeUpdate() > 0) {
//                con.commit();
                res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    public boolean updateTariff(Tariff tariff) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_TARIFF,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, tariff.getNameTariff());
            pstmt.setInt(k++, tariff.getPrice());
            pstmt.setString(k++, tariff.getComment());
            pstmt.setInt(k++, tariff.getId());

            if (pstmt.executeUpdate() > 0) {
                   res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }


    private void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception ex) {
                throw new IllegalStateException("Cannot close " + ac);
            }
        }
    }


    public User findOneUser(int id) {
        User user = new User();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ONE_USER);

            int k = 1;
            pstmt.setInt(k++, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return user;
    }

    public boolean updateUser(User user) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_USER,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;

            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPass());
            pstmt.setString(k++, user.getFio());
            pstmt.setInt(k++, user.getBalance());
            pstmt.setString(k++, user.getTell());
            pstmt.setString(k++, user.getAdress());
            pstmt.setBoolean(k++, user.isBlock());
            pstmt.setInt(k++, user.getId());

            if (pstmt.executeUpdate() > 0) {
                res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    public boolean addDeleteTarifForUser(User user, Tariff tariff, boolean add) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            if(add) {
                pstmt = con.prepareStatement(SQL_ADD_TARIFF_FOR_USER,
                        Statement.RETURN_GENERATED_KEYS);
            }else{
                pstmt = con.prepareStatement(SQL_DELETE_TARIFF_FOR_USER,
                        Statement.RETURN_GENERATED_KEYS);
            }
            int k = 1;
            pstmt.setInt(k++, user.getId());
            pstmt.setInt(k++, tariff.getId());



            if (pstmt.executeUpdate() > 0) {
                    res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }


    public ArrayList<Service> searchServiceForUser(User user) {
        ArrayList<Service> serviceList = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_SERVICE_FOR_ONE_USER);

            int k = 1;
            pstmt.setString(k++, user.getLogin());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                serviceList.add(extractService(rs));
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return serviceList;
    }

    private Service extractService(ResultSet rs) throws SQLException {

        Service service = new Service();
        service.setId(rs.getInt("id"));
        service.setNameService(rs.getString("name_service"));
        service.setPrice(rs.getInt("price"));
        service.setComment(rs.getString("comment"));

        return service;
    }

    public ArrayList<Service> findAllService() {


        return findService(SQL_FIND_ALL_SERVICE);
    }

    public ArrayList<Service> allServiceForTarif(String nameTariff) {
        ArrayList<Service> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ALL_SERVICE_FOR_TARIFF);

            int k = 1;
            pstmt.setString(k++, nameTariff);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                list.add(extractService(rs));

            }

            return list;
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }

        return list;
    }

    public ArrayList<Service> findService(String query) {
        ArrayList<Service> list = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(extractService(rs));
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }

        return list;
    }


    public Tariff searchTariffName(String name, boolean oneTafiffForName) {
        Tariff tariff = new Tariff();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            if(oneTafiffForName) {
                pstmt = con.prepareStatement(SQL_FIND_ONE_TARIFF_NAME);
            }else{
                pstmt = con.prepareStatement(SQL_FIND_ONE_TARIFF_FOR_SERVICE);

            }
            int k = 1;
            pstmt.setString(k++, name);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                tariff = extractTariff(rs);
                return tariff;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }
        return tariff;
    }

    public Service findOneServiceName(String name) {
        Service service = new Service();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_FIND_ONE_SERVICE_NAME);

            int k = 1;
            pstmt.setString(k++, name);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                service = extractService(rs);
                return service;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();
            // log.error("Cannot obtain a user by login", ex);

            // (2)
//            throw new DBException("Cannot obtain a user by login", ex);
        } finally {
            close(con);
        }
        return service;

    }

    public boolean updateService(Service service) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_SERVICE,
                    Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, service.getNameService());
            pstmt.setInt(k++, service.getPrice());
            pstmt.setString(k++, service.getComment());
            pstmt.setInt(k++, service.getId());

            if (pstmt.executeUpdate() > 0) {
                res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }

    public boolean deleteServiceTariff(String nameService, boolean service) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();


            if(service) {
                pstmt = con.prepareStatement(SQL_DELETE_SERVICE,
                        Statement.RETURN_GENERATED_KEYS);
            }else{
                pstmt = con.prepareStatement(SQL_DELETE_TARIFF,
                        Statement.RETURN_GENERATED_KEYS);
            }

            int k = 1;
            pstmt.setString(k++, nameService);

            if (pstmt.executeUpdate() > 0) {
                res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }


    public boolean addServiceForUser(int userUD, int tariffID, int serviceID, boolean add) {
        boolean res = false;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
//            con.setAutoCommit(false);

            if(add) {
                pstmt = con.prepareStatement(SQL_CREATE_COMMUN_USER_SERVICE_TARIFF);
            }else{
                pstmt = con.prepareStatement(SQL_DELETE_COMMUN_USER_SERVICE_TARIFF);
            }
            pstmt.setInt(1, userUD);
            pstmt.setInt(2, tariffID);
            pstmt.setInt(3, serviceID);

            if (pstmt.executeUpdate() > 0) {
                res = true;
            }
        } catch (SQLException ex) {
            // (1) write to log
            ex.printStackTrace();

            // (2)
//            throw new DBException("Cannot create a user:" + user, ex);
        } finally {
            close(con);
        }
        return res;
    }



}
