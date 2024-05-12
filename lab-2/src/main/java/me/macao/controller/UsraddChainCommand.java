package me.macao.controller;

import me.macao.model.UserDto;
import me.macao.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsraddChainCommand
    extends AbstractCommandChain {

    public UsraddChainCommand(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {
        try {

            if (!line[0].equals("usradd"))
                nextChain.exec(service, line);

            else if (line.length != 5)
                nextChain.exec(service, line);

            else service.createUser(
                    new UserDto(
                    line[1],
                    line[2],
                    LocalDate.parse(
                            line[3],
                            DateTimeFormatter.ofPattern("d.MM.yyyy")
                    ),
                    line[4])
            );

        } catch (Exception e) {

            System.out.println(Exception.class + e.getMessage());
        }
    }
}
