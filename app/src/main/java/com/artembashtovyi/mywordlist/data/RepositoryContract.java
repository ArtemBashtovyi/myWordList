package com.artembashtovyi.mywordlist.data;


import java.util.List;

public interface RepositoryContract<T> {
     List<T> getWords();
}
