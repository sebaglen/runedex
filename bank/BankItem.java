package net.runelite.client.plugins.runedex.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankItem {
    private int id;
    private long quantity;
    private long value;
    private String itemName;
}
