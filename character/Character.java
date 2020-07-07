package net.runelite.client.plugins.runedex.character;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Character {
    private final int attackXp;
    private final int strengthXp;
    private final int defenceXp;
    private final int rangedXp;
    private final int prayerXp;
    private final int magicXp;
    private final int runecraftingXp;
    private final int hitpointsXp;
    private final int agilityXp;
    private final int herbloreXp;
    private final int thievingXp;
    private final int craftingXp;
    private final int fletchingXp;
    private final int slayerXp;
    private final int hunterXp;
    private final int miningXp;
    private final int smithingXp;
    private final int fishingXp;
    private final int cookingXp;
    private final int firemakingXp;
    private final int farmingXp;
    private final long totalXp;
    private final int totalLevel;
    private final int combatLevel;
}
