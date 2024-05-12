package me.macao.controller;

import me.macao.service.UserService;

public class DeleteCatCommandChain
    extends AbstractCommandChain {
    public DeleteCatCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("dropcat"))
                nextChain.exec(service, line);

            else if (line.length != 2)
                nextChain.exec(service, line);

            else service.dropCatByName(line[1]);

        } catch (Exception e) {
            System.out.println(Exception.class + e.getMessage());
        }
    }
}
