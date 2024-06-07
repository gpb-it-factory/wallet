package ru.gpbtech.wallet.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс WalletBalance представляет собой сущность баланса кошелька.
 * Хранит информацию о балансе кошелька клиента, валюте и времени последнего обновления.
 * <p>
 * Аннотации:
 * - @Data: Генерирует геттеры, сеттеры, toString(), equals() и hashCode() методы.
 * - @Entity: Указывает, что класс является сущностью JPA.
 * - @NoArgsConstructor: Генерирует конструктор без аргументов.
 * - @Table: Указывает, что данный класс сопоставлен с таблицей "wallet" в базе данных.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "wallet")
public class WalletBalance {
    
    /**
     * Уникальный идентификатор клиента.
     * Обозначен как первичный ключ в таблице.
     */
    @Id
    private String clientId;
    
    /**
     * Текущий баланс кошелька клиента.
     */
    private BigDecimal balance;
    
    /**
     * Валюта баланса кошелька.
     * Пример: "USD", "EUR", "RUB".
     */
    private String currency;
    
    /**
     * Время последнего обновления баланса.
     * Хранится в формате LocalDateTime.
     */
    private LocalDateTime lastUpdated;
}
