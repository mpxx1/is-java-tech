package me.macao.controller;

import me.macao.model.Cat;
import me.macao.service.UserService;

public class AllCatsCommandChain
    extends AbstractCommandChain {
    public AllCatsCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("allcats"))
                nextChain.exec(service, line);

            else {
            for (Cat cat : service.allCats()) {
                System.out.println("____________________________");
                System.out.println(cat.getName());
                System.out.println(cat.getBreed());
                System.out.println(cat.getBirthday());
                System.out.println(cat.getColor());
                System.out.println("friends count: " + cat.getFriends().size());
            }
            }

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
