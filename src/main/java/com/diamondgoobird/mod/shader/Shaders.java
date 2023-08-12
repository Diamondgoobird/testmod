package com.diamondgoobird.mod.shader;

import java.util.ArrayList;
import java.util.Arrays;

public class Shaders {
    static ArrayList<Shader> a;

    static {
        a = new ArrayList<Shader>(Arrays.asList(new Shader(true, "antialias"), new Shader(true, "art"), new Shader(true, "bits"), new Shader(true, "blobs"), new Shader(true, "blobs2"), new Shader(true, "blur"), new Shader(true, "bumpy"), new Shader(true, "color_convolve"), new Shader(true, "creeper"), new Shader(true, "deconverge"), new Shader(true, "desaturate"), new Shader(true, "entity_outline"), new Shader(true, "flip"), new Shader(true, "fxaa"), new Shader(true, "green"), new Shader(true, "invert"), new Shader(true, "notch"), new Shader(true, "ntsc"), new Shader(true, "outline"), new Shader(true, "pencil"), new Shader(true, "phosphor"), new Shader(true, "scan_pincushion"), new Shader(true, "sobel"), new Shader(true, "spider"), new Shader(true, "wobble"), new Shader(false, "antialias"), new Shader(false, "bits"), new Shader(false, "blit"), new Shader(false, "blobs"), new Shader(false, "blobs2"), new Shader(false, "blur"), new Shader(false, "bumpy"), new Shader(false, "color_convolve"), new Shader(false, "deconverge"), new Shader(false, "downscale"), new Shader(false, "entity_outline"), new Shader(false, "flip"), new Shader(false, "fxaa"), new Shader(false, "invert"), new Shader(false, "notch"), new Shader(false, "ntsc_decode"), new Shader(false, "ntsc_encode"), new Shader(false, "outline"), new Shader(false, "outline_combine"), new Shader(false, "outline_soft"), new Shader(false, "outline_watercolor"), new Shader(false, "overlay"), new Shader(false, "phosphor"), new Shader(false, "scan_pincushion"), new Shader(false, "sobel"), new Shader(false, "spider"), new Shader(false, "wobble")));
    }
    public static boolean contains(String check) {
        for (Shader b : a) {
            if (b.name1.equalsIgnoreCase(check)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isPost(String check) {
        for (Shader b : a) {
            if (b.post1) {
                return true;
            }
        }
        return false;
    }
    public static String[] getShaderNames(boolean post) {
        ArrayList<String> c = new ArrayList<>();
        for (Shader b : a) {
            if (b.post1 == post) {
                c.add(b.name1);
            }
        }
        return c.toArray(new String[0]);
    }
}
