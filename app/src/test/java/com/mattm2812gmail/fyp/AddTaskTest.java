package com.mattm2812gmail.fyp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class AddTaskTest {
    @Mock
    Context mContext;

    private static final String fakeListName = "test";
    private static final String fakeTask = "task";
    private static final String fakeSubtask = "subtask";
    private static final String fakeDate = "date";

    @Test
    public void listAdded(){
        Task testTask = new Task(fakeListName, fakeDate, fakeSubtask, fakeTask);
        String result = testTask.getTask();
        assertThat(result, is(fakeTask));
    }
}
