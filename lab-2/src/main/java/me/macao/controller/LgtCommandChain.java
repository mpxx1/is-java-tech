package me.macao.controller;

import me.macao.service.UserService;

public class LgtCommandChain
    extends AbstractCommandChain {
    public LgtCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("lgt"))
                nextChain.exec(service, line);

            else service.logout();

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
