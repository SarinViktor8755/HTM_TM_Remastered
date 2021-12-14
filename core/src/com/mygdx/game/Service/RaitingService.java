package com.mygdx.game.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static com.badlogic.gdx.math.MathUtils.random;

public class RaitingService {
    static public String generateTokken() { // Генерация токкена
        String token;
        String tokken = "tokken";
        Preferences prefs = Gdx.app.getPreferences("USSD_RAITING");
        if (!prefs.contains(tokken)) token = genirateTokk();
        else token = prefs.getString(tokken);
        prefs.putString(tokken, token);
        prefs.flush();
        //System.out.println(prefs.getString(tokken));
        return token;
    }

    private static String genirateTokk() {
        long longToken = Math.abs(random.nextLong());
        return Long.toString(longToken, 16);
    }


}
