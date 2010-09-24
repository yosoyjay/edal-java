/*
 * Copyright (c) 2010 The University of Reading
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University of Reading, nor the names of the
 *    authors or contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package uk.ac.rdg.resc.edal.coverage.domain;

import uk.ac.rdg.resc.edal.coverage.Coverage;

/**
 * <p>A geospatial/temporal domain: defines the set of positions for which a
 * {@link Coverage} is defined.</p>
 * <p>Subclasses must implement a method that returns a value that gives the
 * coordinate reference system to which the positions are referenced.  Such a
 * method is not defined in this top-level interface because subclasses might use
 * very different types of object to define the CRS.  This CRS must match
 * the CRS of the {@link #getExtent() extent}.</p>
 * @param <P> The type of object used to identify positions within this domain
 * @author Jon
 */
public interface Domain<P>
{
    /**
     * Returns true if the given position is contained within this domain.
     */
    public boolean contains(P position);

    /**
     * Returns the extent of this domain.  This is the bounding box that
     * contains all positions within the domain.  Note that there may be
     * positions within the extent that are not considered part of the domain.
     */
    public Extent<P> getExtent();

}
