package me.macao.lab4.service;

import me.macao.lab4.dto.CatCreateDTO;
import me.macao.lab4.dto.CatResponseDTO;
import me.macao.lab4.dto.CatUpdateDTO;
import me.macao.lab4.exception.ObjectNotFoundException;

import java.time.format.DateTimeParseException;
import java.util.Collection;

public interface CatService {

    Collection<CatResponseDTO> getAllCats();

    CatResponseDTO getCatById(Long id)
            throws ObjectNotFoundException;

    CatResponseDTO createCat(CatCreateDTO catCreateDTO);

    CatResponseDTO updateCat(CatUpdateDTO catDto)
            throws ObjectNotFoundException, DateTimeParseException;

    void deleteCatById(Long id);

    void addFriendOrRequest(Long srcId, Long dstId)
            throws ObjectNotFoundException;

    void deleteFriendOrRequest(Long srcId, Long dstId)
            throws ObjectNotFoundException;
}
