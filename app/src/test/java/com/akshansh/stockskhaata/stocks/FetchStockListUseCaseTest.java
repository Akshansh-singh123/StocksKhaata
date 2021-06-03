package com.akshansh.stockskhaata.stocks;

import com.akshansh.stockskhaata.common.Constants.EndpointResultStatus;
import com.akshansh.stockskhaata.common.Utils;
import com.akshansh.stockskhaata.common.database.stocks.StockSchema;
import com.akshansh.stockskhaata.common.database.stocks.StockViewModel;
import com.akshansh.stockskhaata.common.database.stocks.StocksDao;
import com.akshansh.stockskhaata.common.database.stocks.StocksDao_Impl;
import com.akshansh.stockskhaata.common.database.stocks.StocksDatabase_Impl;
import com.akshansh.stockskhaata.common.database.stocks.StocksRepository;
import com.akshansh.stockskhaata.networking.NetworkException;
import com.akshansh.stockskhaata.stocks.FetchStockListUseCase.Listener;
import com.akshansh.stockskhaata.stocks.GetStockListEndpoint.Callback;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FetchStockListUseCaseTest {
    private static final ArrayList<StockSchema> STOCKS = Utils.getStockListMocks();
    private static final StockFilterTerm FILTER_TERM = new StockFilterTerm();
    public static final StockViewModel STOCK_VIEW_MODEL = new
            StockViewModel(new StocksRepository(new StocksDao_Impl(new StocksDatabase_Impl())));

    FetchStockListUseCase SUT;
    @Mock GetStockListEndpoint getStockListEndpointMock;
    @Mock Listener listener1Mock;
    @Mock Listener listener2Mock;
    @Captor
    ArgumentCaptor<ArrayList<StockSchema>> ac;

    @Before
    public void setUp() throws Exception {
        SUT = new FetchStockListUseCase(getStockListEndpointMock);
        success();
    }

    //fetchStockList - success - endpointCalled

    @Test
    public void fetchStockList_endpointCalled_correctDataPassed() throws Exception {
        ArgumentCaptor<StockFilterTerm> arg = ArgumentCaptor.forClass(StockFilterTerm.class);
        SUT.fetchStocksList(FILTER_TERM);
        verify(getStockListEndpointMock).getStocksList(
                arg.capture(),any(Callback.class));
        assertEquals(FILTER_TERM,arg.getValue());
    }

    //fetchStockList - success - listenersNotifiedSuccess

    @Test
    public void fetchStockList_success_listenersNotifiedSuccess() throws Exception {
        SUT.registerListener(listener1Mock);
        SUT.registerListener(listener2Mock);
        SUT.fetchStocksList(FILTER_TERM);
        verify(listener1Mock).onStocksListFetchSuccessful(ac.capture());
        verify(listener2Mock).onStocksListFetchSuccessful(ac.capture());
        assertEquals(STOCKS,ac.getAllValues().get(0));
        assertEquals(STOCKS,ac.getAllValues().get(1));
    }

    //fetchStockList - success - unregisteredListenersNotNotified

    @Test
    public void fetchStockList_success_unregisteredListenersNotNotified() throws Exception {
        SUT.registerListener(listener1Mock);
        SUT.registerListener(listener2Mock);
        SUT.unregisterListener(listener2Mock);
        SUT.fetchStocksList(FILTER_TERM);
        verify(listener1Mock).onStocksListFetchSuccessful(ac.capture());
        verifyNoMoreInteractions(listener2Mock);
    }

    //fetchStockList - generalError - listenersNotifiedFailure

    @Test
    public void fetchStockList_generalError_listenersNotifiedFailure() throws Exception {
        generalError();
        ArgumentCaptor<EndpointResultStatus> arg = ArgumentCaptor.forClass(EndpointResultStatus.class);
        SUT.registerListener(listener1Mock);
        SUT.registerListener(listener2Mock);
        SUT.fetchStocksList(FILTER_TERM);
        verify(listener1Mock).onStocksListFetchFailure(arg.capture());
        verify(listener2Mock).onStocksListFetchFailure(arg.capture());
        assertEquals(EndpointResultStatus.GENERAL_ERROR,arg.getAllValues().get(0));
        assertEquals(EndpointResultStatus.GENERAL_ERROR,arg.getAllValues().get(1));
    }

    //fetchStockList - serverError - listenersNotifiedFailure

    @Test
    public void fetchStockList_serverError_listenerNotifiedFailure() throws Exception {
        serverError();
        ArgumentCaptor<EndpointResultStatus> arg = ArgumentCaptor.forClass(EndpointResultStatus.class);
        SUT.registerListener(listener1Mock);
        SUT.registerListener(listener2Mock);
        SUT.fetchStocksList(FILTER_TERM);
        verify(listener1Mock).onStocksListFetchFailure(arg.capture());
        verify(listener2Mock).onStocksListFetchFailure(arg.capture());
        assertEquals(EndpointResultStatus.SERVER_ERROR,arg.getAllValues().get(0));
        assertEquals(EndpointResultStatus.SERVER_ERROR,arg.getAllValues().get(1));
    }

    //fetchStockList - networkError - listenersNotifiedNetworkError


    @Test
    public void fetchStockList_networkError_listenersNotifiedNetworkError() throws Exception {
        networkError();
        SUT.registerListener(listener1Mock);
        SUT.registerListener(listener2Mock);
        SUT.fetchStocksList(FILTER_TERM);
        verify(listener1Mock).onNetworkError();
        verify(listener2Mock).onNetworkError();
    }

    //fetchStockList - invalidFailureReason - runtimeExceptionThrown

    @Test
    public void fetchStockList_invalidFailureReason_runtimeExceptionThrown() throws Exception {
        invalidStatus();
        assertThrows(RuntimeException.class, () -> SUT
                .fetchStocksList(FILTER_TERM));
    }

    private void success() throws NetworkException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback callback = (Callback)invocation.getArgument(1);
                callback.onFetchStockSuccessful(STOCKS);
                return null;
            }
        }).when(getStockListEndpointMock).getStocksList(
                any(StockFilterTerm.class),
                any(Callback.class));
    }

    private void generalError() throws NetworkException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback callback = (Callback)invocation.getArgument(2);
                callback.onFetchStockFailure(EndpointResultStatus.GENERAL_ERROR);
                return null;
            }
        }).when(getStockListEndpointMock).getStocksList(
                any(StockFilterTerm.class),
                any(Callback.class));
    }

    private void serverError() throws NetworkException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback callback = (Callback)invocation.getArgument(2);
                callback.onFetchStockFailure(EndpointResultStatus.SERVER_ERROR);
                return null;
            }
        }).when(getStockListEndpointMock).getStocksList(any(StockFilterTerm.class),
                any(Callback.class));
    }

    private void networkError() throws NetworkException {
        doThrow(NetworkException.class).when(getStockListEndpointMock)
                .getStocksList(any(StockFilterTerm.class),any(Callback.class));
    }

    private void invalidStatus() throws NetworkException {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback callback = (Callback)invocation.getArgument(1);
                callback.onFetchStockFailure(null);
                return null;
            }
        }).when(getStockListEndpointMock).getStocksList(any(StockFilterTerm.class),
                any(Callback.class));
    }
}