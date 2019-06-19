/*
 * Copyright 2016-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package kotlinx.coroutines.internal

@Suppress("NOTHING_TO_INLINE") // Make sure R8 can remove this file facade
internal actual inline fun <E> arraycopy(
    source: Array<E>,
    srcPos: Int,
    destination: Array<E?>,
    destinationStart: Int,
    length: Int
) {
    System.arraycopy(source, srcPos, destination, destinationStart, length)
}