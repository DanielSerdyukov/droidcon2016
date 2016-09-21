package moscow.droidcon.reddit.rx;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * @author Daniel Serdyukov
 */
public class ImmediateSchedulerTestRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaHooks.reset();
                RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());
                RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());
                base.evaluate();
                RxJavaHooks.reset();
            }
        };
    }

}
