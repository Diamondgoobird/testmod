package com.diamondgoobird.mod;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.awt.*;
import java.io.File;

public class TestConfig extends Vigilant {
    public static TestConfig instance = new TestConfig();

    public TestConfig() {
        super(new File("config/test.toml"), "Test");
    }

    @Property(
            type = PropertyType.SWITCH,
            name = "Mod Toggle",
            category = "General",
            description = "Turns certain mod features on and off"
    )
    public boolean enable = true;

    @Property(
            type = PropertyType.TEXT,
            name = "Window Name",
            category = "Game Customization",
            description = "Changes the Minecraft window to a custom name"
    )
    public String windowName = "Lunar Client 1.8.9";

    @Property(
            type = PropertyType.SLIDER,
            name = "Shader Time",
            category = "Shaders",
            description = "How long before switching shaders automatically",
            max = 10
    )
    public int shaderTime = 1;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Shader Type",
            category = "Shaders",
            description = "Which shader set to use for automatic switching",
            options = {"all", "post", "program"}
    )
    public int shaderType = 0;

    @Property(
            type = PropertyType.COLOR,
            name = "Button Color",
            category = "Misc",
            description = "The color used for text on buttons"
    )
    public Color buttonColor = new Color(0, 251, 255);

    @Property(
            type = PropertyType.SELECTOR,
            name = "Color Type",
            category = "Misc",
            description = "Color modifiers to use for text on buttons",
            options = {"static", "rainbow", "random"}
    )
    public int buttonType = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Switch Shaders",
            category = "Shaders",
            description = "Whether or not shaders should randomly"
    )
    public boolean switchShaders = false;

    @Property(
            type = PropertyType.TEXT,
            name = "Discord Bot Token",
            category = "Discord",
            description = "Token to use for typing in discord channels."
    )
    public String discordToken = "";

    @Property(
            type = PropertyType.TEXT,
            name = "Discord Server ID",
            category = "Discord",
            description = "ID for server to type in."
    )
    public String discordServerId = "";

    @Property(
            type = PropertyType.TEXT,
            name = "Discord Channel ID",
            category = "Discord",
            description = "ID for channel to type in."
    )
    public String discordChannelId = "";
}
