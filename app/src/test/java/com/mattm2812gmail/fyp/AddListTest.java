package com.mattm2812gmail.fyp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class AddListTest {
    @Mock
    Context mContext;

    private static final String fakeListName = "test";
    private HashMap<String, HashMap<String, String>> fakeHashMap = new HashMap<>();

    @Test
    public void listAdded(){
        TaskList testList = new TaskList(fakeListName, fakeHashMap);
        String result = testList.getTaskListName();
        assertThat(result, is(fakeListName));
    }
}
