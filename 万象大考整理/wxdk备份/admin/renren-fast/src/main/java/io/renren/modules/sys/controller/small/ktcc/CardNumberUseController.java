package io.renren.modules.sys.controller.small.ktcc;

import io.renren.modules.sys.service.small.ktcc.CardNumberUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smallLookGuess/cardNumber")
public class CardNumberUseController {
    @Autowired
    private CardNumberUseService cardNumberUseService;

    @GetMapping("/getCardNumberUse")
    public String getCardNumberUse(){
        return cardNumberUseService.getSubjectUse();
    }
}
