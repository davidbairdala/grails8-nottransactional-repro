# Grails 8 `@NotTransactional` reproducer

Minimal reproduction for: **the @Transactional AST transform ignoring `@NotTransactional`
method opt-outs on class-annotated services** (Grails 8.0.0-M6).

The whole app is one service:

```groovy
@Transactional
class DemoService {
    def wrapped()  { }          // expected: transactional
    @NotTransactional
    def optedOut() { }          // expected: NOT transactional
}
```

## Reproduce (Grails 8.0.0-M6 — this branch)

```
./gradlew checkWrappers        # JDK 21 required
```

The task compiles the service and javap-inspects the class. On 8.0.0-M6 it FAILS:

```
wrapped()  has $tt__ wrapper: true (expected: true)
optedOut() has $tt__ wrapper: true (expected: false — @NotTransactional)
> BUG REPRODUCED: @NotTransactional method 'optedOut' was given a transactional wrapper
```

## Control (Grails 7.2.3 — branch `grails-7.2.3`)

```
git checkout grails-7.2.3
./gradlew checkWrappers        # JDK 17+
```

passes: `optedOut()` gets no wrapper — the opt-out is honoured.
