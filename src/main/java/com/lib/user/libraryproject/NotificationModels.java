package com.lib.user.libraryproject;

public class NotificationModels {

    public String to;
    public Notification notification = new Notification();
    public Data data = new Data();

    public static class Notification {
        public String title;
        public String text;
    }

    public static class Data {
        public String title;
        public String text;
    }

}
