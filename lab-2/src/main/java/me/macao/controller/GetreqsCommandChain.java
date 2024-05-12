package me.macao.controller;

import me.macao.service.UserService;

public class GetreqsCommandChain
    extends AbstractCommandChain {
    public GetreqsCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("getreqs"))
                nextChain.exec(service, line);

            else if (line.length == 2)
                nextChain.exec(service, line);

            else service.getFriendshipRequests(line[1]);

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
