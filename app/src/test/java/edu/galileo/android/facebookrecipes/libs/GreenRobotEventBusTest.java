package edu.galileo.android.facebookrecipes.libs;

import org.greenrobot.eventbus.EventBus;
import org.junit.Test;
import org.mockito.Mock;

import edu.galileo.android.facebookrecipes.BaseTest;

import static org.mockito.Mockito.verify;

/**
 * Created by avalo.
 */
public class GreenRobotEventBusTest extends BaseTest {
    @Mock
    private EventBus eventBus;

    private GreenRobotEventBus greenRobotEventBus;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        greenRobotEventBus = new GreenRobotEventBus(eventBus);
    }

    @Test
    public void testRegister() throws Exception {
        String test = "test";

        greenRobotEventBus.register(test);

        verify(eventBus).register(test);
    }

    @Test
    public void testUnRegister() throws Exception {
        String test = "test";

        greenRobotEventBus.unregister(test);

        verify(eventBus).unregister(test);
    }

    @Test
    public void testPost() throws Exception {
        String event = "test";

        greenRobotEventBus.post(event);

        verify(eventBus).post(event);
    }
}
