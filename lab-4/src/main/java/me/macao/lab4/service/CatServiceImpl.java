package me.macao.lab4.service;

import lombok.AllArgsConstructor;
import me.macao.lab4.dto.CatCreateDTO;
import me.macao.lab4.dto.CatResponseDTO;
import me.macao.lab4.dto.CatUpdateDTO;
import me.macao.lab4.exception.ObjectNotFoundException;
import me.macao.lab4.percistence.Cat;
import me.macao.lab4.percistence.CatDao;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CatServiceImpl
    implements CatService {

    private CatDao catDao;
    private DTOMapper dtoMapper;

    public Collection<CatResponseDTO> getAllCats() {
        return catDao
                .findAll()
                .stream()
                .map(cat -> dtoMapper.catToDaoResponseDTO(cat, getReqsIn(cat)))
                .toList();
    }

    public CatResponseDTO getCatById(Long id)
        throws ObjectNotFoundException {

        var tgt = catDao.findById(id);

        return dtoMapper.catToDaoResponseDTO(tgt, getReqsIn(tgt));
    }

    public CatResponseDTO createCat(CatCreateDTO catDto) {

        var cat = catDao.create(catDto);

        return dtoMapper.catToDaoResponseDTO(cat, getReqsIn(cat));
    }

    public CatResponseDTO updateCat(CatUpdateDTO catDto)
            throws ObjectNotFoundException, DateTimeParseException {

        var cat = catDao.update(catDto);

        return dtoMapper.catToDaoResponseDTO(cat, getReqsIn(cat));
    }

    public void deleteCatById(Long id) {
        catDao.deleteById(id);
    }

    public void addFriendOrRequest(Long srcId, Long dstId)
        throws ObjectNotFoundException {

        if (Objects.equals(srcId, dstId)) return;

        Cat srcCat = catDao.findById(srcId);
        Cat dstCat = catDao.findById(dstId);

        if (getReqsIn(srcCat).contains(dstCat)) {

            Collection<Long> reqsOut = new ArrayList<>(dstCat
                    .getReqsOut()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            Collection<Long> friends = new ArrayList<>(dstCat
                    .getFriends()
                    .stream()
                    .map(Cat::getId)
                    .toList());

            friends.add(srcId);
            reqsOut.remove(srcId);

            CatUpdateDTO catDto = new CatUpdateDTO(
                    dstId,
                    null, null, null, null,
                    friends,
                    reqsOut
            );

            catDao.update(catDto);

            friends = new ArrayList<>(srcCat
                    .getFriends()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            friends.add(dstId);

            catDto = new CatUpdateDTO(
                    srcId,
                    null, null, null, null,
                    friends,
                    null
            );

            catDao.update(catDto);
        } else {

            Collection<Long> reqsOut = new ArrayList<>(srcCat
                    .getReqsOut()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            reqsOut.add(dstId);

            CatUpdateDTO catDto = new CatUpdateDTO(
                    srcId,
                    null, null, null, null, null,
                    reqsOut
            );

            catDao.update(catDto);
        }
    }

    public void deleteFriendOrRequest(Long srcId, Long dstId)
        throws ObjectNotFoundException {

        if (Objects.equals(srcId, dstId)) return;

        Cat srcCat = catDao.findById(srcId);
        Cat dstCat = catDao.findById(dstId);

        if (srcCat.getFriends().contains(dstCat)) {

            Collection<Long> reqsOut = new ArrayList<>(dstCat
                    .getReqsOut()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            Collection<Long> friends = new ArrayList<>(dstCat
                    .getFriends()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            friends.remove(srcId);

            if (!reqsOut.contains(srcId))
                reqsOut.add(srcId);

            CatUpdateDTO catDto = new CatUpdateDTO(
                    dstId,
                    null, null, null, null,
                    friends,
                    reqsOut
            );

            catDao.update(catDto);

            friends = new ArrayList<>(srcCat
                    .getFriends()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            friends.remove(dstId);
            catDto = new CatUpdateDTO(
                    srcId,
                    null, null, null, null,
                    friends,
                    null
            );

            catDao.update(catDto);
        } else {

            Collection<Long> reqsOut = new ArrayList<>(srcCat
                    .getReqsOut()
                    .stream()
                    .map(Cat::getId)
                    .toList());
            reqsOut.remove(dstId);

            CatUpdateDTO catDto = new CatUpdateDTO(
                    srcId,
                    null, null, null, null, null,
                    reqsOut
            );

            catDao.update(catDto);
        }
    }

    private Collection<Cat> getReqsIn(Cat cat) {
        return catDao
                .findAll()
                .stream()
                .filter(c -> c.getReqsOut().contains(cat))
                .toList();
    }
}
