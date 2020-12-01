package org.georgich.straightrazor.controller;

import org.georgich.straightrazor.domain.ClassExcelExporter;
import org.georgich.straightrazor.domain.Client;
import org.georgich.straightrazor.domain.User;
import org.georgich.straightrazor.storages.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {
    @Autowired
    private ClientRepository clientRepo;

    // displaying a list of clients
    @GetMapping("/clients")
    public String clients(@AuthenticationPrincipal User user, Map<String, Object> model) {

        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        model.put("clients", clients);

        return "clients";
    }

    // method for adding a new client
    @PostMapping("/addClient")
    public String addClient(@AuthenticationPrincipal User user,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String cellNumber, Map<String, Object> model) {

        Client Client = new Client(name, surname, cellNumber, user);
        clientRepo.saveAndFlush(Client);

        Iterable<Client> clients = clientRepo.findAllByAuthor(user);
        model.put("clients", clients);

        return "clients";
    }

    // uploading the database to Excel
    @GetMapping("/exportClient")
    public void exportToExcel (@AuthenticationPrincipal User user,
                              HttpServletResponse response) throws IOException {

        List<Client> listClients = clientRepo.findAllByAuthor(user);
        ClassExcelExporter excelExporter = new ClassExcelExporter(listClients);

        excelExporter.export(response);
    }

}
