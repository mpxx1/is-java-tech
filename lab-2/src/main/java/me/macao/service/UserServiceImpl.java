package me.macao.service;

import me.macao.crypto.EmailVerifier;
import me.macao.crypto.PasswordVerifier;
import me.macao.crypto.StringHasher;
import me.macao.dao.Dao;
import me.macao.exception.*;
import me.macao.model.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.NoSuchElementException;

public class UserServiceImpl
    implements UserService {

    private final Dao<Integer, User> userDao;
    private final Dao<Integer, Cat> catDao;
    private User currentUser = null;

    public UserServiceImpl(
            Dao<Integer, User> userDao,
            Dao<Integer, Cat> catDao
    ) {
        this.userDao = userDao;
        this.catDao = catDao;
    }

    public User createUser(
            UserDto dto
    ) throws InvalidOperationException, ObjectNotFoundException,
            EmailCreateException, PasswordCreateException {

        if (currentUser != null)
            throw new InvalidOperationException("User is already created");

        currentUser = userDao.create(dto.toUser());
        return currentUser;
    }

    public void login(String email, String password)
        throws ObjectNotFoundException, DBTimeoutException, InvalidOperationException {

        var hasher = new StringHasher();

        String passHash = hasher.generate(password);

        if (currentUser != null)
            throw new InvalidOperationException("Allready logged in");

        try {

            currentUser = userDao
                    .findAll()
                    .stream()
                    .filter(user ->
                            user.getEmail().equals(email) &&
                                    user.getPasswdHash().equals(passHash)
                    )
                    .toList()
                    .getFirst();
        } catch (NoSuchElementException e) {

            throw new ObjectNotFoundException("Wrong email or password");
        }
    }

    public void logout() { currentUser = null; }

    public Collection<Cat> allCats()
        throws SpecifyUserException {

        if (currentUser == null)
            throw new SpecifyUserException();

        return currentUser.getCatList();
    }

    public Cat getCatByName(String catName)
        throws ObjectNotFoundException, SpecifyUserException {

        if (currentUser == null)
            throw new SpecifyUserException();

        try {
            return allCats()
                    .stream()
                    .filter(cat -> cat.getName().equals(catName))
                    .toList()
                    .getFirst();
        } catch (NoSuchElementException e) {

            throw new ObjectNotFoundException();
        }
    }

    public void deleteCat(Cat cat)
        throws ObjectNotFoundException {

        try {

            catDao.delete(cat);
        } catch (Exception e) {

            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    public void dropCatByName(String catName)
            throws ObjectNotFoundException, SpecifyUserException {

        deleteCat(getCatByName(catName));
    }

    public Cat createCat(
            CatDto dto
    ) throws SpecifyUserException, ObjectNotFoundException {

        if (currentUser == null)
            throw new SpecifyUserException();

        try {

            return catDao.create(
                    Cat
                            .builder()
                            .name(dto.name())
                            .breed(dto.breed())
                            .birthday(dto.bday())
                            .color(dto.color())
                            .owner(currentUser)
                            .build()
            );
        } catch (InvalidOperationException e) {

            throw new ObjectNotFoundException(e.getMessage());
        } catch (Exception e) {

            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    public Cat updateCat(Cat newCat)
            throws SpecifyUserException, ObjectNotFoundException, InvalidOperationException {

        if (currentUser == null)
            throw new SpecifyUserException();

        catDao.update(newCat);
        return newCat;
    }

    public void requestFriendship(
            String srcName,
            String email,
            String targetName
    ) throws ObjectNotFoundException, SpecifyUserException,
            DBTimeoutException, InvalidOperationException {

        Cat srcCat = getCatByName(srcName);

        Cat targetCat = catDao
                .findAll()
                .stream()
                .filter(cat -> cat.getOwner().getEmail().equals(email))
                .filter(cat -> cat.getName().equals(targetName))
                .toList()
                .getFirst();

        if (!targetCat.getFriendshipRequests().contains(srcCat)) {

            Collection<Cat> cats = srcCat.getFriendshipRequests();
            cats.add(targetCat);
            srcCat.setFriendshipRequests(cats);

            catDao.update(srcCat);
        } else {

            acceptFriendship(
                    srcName,
                    email,
                    targetName
            );
        }
    }

    public void cancelRequest(String srcName, String email, String targetName)
            throws ObjectNotFoundException, SpecifyUserException,
            DBTimeoutException, InvalidOperationException {

        Cat srcCat = getCatByName(srcName);

        Cat targetCat = catDao
                .findAll()
                .stream()
                .filter(cat -> cat.getOwner().getEmail().equals(email))
                .filter(cat -> cat.getName().equals(targetName))
                .toList()
                .getFirst();

        if (!srcCat.getFriendshipRequests().contains(targetCat))
            throw new ObjectNotFoundException("You are not requesting a friendship with " + targetName);

        Collection<Cat> cats = srcCat.getFriendshipRequests();
        cats.remove(targetCat);
        srcCat.setFriendshipRequests(cats);

        catDao.update(srcCat);
    }

    public Collection<Cat> getFriendshipRequests(String name)
        throws SpecifyUserException, ObjectNotFoundException {

        return getCatByName(name)
                .getFriendshipRequests();
    }

    public Collection<Cat> getFriends(String name)
            throws SpecifyUserException, ObjectNotFoundException {

        return getCatByName(name)
                .getFriends();
    }

    public void acceptFriendship(
            String srcName,
            String email,
            String targetName
    ) throws SpecifyUserException, ObjectNotFoundException,
            DBTimeoutException, InvalidOperationException {

        Cat srcCat = getCatByName(srcName);

        Cat targetCat = catDao
                .findAll()
                .stream()
                .filter(cat -> cat.getOwner().getEmail().equals(email))
                .filter(cat -> cat.getName().equals(targetName))
                .toList()
                .getFirst();

        Collection<Cat> cats = targetCat.getFriendshipRequests();
        cats.remove(srcCat);
        targetCat.setFriendshipRequests(cats);

        cats = srcCat.getFriends();
        cats.add(targetCat);
        srcCat.setFriends(cats);

        cats = targetCat.getFriends();
        cats.add(srcCat);
        targetCat.setFriends(cats);

        catDao.update(srcCat);
        catDao.update(targetCat);
    }

    public void dropFriend(
            String srcName,
            String email,
            String targetName
    ) throws SpecifyUserException, ObjectNotFoundException,
            DBTimeoutException, InvalidOperationException {

        Cat srcCat = getCatByName(srcName);

        Cat targetCat = catDao
                .findAll()
                .stream()
                .filter(cat -> cat.getOwner().getEmail().equals(email))
                .filter(cat -> cat.getName().equals(targetName))
                .toList()
                .getFirst();

        Collection<Cat> cats = srcCat.getFriends();
        cats.remove(targetCat);
        srcCat.setFriends(cats);

        cats = targetCat.getFriends();
        cats.remove(srcCat);
        targetCat.setFriends(cats);

        cats = targetCat.getFriendshipRequests();
        cats.add(srcCat);
        targetCat.setFriendshipRequests(cats);

        catDao.update(srcCat);
        catDao.update(targetCat);
    }
}
