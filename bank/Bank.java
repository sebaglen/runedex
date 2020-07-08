package net.runelite.client.plugins.runedex.bank;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Bank {
    private long bankValue;
    private ArrayList bankItems;
}
