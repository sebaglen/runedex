/*
 * Copyright (c) 2020, Sebastian Aglen Danielsen <https://github.com/sebaglen>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.plugins.runedex.bank;

import lombok.Data;
import net.runelite.api.*;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.runedex.APIManager;


import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;

@Data
public class BankModel {

    @Inject
    private APIManager manager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private Client client;

    @Subscribe
    public void onScriptCallbackEvent(ScriptCallbackEvent event)
    {
        if (event.getEventName().equals("setBankTitle")) {
            final Item[] bankItems = getBankTabItems();
            final long totalGePrice = getTotalGePrice(bankItems);
            final ArrayList<Object> bankItemList = formatBankItems(bankItems);

            Bank bank = new Bank(totalGePrice, bankItemList);
            manager.storeEvent("bank", bank);
        }
    }

    private Item[] getBankTabItems()
    {
        final ItemContainer container = client.getItemContainer(InventoryID.BANK);
        if (container == null)
        {
            return null;
        }
        final Item[] items = container.getItems();

        return items;
    }

    private ArrayList<Object> formatBankItems(Item[] bankItems) {

        ArrayList<Object> build = new ArrayList<>();

        for (final Item item : bankItems)
        {
            final int qty = item.getQuantity();
            final int id = item.getId();

            if (id <= 0 || qty == 0)
            {
                continue;
            }
            build.add(new BankItem(
                    id,
                    qty,
                    (getTotalItemValueByIdAndQuantity(id, qty) / qty),
                    client.getItemDefinition(id).getName()) // check out ItemID in the runelite api, get key of id value
            );
        }

        return build;
    }

    long getTotalGePrice(@Nullable Item[] items)
    {
        if (items == null)
        {
            return 0;
        }

        long ge = 0;

        for (final Item item : items)
        {
            final int qty = item.getQuantity();
            final int id = item.getId();

            if (id <= 0 || qty == 0)
            {
                continue;
            }

            ge += getTotalItemValueByIdAndQuantity(id, qty);
        }

        return ge;
    }

    long getTotalItemValueByIdAndQuantity(int id, int qty) {
        switch (id)
        {
            case ItemID.COINS_995:
                return qty;
            case ItemID.PLATINUM_TOKEN:
                return qty * 1000L;
            default:
                return (long) itemManager.getItemPrice(id) * qty;
        }
    }
}

