package me.macao.lab4.service;

import me.macao.lab4.dto.*;
import me.macao.lab4.percistence.Cat;
import me.macao.lab4.percistence.CatColor;
import me.macao.lab4.percistence.User;
import me.macao.lab4.percistence.UserRole;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DTOMapper {
    public CatResponseDTO catToDaoResponseDTO(Cat cat, Collection<Cat> reqsIn) {
        return new CatResponseDTO(
                cat.getOwner().getId(),
                cat.getId(),
                cat.getName(),
                cat.getBreed(),
                cat.getColor(),
                cat.getBirthday(),
                cat.getFriends()
                        .stream()
                        .map(Cat::getId)
                        .toList(),
                reqsIn
                        .stream()
                        .map(Cat::getId)
                        .toList(),
                cat.getReqsOut()
                        .stream()
                        .map(Cat::getId)
                        .toList()
        );
    }

    public UserResponseDTO userToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getName(),
                user.getBirthday(),
                user
                        .getCatList()
                        .stream()
                        .map(Cat::getId)
                        .toList()
        );
    }

    public CatColor stringToColor(String color) {
        return switch (color) {
            case "red" -> CatColor.RED;
            case "white" -> CatColor.WHITE;
            case "grey" -> CatColor.GREY;
            default -> CatColor.BLACK;
        };
    }

    public UserRole stringToUserRole(String role) {
        if (role.equalsIgnoreCase("admin"))
            return UserRole.ADMIN;

        return UserRole.USER;
    }



//    public SecurityUserDto userToSecurity(User user) {
//        return new SecurityUserDto(
//                user.getId(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getRole()
//        );
//    }
}
