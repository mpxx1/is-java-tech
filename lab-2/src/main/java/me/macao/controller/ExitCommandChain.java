package me.macao.controller;

import me.macao.service.UserService;

public class ExitCommandChain
    extends AbstractCommandChain {

    public ExitCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        if (!line[0].equals("exit"))
            nextChain.exec(service, line);
        else {
            System.out.println("Exit program");
            System.exit(0);
        }
    }
}
