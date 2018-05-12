package com.artembashtovyi.mywordlist;

import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by felix on 1/6/18
 */

public class BasePresenterTest {

    @Mock
    public WordRepositoryImpl repository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    public WordRepositoryImpl getRepository() {
        return repository;
    }
}
