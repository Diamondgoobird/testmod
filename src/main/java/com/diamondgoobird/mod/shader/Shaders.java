package com.diamondgoobird.mod.shader;

import java.util.ArrayList;
import java.util.Arrays;

public class Shaders {
    static ArrayList<Shader> a;

    static {
        a = new ArrayList<Shader>(Arrays.asList(new Shader("post", "antialias"), new Shader("post", "art"), new Shader("post", "bits"), new Shader("post", "blobs"), new Shader("post", "blobs2"), new Shader("post", "blur"), new Shader("post", "bumpy"), new Shader("post", "color_convolve"), new Shader("post", "creeper"), new Shader("post", "deconverge"), new Shader("post", "desaturate"), new Shader("post", "entity_outline"), new Shader("post", "flip"), new Shader("post", "fxaa"), new Shader("post", "green"), new Shader("post", "invert"), new Shader("post", "notch"), new Shader("post", "ntsc"), new Shader("post", "outline"), new Shader("post", "pencil"), new Shader("post", "phosphor"), new Shader("post", "scan_pincushion"), new Shader("post", "sobel"), new Shader("post", "spider"), new Shader("post", "wobble"), new Shader("program", "antialias"), new Shader("program", "bits"), new Shader("program", "blit"), new Shader("program", "blobs"), new Shader("program", "blobs2"), new Shader("program", "blur"), new Shader("program", "bumpy"), new Shader("program", "color_convolve"), new Shader("program", "deconverge"), new Shader("program", "downscale"), new Shader("program", "entity_outline"), new Shader("program", "flip"), new Shader("program", "fxaa"), new Shader("program", "invert"), new Shader("program", "notch"), new Shader("program", "ntsc_decode"), new Shader("program", "ntsc_encode"), new Shader("program", "outline"), new Shader("program", "outline_combine"), new Shader("program", "outline_soft"), new Shader("program", "outline_watercolor"), new Shader("program", "overlay"), new Shader("program", "phosphor"), new Shader("program", "scan_pincushion"), new Shader("program", "sobel"), new Shader("program", "spider"), new Shader("program", "wobble")));
    }

    public static String[] getShaderNames(String directory) {
        ArrayList<String> c = new ArrayList<>();
        for (Shader b : a) {
            if (b.getDirectory().equals(directory)) {
                c.add(b.getName());
            }
        }
        return c.toArray(new String[0]);
    }
}
