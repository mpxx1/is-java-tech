package me.macao.service;

import me.macao.exception.*;
import me.macao.model.*;

import java.time.LocalDate;
import java.util.Collection;

public interface UserService {

    User createUser(
            UserDto dto
    ) throws InvalidOperationException, ObjectNotFoundException,
            EmailCreateException, PasswordCreateException;

    void login(String email, String password)
            throws ObjectNotFoundException, DBTimeoutException, InvalidOperationException;

    void logout();

    Collection<Cat> allCats()
            throws SpecifyUserException;

    Cat getCatByName(String catName)
            throws ObjectNotFoundException, SpecifyUserException;

    void deleteCat(Cat cat)
            throws ObjectNotFoundException;

    void dropCatByName(String catName)
        throws ObjectNotFoundException, SpecifyUserException;

    Cat createCat(
            CatDto dto
    ) throws SpecifyUserException, ObjectNotFoundException;

    Cat updateCat(
            Cat newCat
    ) throws SpecifyUserException, ObjectNotFoundException, InvalidOperationException;

    void requestFriendship(
            String srcName,
            String email,
            String targetName
    ) throws ObjectNotFoundException, SpecifyUserException,
            DBTimeoutException, InvalidOperationException;

    void cancelRequest(
            String srcName,
            String email,
            String targetName
    ) throws ObjectNotFoundException, SpecifyUserException,
            DBTimeoutException, InvalidOperationException;

    Collection<Cat> getFriendshipRequests(String name)
            throws SpecifyUserException, ObjectNotFoundException;

    Collection<Cat> getFriends(String name)
            throws SpecifyUserException, ObjectNotFoundException;

    void acceptFriendship(
            String srcName,
            String email,
            String targetName
    ) throws SpecifyUserException, ObjectNotFoundException,
            DBTimeoutException, InvalidOperationException;

    void dropFriend(
            String srcName,
            String email,
            String targetName
    ) throws SpecifyUserException, ObjectNotFoundException,
            DBTimeoutException, InvalidOperationException;
}
