package me.macao.lab4.controller;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.*;
import me.macao.lab4.exception.InvalidOperationException;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.percistence.UserRole;
import me.macao.lab4.service.CatService;
import me.macao.lab4.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v0.1.0/users")
public class UserController {

    private final UserService userService;

    @GetMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<UserResponseDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public UserResponseDTO getUserById(@PathVariable("id") final Long id)
        throws ObjectNotFoundException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.id().equals(id))
            throw new AccessDeniedException("Access denied");

        return userService.getUserById(id);
    }

    @PutMapping(path = "{id}")
    public UserResponseDTO updateUser(
            @PathVariable("id") final Long id,
            @RequestParam(required = false) final String email,
            @RequestParam(required = false) final String password,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String birthday
    ) throws InvalidOperationException, ObjectNotFoundException, DateTimeParseException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.id().equals(id))
            throw new AccessDeniedException("Access denied");

        UserUpdateDTO userDto = new UserUpdateDTO(
                id, email, password, name, birthday == null ? null : LocalDate.parse(birthday)
        );

        return userService.updateUser(userDto);
    }

    @DeleteMapping(path = "{id}")
    public void deleteUser(@PathVariable("id") final Long id) throws ObjectNotFoundException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.id().equals(id))
            throw new AccessDeniedException("Access denied");

        userService.deleteUserById(id);
    }

    private UserResponseDTO getUserFromSecurityContext()
            throws ObjectNotFoundException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = userDetails.getUsername();
        var uid = userService.getUserIdByEmail(username);

        return userService.getUserById(uid);
    }
}
