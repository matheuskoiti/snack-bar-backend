package com.koiti.snackbar.controller;

import com.koiti.snackbar.domain.response.OrderValueResponse;
import com.koiti.snackbar.domain.Snack;
import com.koiti.snackbar.service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/snack")
public class SnackController {

    @Autowired
    SnackService snackService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Snack> getSnacks() {
        return snackService.getSnacks();
    }
    @RequestMapping(method = RequestMethod.GET,path = "/order/value")
    public double getSnacksValue(
            @RequestParam(name = "snacks")
            @NotNull final List<String> snacks,
            @RequestParam(name = "quantities")
            @NotNull final List<String> quantities
    ) {

        return snackService.getSnacksValue(snacks, quantities);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/custom/value")
    public double getCustomSnacksValue(
            @RequestParam(name = "ingredients")
            @NotNull final List<String> ingredients,
            @RequestParam(name = "quantities")
            @NotNull final List<String> quantities
    ) {

        return snackService.getCustomSnacksValue(ingredients, quantities);
    }
}
