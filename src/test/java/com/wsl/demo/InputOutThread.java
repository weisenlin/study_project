package com.wsl.demo;

/**
 * create by WSL_SILVA
 * 日期: 2018/11/27 0027
 * 用途：  wait  notify的使用
 * 描述:
 */
class Res {
    public String userName;

    public String sex;

    public boolean flag;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

class InputThread extends Thread {

    private Res res;

    public InputThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        int count = 0;

        while (true) {
            synchronized (res) {
                if (res.flag) {
                    try {
                        res.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count == 0) {
                    res.userName = "小红";
                    res.sex = "女";
                } else {
                    res.userName = "老于";
                    res.sex = "男";
                }
                count = (count + 1) % 2;
                res.notify();
                res.flag = true;
            }
        }
    }
}

class OutThread extends Thread {
    private Res res;

    public OutThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (res) {
                if (!res.flag) {
                    try {
                        res.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(res.userName + "   " + res.sex);
                res.notify();
                res.flag = false;
            }

        }

    }
}


public class InputOutThread {
    public static void main(String[] args) {
        Res res = new Res();
        InputThread inputThread = new InputThread(res);
        OutThread outThread = new OutThread(res);
        inputThread.start();
        outThread.start();
    }

}
