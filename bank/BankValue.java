package net.runelite.client.plugins.runedex.bank;

import lombok.Value;

@Value
class BankValue
{
    private long gePrice;

    public long getPrice() {
        return gePrice;
    }
}
