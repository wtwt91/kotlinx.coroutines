/*
 * Copyright 2016-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

// Need InlineOnly for efficient bytecode on Android
@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package kotlinx.coroutines

import java.util.concurrent.locks.*
import kotlin.internal.InlineOnly

internal interface TimeSource {
    fun currentTimeMillis(): Long
    fun nanoTime(): Long
    fun wrapTask(block: Runnable): Runnable
    fun trackTask()
    fun unTrackTask()
    fun registerTimeLoopThread()
    fun unregisterTimeLoopThread()
    fun parkNanos(blocker: Any, nanos: Long) // should return immediately when nanos <= 0
    fun unpark(thread: Thread)
}

// For tests only
// @JvmField: Don't use JvmField here to enable R8 optimizations via "assumenosideeffects"
internal var timeSource: TimeSource? = null

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun currentTimeMillis(): Long =
    timeSource?.currentTimeMillis() ?: System.currentTimeMillis()

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun nanoTime(): Long =
    timeSource?.nanoTime() ?: System.nanoTime()

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun wrapTask(block: Runnable): Runnable =
    timeSource?.wrapTask(block) ?: block

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun trackTask() {
    timeSource?.trackTask()
}

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun unTrackTask() {
    timeSource?.unTrackTask()
}

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun registerTimeLoopThread() {
    timeSource?.registerTimeLoopThread()
}

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun unregisterTimeLoopThread() {
    timeSource?.unregisterTimeLoopThread()
}

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun parkNanos(blocker: Any, nanos: Long) {
    timeSource?.parkNanos(blocker, nanos) ?: LockSupport.parkNanos(blocker, nanos)
}

@Suppress("NOTHING_TO_INLINE")
@InlineOnly
internal inline fun unpark(thread: Thread) {
    timeSource?.unpark(thread) ?: LockSupport.unpark(thread)
}