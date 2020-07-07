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

import com.google.inject.Provides;
import lombok.Data;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.runedex.RuneDexPluginConfiguration;

import javax.inject.Inject;

@Data
public class CharacterModel {
    @Inject
    private Client client;

    @Inject
    private RuneDexPluginConfiguration config;

    @Provides
    RuneDexPluginConfiguration provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(RuneDexPluginConfiguration.class);
    }

    private boolean consentFlag;
    private int combatLevel;
    private int totalLevel;
    private long totalXp;

    public CharacterModel() {
        this.combatLevel = client.getLocalPlayer().getCombatLevel();
        this.totalLevel = client.getTotalLevel();
        this.totalXp = client.getOverallExperience();
        this.consentFlag = config.shareLevels();
    }

    public boolean getConsentFlag() {
        return this.consentFlag;
    }
}
