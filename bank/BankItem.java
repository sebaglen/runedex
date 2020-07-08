package net.runelite.client.plugins.runedex.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.runelite.api.Item;

@Data
@AllArgsConstructor
public class BankItem {
    private int id;
    private long quantity;
    private long value;
    private String itemName;
}
