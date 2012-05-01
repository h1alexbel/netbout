/**
 * Copyright (c) 2009-2012, Netbout.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of netBout Inc. located at www.netbout.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $25,000 for violation. If you received
 * this code occasionally and without intent to use it, please report this
 * incident to the author by email.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.netbout.inf.ray;

import com.netbout.inf.Cursor;
import com.netbout.inf.Term;
import com.netbout.inf.TermBuilder;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * Matching term.
 *
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
final class MatcherTerm implements Term {

    /**
     * Index map.
     */
    private final transient IndexMap imap;

    /**
     * Name of attribute.
     */
    private final transient String attr;

    /**
     * Value to match.
     */
    private final transient String value;

    /**
     * Public ctor.
     * @param map The index map
     * @param atr Attribute
     * @param val Value of it
     */
    public MatcherTerm(final IndexMap map, final String atr, final String val) {
        this.imap = map;
        this.attr = atr;
        this.value = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cursor shift(final Cursor cursor) {
        Cursor shifted;
        if (cursor.end()) {
            shifted = cursor;
        } else {
            SortedSet<Long> tail = this.imap.index(this.attr)
                .msgs(this.value)
                .tailSet(cursor.msg().number());
            if (tail.size() < 2) {
                shifted = new MemCursor(0L, this.imap);
            } else {
                final Iterator<Long> iterator = tail.iterator();
                iterator.next();
                shifted = new MemCursor(iterator.next(), this.imap);
            }
        }
        return shifted;
    }

}
