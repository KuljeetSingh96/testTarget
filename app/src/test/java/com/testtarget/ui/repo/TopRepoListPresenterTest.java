package com.testtarget.ui.repo;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testtarget.network.model.TopWeekly;
import com.testtarget.network.repository.Repository;
import com.testtarget.utils.schedulers.SchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TopRepoListPresenterTest {
    @Mock
    Repository repository;
    @Mock
    TopRepoListViewModel viewModel;

    private TestScheduler testScheduler = new TestScheduler();
    private TestSchedulerProvider testSchedulerProvider;
    private final String LANGUAGE = "java";
    private final String SINCE_TYPE = "weekly";
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
    }


    private TopRepoListPresenter getTopRepoListPresenter()
    {
        viewModel = spy(new TopRepoListViewModel());
        viewModel.errorMessageVisibility=spy(new MutableLiveData<>());
        viewModel.errorMessage =spy(new MutableLiveData<>());
        viewModel.loadingVisibility=spy(new MutableLiveData<>());
        viewModel.repoListData=spy(new MutableLiveData<>());
        viewModel.repoListVisibility = spy(new MutableLiveData<>());
        TopRepoListPresenter topRepoListPresenter =spy(new TopRepoListPresenter(viewModel,repository, testSchedulerProvider));
        return topRepoListPresenter;
    }

    @Test
    public void testGetTopWeeklyRepoWithEmptyResponse() throws Exception {



        TopRepoListPresenter topRepoListPresenter =getTopRepoListPresenter();
        when(repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)).thenReturn(Observable.just(Collections.EMPTY_LIST));

        topRepoListPresenter.getTopWeeklyRepo();
        testScheduler.triggerActions();
            verify(viewModel.errorMessage,times(1)).setValue("No list available.");
       //     verify(viewModel.repoListData,times(0));
        Mockito.verifyZeroInteractions(viewModel.repoListData);
        verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
        verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
        verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());

        }
    @Test
    public void testGetTopWeeklyRepoWithValidResponse() throws Exception {
        List<TopWeekly> weeklyList = getValidList();

        TopRepoListPresenter topRepoListPresenter =getTopRepoListPresenter();
        when(repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)).thenReturn(Observable.just(weeklyList));

        topRepoListPresenter.getTopWeeklyRepo();
        testScheduler.triggerActions();
        verify(viewModel.errorMessage,never()).setValue("No list available.");
        //     verify(viewModel.repoListData,times(0));
        verify(viewModel.repoListData,times(1)).postValue(weeklyList);
        verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
        verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
        verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());
    }

    @Test
    public void testGetTopWeeklyRepoWithNetworkErrorResponse() throws Exception {
        TopRepoListPresenter topRepoListPresenter =getTopRepoListPresenter();
        when(repository.getTopWeeklyRepo(LANGUAGE, SINCE_TYPE)).thenReturn(Observable.error(new Throwable()));

        topRepoListPresenter.getTopWeeklyRepo();
        testScheduler.triggerActions();
        Mockito.verifyZeroInteractions(viewModel.repoListData);
        //     verify(viewModel.repoListData,times(0));

        verify(viewModel.errorMessage,times(1)).setValue("Network error. Please try again later.");
        verify(viewModel.errorMessageVisibility,times(2)).setValue(anyInt());
        verify(viewModel.repoListVisibility,times(2)).setValue(anyInt());
        verify(viewModel.loadingVisibility,times(2)).setValue(anyInt());
    }


    private List<TopWeekly> getValidList()
    {
        String RESPONSE_STRING ="[{\n" +
                "\t\"username\": \"Microsoft\",\n" +
                "\t\"name\": \"Microsoft\",\n" +
                "\t\"url\": \"https://github.com/Microsoft\",\n" +
                "\t\"avatar\": \"https://avatars2.githubusercontent.com/u/6154722?s=96&v=4\",\n" +
                "\t\"repo\": {\n" +
                "\t\t\"name\": \"malmo\",\n" +
                "\t\t\"description\": \"Project Malmo is a platform for Artificial Intelligence experimentation and research built on top of Minecraft. We aim to inspire a new generation of research into challenging new problems presented by this unique environment. --- For installation instructions, scroll down to *Getting Started* below, or visit the project page for more information:\",\n" +
                "\t\t\"url\": \"https://github.com/Microsoft/malmo\"\n" +
                "\t}\n" +
                "}, {\n" +
                "\t\"username\": \"google\",\n" +
                "\t\"name\": \"Google\",\n" +
                "\t\"url\": \"https://github.com/google\",\n" +
                "\t\"avatar\": \"https://avatars0.githubusercontent.com/u/1342004?s=96&v=4\",\n" +
                "\t\"repo\": {\n" +
                "\t\t\"name\": \"guava\",\n" +
                "\t\t\"description\": \"Google core libraries for Java\",\n" +
                "\t\t\"url\": \"https://github.com/google/guava\"\n" +
                "\t}\n" +
                "}, {\n" +
                "\t\"username\": \"flutter\",\n" +
                "\t\"name\": \"Flutter\",\n" +
                "\t\"url\": \"https://github.com/flutter\",\n" +
                "\t\"avatar\": \"https://avatars1.githubusercontent.com/u/14101776?s=96&v=4\",\n" +
                "\t\"repo\": {\n" +
                "\t\t\"name\": \"flutter-intellij\",\n" +
                "\t\t\"description\": \"Flutter makes it easy and fast to build beautiful mobile apps.\",\n" +
                "\t\t\"url\": \"https://github.com/flutter/flutter-intellij\"\n" +
                "\t}\n" +
                "}, {\n" +
                "\t\"username\": \"facebook\",\n" +
                "\t\"name\": \"Facebook\",\n" +
                "\t\"url\": \"https://github.com/facebook\",\n" +
                "\t\"avatar\": \"https://avatars2.githubusercontent.com/u/69631?s=96&v=4\",\n" +
                "\t\"repo\": {\n" +
                "\t\t\"name\": \"fresco\",\n" +
                "\t\t\"description\": \"An Android library for managing images and the memory they use.\",\n" +
                "\t\t\"url\": \"https://github.com/facebook/fresco\"\n" +
                "\t}\n" +
                "}]";
        Gson gson = new Gson();
        List<TopWeekly> apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<List<TopWeekly>>() {
                }.getType());
        return apiResponse;
    }



    public class TestSchedulerProvider extends SchedulerProvider {

        private final TestScheduler testScheduler1;

        public TestSchedulerProvider(TestScheduler testScheduler) {
            this.testScheduler1 = testScheduler;
        }

        @Override
        public Scheduler ui() {
            return testScheduler1;
        }

        @Override
        public Scheduler computation() {
            return testScheduler1;
        }

        @Override
        public Scheduler io() {
            return testScheduler1;
        }

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme