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

package net.runelite.client.plugins.runedex;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup("runedex")
public interface RuneDexPluginConfiguration extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "shareLevels",
            name = "Share levels with RuneDex",
            description = "Enable to share your levels with RuneDex"
    )
    default boolean shareLevels()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "shareQuests",
            name = "Share quests with RuneDex",
            description = "Enable to share your quests with RuneDex"
    )
    default boolean shareQuests()
    {
        return true;
    }

    // Sharing of player wealth data is disabled by default for privacy reasons.
    // This has to be enabled manually if the user wish to send the state of their bank
    // to RuneDex. This must be done if the user wishes to use the RuneDex bank value change notifier.
    @ConfigItem(
            position = 3,
            keyName = "shareBank",
            name = "Share bank with RuneDex",
            description = "Enable to share your bank with RuneDex"
    )
    default boolean shareBank()
    {
        return false;
    }

    @ConfigItem(
            position = 4,
            keyName = "PIN",
            name = "Pin code",
            description = "4-digit pin code"
    )
    default String pin()
    {
        return "";
    }
}
