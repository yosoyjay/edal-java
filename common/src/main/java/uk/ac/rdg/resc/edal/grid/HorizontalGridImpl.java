/*******************************************************************************
 * Copyright (c) 2013 The University of Reading
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
 ******************************************************************************/

package uk.ac.rdg.resc.edal.grid;

import org.geotoolkit.metadata.iso.extent.DefaultGeographicBoundingBox;
import org.geotoolkit.referencing.crs.DefaultGeographicCRS;
import org.opengis.metadata.extent.GeographicBoundingBox;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import uk.ac.rdg.resc.edal.geometry.BoundingBox;
import uk.ac.rdg.resc.edal.geometry.BoundingBoxImpl;
import uk.ac.rdg.resc.edal.position.HorizontalPosition;
import uk.ac.rdg.resc.edal.util.AbstractImmutableArray;
import uk.ac.rdg.resc.edal.util.Array;
import uk.ac.rdg.resc.edal.util.GISUtils;

public class HorizontalGridImpl implements HorizontalGrid {

    protected ReferenceableAxis<Double> xAxis;
    protected ReferenceableAxis<Double> yAxis;
    protected CoordinateReferenceSystem crs;

    protected HorizontalGridImpl() {
        /*
         * No-argument constructor which subclasses can use if they'd prefer to
         * e.g. create the axes from constructor args (see RegularGridImpl for
         * an example of this)
         */
    }

    public HorizontalGridImpl(ReferenceableAxis<Double> xAxis, ReferenceableAxis<Double> yAxis,
            CoordinateReferenceSystem crs) {
        if (xAxis == null || yAxis == null) {
            throw new NullPointerException("Axes cannot be null");
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.crs = crs;
    }

    @Override
    public Array<GridCell2D> getDomainObjects() {
        return new AbstractImmutableArray<GridCell2D>(GridCell2D.class, new int[] { yAxis.size(),
                xAxis.size() }) {
            @Override
            public Class<GridCell2D> getValueClass() {
                return GridCell2D.class;
            }

            @Override
            public GridCell2D get(int... coords) {
                int xIndex = coords[1];
                int yIndex = coords[0];
                return new GridCell2DImpl(coords, new HorizontalPosition(
                        xAxis.getCoordinateValue(xIndex), yAxis.getCoordinateValue(yIndex), crs),
                        new BoundingBoxImpl(xAxis.getCoordinateBounds(xIndex), yAxis
                                .getCoordinateBounds(yIndex), crs), HorizontalGridImpl.this);
            }
        };
    }

    @Override
    public boolean contains(HorizontalPosition position) {
        if (GISUtils.crsMatch(crs, position.getCoordinateReferenceSystem())) {
            return xAxis.contains(position.getX()) && yAxis.contains(position.getY());
        } else {
            HorizontalPosition transformedPos = GISUtils.transformPosition(position, crs);
            return xAxis.contains(transformedPos.getX()) && yAxis.contains(transformedPos.getY());
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBoxImpl(xAxis.getCoordinateExtent().getLow(), yAxis
                .getCoordinateExtent().getLow(), xAxis.getCoordinateExtent().getHigh(), yAxis
                .getCoordinateExtent().getHigh(), crs);
    }

    @Override
    public GeographicBoundingBox getGeographicBoundingBox() {
        if (GISUtils.crsMatch(crs, DefaultGeographicCRS.WGS84)) {
            return new DefaultGeographicBoundingBox(xAxis.getCoordinateExtent().getLow(), xAxis
                    .getCoordinateExtent().getHigh(), yAxis.getCoordinateExtent().getLow(), yAxis
                    .getCoordinateExtent().getHigh());
        } else {
            /*
             * There is no easy transformation here, so we just return a global
             * bounding box
             * 
             * TODO This should be overridden for specific examples
             */
            return new DefaultGeographicBoundingBox(-180, 180, -90, 90);
        }
    }

    @Override
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        return crs;
    }

    @Override
    public ReferenceableAxis<Double> getXAxis() {
        return xAxis;
    }

    @Override
    public ReferenceableAxis<Double> getYAxis() {
        return yAxis;
    }

    @Override
    public long size() {
        return xAxis.size() * yAxis.size();
    }

    @Override
    public int[] findIndexOf(HorizontalPosition position) {
        return new int[] { xAxis.findIndexOf(position.getX()), yAxis.findIndexOf(position.getY()) };
    }
}
