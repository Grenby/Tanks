package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.Logger;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;

public class MyLogger implements Logger {

    public static final Logger LOG = new MyLogger();

    final FileHandle file;
    final PrintStream printStream;
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_WHITE = "\u001B[37m";

    enum Colors{
        RESET("\u001B[0m"),
        WARNING("\u001B[37m"),//ANSI_WHITE
        ERROR("\u001B[31m"),//ANSI_RED
        DEBUG("\u001B[36m"),//ANSI_CYAN
        INFO("\u001B[33m");//ANSI_YELLOW

        private final String color;

        Colors(String color){
            this.color = color;
        }


        @Override
        public String toString() {
            return color;
        }
    }

    private MyLogger(){
        PrintStream printStream1;
        file = Gdx.files.local("ll");
        file.write(false);
        try {
            printStream1 = new PrintStream(file.file());
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            printStream1 = null;
        }
        printStream = printStream1;
    }

    @Override
    public void debug (String tag, String message) {
        printColor(Colors.DEBUG);
        println("[DEBUG]", tag, message);
    }

    @Override
    public void debug (String tag, String message, Throwable exception) {
        printColor(Colors.DEBUG);
        println("[DEBUG]", tag, message, exception);
    }

    @Override
    public void info (String tag, String message) {
        printColor(Colors.INFO);
        println("[INFO]", tag, message);
    }

    @Override
    public void info (String tag, String message, Throwable exception) {
        printColor(Colors.INFO);
        println("[INFO]", tag, message, exception);
    }

    @Override
    public void error (String tag, String message) {
        printColor(Colors.ERROR);
        println("[ERROR]", tag, message);
    }

    @Override
    public void error (String tag, String message, Throwable exception) {
        printColor(Colors.ERROR);
        println("[ERROR]", tag, message, exception);
    }

    private void printColor(Colors colors){
        file.writeString(colors.toString(),true);
    }

    private void println (String level, String tag, String message) {
        file.writeString(level + " " + tag + ": " + message,true);
        file.writeString(Colors.RESET + "\n",true);
    }

    private void println (String level, String tag, String message, Throwable exception){
        println(level, tag, message);
        exception.printStackTrace();
//        try {
//            exception.printStackTrace(new PrintStream(file.file()));
//        }catch (Exception e){
//
//        }
    }
}