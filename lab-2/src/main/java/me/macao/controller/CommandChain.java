package me.macao.controller;

import me.macao.service.UserService;

public interface CommandChain {
    void setNext(CommandChain next);
    void exec(UserService service, String[] line);
}
