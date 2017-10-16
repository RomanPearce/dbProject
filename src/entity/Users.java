package entity;


public class Users {
    private int users_id;
    private String login;
    private String pass;
    private int role;
    private int balance;
    private int block_status;
    private int del_status;

    public Users(int users_id){
        this.users_id = users_id;
        this.login ="test";
        this.pass ="test";
        this.role = 1;
        this.block_status = 0;
        this.del_status = 0;
        this.balance = 0;
    }

    public Users(int users_id, String login, String pass, int role, int balance, int block_status) {
        this.users_id = users_id;
        this.login = login;
        this.pass = pass;
        this.role = role;
        this.balance = balance;
        this.block_status = block_status;
    }

    public Users(String login, String pass, int role, int balance, int block_status, int del_status) {
        this.login = login;
        this.pass = pass;
        this.role = role;
        this.balance = balance;
        this.block_status = block_status;
        this.del_status = del_status;
    }

    public Users(String login, int balance) {
        this.login = login;
        this.balance = balance;
    }

    public Users(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBlock_status() {
        return block_status;
    }

    public void setBlock_status(int block_status) {
        this.block_status = block_status;
    }

    public int getDel_status() {
        return del_status;
    }

    public void setDel_status(int del_status) {
        this.del_status = del_status;
    }
}