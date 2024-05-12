package me.macao.lab4.percistence;

import me.macao.lab4.dto.CatCreateDTO;
import me.macao.lab4.dto.CatUpdateDTO;
import me.macao.lab4.exception.ObjectNotFoundException;

import java.util.Collection;

public interface CatDao {

    Collection<Cat> findAll();

    Cat findById(Long id)
            throws ObjectNotFoundException;

    Cat create(CatCreateDTO catDto);

    Cat update(CatUpdateDTO catDto)
            throws ObjectNotFoundException;

    void deleteById(Long id);

    void deleteByOwnerId(Long ownerId);
}
