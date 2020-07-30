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

package net.runelite.client.plugins.runedex.quests;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.runedex.APIManager;
import net.runelite.client.util.Text;


public class QuestsModel {
    @Inject
    private APIManager manager;

    @Inject
    private Client client;

    private static final List<String> QUEST_HEADERS = ImmutableList.of("Free Quests", "Members' Quests", "Miniquests");

    private EnumMap<QuestContainer, Collection<QuestWidget>> questSet;

    @Subscribe
    public void onScriptPostFired(ScriptPostFired event)
    {
        if (event.getScriptId() != ScriptID.QUESTLIST_PROGRESS_LIST_SHOW)
        {
            return;
        }
        Widget header = client.getWidget(WidgetInfo.QUESTLIST_BOX);
        if (header != null)
        {
            questSet = new EnumMap<>(QuestContainer.class);
            BuildQuestList();
        }
    }

    private void BuildQuestList() {
        final Widget container = client.getWidget(WidgetInfo.QUESTLIST_CONTAINER);
        final Widget freeList = client.getWidget(QuestContainer.FREE_QUESTS.widgetInfo);
        final Widget memberList = client.getWidget(QuestContainer.MEMBER_QUESTS.widgetInfo);
        final Widget miniList = client.getWidget(QuestContainer.MINI_QUESTS.widgetInfo);
        if (container == null || freeList == null || memberList == null || miniList == null)
        {
            return;
        }

        Quests quests = new Quests(
                getQuestList(QuestContainer.FREE_QUESTS),
                getQuestList(QuestContainer.MEMBER_QUESTS),
                getQuestList(QuestContainer.MINI_QUESTS)
        );

        manager.storeEvent("quests", quests);
    }

    private ArrayList<Quest> getQuestList(QuestContainer questContainer) {
        Widget list = client.getWidget(questContainer.widgetInfo);
        Collection<QuestWidget> quests = questSet.get(questContainer);

        if (quests == null) {
            quests = Arrays.stream(list.getDynamicChildren())
                    .filter(w -> !QUEST_HEADERS.contains(w.getText()))
                    .map(w -> new QuestWidget(w, Text.removeTags(w.getText()).toLowerCase()))
                    .collect(Collectors.toList());
            questSet.put(questContainer, quests);
        }

        ArrayList<Quest> build = new ArrayList<>();

        for (QuestWidget questInfo : quests) {
            build.add(new Quest(
                questInfo.title,
                getQuestProgressState(questInfo)
            ));
        }
        return build;
    }

    private String getQuestProgressState(QuestWidget questInfo) {
        final int NOT_STARTED = 0xff0000;
        final int IN_PROGRESS = 0xffff00;
        final int COMPLETED = 0xdc10d;
        int questColor = questInfo.getQuest().getTextColor();

        if (questColor == NOT_STARTED) {
            return "NOT_STARTED";
        }
        if (questColor == IN_PROGRESS) {
            return "IN_PROGRESS";
        }
        if (questColor == COMPLETED) {
            return "COMPLETED";
        }
        return "UNKNOWN";
    }

    @AllArgsConstructor
    @Getter
    private enum QuestContainer {
        FREE_QUESTS(WidgetInfo.QUESTLIST_FREE_CONTAINER),
        MEMBER_QUESTS(WidgetInfo.QUESTLIST_MEMBERS_CONTAINER),
        MINI_QUESTS(WidgetInfo.QUESTLIST_MINIQUEST_CONTAINER);

        private final WidgetInfo widgetInfo;
    }

    @Data
    @AllArgsConstructor
    private static class QuestWidget {
        private Widget quest;
        private String title;
    }
}
