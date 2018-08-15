package com.artembashtovyi.mywordlist.util;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

/**
 * Created by felix on 5/24/18
 */

public class ColorUtil {

    private static final int RED_COLOR_ID = R.color.colorRedState;
    private static final int YELLOW_COLOR_ID = R.color.colorYellowState;
    private static final int GREEN_COLOR_ID = R.color.colorGreenState;

    private static final int WHITE_COLOR_ID = R.color.colorWhiteState;

    public static int getColor(WordState wordState) {
        if (wordState != null) {
            switch (wordState) {
                case YELLOW:
                    return YELLOW_COLOR_ID;
                case GREEN:
                    return GREEN_COLOR_ID;
                case RED:
                    return RED_COLOR_ID;
            }
        }
        return WHITE_COLOR_ID;
    }
}
