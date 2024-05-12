package me.macao.controller;

import me.macao.service.UserService;

public class CnslreqCommandChain
    extends AbstractCommandChain {
    public CnslreqCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("cnslreq"))
                nextChain.exec(service, line);

            else if (line.length != 4)
                nextChain.exec(service, line);

            else service.cancelRequest(
                    line[1],
                    line[2],
                    line[3]
            );

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
