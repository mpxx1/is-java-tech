package me.macao.lab4.percistence;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.macao.lab4.dto.CatCreateDTO;
import me.macao.lab4.dto.CatUpdateDTO;
import me.macao.lab4.exception.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class CatDaoImpl
    implements CatDao {

    private final JpaCatDao catDao;
    private final JpaUserDao userDao;

    public Collection<Cat> findAll() {
        return catDao.findAll();
    }

    public Cat findById(Long id)
            throws ObjectNotFoundException {

            return catDao
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("No cat with id " + id));
    }

    public Cat create(CatCreateDTO catDto) {
        var cat = catDao.save(
                Cat
                        .builder()
                        .owner(userDao
                                .getReferenceById(
                                        catDto.ownerId()
                                ))
                        .name(catDto.name())
                        .breed(catDto.breed())
                        .color(catDto.color())
                        .birthday(catDto.birthday())
                        .build()
        );

        return catDao.save(cat);
    }

    @Transactional
    public Cat update(CatUpdateDTO catDto)
        throws ObjectNotFoundException {
        Cat cat = catDao
                .findById(catDto.id())
                .orElseThrow(() -> new ObjectNotFoundException("No cat with id " + catDto.id()));

        if (catDto.name() != null)
            cat.setName(catDto.name());

        if (catDto.breed() != null)
            cat.setBreed(catDto.breed());

        if (catDto.color() != null)
            cat.setColor(catDto.color());

        if (catDto.birthday() != null)
            cat.setBirthday(catDto.birthday());

//        if (catDto.ownerId() != null)
//            cat.setOwner(
//                    userDao.getReferenceById(catDto.ownerId())
//            );

        try {

            if (catDto.friends() != null) {
                Collection<Long> ids = new ArrayList<>(catDto.friends());
                Collection<Cat> friends = ids
                        .stream()
                        .map(c -> catDao.findById(c).get())
                        .toList();

                cat.setFriends(friends);
            }

            if (catDto.reqsOut() != null) {
                Collection<Long> ids = new ArrayList<>(catDto.reqsOut());
                Collection<Cat> reqsOut = ids
                        .stream()
                        .map(c -> catDao.findById(c).get())
                        .toList();

                cat.setReqsOut(reqsOut);
            }


        } catch (NoSuchElementException e) {

            throw new ObjectNotFoundException("Can't find cat with requested id");
        }

        return cat;
    }

    public void deleteById(Long id) {
        catDao.deleteById(id);
    }

    public void deleteByOwnerId(Long ownerId) {
        User user = userDao.getReferenceById(ownerId);
        user.setCatList(new ArrayList<>());
        userDao.save(user);
    }

    // todo move to service layer
    private Collection<Cat> getReqsIn(Cat cat) {
        return catDao
                .findAll()
                .stream()
                .filter(c -> c.getReqsOut().contains(cat))
                .toList();
    }
}
