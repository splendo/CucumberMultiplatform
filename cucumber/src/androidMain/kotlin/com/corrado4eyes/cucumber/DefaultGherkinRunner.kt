package com.corrado4eyes.cucumber

import io.cucumber.java8.En

actual val EXPECT_VALUE_STRING = "{string}"

actual class GherkinLambda0 (private val lambda: () -> Unit) : () -> Unit, GherkinLambda {
    override fun invoke() {
        lambda()
    }
}

actual class GherkinLambda1 (private val lambda: (String) -> Unit) : (String) -> Unit, GherkinLambda {
    override fun invoke(p0: String) {
        lambda(p0)
    }
}

actual class GherkinLambda2 (private val lambda: (String, String) -> Unit) : (String, String) -> Unit, GherkinLambda {
    override fun invoke(p0: String, p1: String) {
        lambda(p0, p1)
    }
}

//actual class GherkinLambda3 (private val lambda: (String, String, String) -> Unit) : (String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String) {
//        lambda(p0, p1, p2)
//    }
//}
//
//actual class GherkinLambda4 (private val lambda: (String, String, String, String) -> Unit) : (String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String) {
//        lambda(p0, p1, p2, p3)
//    }
//}
//
//actual class GherkinLambda5 (private val lambda: (String, String, String, String, String) -> Unit) : (String, String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String, p4: String) {
//        lambda(p0, p1, p2, p3, p4)
//    }
//}
//
//actual class GherkinLambda6 (private val lambda: (String, String, String, String, String, String) -> Unit) : (String, String, String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String) {
//        lambda(p0, p1, p2, p3, p4, p5)
//    }
//}
//
//actual class GherkinLambda7 (private val lambda: (String, String, String, String, String, String, String) -> Unit) : (String, String, String, String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String) {
//        lambda(p0, p1, p2, p3, p4, p5, p6)
//    }
//}
//
//actual class GherkinLambda8 (private val lambda: (String, String, String, String, String, String, String, String) -> Unit) : (String, String, String, String, String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String) {
//        lambda(p0, p1, p2, p3, p4, p5, p6, p7)
//    }
//}
//
//actual class GherkinLambda9 (private val lambda: (String, String, String, String, String, String, String, String, String) -> Unit) : (String, String, String, String, String, String, String, String, String) -> Unit, GherkinLambda {
//    override fun invoke(p0: String, p1: String, p2: String, p3: String, p4: String, p5: String, p6: String, p7: String, p8: String) {
//        lambda(p0, p1, p2, p3, p4, p5, p6, p7, p8)
//    }
//}

actual fun given(regex: String, lambda: GherkinLambda0) {
    object : En {
        init {
            Given(regex, lambda)
        }
    }
}

actual fun given(regex: String, lambda: GherkinLambda1) {
    object : En {
        init {
            Given(regex, lambda)
        }
    }
}

actual fun given(regex: String, lambda: GherkinLambda2) {
    object : En {
        init {
            Given(regex, lambda)
        }
    }
}

actual fun `when`(regex: String, lambda: GherkinLambda0) {
    object : En {
        init {
            When(regex, lambda)
        }
    }
}

actual fun `when`(regex: String, lambda: GherkinLambda1) {
    object : En {
        init {
            When(regex, lambda)
        }
    }
}

actual fun `when`(regex: String, lambda: GherkinLambda2) {
    object : En {
        init {
            When(regex, lambda)
        }
    }
}

actual fun then(regex: String, lambda: GherkinLambda0) {
    object : En {
        init {
            Then(regex, lambda)
        }
    }
}

actual fun then(regex: String, lambda: GherkinLambda1) {
    object : En {
        init {
            Then(regex, lambda)
        }
    }
}

actual fun then(regex: String, lambda: GherkinLambda2) {
    object : En {
        init {
            Then(regex, lambda)
        }
    }
}
