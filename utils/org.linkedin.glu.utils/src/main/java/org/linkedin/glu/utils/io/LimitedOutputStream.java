/*
 * Copyright (c) 2012 Yan Pujante
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.linkedin.glu.utils.io;

import org.linkedin.util.lang.MemorySize;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An output stream which writes at most <code>limit</code> bytes
 *
 * @author yan@pongasoft.com
 */
public class LimitedOutputStream extends OutputStream
{
  private final OutputStream _outputStream;
  private final long _limit;
  private long _numberOfBytesWritten = 0;
  private long _totalNumberOfBytes = 0;

  public LimitedOutputStream(OutputStream outputStream, MemorySize limit)
  {
    this(outputStream, limit.getSizeInBytes());
  }

  public LimitedOutputStream(OutputStream outputStream, long limit)
  {
    _outputStream = outputStream;
    _limit = limit;
  }

  public long getNumberOfBytesWritten()
  {
    return _numberOfBytesWritten;
  }

  public long getTotalNumberOfBytes()
  {
    return _totalNumberOfBytes;
  }

  public long getNumberOfBytesSkipped()
  {
    return getTotalNumberOfBytes() - getNumberOfBytesWritten();
  }

  @Override
  public void flush() throws IOException
  {
    _outputStream.flush();
  }

  @Override
  public void close() throws IOException
  {
    _outputStream.close();
  }

  @Override
  public void write(int b) throws IOException
  {
    if(_numberOfBytesWritten < _limit)
    {
      _outputStream.write(b);
      _numberOfBytesWritten++;
    }
    _totalNumberOfBytes++;
  }

  @Override
  public void write(byte[] b) throws IOException
  {
    write(b, 0, b.length);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException
  {
    long numberOfBytesToWrite = Math.min(len, _limit - _numberOfBytesWritten);

    if(numberOfBytesToWrite > 0)
    {
      _outputStream.write(b, off, (int) numberOfBytesToWrite);
      _numberOfBytesWritten += numberOfBytesToWrite;
    }
    
    _totalNumberOfBytes += len;
  }
}
