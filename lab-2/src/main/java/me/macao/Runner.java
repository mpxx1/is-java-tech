package me.macao;

import me.macao.controller.*;
import me.macao.dao.CatDao;
import me.macao.dao.UserDao;
import me.macao.model.Cat;
import me.macao.model.User;
import me.macao.service.UserService;
import me.macao.service.UserServiceImpl;

import java.util.Scanner;

public class Runner {

    private static Scanner scanner = new Scanner(System.in);
    private static String line;

    public static void main(String[] args) {
        UserService service = new UserServiceImpl(
                new UserDao<>(User.class),
                new CatDao<>(Cat.class)
        );
        CommandChain chain = initChain();
        System.out.print(">> ");

        while (true) {
            line = scanner.nextLine();
            chain.exec(service, line.split(" "));
            System.out.print(">> ");
        }
    }

    private static CommandChain initChain() {
        CommandChain help = new HelpCommandChain(null);
        CommandChain exit = new ExitCommandChain(help);
        CommandChain dropfr = new DropFrCommandChain(exit);
        CommandChain acptreq = new AcptreqCommandChain(dropfr);
        CommandChain getreqs = new GetreqsCommandChain(acptreq);
        CommandChain cnslreq = new CnslreqCommandChain(getreqs);
        CommandChain reqfrnd = new ReqfrndCommandChain(cnslreq);
        CommandChain dropcat = new DeleteCatCommandChain(reqfrnd);
        CommandChain addcat = new AddCatCommandChain(dropcat);
        CommandChain allcats = new AllCatsCommandChain(addcat);
        CommandChain lgt = new LgtCommandChain(allcats);
        CommandChain lgn = new LgnCommandChain(lgt);
        CommandChain usradd = new UsraddChainCommand(lgn);


        return usradd;
    }
}
