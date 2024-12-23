package Utility;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

import java.util.List;

public class StepDetails implements ConcurrentEventListener {
    public static String stepName;
    public static Throwable error;
    public static List<PickleStepTestStep> testSteps;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        testSteps = event.getTestCase().getTestSteps().stream().filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x).toList();
        if (event.getTestStep() instanceof PickleStepTestStep testStep) {
            stepName = testStep.getStep().getKeyword() + " | " + testStep.getStep().getText();
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        error = event.getResult().getError();
    }
}
