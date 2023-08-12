package com.diamondgoobird.mod.shader;

import net.minecraft.util.ResourceLocation;

public class Shader {
    private final ResourceLocation location;
    private final String name;
    private final String directory;

    public Shader(String directory, String name) {
        this.name = name;
        this.directory = directory;
        location = new ResourceLocation("shaders/" + directory + "/" + name + ".json");
    }
    public ResourceLocation getLocation() {
        return location;
    }

    public String getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }
}