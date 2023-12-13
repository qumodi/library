package com.example.library.controllers;

import com.example.library.models.Visitor;
import com.example.library.services.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class VisitorController {
    private final VisitorService visitorService;

    @GetMapping("/visitors")
    public String visitors(Model model){
        model.addAttribute("visitors",visitorService.listVisitors(null));
        return "visitors";
    }

    @GetMapping("/visitor/add")
    public String addVisitor(Model model){
        return "visitor-add";
    }

    @PostMapping("/visitor/add")
    public String addVisitorReal(@RequestParam String name,@RequestParam String surname,@RequestParam String phoneNumber,Model model){
        Visitor visitor = new Visitor(name,surname,phoneNumber);
        visitorService.saveVisitor(visitor);
        return "redirect:/visitors";
    }

    @GetMapping("/visitor/{id}")
    public String visitorDetail(@PathVariable(value = "id") long id,Model model){
        if(visitorService.getVisitorByID(id) == null){
            return "redirect:/visitors";
        }
        Visitor visitor = visitorService.getVisitorByID(id);
        model.addAttribute("visitor",visitor);
        return "visitor-info";
    }

    @GetMapping("/visitor/{id}/edit")
    public String visitorEdit(@PathVariable(value = "id") long id, Model model){
        if(visitorService.getVisitorByID(id) == null){
            return "redirect:/visitors";
        }
        Visitor visitor = visitorService.getVisitorByID(id);
        model.addAttribute("visitor",visitor);
        return "visitor-edit";
    }
    @PostMapping("/visitor/{id}/edit")
    public String visitorUpdate(@PathVariable(value = "id") long id,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String phoneNumber) {
        Visitor visitor = visitorService.getVisitorByID(id);
        visitor.setName(name);
        visitor.setSurname(surname);
        visitor.setPhoneNumber(phoneNumber);
        visitorService.saveVisitor(visitor);
        return "redirect:/visitors";
    }
        @PostMapping("/visitor/{id}/delete")
    public String visitorDelete(@PathVariable(value = "id") long id,Model model){
        visitorService.deleteVisitor(id);
        return "redirect:/visitors";
    }
}
