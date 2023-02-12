package ru.outs.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.outs.dto.ClientDTO;
import ru.outs.services.ClientService;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping({"/", "/client/list"})
    public String clientsList(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@ModelAttribute ClientDTO clientDTO) {
        clientService.saveClient(clientDTO);
        return new RedirectView("/", true);
    }
}
