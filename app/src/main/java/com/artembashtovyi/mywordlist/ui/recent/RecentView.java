package com.artembashtovyi.mywordlist.ui.recent;

import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

/**
 * Created by felix on 12/28/17
 *
 */

public interface RecentView {

   void showWords(List<Word> words);
}
