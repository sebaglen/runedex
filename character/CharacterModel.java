/*
 * Copyright (c) 2018, Sebastian Aglen Danielsen <https://github.com/sebaglen>
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
package net.runelite.client.plugins.runedex.character;

import lombok.Data;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.runedex.APIManager;

import javax.inject.Inject;

@Data
public class CharacterModel {
    @Inject
    private Client client;

    @Inject
    private APIManager manager;

    @Subscribe
    public void onStatChanged(StatChanged statChanged)
    {
        Character character = new Character(
                client.getSkillExperience(Skill.ATTACK),
                client.getSkillExperience(Skill.STRENGTH),
                client.getSkillExperience(Skill.DEFENCE),
                client.getSkillExperience(Skill.RANGED),
                client.getSkillExperience(Skill.PRAYER),
                client.getSkillExperience(Skill.MAGIC),
                client.getSkillExperience(Skill.RUNECRAFT),
                client.getSkillExperience(Skill.HITPOINTS),
                client.getSkillExperience(Skill.AGILITY),
                client.getSkillExperience(Skill.HERBLORE),
                client.getSkillExperience(Skill.THIEVING),
                client.getSkillExperience(Skill.CRAFTING),
                client.getSkillExperience(Skill.FLETCHING),
                client.getSkillExperience(Skill.SLAYER),
                client.getSkillExperience(Skill.HUNTER),
                client.getSkillExperience(Skill.MINING),
                client.getSkillExperience(Skill.SMITHING),
                client.getSkillExperience(Skill.FISHING),
                client.getSkillExperience(Skill.COOKING),
                client.getSkillExperience(Skill.FIREMAKING),
                client.getSkillExperience(Skill.FARMING),
                client.getOverallExperience(),
                client.getTotalLevel(),
                client.getLocalPlayer().getCombatLevel()
        );

        manager.storeEvent("character", character);
    }
}
