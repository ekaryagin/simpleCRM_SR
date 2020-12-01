package org.georgich.straightrazor.controller;

import org.georgich.straightrazor.domain.Client;
import org.georgich.straightrazor.domain.Employee;
import org.georgich.straightrazor.domain.Note;
import org.georgich.straightrazor.domain.User;
import org.georgich.straightrazor.storages.ClientRepository;
import org.georgich.straightrazor.storages.EmployeeRepository;
import org.georgich.straightrazor.storages.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ScheduleController {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EmployeeRepository staffRepo;

    @Autowired
    private NoteRepository noteRepo;

    // the schedule page
    @GetMapping("/schedule")
    public String clientOnSchedule(@AuthenticationPrincipal User user,
                                   Map<String, Object> model) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        model.put("date", dtf.format(now).toString());

        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        Iterable<Employee> staff = staffRepo.findAllByAuthor(user);
        Iterable<Note> notes = noteRepo.findAllByDateAndAuthor(dtf.format(now).toString(), user);

        model.put("notes", notes);
        model.put("clients", clients);
        model.put("staff", staff);

        return "schedule";
    }

    // refresh the schedule page
    @PostMapping("/refreshSchedule")
    public String refreshSchedule(@AuthenticationPrincipal User user,
                                  @RequestParam String date,
                                  @RequestParam String master,
                                  Map<String, Object> model){
        Iterable<Note> notes;
        Employee employee = staffRepo.findBySurname(master);
        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        Iterable<Employee> staff = staffRepo.findAllByAuthor(user);
        if (master.equals("Выберите мастера...")) {
            notes = noteRepo.findAllByDateAndAuthor(date, user);
        } else {
            notes = noteRepo.findAllByDateAndEmployee(date, employee);
        }

        model.put("date", date);
        model.put("notes", notes);
        model.put("clients", clients);
        model.put("staff", staff);

        return "schedule";
    }

    // adding a new entry to the schedule
    @PostMapping("/addNote")
    public String addNote(@AuthenticationPrincipal User user,
                          @RequestParam String addNoteDate,
                          @RequestParam String noteInputTime1,
                          @RequestParam String noteInputTime2,
                          @RequestParam String noteInputTime3,
                          @RequestParam String noteInputTime4,
                          @RequestParam String addNoteMaster,
                          @RequestParam String addNoteClient,
                          @RequestParam String addNoteComment,
                          Map<String, Object> model){
        Client client = clientRepo.findByCellNumber(addNoteClient);
        Employee employee = staffRepo.findBySurname(addNoteMaster);
        String time = noteInputTime1+":"+noteInputTime2+" - "+noteInputTime3+":"+noteInputTime4;
        Note note = new Note(addNoteDate, time, addNoteComment, employee, client, user);
        noteRepo.saveAndFlush(note);

        model.put("date", addNoteDate);

        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        Iterable<Employee> staff = staffRepo.findAllByAuthor(user);
        Iterable<Note> notes = noteRepo.findAllByDateAndEmployee(addNoteDate, employee);

        model.put("notes", notes);
        model.put("clients", clients);
        model.put("staff", staff);

        return "schedule";
    }

    // search for a client by parameters
    @PostMapping("/findClient")
    public String findClient(@AuthenticationPrincipal User user,
                             @RequestParam String findRequest,
                             Map<String, Object> model){
        Iterable<Employee> staff = staffRepo.findAllByAuthor(user);
        Iterable<Client> clients;
        Pattern pattern = Pattern.compile("^[0-9]+");
        Matcher matcher = pattern.matcher(findRequest);

        if (findRequest.equals("")){
            clients = clientRepo.findAllByAuthor(user);
        } else if (matcher.matches()) {
            clients = clientRepo.findAllByCellNumber(findRequest);
        }else {
            clients = clientRepo.findAllBySurname(findRequest);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        model.put("date", dtf.format(now));
        model.put("clients", clients);
        model.put("staff", staff);

        return "schedule";
    }

    // adding a new client on the schedule page
    @PostMapping("/addClientSchedule")
    public String addClients(@AuthenticationPrincipal User user,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String cellNumber, Map<String, Object> model) {

        Client Client = new Client(name, surname, cellNumber, user);
        clientRepo.saveAndFlush(Client);

        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        Iterable<Employee> staff = staffRepo.findAllByAuthor(user);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        model.put("date", dtf.format(now));
        model.put("staff", staff);
        model.put("clients", clients);

        return "schedule";
    }
}
