package entity;

public class User {
    private int id;
    private String login;
    private String pass;
    private String fio;
    private int balance;
    private String tell;
    private String adress;
    private boolean administrator;
    private boolean block;
    private String tariffForUser;


    public User(int id, String login, String pass, String fio, int balance, String tell, String adress, boolean administrator, boolean block, String tariffForUser) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.fio = fio;
        this.balance = balance;
        this.tell = tell;
        this.adress = adress;
        this.administrator = administrator;
        this.block = block;
        this.tariffForUser = tariffForUser;
    }



    public User(String login, String pass, String fio, int balance, String tell, String adress, boolean administrator, boolean block, String tariffForUser) {
        this.login = login;
        this.pass = pass;

        this.fio = fio;
        this.balance = balance;
        this.tell = tell;
        this.adress = adress;
        this.administrator = administrator;
        this.block = block;
        this.tariffForUser = tariffForUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {

    }

    public String getTariffForUser() {
        return tariffForUser;
    }

    public void setTariffForUser(String tariffForUser) {
        this.tariffForUser = tariffForUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (balance != user.balance) return false;
        if (administrator != user.administrator) return false;
        if (block != user.block) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        if (fio != null ? !fio.equals(user.fio) : user.fio != null) return false;
        if (tell != null ? !tell.equals(user.tell) : user.tell != null) return false;
        if (adress != null ? !adress.equals(user.adress) : user.adress != null) return false;
        return tariffForUser != null ? tariffForUser.equals(user.tariffForUser) : user.tariffForUser == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + balance;
        result = 31 * result + (tell != null ? tell.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (administrator ? 1 : 0);
        result = 31 * result + (block ? 1 : 0);
        result = 31 * result + (tariffForUser != null ? tariffForUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", fio='" + fio + '\'' +
                ", balance=" + balance +
                ", tell='" + tell + '\'' +
                ", adress='" + adress + '\'' +
                ", administrator=" + administrator +
                ", block=" + block +
                ", tariffForUser='" + tariffForUser + '\'' +
                '}';
    }
}
