package net.runelite.client.plugins.runedex.bank;

import lombok.Data;
import lombok.AllArgsConstructor;
import net.runelite.api.Item;

@Data
@AllArgsConstructor
public class Bank {
    private long bankValue;
    private Item[] bankItems;
}
