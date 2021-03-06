package com.sksamuel.kotlintest.junit5

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.specs.FunSpec
import org.junit.platform.engine.discovery.DiscoverySelectors.selectClass
import org.junit.platform.testkit.engine.EngineTestKit

class InitErrorEngineKitTest : FunSpec({

  test("a test that fails to initialise should fail the run") {

    System.setProperty("KotlinTestEngineTest", "true")

    val results = EngineTestKit
            .engine("kotlintest")
            .selectors(selectClass(InitErrorSpec::class.java))
            .execute()

    /*
    There should be the following container events:
      - Event [type = STARTED,                 testDescriptor = KotlinTestEngineDescriptor: [engine:kotlintest],                                                            payload = null]
      - Event [type = DYNAMIC_TEST_REGISTERED, testDescriptor = createSpecDescriptor$descriptor$1: [engine:kotlintest]/[spec:com.sksamuel.kotlintest.junit5.InitErrorSpec], payload = null]
      - Event [type = STARTED,                 testDescriptor = createSpecDescriptor$descriptor$1: [engine:kotlintest]/[spec:com.sksamuel.kotlintest.junit5.InitErrorSpec], payload = null]
      - Event [type = FINISHED,                testDescriptor = createSpecDescriptor$descriptor$1: [engine:kotlintest]/[spec:com.sksamuel.kotlintest.junit5.InitErrorSpec], payload = TestExecutionResult [status = FAILED, throwable = java.lang.reflect.InvocationTargetException]]
      - Event [type = FINISHED,                testDescriptor = KotlinTestEngineDescriptor: [engine:kotlintest],                                                            payload = TestExecutionResult [status = FAILED, throwable = java.lang.reflect.InvocationTargetException]]
     */
    results
         .containers()
         .assertStatistics { it
                 .skipped(0)
                 .started(2)
                 .succeeded(0)
                 .aborted(0)
                 .failed(2)
                 .finished(2)
         }
  }
}) {
  override fun afterTest(testCase: TestCase, result: TestResult) {
    super.afterTest(testCase, result)
    System.clearProperty("KotlinTestEngineTest")
  }
}
