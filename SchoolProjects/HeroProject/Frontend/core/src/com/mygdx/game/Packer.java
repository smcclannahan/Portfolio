package com.mygdx.game;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class Packer {
    public static void main (String [] arg) throws Exception{
        TexturePacker.process("android/assets/Player/characters/p4/down",
                "android/assets/Player/characters/p4/down_finished",
                "p4_down_animation");
        TexturePacker.process("android/assets/Player/characters/p4/left",
                "android/assets/Player/characters/p4/left_finished",
                "p4_left_animation");
        TexturePacker.process("android/assets/Player/characters/p4/right",
                "android/assets/Player/characters/p4/right_finished",
                "p4_right_animation");
        TexturePacker.process("android/assets/Player/characters/p4/up",
                "android/assets/Player/characters/p4/up_finished",
                "p4_up_animation");
    }
}
