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

    @Property(
            type = PropertyType.TEXT,
            name = "AI Model",
            category = "AI",
            description = "AI Model for to use"
    )
    public String aiModelName = "llama3";

    @Property(
            type = PropertyType.PARAGRAPH,
            name = "System Prompt",
            category = "AI",
            description = "System prompt for AI Model to use"
    )
    public String aiSystemPrompt = "Play the role of %player%, a chatter on the hypixel server who acts kind, cheerful, and family friendly.";

    @Property(
            type = PropertyType.TEXT,
            name = "Ollama API",
            category = "AI",
            description = "URL for the Ollama API"
    )
    public String aiOllamaAPI = "http://127.0.0.1:11434";

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Send Messages",
            category = "AI",
            description = "Should rat automatically send ai messages"
    )
    public boolean aiAutoSend = false;

    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Minimum Send Delay",
            category = "AI",
            description = "Minimum delay before autosending in seconds",
            minF = 0.0F,
            maxF = 15.0F
    )
    public float aiMinimumDelay = 0.0f;

    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Maximum Send Delay",
            category = "AI",
            description = "Maximum delay before autosending in seconds",
            minF = 0.0F,
            maxF = 15.0F
    )
    public float aiMaximumDelay = 5.0f;

    @Property(
            type = PropertyType.TEXT,
            name = "Keyword",
            category = "AI",
            description = "Phrase that ai should respond to"
    )
    public String aiKeyword = "";

    @Property(
            type = PropertyType.SWITCH,
            name = "Model Options",
            category = "AI",
            subcategory = "Advanced",
            description = "Enables custom model options"
    )
    public boolean aiOptions = false;

    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Temperature",
            category = "AI",
            description = "Model temperature, increasing makes the model more creative",
            subcategory = "Advanced",
            minF = 0.0F,
            maxF = 1.0F
    )
    public float aiTemperature = 0.9f;

    @Property(
            type = PropertyType.NUMBER,
            name = "Maximum Tokens",
            category = "AI",
            description = "Maximum number of tokens to predict when generating text",
            subcategory = "Advanced"
    )
    public int aiMaximumTokens = 30;

    @Property(
            type = PropertyType.NUMBER,
            name = "Top K",
            category = "AI",
            description = "The number of tokens to consider generating, higher for more diverse answers",
            subcategory = "Advanced"
    )
    public int aiTopK = 80;
}
