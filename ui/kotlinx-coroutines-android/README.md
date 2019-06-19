# Module kotlinx-coroutines-android

Provides `Dispatchers.Main` context for Android applications.

Read [Guide to UI programming with coroutines](https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md)
for tutorial on this module.

# Optimization

You can optimize the size of the coroutines library in Android projects by statically removing 
debugging and other advanced features using R8 by adding the following rule to your 
`proguard-rules.pro` file:

```
# Statically turn off all debugging facilities
-assumenosideeffects class kotlinx.coroutines.DebugKt {
    boolean getDEBUG() return false;
    boolean getRECOVER_STACK_TRACES() return false;
}

-checkdiscard class kotlinx.coroutines.DebugKt
-checkdiscard class kotlinx.coroutines.internal.StackTraceRecoveryKt

# Use simpler and smaller CommonPool for both Dispatchers.Default and IO
-assumenosideeffects class kotlinx.coroutines.DispatchersKt {
    boolean getDEFAULT_SCHEDULER() return false;
    boolean getIO_SCHEDULER() return false;
}

-checkdiscard class kotlinx.coroutines.DispatchersKt
-checkdiscard class kotlinx.coroutines.scheduling.CoroutinesScheduler

# Remove the custom, fast service loader implementation
-assumevalues class kotlinx.coroutines.internal.MainDispatcherLoader {
    boolean FAST_SERVICE_LOADER_ENABLED return false;
}

-checkdiscard class kotlinx.coroutines.internal.FastServiceLoader

# Disable support for "Missing Main Dispatcher", since we always have Android main dispatcher
-assumevalues class kotlinx.coroutines.internal.MainDispatchersKt {
    boolean SUPPORT_MISSING return false;
}

# Disable internal virtual time source features that are used for tests
-assumenosideeffects class kotlinx.coroutines.TimeSourceKt {
    kotlinx.coroutines.TimeSource getTimeSource() return null;
}
```

Altogether, this gives ~64Kb reduction in the size of non-obfuscated dex file.

<!--

:todo: due to R8 defficiencies the following classes should be removed by are not removed yet:

-whyareyoukeeping class kotlinx.coroutines.CoroutineId
-whyareyoukeeping class kotlinx.coroutines.internal.SystemPropsKt
-whyareyoukeeping class kotlinx.coroutines.internal.LockFreeLinkedListNode$CondAddOp
-whyareyoukeeping class kotlinx.coroutines.internal.MissingMainCoroutineDispatcher

-->

# Package kotlinx.coroutines.android

Provides `Dispatchers.Main` context for Android applications.
