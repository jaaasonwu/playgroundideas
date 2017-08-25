package com.playgroundideas.playgroundideas;

import android.content.Context;
import android.graphics.Typeface;

/**
 * This is the class for managing different fonts
 */
class FontManager {
    static final String FONTAWESOME = "fontawesome-webfont.ttf";
    static final String MATERIALICONS = "materialIcons-regular.ttf";

    static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}
