/*
 * Copyright (c) 2013 Reading e-Science Centre, University of Reading, UK
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Reading e-Science Centre, University of Reading, UK, nor the names of the
 *    authors or contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.rdg.resc.edal.dataset;

import java.io.IOException;
import java.util.Map;

/**
 * A factory for {@link Dataset} objects.  The intention is that one factory
 * object will be created for each type of data source (e.g. one factory object
 * per file format).  These objects can be stateless (hence thread-safe) singletons
 * and shared between datasets.
 * @param <D> The type of Dataset that this factory creates.
 * @author Jon
 */
public interface DatasetFactory<D extends Dataset>
{
    /**
     * Returns a Dataset object representing the data at the given location.
     * @param location The location of the source data: this may be a file,
     * database connection string or a remote server address.
     * @param parameters Parameters that affect the creation of the dataset.
     * These are specific to the Factory in question.
     * @return 
     */
    public D createDataset(String location, Map<String, Object> parameters) throws IOException;
    
}