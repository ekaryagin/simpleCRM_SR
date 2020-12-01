package org.georgich.straightrazor.storages;

import org.georgich.straightrazor.domain.Client;
import org.georgich.straightrazor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);

    Client findBySurname(String surname);

    Client findByCellNumber(String cellNumber);

    List<Client> findAllByAuthor(User user);

    List<Client> findAllByCellNumber(String cellNumber);

    List<Client> findAllBySurname(String surname);

}
