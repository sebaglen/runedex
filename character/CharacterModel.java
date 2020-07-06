package net.runelite.client.plugins.runedex.character;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CharacterModel {
    private int combatLevel;
    private int totalLevel;
    private long totalXp;
}
