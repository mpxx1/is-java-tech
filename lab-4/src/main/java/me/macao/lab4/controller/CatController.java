package me.macao.lab4.controller;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.*;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.percistence.UserRole;
import me.macao.lab4.service.CatService;
import me.macao.lab4.service.DTOMapper;
import me.macao.lab4.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v0.1.0/cats")
public class CatController {

    private final CatService catService;
    private final UserService userService;
    private final DTOMapper dtoMapper;

    @GetMapping("all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<CatResponseDTO> getAllCats() {
        return catService.getAllCats();
    }

    @GetMapping("my")
    @PreAuthorize("hasAuthority('USER')")
    public Collection<CatResponseDTO> getMyCats() throws ObjectNotFoundException {

        var uid = getUserFromSecurityContext().id();

        return catService
                .getAllCats()
                .stream()
                .filter(cat -> cat.ownerId().equals(uid))
                .toList();
    }

    @GetMapping("{id}")
    public CatResponseDTO getCatById(@PathVariable("id") Long id)
            throws ObjectNotFoundException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.cats().contains(id))
            throw new AccessDeniedException("Access denied");


        return catService.getCatById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('USER')")
    public CatResponseDTO createCat(@RequestBody CatInitDTO catDto)
            throws ObjectNotFoundException {

        var uid = getUserFromSecurityContext().id();

        var catCreate = new CatCreateDTO(
                uid,
                catDto.name(),
                catDto.breed(),
                catDto.color(),
                catDto.birthday()
        );

        return catService.createCat(catCreate);
    }

    @PutMapping("{id}")
    public CatResponseDTO updateCat(
            @PathVariable("id") final Long id,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String breed,
            @RequestParam(required = false) final String color,
            @RequestParam(required = false) final String birthday,
            @RequestParam(required = false) final Long addFriend,
            @RequestParam(required = false) final Long rmFriend
            ) throws ObjectNotFoundException, NumberFormatException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.cats().contains(id))
            throw new AccessDeniedException("Access denied");

        if (addFriend != null) {
            catService.addFriendOrRequest(id, addFriend);
        }

        if (rmFriend != null) {
            catService.deleteFriendOrRequest(id, rmFriend);
        }

        return catService.updateCat(
                new CatUpdateDTO(
                        id, name, breed,
                        color == null ? null : dtoMapper.stringToColor(color),
                        birthday == null ? null : LocalDate.parse(birthday),
                        null,
                        null
                )
        );
    }

    @DeleteMapping("{id}")
    public void deleteCat(@PathVariable("id") final Long id)
            throws ObjectNotFoundException {

        var user = getUserFromSecurityContext();

        if (user.role() != UserRole.ADMIN && !user.cats().contains(id))
            throw new AccessDeniedException("Access denied");;

        catService.deleteCatById(id);
    }

    private UserResponseDTO getUserFromSecurityContext()
            throws ObjectNotFoundException {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = userDetails.getUsername();
        var uid = userService.getUserIdByEmail(username);

        return userService.getUserById(uid);
    }
}
