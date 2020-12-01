package org.georgich.straightrazor.storages;

import org.georgich.straightrazor.domain.Client;
import org.georgich.straightrazor.domain.Employee;
import org.georgich.straightrazor.domain.Note;
import org.georgich.straightrazor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByDateAndAuthor (String date, User user);

    List<Note> findAllByDateAndEmployee (String date, Employee employee);

    List<Note> findAllByClient (Client client);

    Note findByClient (Client client);

    Note findByAuthor (User user);

    Note findByEmployee (Employee employee);

}
