package com.yanm.command;

public interface Command<T> {

    void execute(T t);

}
