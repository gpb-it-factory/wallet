package ru.gpbtech.wallet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gpbtech.wallet.model.GetWalletBalanceRequest;
import ru.gpbtech.wallet.model.GetWalletBalanceResponse;
import ru.gpbtech.wallet.service.WalletBalanceService;

/**
 * Контроллер для управления операциями с балансом кошелька.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletBalanceController {
    
    private final WalletBalanceService walletBalanceService;
    
    /**
     * Получает баланс кошелька на основе предоставленных параметров.
     *
     * @param request Запрос на получение баланса кошелька.
     *
     * @return Ответ с балансом кошелька и дополнительной информацией.
     */
    @PostMapping("/balance")
    public ResponseEntity<GetWalletBalanceResponse> getWalletBalance(@RequestBody GetWalletBalanceRequest request) {
        log.info("Запрос на получение информации по балансу для клиента {}", request.getClientId());
        
        try {
            GetWalletBalanceResponse walletBalance = walletBalanceService.getWalletBalance(request);
            
            log.trace("Результат работы сервиса {}", walletBalance);
            
            return ResponseEntity.ok(walletBalance);
        } catch (Exception exception) {
            log.error("При получении данных о балансе произошла ошибка ", exception);
            return ResponseEntity.badRequest().build();
        }
    }
}