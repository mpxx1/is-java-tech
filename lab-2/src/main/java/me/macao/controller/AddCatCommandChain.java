package me.macao.controller;

import me.macao.model.CatColor;
import me.macao.model.CatDto;
import me.macao.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddCatCommandChain
    extends AbstractCommandChain {
    public AddCatCommandChain(CommandChain nextChain) {
        super(nextChain);
    }

    @Override
    public void exec(UserService service, String[] line) {

        try {

            if (!line[0].equals("addcat"))
                nextChain.exec(service, line);

            else if (line.length != 5)
                nextChain.exec(service, line);

            else service.createCat(
                    new CatDto(
                    line[1],
                    LocalDate.parse(
                            line[2],
                            DateTimeFormatter.ofPattern("d.MM.yyyy")
                    ),
                    line[3],
                    getCatColor(line[4]))
            );

        } catch (Exception e) {
            System.out.println(Exception.class + e.getMessage());
        }
    }

    private static CatColor getCatColor(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> CatColor.Red;
            case "white" -> CatColor.White;
            case "black" -> CatColor.Black;
            case "grey" -> CatColor.Grey;
            default -> throw new IllegalArgumentException("Invalid cat color: " + color +
                    ". Valid values are red, white, black, grey.");
        };
    }
}
