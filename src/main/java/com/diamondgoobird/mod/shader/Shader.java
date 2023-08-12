package com.diamondgoobird.mod.shader;

import net.minecraft.util.ResourceLocation;

public class Shader {
    boolean post1;
    String name1;
    public Shader(boolean post, String name) {
        post1 = post;
        name1 = name;
    }
    public ResourceLocation getLocation() {
        if (post1) {
            return new ResourceLocation("shaders/post/" + name1 + ".json");
        }
        return new ResourceLocation("shaders/program/" + name1 + ".json");
    }
}