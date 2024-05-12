package me.macao.controller;

import me.macao.service.UserService;

public class LgnCommandChain
    extends AbstractCommandChain {
    public LgnCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {
        try {

            if (!line[0].equals("lgn"))
                nextChain.exec(service, line);

            else if (line.length != 3)
                nextChain.exec(service, line);

            else service.login(line[1], line[2]);

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
